package DataStructure;

import java.util.ArrayList;

/**
 * Implementation of the BSP Tree.
 * Based on Prof. V. Bruyère implementation of Tree in Java on Moodle UMons ("Implémentation" part of "Structure de Données 2" course, 2021-2022)
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class BSPTree<D>{
    private ArrayList<D> data;
    private BSPTree<D> left, right, parent;

    //constructors

    /**
     * Constructor.
     */
    public BSPTree(){
        data = new ArrayList<>();
        left = null;
        right = null;
        parent = null;
    }

    /**
     * Constructor.
     * @param d
     *      ArrayList of data stocked in the node.
     * @param l
     *      Left sub-tree.
     * @param r
     *      Right sub-three.
     */
    public BSPTree(ArrayList<D> d, BSPTree<D> l, BSPTree<D> r){
        parent = null;
        data = d;
        left = l;
        right = r;
        //System.out.println("\n\tcreating tree\n");
        if (l != null) {
            l.setParent(this);
        }
        if (r != null) {
            r.setParent(this);
        }
        //System.out.println("left & right subtree done");
    }

    /**
     * Constructor.
     * @param d
     *      Segment data stocked in the node.
     * @param l
     *      Left sub-tree.
     * @param r
     *      Right sub-three.
     */
    public BSPTree(D d, BSPTree<D> l, BSPTree<D> r){
        parent = null;
        data = new ArrayList<D>();
        data.add(d);
        left = l;
        right = r;
        //System.out.println("\n\tcreating tree\n");
        if (l != null) {
            l.setParent(this);
        }
        if (r != null) {
            r.setParent(this);
        }
        //System.out.println("left & right subtree done");
    }

    /**
     * Constructor.
     * @param p
     *      Parent tree.
     * @param d
     *      ArrayList of data stocked in the node.
     * @param l
     *      Left sub-tree.
     * @param r
     *      Right sub-three.
     */
    public BSPTree(BSPTree<D> p, ArrayList<D> d, BSPTree<D> l, BSPTree<D> r){
        parent = p;
        data = d;
        left = l;
        right = r;
        //System.out.println("\n\tcreating tree\n");
        if (l != null) {
            l.setParent(this);
        }
        if (r != null) {
            r.setParent(this);
        }
        //System.out.println("left & right subtree done");
    }

    /**
     * Constructor
     * @param p
     *      Parent tree.
     * @param d
     *      Segment data stocked in the node.
     * @param l
     *      Left sub-tree.
     * @param r
     *      Right sub-three.
     */
    public BSPTree(BSPTree<D> p, D d, BSPTree<D> l, BSPTree<D> r){
        parent = p;
        data = new ArrayList<D>();
        data.add(d);
        left = l;
        right = r;
        //System.out.println("\n\tcreating tree\n");
        if (l != null) {
            l.setParent(this);
        }
        if (r != null) {
            r.setParent(this);
        }
        //System.out.println("left & right subtree done");
    }

    //Getter

    /**
     * Get data stored in the root node.
     * @return
     *      ArrayList of data.
     */
    public ArrayList<D> getData(){
        return data;
    }

    /**
     * Get left sub-tree.
     * @return
     *      Left BSPTree.
     */
    public BSPTree<D> getLeft(){
        return left;
    }

    /**
     * Get right sub-tree
     * @return
     *      Right BSPTree.
     */
    public BSPTree<D> getRight(){
        return right;
    }

    /**
     * Get parent node. (root)
     * @return
     *      Parent BSPTree.
     */
    public BSPTree<D> getParent() {
        return parent;
    }

    /**
     * Check if the tree is empty.
     * @return
     *      If no data and no sub-tree.
     */
    public boolean isEmpty(){
        return (data.size() == 0 && left == null && right == null);
    }

    /**
     * check if the tree is actually a leaf.
     * @return
     *      If there are data but no sub-tree.
     */
    public boolean isLeaf(){
        return (!isEmpty() && left == null && right == null);
    }

    /**
     * Show all the tree structure.
     */
    public void print() {
        if(!isEmpty()){
            if (!isLeaf() && left != null){
                System.out.println("\non affiche le sous-arbre de gauche");
                left.print();
            }

            System.out.println("on affiche nos données");
            if (data.size() > 0)
                for (D d : data){
                    System.out.println(d);
                }

            if (!isLeaf() && right != null) {
                System.out.println("\non affiche le sous-arbre de droite");
                right.print();
            }
        }else{
            System.out.println("je suis vide");
        }
    }

    /**
     * Get the height of the tree from this node.
     * @return
     *      The height of the tree.
     */
    public int height() {
        if (isEmpty())
            return 0;
        else
            return 1 + Math.max(left.height(),right.height());
    }

    /**
     * Get the length of the tree.
     *
     * @return
     *      The length of the tree.
     */
    public int length(){
        if (isEmpty())
            return 0;
        else
            return left.length() + data.size() + right.length();
    }

    //Setter

    /**
     * Set left sub-tree.
     * @param l
     *      Left BSPTree.
     */
    public void setLeft(BSPTree<D> l){
        if (left != null)
            left.setParent(null);
        left = l;
        if (left != null) {
            l.setParent(this);
        }
    }

    /**
     * Set Right sub-tree.
     * @param r
     *      Right BSPTree.
     */
    public void setRight(BSPTree<D> r){
        if (right != null)
            right.setParent(null);
        right = r;
        if (right != null) {
            r.setParent(this);
        }
    }

    /**
     * Set the list of data to store in the node.
     * @param d
     *      ArrayList of data.
     */
    public void setData(ArrayList<D> d){
        data = d;
    }

    /**
     * Set the parent node.
     * @param parent
     *      Parent BSPTree.
     */
    public void setParent(BSPTree<D> parent) {
        this.parent = parent;
    }

    /**
     * Add data to the node.
     * @param d
     *      Data to add.
     */
    public void addData(D d){
        data.add(d);
    }

    //Overrides

    /**
     * Representation of the tree.
     * @return
     *      First data in list of data.
     */
    @Override
    public String toString(){
        return data.get(0).toString();
    }

    /**
     * Check if a BSPTree is equals to another object.
     * @param o
     *      The other object to compare with. (Has to be a BSPTree instance)
     * @return
     *      If the 2 objects are equals or not.
     */
    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        //On vérifie qu'on compare bien 2 arbres
        if (!(o instanceof BSPTree))
            return false;

        BSPTree<D> oTree = (BSPTree<D>) o;

        //On vérifie que les 2 arbres ont les mêmes données
        ArrayList<D> thisData = this.getData();
        ArrayList<D> oData = oTree.getData();

        if (thisData.size() != oData.size())
            return false;

        Segment thisSeg, oSeg;

        for (int i = 0; i < thisData.size(); i++){
            if (!(thisData.get(i).equals(oData.get(i)))) {
                return false;
            }
        }

        if (isLeaf() && oTree.isLeaf())
            return true;
        else{
            //On vérifie que les sous-arbres gauche et droit sont équivalents pour les 2 arbres
            if (left != null && oTree.getLeft() != null) {
                if (!(this.getLeft().equals(oTree.getLeft())))
                    return false;

            }

            if (right != null && oTree.getRight() != null) {
                /*System.out.println("\n");
                System.out.println(getRight().equals(oTree.getRight()));
                System.out.println("\n");*/
                if (!(this.getRight().equals(oTree.getRight())))
                    return false;
            }
        }

        return true;
    }

}
