package GUI.Model;

import DataStructure.Segment;

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

    //constructors

    /**
     * Constructor.
     */
    public SceneModel(){
        isDrawn = false;
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
}
