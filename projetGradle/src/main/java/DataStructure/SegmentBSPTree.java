package DataStructure;

import Console.TestMain;

import javax.management.InvalidAttributeValueException;
import java.util.*;

public class SegmentBSPTree extends BSPTree<Segment>{
    protected boolean firstOnEdge = false, secondOnEdge = false;
    public SegmentBSPTree(){
        super();
    }
    public SegmentBSPTree(LinkedList<Segment> d, SegmentBSPTree l, SegmentBSPTree r){
        super(d, l, r);
    }
    public SegmentBSPTree(Segment d, SegmentBSPTree l, SegmentBSPTree r){
        super(d, l, r);
    }
    public SegmentBSPTree(SegmentBSPTree p, LinkedList<Segment> d, SegmentBSPTree l, SegmentBSPTree r){
        super(p, d, l, r);
    }
    public SegmentBSPTree(SegmentBSPTree p, Segment d, SegmentBSPTree l, SegmentBSPTree r){
        super(p, d, l, r);
    }

    /*@Override
    public void setParent(BSPTree<Segment> parent){
        this.parent = parent;

        //set boolean flags for freesplit

        Point2D pt1, pt2;
        BSPTree<Segment> ancestor = parent;
        Segment edge;
        for (Segment seg: data){
            pt1 = seg.getFrom();
            pt2 = seg.getTo();
            edge = ancestor.getData().get(0);

            if (pt1.on(edge))
                firstOnEdge = true;
            if (pt2.on(edge))
                secondOnEdge = true;

            if (firstOnEdge && secondOnEdge)
                break;

            ancestor = ancestor.getParent();
        }
    }*/

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
                int i = 0, j = S.length - 1;
                Segment temp;
                while(i < j){
                    if (S[i].isFreeSplit())
                        i++;
                    else{
                        temp = S[i];
                        S[i] = S[j];
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

    /*
    /**
     * Create a BSPTree following the order of the given segment list.
     * @param S
     *      List of Segments representing the scene to partition.
     * @param parent
     *      Parent node to which will be added the sub-tree that will be created.
     *      If null, the root is created.
     * @return
     *      The BSPTree of the scene.
     *-/
    static public SegmentBSPTree makeBasicTree(Segment[] S, SegmentBSPTree parent){
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
     *-/
    static public SegmentBSPTree makeBasicTree(Segment[] S, SegmentBSPTree parent, boolean switchToFreeSplit){

        SegmentBSPTree tree;
        if (S.length == 1){
            //le set S de segments est unique, donc il s'agit d'une feuille, on crée donc un arbe qui est en réalité une feuille avec la data.
            tree = new SegmentBSPTree(parent, S[0], null, null);
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
            LinkedList<Segment> Sminus = new LinkedList<>();
            LinkedList<Segment> Splus = new LinkedList<>();

            double[] segCoord;
            double r1, r2;

            //vérification, pour chaque segment, de sa position par rapport à la ligne de découpe pour en créer le bsp
            tree = new SegmentBSPTree();
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
     *-/
    static public SegmentBSPTree makeFreeSplitTree(Segment[] S, SegmentBSPTree parent) {
        SegmentBSPTree tree;

        if (S.length == 1){
            //le set S de segments est unique, donc il s'agit d'une feuille, on crée donc un arbe qui est en réalité une feuille avec la data.
            tree = new SegmentBSPTree(S[0], null, null);
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
                    *-/
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
    */

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
                try{
                    if (eye.isInSight(s.getFrom()) || eye.isInSight(s.getTo()))
                        data.add(s);
                }catch (InvalidAttributeValueException ignored){
                }
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
                try {
                    if (eye.isInSight(seg.getFrom()))
                        segList.add(seg);
                    else if (eye.isInSight(seg.getTo()))
                        segList.add(seg);
                }catch(InvalidAttributeValueException ignored){}
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
                try {
                    if (eye.isInSight(seg.getFrom()))
                        segList.add(seg);
                    else if (eye.isInSight(seg.getTo()))
                        segList.add(seg);
                }catch(InvalidAttributeValueException ignored){}
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
