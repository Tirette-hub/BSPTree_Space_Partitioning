package DataStructure;

import Console.TestMain;

import java.util.*;

/**
 * Precise BSPTree of Segments needed for this project.
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class SegmentBSPTree extends BSPTree<Segment>{
    /**
     * Constructor of empty tree.
     */
    public SegmentBSPTree(){
        super();
    }

    /**
     * Constructor.
     * @param d
     *      List of segments to store in the root node of the tree.
     * @param l
     *      Left sub-tree.
     * @param r
     *      Right sub-tree.
     */
    public SegmentBSPTree(LinkedList<Segment> d, SegmentBSPTree l, SegmentBSPTree r){
        super(d, l, r);
    }

    /**
     * Constructor.
     * @param d
     *      Segment to store in the root of the tree.
     * @param l
     *      Left sub-tree.
     * @param r
     *      Right sub-tree.
     */
    public SegmentBSPTree(Segment d, SegmentBSPTree l, SegmentBSPTree r){
        super(d, l, r);
    }

    /**
     * Constructor.
     * @param p
     *      Parent node.
     * @param d
     *      List of segments to store in the root node of the tree.
     * @param l
     *      Left sub-tree.
     * @param r
     *      Right sub-tree.
     */
    public SegmentBSPTree(SegmentBSPTree p, LinkedList<Segment> d, SegmentBSPTree l, SegmentBSPTree r){
        super(p, d, l, r);
    }

    /**
     * Constructor.
     * @param p
     *      Parent node.
     * @param d
     *      Segment to store in the root node of the tree.
     * @param l
     *      Left sub-tree.
     * @param r
     *      Right sub-tree.
     */
    public SegmentBSPTree(SegmentBSPTree p, Segment d, SegmentBSPTree l, SegmentBSPTree r){
        super(p, d, l, r);
    }

    //creation of tree and utilisation of painter's algorithm

    /**
     * Create BSPTree with the given set of segments.
     * @param S
     *      The set of segments used to create the tree.
     * @param parent
     *      Parent node to which will be added the sub-tree that will be created.
     *      If null, the root is created.
     * @param freeSplit
     *      Flag to tell to use the "free split" optimization.
     * @return
     *      A BSP Tree of segments.
     */
    static public SegmentBSPTree makeTree(Segment[] S, SegmentBSPTree parent, boolean freeSplit){
        SegmentBSPTree tree = new SegmentBSPTree();

        if (S.length == 1){
            tree = new SegmentBSPTree(parent, S[0], null, null);
        }else if (S.length == 0) {
            return null;
        }
        else{
            if (freeSplit){
                //sort segments by putting freeSplit segment at front of the tab.
                int j = S.length - 1;
                Segment temp;
                while(0 < j){
                    if (S[0].isFreeSplit())
                        break;
                    else{
                        temp = S[0];
                        S[0] = S[j];
                        S[j] = temp;
                        j--;
                    }
                }

                //if there is no freesplit then shuffle
                if (!S[0].isFreeSplit()){
                    List<Segment> newS = Arrays.asList(S);
                    Collections.shuffle(newS);
                }
            }

            Segment l = S[0];

            //calcul des paramètres d'équation de la droite (équation de l'hyperplan)
            double[] abc = Segment.getCutlineParameters(l.getFrom(), l.getTo());
            double a = abc[0],
                    b = abc[1],
                    c = abc[2];

            double r1, r2;

            //on crée les listes des segments se trouvant à gauche et à droite de la ligne de découpe
            LinkedList<Segment> Sminus = new LinkedList<>();
            LinkedList<Segment> Splus = new LinkedList<>();

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
                        Pair<Segment, Segment> cutResult = Segment.cut(seg, l);

                        //on vérifie quel coté de la découpe se trouve à gauche et quel coté se trouve à droite, de la ligne de découpe
                        Point2D cutLineCoord = cutResult.getL().getFrom();
                        double result = a*cutLineCoord.getX() + b*cutLineCoord.getY() + c;

                        if (result < 0){
                            Sminus.add(cutResult.getL());
                            Splus.add(cutResult.getR());
                        }else{
                            Sminus.add(cutResult.getR());
                            Splus.add(cutResult.getL());
                        }
                    }
                }
            }
            // creates left and right subtree;
            BSPTree<Segment> left = makeTree(Sminus.toArray(new Segment[0]), tree, freeSplit);
            BSPTree<Segment> right = makeTree(Splus.toArray(new Segment[0]), tree, freeSplit);

            //add left and right subtree to the tree
            tree.setLeft(left);
            tree.setRight(right);
        }

        return tree;
    }

    /**
     * Get the list of segments that have to be painted in order
     * @param tree
     *      BSPTree representing the scene.
     * @param eye
     *      Eye that sees the scene.
     * @return
     *      List of Segments in order.
     */
    static public LinkedList<Segment> paintersAlgorithm(SegmentBSPTree tree, Eye eye){
        //if leaf: just return data
        if (tree == null)
            return new LinkedList<>();
        if (tree.isLeaf()) {
            LinkedList<Segment> data = new LinkedList<>();

            for (Segment s: tree.getData()){
                if (eye.isInSight(s.getFrom()) || eye.isInSight(s.getTo()))
                    data.add(s);
            }

            return data;
        }

        //set all the data that will be needed
        Segment h = tree.getData().get(0);
        Pair<Segment, Segment> FOVLines = eye.getFOVLine();
        double[] abc = Segment.getCutlineParameters(h.getFrom(), h.getTo()),
                abc2 = Segment.getCutlineParameters(FOVLines.getL().getFrom(), FOVLines.getL().getTo()),
                abc3 = Segment.getCutlineParameters(FOVLines.getR().getFrom(), FOVLines.getR().getTo());
        double a = abc[0], b = abc[1], c = abc[2];
        Point2D POVposition = eye.getPosition();
        Point2D intersection1, intersection2;
        Segment testVect;
        double x = POVposition.getX(), y = POVposition.getY();
        double result = a*x+b*y+c;
        boolean check = false;

        LinkedList<Segment> segList = new LinkedList<>();
        //recursion on left and right trees of the algorithm
        LinkedList<Segment> Tm = null , Tp = null; //T-, T+

        /*
         * Si l'angle de la fov est > 180, alors on doit parcourir les 2 sous-arbres obligatoirement
         * Sinon, si la FOV ne voit jamais ce qu'il y a dans l'espace d'un autre sous-arbre, on peut ne pas appliquer l'algorithme
         * du peintre sur cette partie de l'arbre.
         * Pour ce faire, on regarde si, pour au moins 1 des 2 lignes de la FOV (qui représentent les extrémités de la FOV),
         * il y a un point d'intersection.
         * Si oui, alors une partie du sous-espace peut être vu et doit donc être calculé
         * Sinon, l'oeil n'est pas orienté vers l'autre sous-arbre.
         */

        if (eye.getAngle() > 180) {
            if (tree.getLeft() != null) //pre-compute painter's algorithm on left sub-tree if exists
                Tm = paintersAlgorithm((SegmentBSPTree) tree.getLeft(), eye);
            if (tree.getRight() != null) //pre-compute painter's algorithm on right sub-tree if exists
                Tp = paintersAlgorithm((SegmentBSPTree) tree.getRight(), eye);
        }else{
            intersection1 = Segment.getIntersection(abc, abc2);
            intersection2 = Segment.getIntersection(abc, abc3);

            if (intersection1 != null){
                testVect = new Segment(eye.getPosition(), intersection1);
                if (FOVLines.getL().hasSameSens(testVect))
                    check = true;
            }

            if (intersection2 != null && !check){
                testVect = new Segment(eye.getPosition(), intersection2);
                if (FOVLines.getR().hasSameSens(testVect))
                    check = true;
            }
        }

        if (result > 0){ //pov € h+
            if (Tm == null) {
                if (check && tree.getLeft() != null) {
                    Tm = paintersAlgorithm((SegmentBSPTree) tree.getLeft(), eye);
                    segList.addAll(Tm); //adding left data first because will be painted first
                }
            }else
                segList.addAll(Tm);

            for (Segment seg : tree.getData()){//then segments in this node if visible by the eye
                if (eye.isInSight(seg.getFrom()))
                    segList.add(seg);
                else if (eye.isInSight(seg.getTo()))
                    segList.add(seg);
            }

            if (Tp == null) {
                if (tree.getRight() != null) {
                    Tp = paintersAlgorithm((SegmentBSPTree) tree.getRight(), eye);
                    segList.addAll(Tp);
                }
            }else
                segList.addAll(Tp); //finally adding the right tree data because will be painted last
        }
        else if (result < 0){ //pov € h-
            //same as above but inverted because the eye is in the other side of the cutline (so inverted)
            if (Tp == null){
                if (check && tree.getRight() != null) {
                    Tp = paintersAlgorithm((SegmentBSPTree) tree.getRight(), eye);
                    segList.addAll(Tp);
                }
            }else
                segList.addAll(Tp);

            for (Segment seg : tree.getData()){
                if (eye.isInSight(seg.getFrom()))
                    segList.add(seg);
                else if (eye.isInSight(seg.getTo()))
                    segList.add(seg);
            }

            if (Tm == null) {
                if (tree.getLeft() != null) {
                    Tm = paintersAlgorithm((SegmentBSPTree) tree.getLeft(), eye);
                    segList.addAll(Tm);
                }
            }else
                segList.addAll(Tm);
        }
        else{ //pov € h
            //eye on the cutline so does not see segments contained in this node.
            if (Tm == null)
                Tm = paintersAlgorithm((SegmentBSPTree) tree.getLeft(), eye);
            segList.addAll(Tm);
            if (Tp == null)
                Tp = paintersAlgorithm((SegmentBSPTree) tree.getRight(), eye);
            segList.addAll(Tp);
        }

        return segList;
    }

}
