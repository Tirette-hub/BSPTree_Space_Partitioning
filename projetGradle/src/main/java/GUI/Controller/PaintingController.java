package GUI.Controller;

import Console.TestMain;
import DataStructure.*;
import GUI.Model.PaintingModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import javax.management.InvalidAttributeValueException;
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
    private Eye POVData;
    private boolean hasChanged;

    //view data
    @FXML
    private Canvas fxCanvas;

    @FXML
    private Pane fxPane;

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
    public void onPaint(ActionEvent event) {
        //System.out.println("painting size: (" + fxCanvas.getWidth() + ";" + fxCanvas.getHeight() + ")");
        //clear view if already painted
        if (model.isPainted())
            onClear();

        //get segments and eye position and heuristic

        segs = null;
        dataCallback.handle(event);

        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        //make the BSP Tree
        if (segs != null) {
            SegmentBSPTree t = createTree(segs, h);
            model.setBSPTree(t);

            Segment[] segmentsToPaint = SegmentBSPTree.paintersAlgorithm(t, POVData).toArray(new Segment[0]);

            double angle1, angle2, x1, x2;
            double paintingCanvasWidth = fxCanvas.getWidth(); //100%
            double[] abc = Segment.getCutlineParameters(POVData.getDirectionLine().getFrom(), POVData.getDirectionLine().getTo());
            double a = abc[0], b = abc[1], c = abc[2];

            for (Segment seg: segmentsToPaint){
                try {
                    angle1 = Segment.getAngle(POVData.getDirectionLine(), seg.getFrom());
                    angle2 = Segment.getAngle(POVData.getDirectionLine(), seg.getTo());

                    x1 = Segment.getProjection(seg.getFrom(), a, b, c, angle1, POVData.getAngle(), paintingCanvasWidth);

                    x2 = Segment.getProjection(seg.getTo(), a, b, c, angle2, POVData.getAngle(), paintingCanvasWidth);

                    //check if line not visible because on the eye guideline
                    if (Math.abs(x2 - x1) < 1e-4)
                        continue;

                    //check if line not visible because outside of eye FOV

                    if ((x1 < 0 && x2 < 0) || (x1 > paintingCanvasWidth && x2 > paintingCanvasWidth))
                        continue;

                    //correct left and right padding
                    gc.setStroke(seg.getEColor().getColor());
                    gc.setLineWidth(10);

                    //paint the segment
                    if (POVData.getDirection() > 180)
                        gc.strokeLine(x2, fxCanvas.getHeight() / 2.0, x1, fxCanvas.getHeight() / 2.0);
                    else
                        gc.strokeLine(x1, fxCanvas.getHeight() / 2.0, x2, fxCanvas.getHeight() / 2.0);
                }catch (InvalidAttributeValueException ignore){

                }
            }

            //set model
            model.setIsPainted(true);
        }

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
    public void setData(EHeuristic h, Segment[] segs, Eye POVData){
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
    public SegmentBSPTree createTree(Segment[] S, EHeuristic h){
        SegmentBSPTree tree;
        switch(h){
            case H1:
                //shuffle S
                Segment[] shuffledS = S.clone();
                List<Segment> newS = Arrays.asList(shuffledS);
                Collections.shuffle(newS);

                tree = SegmentBSPTree.makeTree(shuffledS, null, false);
                break;
            case H2:
                tree = SegmentBSPTree.makeTree(S, null, false);
                break;
            case H3:
                //change
                tree = SegmentBSPTree.makeTree(S, null, true);
                break;
            default:
                tree = null;
        }
        return tree;
    }
}
