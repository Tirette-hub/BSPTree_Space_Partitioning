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

    /**
     * Set the FOV angle.
     * @param angle
     *      FOV angle.
     */
    public void setFOV(double angle){
        FOV = angle;
    }

    /**
     * Set the POV (eye) direction.
     * @param angle
     *      Direction of the POV (eye).
     */
    public void setFOVDirection(double angle){
        FOVDirection = angle;
    }

    /**
     * Set the possibility to see the FOV.
     * @param flag
     *      Flag that says if the FOV is visible or not.
     */
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

    /**
     * Get the FOV angle.
     * @return
     *      FOV angle value.
     */
    public Double getFOV(){
        return FOV;
    }

    /**
     * Get the POV (eye) direction angle.
     * @return
     *      POV (eye) direction angle value.
     */
    public Double getFOVDirection(){
        return FOVDirection;
    }

    /**
     * Get the Flag that says if the FOV has to be shown.
     * @return
     *      If the FOV guide lines are visible.
     */
    public boolean isFOVVisible(){
        return FOVVisible;
    }

    /**
     * Get the Value Factory of the FOV angle spinner object.
     * @return
     *      Value Factory of the FOV angle spinner object.
     */
    public SpinnerValueFactory<Double> getFOVSpinnerFactory() {
        return FOVSpinnerFactory;
    }

    /**
     * Get the Value Factory of the POV (eye) direction angle spinner object.
     * @return
     *      Value Factory of the POV (eye) direction angle spinner object.
     */
    public SpinnerValueFactory<Double> getAngleSpinnerFactory(){
        return AngleSpinnerFactory;
    }
}
