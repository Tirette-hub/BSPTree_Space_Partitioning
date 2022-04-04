package GUI.Controller;

import DataStructure.BSPTree;
import DataStructure.EHeuristic;
import DataStructure.Segment;
import GUI.Model.PaintingModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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

    //variable used to build the BSP Tree
    private EHeuristic h;
    private Segment[] segs;
    private double[] POVData;

    //view data
    @FXML
    private Canvas fxCanvas;

    @FXML
    private Pane fxPane;

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
     *      Null
     */
    @FXML
    public void onPaint(ActionEvent event){
        //System.out.println("painting size: (" + fxCanvas.getWidth() + ";" + fxCanvas.getHeight() + ")");
        //clear view if already painted
        if (model.isPainted())
            onClear();

        //get segments and eye position and heuristic

        dataCallback.handle(event);
        /*System.out.println("get data:");
        System.out.println(h);
        /*for (Segment seg: segs) {
            System.out.println(seg.toString());
        }
        for (double coord: POVPosition){
            System.out.println(Double.toString(coord));
        }*/

        //make the BSP Tree
        //System.out.println("create tree");
        BSPTree<Segment> t = createTree(segs, h);
        model.setBSPTree(t);

        //Paint the solution

        double padding = 20;
        double paintingCanvasWidth = fxCanvas.getWidth() - 2*padding; //100%

        double[] POVPosition = {
                POVData[0],
                POVData[1]
        };
        // alpha                    FOV Direction
        double FOV = POVData[2], FOVDirection = POVData[3]-90;

        //creating segment for direction line
        double dx = Math.cos(Math.toRadians(FOVDirection));
        double dy = Math.sin(Math.toRadians(FOVDirection));
        Segment directionLine = new Segment(POVPosition[0], POVPosition[1], POVPosition[0] + dx, POVPosition[1] + dy);

        //get parameters
        double x1, x2, y = fxCanvas.getHeight()/2.0;
        double[] abc = Segment.getCutlineParameters(directionLine.get());
        double a = abc[0], b = abc[1], c = abc[2];
        double angle;

        //System.out.println("painter's algorithm");
        Segment[] segsInOrder = Segment.paintersAlgorithm(t, POVPosition).toArray(new Segment[0]);

        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        //System.out.println("paint segs");
        for (Segment s : segsInOrder){
            //scan convert segments
            double[] pt1 = s.getFrom();
            double[] pt2 = s.getTo();

            angle = Segment.getAngle(directionLine, pt1);
            //System.out.println("angle1: " + angle);

            if (Math.abs(a*pt1[0]+b*pt1[0]+c) < 1e-4)
                x1 = paintingCanvasWidth/2 + padding;
            else if (a*pt1[0]+b*pt1[0]+c < -1e-4) {
                //pt1 to the left
                if ((FOVDirection > 0 && FOVDirection < 90) || (FOVDirection > 180 && FOVDirection < 270))
                    x1 = paintingCanvasWidth/2 - angle*paintingCanvasWidth/FOV;
                //pt1 to the right
                else
                    x1 = paintingCanvasWidth/2 + angle*paintingCanvasWidth/FOV;
            }
            else { //if (a*pt1[0]+b*pt1[0]+c > 1e-4)
                //pt1 to the right
                if ((FOVDirection > 0 && FOVDirection < 90) || (FOVDirection > 180 && FOVDirection < 270))
                    x1 = paintingCanvasWidth/2 + angle*paintingCanvasWidth/FOV;
                //pt1 to the right
                else
                    x1 = paintingCanvasWidth/2 - angle*paintingCanvasWidth/FOV;
            }

            angle = Segment.getAngle(directionLine, pt2);
            //System.out.println("angle2: " + angle);

            if (Math.abs(a*pt2[0]+b*pt2[1]+c) < 1e-4)
                x2 = paintingCanvasWidth/2 + padding;
            else if (a*pt2[0]+b*pt2[1]+c < -1e-4){
                //pt2 to the left
                if ((FOVDirection > 0 && FOVDirection < 90) || (FOVDirection > 180 && FOVDirection < 270))
                    x2 = paintingCanvasWidth/2 - angle*paintingCanvasWidth/FOV;
                    //pt2 to the right
                else
                    x2 = paintingCanvasWidth/2 + angle*paintingCanvasWidth/FOV;
            }
            else { //if (a*pt2[0]+b*pt2[0]+c > 1e-4)
                //pt2 to the right
                if ((FOVDirection > 0 && FOVDirection < 90) || (FOVDirection > 180 && FOVDirection < 270))
                    x2 = paintingCanvasWidth/2 + angle*paintingCanvasWidth/FOV;
                    //pt2 to the right
                else
                    x2 = paintingCanvasWidth/2 - angle*paintingCanvasWidth/FOV;
            }

            /*System.out.println(
                    "painting line: (" +
                            x1 + "," + y + "," +
                            x2 + "," + y + ")"
            );*/
            //check if line not visible
            if ((x1 > padding && x2 > padding) || (x1 < paintingCanvasWidth + padding && x2 < paintingCanvasWidth + padding)) {
                //correct left and right padding
                gc.setStroke(s.getEColor().getColor());
                gc.setLineWidth(10);
                if (x1 < padding)
                    x1 = padding;
                if (x2 < paintingCanvasWidth + padding)
                    x2 = paintingCanvasWidth + padding;
                gc.strokeLine(x1, y, x2, y);
            }
        }

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

    //constructor

    /**
     * Method called by the fxml view to create the controller.
     */
    public void initialize(){
        //create model
        model = new PaintingModel();

        //link Model with view

        //link controller to view - configurations

        //other controller initialisation
        fxPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                fxCanvas.setWidth(fxPane.getWidth());
            }
        });
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
     * @param POVData
     *      Coordinates of the Point Of View, its direction angle and FOV
     */
    public void setData(EHeuristic h, Segment[] segs, double[] POVData){
        this.h = h;
        this.segs = segs;
        this.POVData = POVData;
    }

    //getters

    /**
     * Create a BSP Tree from data.
     * @param S
     *      Scene to load into the tree.
     * @param h
     *      EHeuristic enum of the chosen heuristic.
     * @return
     *      The BSP tree created.
     */
    public BSPTree<Segment> createTree(Segment[] S, EHeuristic h){
        BSPTree<Segment> tree;
        switch(h){
            case H1:
                //shuffle S
                List<Segment> newS = Arrays.asList(S);
                Collections.shuffle(newS);
                tree = Segment.makeBasicTree(newS.toArray(new Segment[0]), null, false);
                break;
            case H2:
                tree = Segment.makeBasicTree(S, null, false);
                break;
            case H3:
                //change
                tree = Segment.makeFreeSplitTree(S, null);
                break;
            default:
                tree = null;
        }
        return tree;
    }
}
