package GUI.Controller;

import DataStructure.BSPTree;
import DataStructure.EHeuristic;
import DataStructure.Segment;
import GUI.Model.PaintingModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Canvas where is drawn the solution using the Painter's algorithm.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class PaintingController {
    private PaintingModel model;
    private EventHandler<ActionEvent> dataCallback;

    //variable used to build the BP Tree
    private EHeuristic h;
    private Segment[] segs;
    private double[] POVPosition;

    //view data
    @FXML
    private Canvas fxCanvas;

    @FXML
    private Button fxPaintButton, fxClearButton;

    //view callbacks


    /*
     *
     *
     *
     * TBC
     *
     *
     *
     * */

    /**
     * Callback called by the fxml view in order to paint the solution on the canvas.
     * @param event
     *      None
     */
    @FXML
    public void onPaint(ActionEvent event){
        //clear view if already painted
        if (model.isPainted())
            onClear();

        dataCallback.handle(event);

        //get segments and eye position and heuristic
        System.out.println("get data:");
        System.out.println(h);
        System.out.println(segs);
        /*for (Segment seg: segs) {
            System.out.println(seg.toString());
        }*/
        for (double coord: POVPosition){
            System.out.println(Double.toString(coord));
        }

        //make the BSP Tree
        /*BSPTree t = makeTree(segs, h, POVPosition);
        model.setBSPTree(t);*/

        //Paint the solution

        //set model
        model.setIsPainted(true);

        event.consume();
    }

    /**
     * Callback called by the fxml view in order to clean the canvas.
     */
    @FXML
    public void onClear(){
        //clear view
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();
        gc.clearRect(0,0, fxCanvas.getWidth(), fxCanvas.getHeight());

        //clear model
        model.setIsPainted(false);
    }

    //constructor called by the fxml file
    /**
     * Method called by the fxml view to create the controller.
     */
    public void initialize(){
        //create model
        model = new PaintingModel();

        //link Model with view

        //link controller to view - configurations

        //other controller initialisation
    }

    /**
     * Create a BSP Tree from data.
     * @param S
     *      Scene to load into the tree.
     * @return
     *      The BSP tree created.
     */
    private BSPTree<Segment> createTree(Segment[] S, EHeuristic h, double[] POVPosition){
        switch(h){
            case H1:
                return Segment.makeBasicTree(S);
            case H2:
                //shuffle S
                List<Segment> newS = Arrays.asList(S);
                Collections.shuffle(newS);
                return Segment.makeBasicTree(newS.toArray(new Segment[0]));
            case H3:
                //change
                return new BSPTree<Segment>();
            default:
                return null;
        }
    }

    //setters
    /**
     * Set the Callback that will be used to get needed data to build the BSP Tree.
     * @param callback
     *      Callback.
     */
    public void setDataCallback(EventHandler<ActionEvent> callback){
        dataCallback = callback;
    }

    /**
     * Set the BSP Tree builder parameters.
     * @param h
     *      Heuristic choice.
     * @param segs
     *      List of segments composing the scene.
     * @param pos
     *      Point Of View (Eye) position.
     */
    public void setData(EHeuristic h, Segment[] segs, double[] pos){
        this.h = h;
        this.segs = segs;
        POVPosition = pos;
    }

    //getters
}
