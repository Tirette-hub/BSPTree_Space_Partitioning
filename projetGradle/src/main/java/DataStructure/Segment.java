package DataStructure;

import Console.TestMain;

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
public class Segment implements IVector {
    protected Point2D firstPoint, lastPoint;
    private EColor color;

    // interface implementation

    /**
     * Get the cartesian distance between the 2 points.
     * @return
     *      Distance between the 2 points.
     */
    public double getDistance(){
        double dx, dy;

        dx = lastPoint.getX() - firstPoint.getX();
        dy = lastPoint.getY() - firstPoint.getY();

        return Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
    }

    /**
     * Get segment x component.
     * @return
     *      x component.
     */
    public double getX(){
        return lastPoint.getX() - firstPoint.getX();
    }

    /**
     * Get segment y component.
     * @return
     *      y component.
     */
    public double getY(){
        return lastPoint.getY() - firstPoint.getY();
    }

    /**
     * Check if this vector is a multiple of another one.
     * @param o
     *      Another Segment to compare with.
     * @return
     *      If the 2 segments are multiples of each other.
     */
    public boolean isMultipleOf(Segment o){
        double x = getX(), y = getY();
        double deltaX, deltaY;
        deltaX = o.getX();
        deltaY = o.getY();

        if (Math.abs(deltaX) < TestMain.epsilon && Math.abs(deltaY) < TestMain.epsilon)
            return false;
        else if (Math.abs(deltaX) < TestMain.epsilon)
            //o is a vertical line
            return (Math.abs(x) < TestMain.epsilon); //this also is a vertical line
        else if (Math.abs(deltaY) < TestMain.epsilon)
            //o is a horizontal line
            return (Math.abs(y) < TestMain.epsilon); //this also is a horizontal line;
        else
            return (Math.abs(x/deltaX - y/deltaY) < TestMain.epsilon);
    }

    /**
     * Get the factor between 2 segments if they are multiple of each other.
     * @param o
     *      The other segment to campare with.
     * @return
     *      The factor between the 2 vectors.
     */
    public Double getFactor(Segment o){
        double x = getX(), y = getY();
        double deltaX, deltaY;
        deltaX = o.getX();
        deltaY = o.getY();

        if (isMultipleOf(o)){
            if (Math.abs(deltaX) < TestMain.epsilon && Math.abs(deltaY) < TestMain.epsilon)
                return 0.0;
            else if (Math.abs(deltaX) < TestMain.epsilon)
                //o is a vertical line
                if (Math.abs(x) < TestMain.epsilon)
                    //this also is a vertical line
                    return y/deltaY;
                else if (Math.abs(deltaY) < TestMain.epsilon)
                    //o is a horizontal line
                    if (Math.abs(y) < TestMain.epsilon)
                        //this also is a horizontal line;
                        return x/deltaX;
                    else
                    if (Math.abs(x/deltaX - y/deltaY) < TestMain.epsilon)
                        //the factor is the same for x and y component
                        return x/deltaX;
        }

        return null;
    }

    /**
     * Get a perpendicular vector (90 degrees to the right) from the beginning of this vector.
     * @return
     *      A new vector representing the perpendicular.
     */
    public Segment getPerp(){
        return new Segment(getFrom().getX(), getFrom().getY(), getFrom().getX() + getTo().getY() -getFrom().getY(), getFrom().getY() - getTo().getX() + getFrom().getX());
    }

    //constructors

    /**
     * Constructor.
     */
    public Segment(){
        firstPoint = new Point2D();
        lastPoint = new Point2D();
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
        firstPoint = new Point2D(x1, y1);
        lastPoint = new Point2D(x2, y2);
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
        firstPoint = new Point2D(x1, y1);
        lastPoint = new Point2D(x2, y2);
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

        if (seg.getFrom() != getFrom() || seg.getTo() != getTo())
            return false;

        return (this.getEColor() == seg.getEColor());
    }

    /**
     * Representation of the segment.
     * @return
     *      Coordinates of first and last points and the color.
     */
    @Override
    public String toString(){
        return "Segment: (x1:" + firstPoint.getX()
                + ", y1:" + firstPoint.getY()
                + ", x2:" + lastPoint.getX()
                + ", y2:" + lastPoint.getY()
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
        firstPoint = new Point2D(x1, y1);
        lastPoint = new Point2D(x2, y2);
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
     * Get coordinates of the begin point of the segment.
     * @return
     *      List of x and y coordinates.
     */
    public Point2D getFrom() {
        return firstPoint;
    }

    /**
     * Get coordinates of the end point of the segment.
     * @return
     *      List of x and y coordinates.
     */
    public Point2D getTo() {
        return lastPoint;
    }

    /**
     * Get the color of the object.
     * @return
     *      EColor Enum value of the segment color.
     */
    public EColor getEColor(){
        return color;
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
        double x11 = cutLine.getFrom().getX(),
                y11 = cutLine.getFrom().getY(),
                x21 = cutLine.getTo().getX(),
                y21 = cutLine.getTo().getY();
        double dx1 = x21-x11;
        double dy1 = y21-y11;

        double[] abc = getCutlineParameters(cutLine.getFrom(), cutLine.getTo());
        double a1 = abc[0], b1 = abc[1], c1 = abc[2]; //h: a1*xi1 + b1*yi1 + c1 = 0

        //get second line equation
        double x12 = lineToCut.getFrom().getX(),
                y12 = lineToCut.getFrom().getY(),
                x22 = lineToCut.getTo().getX(),
                y22 = lineToCut.getTo().getY();
        //get intersection coordinates
        double dx2 = x22-x12;
        double dy2 = y22-y12;

        abc = getCutlineParameters(lineToCut.getFrom(), lineToCut.getTo());
        double a2 = abc[0], b2 = abc[1], c2 = abc[2]; //h: a*x + b*y + c = 0

        //get intersection coordinates
        double x, y;
        if (Math.abs(dy1) < TestMain.epsilon && Math.abs(dx2) < TestMain.epsilon){
            x = x12;
            y = y11;
        }
        else if(Math.abs(dy2) < TestMain.epsilon && Math.abs(dx1) < TestMain.epsilon){
            x = x11;
            y = y12;
        }
        else if (Math.abs(dx1) < TestMain.epsilon){
            x = x11;
            y = ((-c2/a2)+(c1/a1))/((-b1/a1)+(b2/a2));
        }
        else if (Math.abs(dy1) < TestMain.epsilon){
            x = ((-c2/b2)+(c1/b1))/((-a1/b1)+(a2/b2));
            y = y11;
        }
        else if (Math.abs(dx2) < TestMain.epsilon){
            x = x12;
            y = ((-c2/a2)+(c1/a1))/((-b1/a1)+(b2/a2));
        }
        else if (Math.abs(dy2) < TestMain.epsilon){
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
     * @param from
     *      First point coordinate of the line.
     * @param to
     *      Last point coordinate of the line.
     * @return
     *      Array of 3 doubles representing a, b and c parameters (respectively).
     */
    static public double[] getCutlineParameters(Point2D from, Point2D to){
        double x1 = from.getX(),
                y1 = from.getY(),
                x2 = to.getX(),
                y2 = to.getY();
        double dx = x2-x1;
        double dy = y2-y1;
        double a, b, c; //h: a*xi + b*yi + c = 0
        //case dx = 0 => vertical line
        if (Math.abs(dx) < TestMain.epsilon){
            a = 1;
            b = 0;
            c = -x1;
        }
        //case dy = 0 => horizontal line
        else if (Math.abs(dy) < TestMain.epsilon){
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
            double[] abc = getCutlineParameters(l.getFrom(), l.getTo());
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

                for (Segment s : S){
                    x1 = s.getFrom().getX(); y1 = s.getFrom().getY(); //pt1
                    x2 = s.getTo().getX(); y2 = s.getTo().getY(); //pt2
                    pt1 = false; pt2 = false;

                    borne = parent;
                    while(borne != null){
                        abc = getCutlineParameters(borne.getData().get(0).getFrom(), borne.getData().get(0).getTo());
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
                            abc = getCutlineParameters(borne.getData().get(0).getFrom(), borne.getData().get(0).getTo());
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
    static public ArrayList<Segment> paintersAlgorithm(BSPTree<Segment> tree, Point2D POVposition){
        //if leaf: just return data
        if (tree.isLeaf()) {
            return tree.getData();
        }

        //set all the data that will be needed
        Segment h = tree.getData().get(0);
        double[] abc = getCutlineParameters(h.getFrom(), h.getTo());
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

    /**
     * Get the intersection point coordinates between two lines.
     * @param abc1
     *      Hyperplane 1 equation parameters.
     * @param abc2
     *      Hyperplane 2 equation parameters.
     * @return
     *      Coordinates of the intersection.
     */
    static public double[] getIntersection(double[] abc1, double[] abc2){
        double a1 = abc1[0], b1 = abc1[1], c1 = abc1[2],
                a2 = abc2[0], b2 = abc2[1], c2 = abc2[2];

        double x, y;

        //different cases by parameters values
        if (Math.abs(a1) < TestMain.epsilon){
            // line 1: horizontal
            if (Math.abs(a2) < TestMain.epsilon){
                //line 2: horizontal
                x = 0;
                if (Math.abs(c2-c1) > TestMain.epsilon)   //parallels
                    return null;
            }
            else if (Math.abs(b2) < TestMain.epsilon)//line2: vertival
                x = -c2/b2;
            else
                x = (b2*c1/b1 - c2)/a2;
            y = -c1/b1;
        }
        else if (Math.abs(b1) < TestMain.epsilon){
            //line 1: vertical
            x = -c1/a1;
            if (Math.abs(a2) < TestMain.epsilon)//line 2: horizontal
                y = -c2/b2;
            else if (Math.abs(b2) < TestMain.epsilon){
                //line 2: vertical
                y = 0;
                if (Math.abs(c2-c1) > TestMain.epsilon)   //parallels
                    return null;
            }
            else
                y = (a2*c1/b1 - c2)/b2;
        }
        else{
            if (Math.abs(a2) < TestMain.epsilon){
                //line 2: horizontal
                x = (b1*c2/b2 - c1)/a1;
                y = -c2/b2;
            }
            else if (Math.abs(b2) < TestMain.epsilon){
                System.out.println("here");
                //line 2: vertical
                x = -c2/a2;
                y = (a1*c2/a2 - c1)/b1;
            }
            else{
                if (Math.abs(b2-b1) < TestMain.epsilon) {
                    x = -(c1 - c2) / (a1 - a2);
                    y = -c2 + a2 * (c1 - c2) / (a1 - a2);
                }
                else if (Math.abs(a2-a1) < TestMain.epsilon) // parallels
                    return null;
                else{
                    x = (-b2 * (c1 - c2) / (b2 - b1) - c2) / (a2 + b2 * (a1 - a2) / (b2 - b1));
                    y = (a1 - a2) * (-b2 * (c1 - c2) / (b2 - b1) - c2) / (a2 * (b2 - b1) + b2 * (a1 - a2));
                }
            }
        }

        return new double[]{x, y};
    }

    /**
     * Get the angle in degrees between a reference line and a point.
     * @param dirLine
     *      Segment representing the reference line
     * @param pt
     *      Point to get the angle.
     * @return
     *      Angle in degrees.
     */
    static public Double getAngle(Segment dirLine, Point2D pt){
        double [] abc1 = Segment.getCutlineParameters(dirLine.getFrom(), dirLine.getTo()), abc2;
        double a1 = abc1[0], b1 = abc1[1], c1 = abc1[2];
        double a2, b2, c2;

        double x1 = dirLine.getFrom().getX(), x2, dx, y1 = dirLine.getFrom().getY(), y2, dy;

        //perpendicular line parameters
        if (Math.abs(a1) < TestMain.epsilon){
            //line 1: horizontal
            a2 = 1;
            b2 = 0;
            //line 2: vertical
        }
        else if (Math.abs(b1) < TestMain.epsilon){
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
        c2 = -a2*pt.getX() - b2*pt.getY();

        //collecting line 2 parameters
        abc2 = new double[]{a2, b2, c2};

        //calculate intersection point coordinates
        double[] x2y2 = Segment.getIntersection(abc1, abc2);
        if (x2y2 == null)
            return null;
        x2 = x2y2[0]; y2 = x2y2[1];

        //calculate opposite and Adjascent segments (trigono)
        Segment O, A;
        O = new Segment(pt.getX(), pt.getY(), x2, y2);
        A = new Segment(x1, y1, x2, y2);

        //calculate the angle
        //System.out.println("distance O: " + O.getDistance());
        //System.out.println("distance A: " + A.getDistance());
        return Math.toDegrees(Math.atan(O.getDistance()/ A.getDistance()));
    }

    /**
     * Get the x coordinate for the projection.
     * @param pt
     *      Point to project.
     * @param a
     *      a parameter of the hyperplane equation of the eye direction line.
     * @param
     *      b parameter of the hyperplane equation of the eye direction line.
     * @param c
     *      c parameter of the hyperplane equation of the eye direction line.
     * @param angle
     *      Angle between the eye direction line and the point to project.
     * @param FOV
     *      FOV of the eye.
     * @param screenWidth
     *      Width of the screen where the point will be projected.
     * @return
     *      X position in the screen where the point is projected.
     */
    static public Double getProjection(Point2D pt, double a, double b, double c, double angle, double FOV, double screenWidth){
        double result;

        double left = screenWidth/2 - angle*screenWidth/FOV,
                right = screenWidth/2 + angle*screenWidth/FOV;

        boolean onH = Math.abs(a*pt.getX()+b*pt.getY()+c) < TestMain.epsilon;

        if (angle <= 180 || Math.abs(angle-360) < TestMain.epsilon)     //direction to the top, bottom or right
            if (onH)                                        //pt1 on the guide line
                result = screenWidth/2;
            else if (a*pt.getX()+b*pt.getY()+c > 0)                 // upper part of direction guideline is h+
                result = left;                              // so to the left of the eye
            else                                            // lower part of direction guideline is h-
                result = right;                             // so to the right of the eye
        else                                                //direction to the left
            if (onH)                                        //pt1 on the guide line
                result = screenWidth/2;
            else if (a*pt.getX()+b*pt.getY()+c > 0)                 // upper part of direction guideline is h+
                result = right;                             // so to the right of the eye
            else                                            // lower part of direction guideline is h-
                result = left;                              // so to the left of the eye

        return result;
    }
}
