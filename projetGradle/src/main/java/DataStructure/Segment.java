package DataStructure;

import java.util.ArrayList;

/**
 * Data structure of a segment.
 * Stores its coordinates and color information
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class Segment {
    private double x1, y1, x2, y2;
    private EColor color;

    //constructors

    /**
     * Constructor.
     */
    public Segment(){
        x1 = 0; y1 = 0;
        x2 = 0; y2 = 0;
        color = EColor.BLACK;
    }

    /**
     * Constructor.
     * @param x1
     *      x coordinate of the begin point.
     * @param y1
     *      y coordinate of the begin point.
     * @param x2
     *      x coordinate of the end point.
     * @param y2
     *      y coordinate of the end point.
     */
    public Segment(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        color = EColor.BLACK;
    }

    /**
     * Constructor.
     * @param x1
     *      x coordinate of the begin point.
     * @param y1
     *      y coordinate of the begin point.
     * @param x2
     *      x coordinate of the end point.
     * @param y2
     *      y coordinate of the end point.
     * @param color
     *      Enum value representing the color of the segment.
     */
    public Segment(double x1, double y1, double x2, double y2, EColor color){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        if (color != null)
            this.color = color;
        else
            this.color = EColor.BLACK;
    }

    //Overrides

    /**
     * Check if a Segment is equals to another object.
     * @param o
     *      The other object to compare with. (Has to be a Segment instance)
     * @return
     *      If the 2 objects are equals.
     */
    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Segment))
            return false;

        Segment seg = (Segment) o;

        double[] thisArray = this.get();
        double[] oArray = seg.get();
        for(int i = 0; i < 4; i++){
            if (Math.abs(thisArray[i]-oArray[i]) > 1e-4)
                return false;
        }

        return (this.getEColor() == seg.getEColor());
    }

    /**
     * Representation of the segment.
     * @return
     *      Coordinates of first and last points and the color.
     */
    @Override
    public String toString(){
        return "Segment: (x1:" + Double.toString(x1)
                + ", y1:" + Double.toString(y1)
                + ", x2:" + Double.toString(x2)
                + ", y2:" + Double.toString(y2)
                + "), color: " + getEColor().toString();
    }

    //setters

    /**
     * setter of the segment data.
     * @param x1
     *      x coordinate of the begin point.
     * @param y1
     *      y coordinate of the begin point.
     * @param x2
     *      x coordinate of the end point.
     * @param y2
     *      y coordinate of the end point.
     * @param color
     *      Enum value representing the color of the segment.
     */
    public void set(double x1, double y1, double x2, double y2, EColor color){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        if (color != null)
            this.color = color;
    }

    /**
     * setter of the color.
     * @param color
     *      Enum value representing the color of the segment.
     */
    public void setColor(EColor color){
        if (color != null)
            this.color = color;
    }

    //getters

    /**
     * Get the coordinates of the begin and the end points of the segment.
     * @return
     *      List of coordinates.
     */
    public double[] get(){
        return new double[]{x1,y1,x2,y2};
    }

    /**
     * Get coordinates of the begin point of the segment.
     * @return
     *      List of x and y coordinates.
     */
    public double[] getFrom() {
        return new double[]{x1,y1};
    }

    /**
     * Get coordinates of the end point of the segment.
     * @return
     *      List of x and y coordinates.
     */
    public double[] getTo() {
        return new double[]{x2,y2};
    }

    /**
     * Get the color of the object.
     * @return
     *      EColor Enum value of the segment color.
     */
    public EColor getEColor(){
        return color;
    }


    public double getDistance(){
        double dx, dy;

        dx = x2 - x1;
        dy = y2 - y1;

        return Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
    }

    //static methods

    /**
     * Allows to cut a segment with another.
     * @param lineToCut
     *      Segment that will be cut.
     * @param cutLine
     *      Segment that will be extended to a line to cut.
     * @return
     *      Array of Segments representing the 2 parts of the segment that has been cut.
     */
    static public Segment[] cut(Segment lineToCut, Segment cutLine){
        //get the hyperplan equation (general 2D line equation) of cutline
        double[] coord = cutLine.get();
        double x11 = coord[0],
                y11 = coord[1],
                x21 = coord[2],
                y21 = coord[3];
        double dx1 = x21-x11;
        double dy1 = y21-y11;

        double[] abc = getCutlineParameters(coord);
        double a1 = abc[0], b1 = abc[1], c1 = abc[2]; //h: a1*xi1 + b1*yi1 + c1 = 0

        //get second line equation
        coord = lineToCut.get();
        double x12 = coord[0],
                y12 = coord[1],
                x22 = coord[2],
                y22 = coord[3];
        //get intersection coordinates
        double dx2 = x22-x12;
        double dy2 = y22-y12;

        abc = getCutlineParameters(coord);
        double a2 = abc[0], b2 = abc[1], c2 = abc[2]; //h: a*x + b*y + c = 0

        //get intersection coordinates
        double x, y;
        if (Math.abs(dy1) < 1e-4 && Math.abs(dx2) < 1e-4){
            x = x12;
            y = y11;
        }
        else if(Math.abs(dy2) < 1e-4 && Math.abs(dx1) < 1e-4){
            x = x11;
            y = y12;
        }
        else if (Math.abs(dx1) < 1e-4){
            x = x11;
            y = ((-c2/a2)+(c1/a1))/((-b1/a1)+(b2/a2));
        }
        else if (Math.abs(dy1) < 1e-4){
            x = ((-c2/b2)+(c1/b1))/((-a1/b1)+(a2/b2));
            y = y11;
        }
        else if (Math.abs(dx2) < 1e-4){
            x = x12;
            y = ((-c2/a2)+(c1/a1))/((-b1/a1)+(b2/a2));
        }
        else if (Math.abs(dy2) < 1e-4){
            x = ((-c2/b2)+(c1/b1))/((-a1/b1)+(a2/b2));
            y = y12;
        }
        else{
            x = ((-c2/b2)+(c1/b1))/((-a1/b1)+(a2/b2));
            y = ((-c2/a2)+(c1/a1))/((-b1/a1)+(b2/a2));
        }


        Segment[] S = {new Segment(x12, y12, x, y), new Segment(x, y, x22, y22)};
        return S;
    }

    /**
     * Get the 2D hyperplan parameters.
     * @param coord
     *      2 points coordinates of the line.
     * @return
     *      Array of 3 doubles representing a, b and c parameters (respectively).respectivement
     */
    static public double[] getCutlineParameters(double[] coord){
        double x1 = coord[0],
                y1 = coord[1],
                x2 = coord[2],
                y2 = coord[3];
        double dx = x2-x1;
        double dy = y2-y1;
        double a, b, c; //h: a*xi + b*yi + c = 0
        //case dx = 0 => vertical line
        if (Math.abs(dx) < 1e-4){
            a = 1;
            b = 0;
            c = -x1;
        }
        //case dy = 0 => horizontal line
        else if (Math.abs(dy) < 1e-4){
            a = 0;
            b = 1;
            c = -y1;
        }
        //general case
        else{
            b = 1;
            a = -(y1-y2)/(x1-x2);
            c = -a*x1-y1;
        }

        return new double[]{a,b,c};
    }

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
            double[] abc = getCutlineParameters(l.get());
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
                    segCoord = seg.get();

                    r1 = a*segCoord[0] + b*segCoord[1] + c;
                    r2 = a*segCoord[2] + b*segCoord[3] + c;

                    if (Math.abs(r1) < 1e-4 && Math.abs(r2) < 1e-4){
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
                        double[] cutLineCoord = cutResult[0].getFrom();
                        double x = cutLineCoord[0], y = cutLineCoord[1];
                        double result = a*x + b*y + c;

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

                for (Segment s : S){
                    coord = s.get();
                    x1 = coord[0]; y1 = coord[1]; //pt1
                    x2 = coord[2]; y2 = coord[3]; //pt2
                    pt1 = false; pt2 = false;

                    borne = parent;
                    while(borne != null){
                        abc = getCutlineParameters(borne.getData().get(0).get());
                        a = abc[0]; b = abc[1]; c = abc[2];
                        if (Math.abs(a*x1+b*y1+c) < 1e-4){
                            pt1 = true;
                            break;
                        }

                        borne = borne.getParent();
                    }
                    if (pt1){
                        //check pt2
                        borne = parent;
                        while(borne != null){
                            abc = getCutlineParameters(borne.getData().get(0).get());
                            a = abc[0]; b = abc[1]; c = abc[2];
                            if (Math.abs(a*x2+b*y2+c) < 1e-4){
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
                }

                if(!pt1 || !pt2) {
                    tree = makeBasicTree(S, parent, true);
                }
                else{
                    //make freesplit
                    //rework S with l as first segment.
                    Segment[] newS = new Segment[S.length];
                    newS[0] = freeSplitSeg;
                    int n = 1;
                    for (int i = 0; i < S.length; i++){
                        if (S[i] != newS[0])
                            newS[i+n] = S[i];
                        else
                            n = 0;
                    }

                    tree = makeBasicTree(newS, parent, true);

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
    static public ArrayList<Segment> paintersAlgorithm(BSPTree<Segment> tree, double[] POVposition){
        //if leaf: just return data
        if (tree.isLeaf()) {
            return tree.getData();
        }

        //set all the data that will be needed
        Segment h = tree.getData().get(0);
        double[] abc = getCutlineParameters(h.get());
        double a = abc[0], b = abc[1], c = abc[2];
        double x = POVposition[0], y = POVposition[1];
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


    static public double[] getIntersection(double[] abc1, double[] abc2){
        double a1 = abc1[0], b1 = abc1[1], c1 = abc1[2],
                a2 = abc2[0], b2 = abc2[1], c2 = abc2[2];
        //System.out.println("a1: "+a1+", b1: "+b1+", c1: "+c1);
        //System.out.println("a2: "+a2+", b2: "+b2+", c2: "+c2);

        double x, y;

        //different cases by parameters values
        if (Math.abs(a1) < 1e-4){
            // line 1: horizontal
            if (Math.abs(a2) < 1e-4){
                //line 2: horizontal
                x = 0;
                if (Math.abs(c2-c1) > 1e-4)   //parallels
                    return null;
            }
            else if (Math.abs(b2) < 1e-4)//line2: vertival
                x = -c2/b2;
            else
                x = (b2*c1/b1 - c2)/a2;
            y = -c1/b1;
        }
        else if (Math.abs(b1) < 1e-4){
            //line 1: vertical
            x = -c1/b1;
            if (Math.abs(a2) < 1e-4)//line 2: horizontal
                y = -c2/b2;
            else if (Math.abs(b2) < 1e-4){
                //line 2: vertical
                y = 0;
                if (Math.abs(c2-c1) > 1e-4)   //parallels
                    return null;
            }
            else
                y = (a2*c1/b1 - c2)/b2;
        }
        else{
            if (Math.abs(a2) < 1e-4){
                //line 2: horizontal
                x = (b1*c2/b2 - c1)/a1;
                y = -c2/b2;
            }
            else if (Math.abs(b2) < 1e-4){
                System.out.println("here");
                //line 2: vertical
                x = -c2/a2;
                y = (a1*c2/a2 - c1)/b1;
            }
            else{
                if (Math.abs(b2-b1) < 1e-4) {
                    x = -(c1 - c2) / (a1 - a2);
                    y = -c2 + a2 * (c1 - c2) / (a1 - a2);
                }
                else if (Math.abs(a2-a1) < 1e-4) // parallels
                    return null;
                else{
                    x = (-b2 * (c1 - c2) / (b2 - b1) - c2) / (a2 + b2 * (a1 - a2) / (b2 - b1));
                    y = (a1 - a2) * (-b2 * (c1 - c2) / (b2 - b1) - c2) / (a2 * (b2 - b1) + b2 * (a1 - a2));
                }
            }
        }

        return new double[]{x, y};
    }


    static public Double getAngle(Segment dirLine, double[] pt){
        double [] abc1 = Segment.getCutlineParameters(dirLine.get()), abc2;
        double a1 = abc1[0], b1 = abc1[1], c1 = abc1[2];
        double a2, b2, c2;

        double[] x1y1 = dirLine.getFrom();

        double x1 = x1y1[0], x2, dx, y1 = x1y1[1], y2, dy;

        //perpendicular line parameters
        if (Math.abs(a1) < 1e-4){
            //line 1: horizontal
            a2 = 1;
            b2 = 0;
            //line 2: vertical
        }
        else if (Math.abs(b1) < 1e-4){
            //line 1: vertical
            a2 = 0;
            b2 = 1;
            //line 2: horizontal
        }
        else{
            //general case
            a2 = -1.0/a1;
            b2 = b1;
        }
        c2 = -a2*pt[0] - b2*pt[1];

        //callecting line 2 parameters
        abc2 = new double[]{a2, b2, c2};

        //calculate intersection point coordinates
        double[] x2y2 = Segment.getIntersection(abc1, abc2);
        if (x2y2 == null)
            return null;
        x2 = x2y2[0]; y2 = x2y2[1];

        //calculate opposite and Adjascent segments (trigono)
        Segment O, A;
        O = new Segment(pt[0], pt[1], x2, y2);
        A = new Segment(x1, y1, x2, y2);

        //calculate the angle
        //System.out.println("distance O: " + O.getDistance());
        //System.out.println("distance A: " + A.getDistance());
        return Math.toDegrees(Math.atan(O.getDistance()/ A.getDistance()));
    }
}
