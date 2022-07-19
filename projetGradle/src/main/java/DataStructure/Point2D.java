package DataStructure;

import Console.TestMain;

/**
 * Data structure of a point. Actually is a pair of doubles.
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class Point2D extends Pair<Double, Double>{
    //Constructors

    /**
     * Constructor.
     * @param x
     *      X coordinate of the point.
     * @param y
     *      Y coordinate of the point.
     */
    public Point2D(double x, double y){
        super(x, y);
    }

    /**
     * Constructor.
     */
    public Point2D(){
        this(0,0);
    }

    //Getters

    /**
     * Get the X coordinate of the point.
     * @return
     *      X coordinate.
     */
    public double getX(){return getL();}

    /**
     * Get the Y coordinate of the point.
     * @return
     *      Y coordinate.
     */
    public double getY(){return getR();}

    /**
     * Check if the point is on a segment.
     * @param s
     *      Segment to check.
     * @return
     *      True if this point is on the given segment.
     */
    public boolean on(Segment s){
        double[] abc = Segment.getCutlineParameters(s.getFrom(), s.getTo());
        double a = abc[0], b = abc[1], c = abc[2];

        return Math.abs(a * getX() + b * getY() + c) < TestMain.epsilon;
    }

    //Overrides

    /**
     * Check if another object is equivalent to this point.
     * @param o
     *      Other object to compare.
     * @return
     *      True if the other object is equivalent to this point.
     */
    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Point2D))
            return false;

        Point2D other = (Point2D) o;

        return Math.abs(other.getX() - getX()) < TestMain.epsilon && Math.abs(other.getY() - getY()) < TestMain.epsilon;
    }
}
