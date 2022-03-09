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
            if (thisArray[i] != oArray[i])
                return false;
        }

        return (this.getEColor() == seg.getEColor());
    }

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

    static public Segment[] cut(Segment lineToCut, Segment cutLine){
        //get the hyperplan equation (general 2D line equation) of cutline
        double[] coord = cutLine.get();
        double x11 = coord[0],
                y11 = coord[1],
                x21 = coord[2],
                y21 = coord[3];
        double dx1 = x21-x11;
        double dy1 = y21-y11;

        double a1, b1, c1; //h: a1*xi1 + b1*yi1 + c1 = 0
        //case dx = 0 => vertical line
        if (Math.abs(dx1) < 1e-4){
            a1 = 1;
            b1 = 0;
            c1 = -x11;
        }
        //case dy = 0 => horizontal line
        else if (Math.abs(dy1) < 1e-4){
            a1 = 0;
            b1 = 1;
            c1 = -y11;
        }
        //general case
        else{
            b1 = 1;
            a1 = -(y11-y21)/(x11-x21);
            c1 = -a1*x11-y11;
        }

        //get second line equation
        coord = lineToCut.get();
        double x12 = coord[0],
                y12 = coord[1],
                x22 = coord[2],
                y22 = coord[3];
        //get intersection coordinates
        double dx2 = x22-x12;
        double dy2 = y22-y12;

        double a2, b2, c2; //h: a*x + b*y + c = 0
        //case dx = 0 => vertical line
        if (Math.abs(dx2) < 1e-4){
            a2 = 1;
            b2 = 0;
            c2 = -x12;
        }
        //case dy = 0 => horizontal line
        else if (Math.abs(dy2) < 1e-4){
            a2 = 0;
            b2 = 1;
            c2 = -y12;
        }
        //general case
        else{
            b2 = 1;
            a2 = -(y12-y22)/(x12-x22);
            c2 = -a2*x12-y12;
        }

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

    static public BSPTree<Segment> makeBasicTree(Segment[] S){
        BSPTree<Segment> tree;
        if (S.length <= 1){
            //le set S de segments est unique, donc il s'agit d'une feuille, on crée donc un arbe qui est en réalité une feuille avec la data.
            tree = new BSPTree<Segment>(S[0], null, null);
        }else{
            // segment correspondant à la ligne de découpe
            Segment l = S[0];

            //calcul des paramètres d'équation de la droite (équation de l'hyperplan)
            double[] coord = l.get();
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

            //on crée les listes des segments se trouvant à gauche et à droite de la ligne de découpe
            ArrayList<Segment> Sminus = new ArrayList<Segment>();
            ArrayList<Segment> Splus = new ArrayList<Segment>();

            double[] segCoord;
            double r1, r2;

            //vérification, pour chaque segment, de sa position par rapport à la ligne de découpe pour en créer le bsp
            tree = new BSPTree<Segment>();
            for (Segment seg : S){
                if (seg != l){
                    segCoord = seg.get();

                    r1 = a*segCoord[0] + b*segCoord[1] + c;
                    r2 = a*segCoord[2] + b*segCoord[2] + c;

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
            BSPTree<Segment> left = makeBasicTree(Sminus.toArray(new Segment[0]));
            BSPTree<Segment> right = makeBasicTree(Splus.toArray(new Segment[0]));

            //add left and right subtree to the tree
            tree.setLeft(left);
            tree.setRight(right);
        }

        return tree;
    }
}
