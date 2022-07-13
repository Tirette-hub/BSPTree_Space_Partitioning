package DataStructure;

import Console.TestMain;

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
            return 1 + Math.max(left != null ? left.height() : 0, right != null ? right.height() : 0);
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
            return (left != null ? left.length() : 0) + data.size() + (right != null ? right.length() : 0);
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

    //creation of tree and utilisation of painter's algorithm

    /**
     * Create a BSPTree following the order of the given segment list.
     * @param S
     *      List of Segments representing the scene to partition.
     * @param parent
     *      Parent node to which will be added the sub-tree that will be created.
     *      If null, the root is created.
     * @return
     *      The BSPTree of the scene.
     */
    static public BSPTree<Segment> makeBasicTree(Segment[] S, BSPTree<Segment> parent){
        return makeBasicTree(S, parent, false);
    }

    /**
     * Create a BSPTree following the order of the given segment list but can also follow the "free split" rule
     * @param S
     *      List of Segments representing the scene to partition.
     * @param parent
     *      Parent node to which will be added the sub-tree that will be created.
     *      If null, the root is created.
     * @param switchToFreeSplit
     *      Flag to know if the algorithm has to switch to "free split" rule or not.
     * @return
     *      The BSPTree of the scene.
     */
    static public BSPTree<Segment> makeBasicTree(Segment[] S, BSPTree<Segment> parent, boolean switchToFreeSplit){

        BSPTree<Segment> tree;
        if (S.length == 1){
            //le set S de segments est unique, donc il s'agit d'une feuille, on crée donc un arbe qui est en réalité une feuille avec la data.
            tree = new BSPTree<>(parent, S[0], null, null);
        }else if (S.length == 0) {
            return null;
        }else{
            // segment correspondant à la ligne de découpe
            Segment l = S[0];

            //calcul des paramètres d'équation de la droite (équation de l'hyperplan)
            double[] abc = Segment.getCutlineParameters(l.getFrom(), l.getTo());
            double a = abc[0],
                    b = abc[1],
                    c = abc[2];

            //on crée les listes des segments se trouvant à gauche et à droite de la ligne de découpe
            ArrayList<Segment> Sminus = new ArrayList<>();
            ArrayList<Segment> Splus = new ArrayList<>();

            double[] segCoord;
            double r1, r2;

            //vérification, pour chaque segment, de sa position par rapport à la ligne de découpe pour en créer le bsp
            tree = new BSPTree<>();
            tree.addData(l);
            tree.setParent(parent);
            for (Segment seg : S){
                if (seg != l){
                    r1 = a*seg.getFrom().getX() + b*seg.getFrom().getY() + c;
                    r2 = a*seg.getTo().getX() + b*seg.getTo().getY() + c;

                    if (Math.abs(r1) < TestMain.epsilon && Math.abs(r2) < TestMain.epsilon){
                        //segment sur la ligne de découpe
                        //ajouter ce segment à la liste de semgments que contient le noeud
                        tree.addData(seg);
                    }
                    else if(r1 <= 0 && r2 <= 0){
                        //segment à gauche de la ligne de découpe (possibilité qu'un des points se trouve sur la ligne de découpe)
                        //ajouter le segment à la liste des S-
                        Sminus.add(seg);
                    }
                    else if(r1 >= 0 && r2 >= 0){
                        //segment à droite de la ligne de découpe (possibilité qu'un des points se trouve sur la ligne de découpe)
                        //ajouter le segment à la liste des S+
                        Splus.add(seg);
                    }
                    else if((r1 <= 0 && r2 >= 0) || (r1 >= 0 && r2 <= 0)){
                        //segment seg coupé par la ligne de découpe (appel à cut)
                        Segment[] cutResult = Segment.cut(seg, l);

                        //on vérifie quel coté de la découpe se trouve à gauche et quel coté se trouve à droite, de la ligne de découpe
                        Point2D cutLineCoord = cutResult[0].getFrom();
                        double result = a*cutLineCoord.getX() + b*cutLineCoord.getY() + c;

                        if (result < 0){
                            Sminus.add(cutResult[0]);
                            Splus.add(cutResult[1]);
                        }else{
                            Sminus.add(cutResult[1]);
                            Splus.add(cutResult[0]);
                        }
                    }
                }
            }
            // creates left and right subtree;
            BSPTree<Segment> left;
            BSPTree<Segment> right;

            if (switchToFreeSplit){
                left = makeFreeSplitTree(Sminus.toArray(new Segment[0]), tree);
                right = makeFreeSplitTree(Splus.toArray(new Segment[0]), tree);
            }
            else {
                left = makeBasicTree(Sminus.toArray(new Segment[0]), tree);
                right = makeBasicTree(Splus.toArray(new Segment[0]), tree);
            }

            //add left and right subtree to the tree
            tree.setLeft(left);
            tree.setRight(right);
        }

        return tree;
    }

    /**
     * Create a BSPTree following the "free split" rule
     * @param S
     *      List of Segments representing the scene to partition.
     * @param parent
     *      Parent node to which will be added the sub-tree that will be created.
     *      If null, the root is created.
     * @return
     *      The BSPTree of the scene.
     */
    static public BSPTree<Segment> makeFreeSplitTree(Segment[] S, BSPTree<Segment> parent) {
        BSPTree<Segment> tree;

        if (S.length == 1){
            //le set S de segments est unique, donc il s'agit d'une feuille, on crée donc un arbe qui est en réalité une feuille avec la data.
            tree = new BSPTree<>(S[0], null, null);
            tree.setParent(parent);
        }else if (S.length == 0) {
            return null;
        }else {
            if (parent != null) {
                //Il y a peut-être un freeSplit à faire
                double[] coord;
                double x1, y1, x2, y2;
                BSPTree<Segment> borne;
                boolean pt1 = false, pt2 = false; //flag pour savoir si pt1 et/ou pt2 est sur une borne de l'espace
                double[] abc;
                double a, b, c;
                Segment freeSplitSeg = null;

                int n = 0;
                for (Segment s : S){
                    x1 = s.getFrom().getX(); y1 = s.getFrom().getY(); //pt1
                    x2 = s.getTo().getX(); y2 = s.getTo().getY(); //pt2
                    pt1 = false; pt2 = false;

                    borne = parent;
                    while(borne != null){
                        abc = Segment.getCutlineParameters(borne.getData().get(0).getFrom(), borne.getData().get(0).getTo());
                        a = abc[0]; b = abc[1]; c = abc[2];
                        if (Math.abs(a*x1+b*y1+c) < TestMain.epsilon){
                            pt1 = true;
                            break;
                        }

                        borne = borne.getParent();
                    }
                    if (pt1){
                        //check pt2
                        borne = parent;
                        while(borne != null){
                            abc = Segment.getCutlineParameters(borne.getData().get(0).getFrom(), borne.getData().get(0).getTo());
                            a = abc[0]; b = abc[1]; c = abc[2];
                            if (Math.abs(a*x2+b*y2+c) < TestMain.epsilon){
                                pt2 = true;
                                break;
                            }

                            borne = borne.getParent();
                        }
                    }
                    if (pt2) {
                        freeSplitSeg = s;
                        break;
                    }
                    n += 1;
                }

                if(!pt1 || !pt2) {
                    tree = makeBasicTree(S, parent, true);
                }
                else{
                    //make freesplit
                    //rework S with l as first segment.
                    /*
                    Segment[] newS = new Segment[S.length];
                    newS[0] = freeSplitSeg;
                    int n = 1;
                    for (int i = 0; i < S.length; i++){
                        if (S[i] != newS[0])
                            newS[i+n] = S[i];
                        else
                            n = 0;
                    }
                    */
                    S[n] = S[0];
                    S[0] = freeSplitSeg;
                    tree = makeBasicTree(S, parent, true);

                }
            }
            else{
                //on fait un arbre normal, mais on appelle freeSplit pour la suite
                tree = makeBasicTree(S, parent, true);
            }
        }

        return tree;
    }

    /**
     * Get the list of segments that have to be painted in order
     * @param tree
     *      BSPTree representing the scene.
     * @param POVposition
     *      Position of the Point Of View (Eye).
     * @return
     *      List of Segments in order.
     */
    static public ArrayList<Segment> paintersAlgorithm(BSPTree<Segment> tree, Point2D POVposition){
        //if leaf: just return data
        if (tree.isLeaf()) {
            return tree.getData();
        }

        //set all the data that will be needed
        Segment h = tree.getData().get(0);
        double[] abc = Segment.getCutlineParameters(h.getFrom(), h.getTo());
        double a = abc[0], b = abc[1], c = abc[2];
        double x = POVposition.getX(), y = POVposition.getY();
        double result = a*x+b*y+c;

        ArrayList<Segment> segList = new ArrayList<>();
        //recursion on left and right trees of the algorithm
        ArrayList<Segment> Tm = null , Tp = null; //T-, T+

        if (tree.getLeft() != null) //pre-compute painter's algorithm on left sub-tree if exists
            Tm = paintersAlgorithm(tree.getLeft(), POVposition);
        if (tree.getRight() != null) //pre-compute painter's algorithm on right sub-tree if exists
            Tp = paintersAlgorithm(tree.getRight(), POVposition);

        if (result > 0){ //pov € h+
            if (Tm != null)
                segList.addAll(Tm); //adding left data first because will be painted first
            segList.addAll(tree.getData()); //then segments in this node
            if (Tp != null)
                segList.addAll(Tp); //finally adding the right tree data because will be painted last
        }
        else if (result < 0){ //pov € h-
            //same as above but inverted because the eye is in the other side of the cutline (so inverted)
            if (Tp != null)
                segList.addAll(Tp);
            segList.addAll(tree.getData());
            if (Tm != null)
                segList.addAll(Tm);
        }
        else{ //pov € h
            //eye on the cutline so does not see segments contained in this node.
            if (Tm != null)
                segList.addAll(Tm);
            if (Tp != null)
                segList.addAll(Tp);
        }

        return segList;
    }

}
