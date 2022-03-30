package GUI.Controller;

import DataStructure.Segment;
import GUI.Model.SceneModel;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.text.NumberFormat;

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
    private boolean POVEnabled = false;

    //view data
    @FXML
    private Canvas fxCanvas;

    @FXML
    private Button fxClearButton, fxPOVButton;

    @FXML
    private ImageView eyeButtonImage;

    @FXML
    private Slider fxFOVSlider, fxFOVAngle;

    @FXML
    private Spinner<Double> fxFOVSpinner, fxAngleSpinner;

    @FXML
    private CheckBox fxChecker;

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
        if (model.getPOVPosition() != null)
            model.setPOVPosition(null, null);

        //allow the possibility to draw an eye or not if clicked again
        POVEnabled = !POVEnabled;

        //redraw scene but not the eye and its parameters
        drawScene(model.getData());
    }

    /**
     * Callback called by the fxml view in order to place the Point Of View (Eye) on the canvas at the clicked position.
     * @param event
     *      Event created on click.
     */
    @FXML
    public void onClick(MouseEvent event){
        if (POVEnabled){
            //set POVposition
            model.setPOVPosition(event.getSceneX()-4, event.getSceneY()-30);

            //update view
            update();

            //disable the flag that allows the eye to track the mouse into the scene
            POVEnabled = false;
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
        if (POVEnabled){
            //set POVposition
            model.setPOVPosition(event.getSceneX()-4, event.getSceneY()-30);
            //update view
            update();
        }
        event.consume();
    }

    /**
     * Callback calles by the fxml view in order to be able to see or hide the eye direction and FOV
     */
    @FXML
    public void onCheck(){
        model.setFOVVisible(fxChecker.isSelected());

        update();
    }

    //setters

    /**
     * Set scene data.
     * @param data
     *      Array of Segments representing the scene.
     */
    public void setData(Segment[] data){
        model.setData(data);
    }

    /**
     * Resize the scene view size.
     * @param width
     *      New width.
     * @param height
     *      New height.
     */
    public void setSceneSize(int width, int height){
        fxCanvas.setWidth(width);
        fxCanvas.setHeight(height);
        VBox vbox = (VBox) fxCanvas.getParent();
        vbox.setPrefSize(width, height);
    }

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
     *      Array of Segment objects representing the scene.
     */
    public Segment[] getData(){
        return model.getData();
    }

    //view methods

    /**
     * Draw the scene on the Canvas.
     * @param scene
     *      Arry of Segments representing the scene to draw.
     */
    public void drawScene(Segment[] scene){
        //get the drawable object from the canvas
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        if (model.isDrawn())
            gc.clearRect(0,0, fxCanvas.getWidth(), fxCanvas.getHeight());

        //draw
        for (Segment s : scene){
            double [] coords = s.get(); //x1,y1,x2,y2
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
        gc.drawImage(eye, x, y, 10, 10);
    }

    /**
     * Draw the eye direction and FOV guide lines on the canvas.
     */
    private void drawEyeParameters(){
        if (model.isFOVVisible()){
            double[] firstPoint;
            double R = fxCanvas.getWidth()/10;
            double dx, dy, x, y; //a,
            double[] xy1, xy2;

            //create direction line with polar equation
            firstPoint = model.getPOVPosition();
            double directionAngle = model.getFOVDirection()-90;
            dx = R*Math.cos(Math.toRadians(directionAngle));
            dy = R*Math.sin(Math.toRadians(directionAngle));
            x = firstPoint[0] + dx;
            y = firstPoint[1] + dy;
            Segment directionLine = new Segment(firstPoint[0], firstPoint[1], x, y);

            //create 2 FOV lines with polar equation
            firstPoint = model.getPOVPosition();
            double FOVAngle = model.getFOV()/2.0;
            //first line
            dx = R*Math.cos(Math.toRadians(directionAngle-FOVAngle));
            dy = R*Math.sin(Math.toRadians(directionAngle-FOVAngle));
            x = firstPoint[0] + dx;
            y = firstPoint[1] + dy;
            Segment FOVLeftLine = new Segment(firstPoint[0], firstPoint[1], x, y);
            //second line
            dx = R*Math.cos(Math.toRadians(directionAngle+FOVAngle));
            dy = R*Math.sin(Math.toRadians(directionAngle+FOVAngle));
            x = firstPoint[0] + dx;
            y = firstPoint[1] + dy;
            Segment FOVRightLine = new Segment(firstPoint[0], firstPoint[1], x, y);


            //get the drawable object from the canvas
            GraphicsContext gc = fxCanvas.getGraphicsContext2D();
            Color colorDirectionLine = new Color(.22, .14, 0, .75);

            //create the 3lines with the color
            gc.setStroke(colorDirectionLine);

            xy1 = directionLine.getFrom();
            xy2 = directionLine.getTo();
            gc.strokeLine(xy1[0], xy1[1], xy2[0], xy2[1]);

            xy1 = FOVLeftLine.getFrom();
            xy2 = FOVLeftLine.getTo();
            gc.strokeLine(xy1[0], xy1[1], xy2[0], xy2[1]);

            xy1 = FOVRightLine.getFrom();
            xy2 = FOVRightLine.getTo();
            gc.strokeLine(xy1[0], xy1[1], xy2[0], xy2[1]);
        }
    }

    /**
     * Update the view
     */
    private void update(){
        if (model.isDrawn()) {
            GraphicsContext gc = fxCanvas.getGraphicsContext2D();
            //clear everything and redraw all
            gc.clearRect(0, 0, fxCanvas.getWidth(), fxCanvas.getHeight());

            drawScene(model.getData());
            double[] xy = model.getPOVPosition();
            //if the POV has been set
            if (xy != null) {
                drawEye(eyeButtonImage.getImage(), xy[0], xy[1]);
                drawEyeParameters();
            }
        }
    }

    //constructor

    /**
     * Method called by the fxml view to create the controller.
     */
    public void initialize(){
        //create model
        model = new SceneModel();

        //link Model with view
        //setting value factory for the spinners
        fxFOVSpinner.setValueFactory(model.getFOVSpinnerFactory());
        fxAngleSpinner.setValueFactory(model.getAngleSpinnerFactory());

        //handling changes on spinners' and slides' values
        fxFOVSpinner.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                model.setFOV(newValue);
                fxFOVSlider.setValue(newValue);
                update();
            }
        });
        fxFOVSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //no model modification and no view update because spinner's handler will be called automatically.
                fxFOVSpinner.getValueFactory().setValue(newValue.doubleValue());
            }
        });

        fxAngleSpinner.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                model.setFOVDirection(newValue);
                fxFOVAngle.setValue(newValue);
                update();
            }
        });
        fxFOVAngle.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //no model modification and no view update because spinner's handler will be called automatically.
                fxAngleSpinner.getValueFactory().setValue(newValue.doubleValue());
            }
        });

        //link controller to view - configurations

        //other controller initialisation
        eyeButtonImage.setImage(new Image(getClass().getResource("/img/eye.png").toString()));
    }
}
