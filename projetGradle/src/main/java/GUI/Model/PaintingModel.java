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

    /**
     * Constructor.
     */
    public PaintingModel(){
        isPainted = true;
    }

    /**
     * Check if the canvas has something painted on it.
     * @return
     *      Flag.
     */
    public boolean isPainted(){
        return isPainted;
    }

    /**
     * Set the flag that says if the canvas has something painted on it.
     * @param flag
     *      Flag value.
     */
    public void setIsPainted(boolean flag){
        isPainted = flag;
    }

    /**
     * Set the BSP tree.
     * @param tree
     *      BSPTree object to store.
     */
    public void setBSPTree(BSPTree tree){
        this.tree = tree;
    }

    /**
     * Get the BSP tree.
     * @return
     *      BSPTree object stored.
     */
    public BSPTree getBSPTree(){
        return tree;
    }
}
