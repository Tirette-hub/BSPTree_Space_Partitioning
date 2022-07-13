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

        segs = null;
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
        if (segs != null) {
            BSPTree<Segment> t = createTree(segs, h);
            model.setBSPTree(t);

            //Paint the solution
            double paintingCanvasWidth = fxCanvas.getWidth(); //100%

            double[] POVPosition = {
                    POVData[0],
                    POVData[1]
            };
            // alpha                    FOV Direction
            double FOV = POVData[2], FOVDirection = -(POVData[3] - 90);

        /*System.out.println("parameters:\n" +
                "screen width: " + paintingCanvasWidth +
                "\nPOV: (" + POVPosition[0] + "; " + POVPosition[1] + ")" +
                "\ndirection: " + FOVDirection +
                "\nFOV: " + FOV);*/

            //creating segment for direction line
            double dx = Math.cos(Math.toRadians(FOVDirection));
            double dy = Math.sin(Math.toRadians(FOVDirection));
            Segment directionLine = new Segment(POVPosition[0], POVPosition[1], POVPosition[0] + dx, POVPosition[1] + dy);

            //get parameters
            double x1, x2, y = fxCanvas.getHeight() / 2.0;
            double[] abc = Segment.getCutlineParameters(directionLine.getFrom(), directionLine.getTo());
            double a = abc[0], b = abc[1], c = abc[2];
            double angle1, angle2;

            //System.out.println("painter's algorithm");
            Segment[] segsInOrder = Segment.paintersAlgorithm(t, POVPosition).toArray(new Segment[0]);

            GraphicsContext gc = fxCanvas.getGraphicsContext2D();

            //System.out.println("paint segs");
            for (Segment s : segsInOrder) {
                //scan convert segments
                Point2D pt1 = s.getFrom();
                Point2D pt2 = s.getTo();

                Segment v1, v2;
                v1 = new Segment(POVPosition[0], POVPosition[1], pt1.getX(), pt1.getY());
                v2 = new Segment(POVPosition[0], POVPosition[1], pt2.getX(), pt2.getY());

                angle1 = Segment.getAngle(directionLine, pt1);
                angle2 = Segment.getAngle(directionLine, pt2);

                //System.out.println("angle1:" + angle1);
                //System.out.println("angle2:" + angle2);

                //recalculate the angle because previous method does not take care of front or back
                //System.out.println("a: " + a + ", b: " + b + ", c: " + c);
                abc = Segment.getCutlineParameters(directionLine.getPerp().getFrom(), directionLine.getPerp().getTo());
                //System.out.println("ap: " + abc[0] + ", bp: " + abc[1] + ", cp: " + abc[2]);
                //System.out.println("pt2: (" + pt2[0] + ", " + pt2[1] + ")");

                if (Math.abs(angle1) < TestMain.epsilon) {
                    //v1 on the direction line
                    if (directionLine.getFactor(v1) < 0)
                        //v1 in the back
                        angle1 = 180;
                } else {
                    if (directionLine.getY() < 0 || (Math.abs(directionLine.getY()) < TestMain.epsilon && directionLine.getX() < 0)) {
                        if (abc[0] * pt1.getX() + abc[1] * pt1.getY() + abc[2] > 0)
                            //back is part of h+ and pt1 is in it
                            angle1 = 180 - angle1;
                    } else {
                        if (abc[0] * pt1.getX() + abc[1] * pt1.getY() + abc[2] < 0)
                            //back is then part of h- and pt1 is in it
                            angle1 = 180 - angle1;
                    }

                    //otherwise, nothing changed
                }

                //same for angle2
                if (Math.abs(angle2) < TestMain.epsilon) {
                    //v1 on the direction line
                    if (directionLine.getFactor(v2) < 0) {
                        //v1 in the back
                        angle2 = 180;
                    }
                } else {
                    //System.out.println("direction line dy " + directionLine.getY());
                    if (directionLine.getY() < 0 || (Math.abs(directionLine.getY()) < TestMain.epsilon && directionLine.getX() < 0)) {
                        if (abc[0] * pt2.getX() + abc[1] * pt2.getY() + abc[2] > 0) {
                            //back is part of h+ and pt1 is in it
                            angle2 = 180 - angle2;
                            //System.out.println("angle2 in h+");
                        }
                    } else {
                        if (abc[0] * pt2.getX() + abc[1] * pt2.getY() + abc[2] < 0) {
                            //back is then part of h- and pt1 is in it
                            angle2 = 180 - angle2;
                            //System.out.println("angle2 in h-");
                        }
                    }
                    //otherwise nothing changed
                }

                //System.out.println("angle1:" + angle1);
                //System.out.println("angle2:" + angle2);

                //do not pay attention to segments out of view
                if (FOV <= 180) {
                    if (angle1 > 90 && angle2 > 90)
                        continue;
                } else {
                    if (angle1 >= FOV / 2 && angle2 >= FOV / 2)
                        continue;
                }

                x1 = Segment.getProjection(pt1, a, b, c, angle1, FOV, paintingCanvasWidth);

                x2 = Segment.getProjection(pt2, a, b, c, angle2, FOV, paintingCanvasWidth);

            /*System.out.println(
                    "painting line: (" +
                            x1 + "," + y + "," +
                            x2 + "," + y + "), Color = " +
                            s.getEColor().toString()
            );*/

                //check if line not visible because on the eye guideline
                if (Math.abs(x2 - x1) < TestMain.epsilon)
                    continue;

                //check if line not visible because outside of eye FOV

                if ((x1 < 0 && x2 < 0) || (x1 > paintingCanvasWidth && x2 > paintingCanvasWidth))
                    continue;

                //correct left and right padding
                gc.setStroke(s.getEColor().getColor());
                gc.setLineWidth(10);
                //get only the visible part of the segment
            /*if (x1 < 0)
                x1 = 0;
            if (x2 > paintingCanvasWidth)
                x2 = paintingCanvasWidth;*/

                //paint the segment
                gc.strokeLine(x1, y, x2, y);

                //System.out.println("line painted");

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
                Segment[] shuffledS = S.clone();
                List<Segment> newS = Arrays.asList(shuffledS);
                Collections.shuffle(newS);

                tree = Segment.makeBasicTree(shuffledS, null, false);
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
