package GUI.Model;

import DataStructure.Eye;
import DataStructure.Segment;
import javafx.scene.control.SpinnerValueFactory;

/**
 * Model of SceneController.
 * Stores all the segments of the scene and the Point Of View (eye) position and the state of the state of the canvas.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 2.0.0
 */
public class SceneModel {
    private boolean isDrawn;
    private Segment[] data;
    private final Eye eye;
    private final SpinnerValueFactory<Double> FOVSpinnerFactory,  AngleSpinnerFactory;
    private double a, b;

    //constructors

    /**
     * Constructor.
     */
    public SceneModel(){
        isDrawn = false;

        eye = new Eye();

        FOVSpinnerFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 359.9, 90.0, 1.0);
        AngleSpinnerFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 359.9, 0.0, 1.0);
    }

    //setters

    /**
     * Set scene width value.
     * @param value
     *      Scene width.
     */
    public void setA(double value){
        a = value;
    }

    /**
     * Set scene height value
     * @param value
     *      Scene height.
     */
    public void setB(double value){
        b = value;
    }

    /**
     * Set the "drawn" value.
     * @param flag
     *      Boolean value that represents if the canvas has something drawn on it or not.
     */
    public void setIsDrawn(boolean flag){
        isDrawn = flag;
    }

    /**
     * Set scene data.
     * @param data
     *      Scene data (segments).
     */
    public void setData(Segment[] data){
        this.data = data;
    }

    //getters

    /**
     * Get the eye of the scene.
     * @return
     *      Eye of the scene.
     */
    public Eye getEye(){
        return eye;
    }

    /**
     * Get scene width value.
     * @return
     *      Scene width value.
     */
    public double getA(){
        return a;
    }

    /**
     * Get scene height value.
     * @return
     *      Scene height value.
     */
    public double getB() {
        return b;
    }

    /**
     * Get the "drawn" value.
     * @return
     *      "isDrawn" boolean value.
     */
    public boolean isDrawn(){
        return isDrawn;
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
