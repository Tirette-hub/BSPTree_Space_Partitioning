package GUI.Controller;

import DataStructure.Segment;
import GUI.Model.SceneModel;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Scene object that can be cleared and where it is possible to set the Point Of View (eye).
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class SceneController {
    private SceneModel model;
    private boolean POVenabled = false;

    //view data
    @FXML
    private Canvas fxCanvas;

    @FXML
    private Button fxClearButton, fxPOVButton;

    @FXML
    private ImageView eyeButtonImage;

    //view callbacks

    /**
     * Callback called by the fxml view in order to clear the canvas when the "Clear" button is pushed.
     */
    @FXML
    public void onClear(){
        //clear view
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();
        gc.clearRect(0,0, fxCanvas.getWidth(), fxCanvas.getHeight());

        //clear model
        model.setIsDrawn(false);
        model.setPOVPosition(null, null);
        model.setData(null);
    }

    /**
     * Callback called by the fxml view in order to be able to place the Point Of View (Eye) on the canvas when the "eye" button is pushed.
     */
    @FXML
    public void onPOV(){
        //allow the possibility to draw an eye
        POVenabled = !POVenabled;

        //delete the previous POV (eye)
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        //clear everything and redraw all
        gc.clearRect(0,0, fxCanvas.getWidth(), fxCanvas.getHeight());
        drawScene(model.getData());
    }

    /**
     * Callback called by the fxml view in order to place the Point Of View (Eye) on the canvas at the clicked position.
     * @param event
     *      Event created on click.
     */
    @FXML
    public void onClick(MouseEvent event){
        if (POVenabled){
            GraphicsContext gc = fxCanvas.getGraphicsContext2D();

            //clear everything and redraw all
            gc.clearRect(0,0, fxCanvas.getWidth(), fxCanvas.getHeight());
            drawScene(model.getData());

            drawEye(eyeButtonImage.getImage(), event.getSceneX(), event.getSceneY());
            POVenabled = false;
        }
        event.consume();
    }

    /**
     * Callback called by the fxml view in order to be able to move the Point Of View (Eye) in the canvas to see where it could be placed.
     * @param event
     *      Event created on mouse moving.
     */
    @FXML
    public void onMove(MouseEvent event){
        if (POVenabled){
            GraphicsContext gc = fxCanvas.getGraphicsContext2D();

            //clear everything and redraw all
            gc.clearRect(0,0, fxCanvas.getWidth(), fxCanvas.getHeight());
            drawScene(model.getData());

            drawEye(eyeButtonImage.getImage(), event.getSceneX(), event.getSceneY());
        }
        event.consume();
    }

    //setters

    //getters
    /**
     * Check if something has been drawn on the canvas.
     * @return
     *      If the canvas has something drawn on it.
     */
    public boolean isDrawn(){
        return model.isDrawn();
    }

    /**
     * Get the Point of View (eye) position in the scene.
     * @return
     *      Point Of View (eye) position.
     */
    public double[] getPOVPosition(){
        return model.getPOVPosition();
    }

    /**
     * Get the scene data.
     * @return
     *      List of Segment objects representing the scene.
     */
    public Segment[] getData(){
        return model.getData();
    }

    //drawing segments into the Canvas

    /**
     * Draw the scene on the Canvas.
     * @param scene
     *      Scene to draw.
     */
    public void drawScene(Segment[] scene){
        if (model.isDrawn())
            this.onClear();

        //get the drawable object from the canvas
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        //draw
        for (Segment s : scene){
            int [] coords = s.get(); //x1,y1,x2,y2
            Color color = s.getEColor().getColor();

            //draw the segment on the canvas with its color as set up in the scene file
            gc.setStroke(color);
            gc.strokeLine(coords[0], coords[1], coords[2], coords[3]);
        }

        model.setIsDrawn(true);
    }

    /**
     * Draw an eye on the canvas.
     * @param eye
     *      Eye image resource URL.
     * @param x
     *      X coordinate.
     * @param y
     *      Y coordinate.
     */
    public void drawEye(Image eye, double x, double y){
        model.setPOVPosition(x,y);

        //get the drawable object from the canvas
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        //draw
        gc.drawImage(eye, x, y);
    }

    //constructor called by the fxml file
    /**
     * Method called by the fxml view to create the controller.
     */
    public void initialize(){
        //create model
        model = new SceneModel();

        //link Model with view

        //link controller to view - configurations

        //other controller initialisation
        eyeButtonImage.setImage(new Image(getClass().getResource("/img/eye.png").toString()));
    }
}
