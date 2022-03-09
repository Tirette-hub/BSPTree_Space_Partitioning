package DataStructure;

import java.util.ArrayList;

/**
 * Implementation of the BSP Tree.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class BSPTree<D> extends Tree<D>{
    protected ArrayList<D> data;
    protected BSPTree<D> left, right;

    public BSPTree(){
        super();
    }
    public BSPTree(ArrayList<D> d, Tree<D> l, Tree<D> r){
        super(null,l,r);
        data = d;
    }

    public BSPTree(D d, Tree<D> l, Tree<D> r){
        super(null, l, r);
        data = new ArrayList<D>();
        data.add(d);
    }

    public BSPTree<D> getLeft(){
        return (BSPTree<D>) super.getLeft();
    }
    public BSPTree<D> getRight(){
        return (BSPTree<D>) super.getRight();
    }

    public void addData(D d){
        data.add(d);
    }

    /*public boolean search(D d) {
        /*
         *
         * TBC
         *
         *-/

        if (isEmpty())
            return false;
        else	if (getData() == d)
            return getRight().search(d);
        else 	if (getData() == d)
            return getLeft().search(d);
        else 	return true;
    }*/
}
