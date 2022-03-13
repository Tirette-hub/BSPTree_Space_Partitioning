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

    public BSPTree(){
        data = new ArrayList<>();
        left = null;
        right = null;
        parent = null;
    }
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
    public ArrayList<D> getData(){
        return data;
    }

    public BSPTree<D> getLeft(){
        return left;
    }

    public BSPTree<D> getRight(){
        return right;
    }

    public BSPTree<D> getParent() {
        return parent;
    }

    //Setter
    public void setLeft(BSPTree<D> l){
        if (left != null)
            left.setParent(null);
        left = l;
        if (left != null) {
            l.setParent(this);
        }
    }

    public void setRight(BSPTree<D> r){
        if (right != null)
            right.setParent(null);
        right = r;
        if (right != null) {
            r.setParent(this);
        }
    }

    public void setData(ArrayList<D> d){
        data = d;
    }

    public void setParent(BSPTree<D> parent) {
        this.parent = parent;
    }

    public void addData(D d){
        data.add(d);
    }

    @Override
    public String toString(){
        return data.get(0).toString();
    }

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

    public boolean isEmpty(){
        return (data.size() == 0 && left == null && right == null);
    }

    public boolean isLeaf(){
        return (!isEmpty() && left == null && right == null);
    }

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

    public int height() {
        if (isEmpty())
            return 0;
        else
            return 1 + Math.max(left.height(),right.height());
    }


}
