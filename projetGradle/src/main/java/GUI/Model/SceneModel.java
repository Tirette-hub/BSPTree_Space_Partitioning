package GUI.Model;

import DataStructure.Segment;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.SpinnerValueFactory;

/**
 * Model of SceneController.
 * Stores all the segments of the scene and the Point Of View (eye) position and the state of the state of the canvas.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class SceneModel {
    private boolean isDrawn;
    private Segment[] data;
    private double[] POVPosition;
    private double FOV;
    private double FOVDirection;
    private boolean FOVVisible;
    private SpinnerValueFactory<Double> FOVSpinnerFactory, AngleSpinnerFactory;

    //constructors

    /**
     * Constructor.
     */
    public SceneModel(){
        isDrawn = false;
        FOVVisible = false;

        FOVSpinnerFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 359.9, 90.0, 1.0);
        AngleSpinnerFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 359.9, 1.0, 1.0);
    }

    //setters

    /**
     * Set the "drawn" value.
     * @param flag
     *      Boolean value that represents if the canvas has something drawn on it or not.
     */
    public void setIsDrawn(boolean flag){
        isDrawn = flag;
    }

    /**
     * Set the Point Of View (eye) coordinates.
     * @param x
     *      x coordinates.
     * @param y
     *      y coordinates.
     */
    public void setPOVPosition(Double x, Double y){
        if (x != null && y != null)
            POVPosition = new double[]{x, y};
        else
            POVPosition = null;
    }

    /**
     * Set scene data.
     * @param data
     *      Scene data (segments).
     */
    public void setData(Segment[] data){
        this.data = data;
    }

    public void setFOV(double angle){
        FOV = angle;
    }

    public void setFOVDirection(double angle){
        FOVDirection = angle;
    }

    public void setFOVVisible(boolean flag){
        FOVVisible = flag;
    }

    //getters

    /**
     * Get the "drawn" value.
     * @return
     *      "isDrawn" boolean value.
     */
    public boolean isDrawn(){
        return isDrawn;
    }

    /**
     * Get the Point Of View (eye) coordinates.
     * @return
     *      List of coordinates.
     */
    public double[] getPOVPosition(){
        return POVPosition;
    }

    /**
     * Get Scene data.
     * @return
     *      Scene data (segments).
     */
    public Segment[] getData(){
        return data;
    }

    public Double getFOV(){
        return FOV;
    }

    public Double getFOVDirection(){
        return FOVDirection;
    }

    public boolean isFOVVisible(){
        return FOVVisible;
    }

    public SpinnerValueFactory<Double> getFOVSpinnerFactory() {
        return FOVSpinnerFactory;
    }

    public SpinnerValueFactory<Double> getAngleSpinnerFactory(){
        return AngleSpinnerFactory;
    }
}
