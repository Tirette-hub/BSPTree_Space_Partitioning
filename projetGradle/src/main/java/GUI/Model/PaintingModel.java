package GUI.Model;

import DataStructure.BSPTree;

/**
 * Model of PaintingController.
 * Stores the BSP Tree.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class PaintingModel {
    private boolean isPainted;
    private BSPTree tree;

    //constructors

    /**
     * Constructor.
     */
    public PaintingModel(){
        isPainted = true;
    }

    //getters

    /**
     * Check if the canvas has something painted on it.
     * @return
     *      Flag.
     */
    public boolean isPainted(){
        return isPainted;
    }

    /**
     * Get the BSP tree.
     * @return
     *      BSPTree object stored.
     */
    public BSPTree getBSPTree(){
        return tree;
    }

    //setters

    /**
     * Set the flag that says if the canvas has something painted on it.
     * @param flag
     *      New flag value.
     */
    public void setIsPainted(boolean flag){
        isPainted = flag;
    }

    /**
     * Set the BSP tree.
     * @param tree
     *      New BSPTree object to store.
     */
    public void setBSPTree(BSPTree tree){
        this.tree = tree;
    }
}
