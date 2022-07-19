package GUI.Controller;

import DataStructure.Eye;
import DataStructure.Pair;
import DataStructure.Point2D;
import DataStructure.Segment;
import GUI.Model.SceneModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Scene object that can be cleared and where it is possible to set the Point Of View (eye).
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 2.0.0
 */
public class SceneController {
    private SceneModel model;
    private boolean POVEnabled = false;

    //view data
    @FXML
    private Pane fxPane;

    @FXML
    private Canvas fxCanvas;

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
        model.getEye().setPosition(null);
        model.setData(null);
    }

    /**
     * Callback called by the fxml view in order to be able to place the Point Of View (Eye) on the canvas when the "eye" button is pushed.
     */
    @FXML
    public void onPOV(){
        if (model.getEye().getPosition() != null)
            model.getEye().setPosition(null);

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
            model.getEye().setPosition(new Point2D(event.getSceneX()-4, model.getB() - (event.getSceneY() - 30)));

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
            model.getEye().setPosition(new Point2D(event.getSceneX()-4, model.getB() - (event.getSceneY() - 30)));
            //System.out.println(model.getEye().getPosition().getX() + "; " + model.getEye().getPosition().getY());
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
        model.getEye().setVisible(fxChecker.isSelected());

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
        //System.out.println("resize " + (width+10) + ", " + (height+10));
        model.setA(width);
        model.setB(height);

        fxCanvas.setWidth(width+10);
        fxCanvas.setHeight(height+10);
        fxPane.setPrefSize(width+10, height+10);
        fxPane.setMinSize(width+10, height+10);
        fxPane.resize(width+10, height+10);
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
     * Get the scene data.
     * @return
     *      Array of Segment objects representing the scene.
     */
    public Segment[] getData(){
        return model.getData();
    }

    /**
     * Get the eye object of the scene.
     * @return
     *      Eye object of the scene.
     */
    public Eye getEye(){
        Eye eye = model.getEye();

        if (eye == null)
            return null;

        if (eye.getPosition() == null)
            return null;

        return eye;
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
        double x1, x2, y1, y2;
        int padding = 5; //used to make a padding in canvas so that segments are not on the edge of it
        for (Segment s : scene){
            Point2D from = s.getFrom(), to = s.getTo();
            x1 = from.getX(); y1 = from.getY();
            x2 = to.getX(); y2 = to.getY();
            Color color = s.getEColor().getColor();

            //draw the segment on the canvas with its color as set up in the scene file
            gc.setStroke(color);
            gc.strokeLine(x1 + padding, -(y1 - model.getB() - padding), x2 + padding, -(y2 - model.getB() - padding));
        }

        model.setIsDrawn(true);
    }

    /**
     * Draw an eye on the canvas.
     * @param eye
     *      Eye image resource URL.
     * @param pos
     *      Coordinates of the position.
     */
    public void drawEye(Image eye, Point2D pos){
        model.getEye().setPosition(pos);

        //get the drawable object from the canvas
        GraphicsContext gc = fxCanvas.getGraphicsContext2D();

        //draw
        gc.drawImage(eye, pos.getX(), model.getB() - pos.getY(), 10, 10);
    }

    /**
     * Draw the eye direction and FOV guide lines on the canvas.
     */
    private void drawEyeParameters(){
        if (model.getEye().isVisible()){
            Point2D from, to;

            //create direction line with polar equation
            Segment directionLine = model.getEye().getDirectionLine(20);

            //create 2 FOV lines with polar equation
            Pair<Segment, Segment> FOVLines = model.getEye().getFOVLine(20);

            //get the drawable object from the canvas
            GraphicsContext gc = fxCanvas.getGraphicsContext2D();
            Color colorDirectionLine = new Color(.22, .14, 0, .75);

            //create the 3lines with the color
            gc.setStroke(colorDirectionLine);

            from = directionLine.getFrom();
            to = directionLine.getTo();
            gc.strokeLine(from.getX(), model.getB() - from.getY(), to.getX(), model.getB() - to.getY());

            from = FOVLines.getL().getFrom();
            to = FOVLines.getL().getTo();
            gc.strokeLine(from.getX(), model.getB() - from.getY(), to.getX(), model.getB() - to.getY());

            from = FOVLines.getR().getFrom();
            to = FOVLines.getR().getTo();
            gc.strokeLine(from.getX(), model.getB() - from.getY(), to.getX(), model.getB() - to.getY());
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
            Point2D pos = model.getEye().getPosition();
            //if the POV has been set
            if (pos != null) {
                drawEye(eyeButtonImage.getImage(), pos);
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
                model.getEye().setAngle(newValue);
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
                model.getEye().setDirection(newValue);
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

        fxFOVSpinner.increment();
        fxFOVSpinner.decrement();
        fxAngleSpinner.increment();
        fxAngleSpinner.decrement();

        //link controller to view - configurations

        //other controller initialisation
        eyeButtonImage.setImage(new Image(getClass().getResource("/img/eye.png").toString()));
    }
}
