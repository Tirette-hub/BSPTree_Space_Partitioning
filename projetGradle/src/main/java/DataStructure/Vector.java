package DataStructure;

/**
 * Data structure of a vector.
 * Extends from Segment data structure.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class Vector extends Segment{
    private double x, y;

    //constructors

    /**
     * Constructor.
     */
    public Vector(){
        super();
        x = 0;
        y = 0;
    }

    /**
     * Constructor.
     * @param s
     *      Segment to get the vector of.
     */
    public Vector(Segment s){
        double[] xy1 = s.getFrom();
        double[] xy2 = s.getTo();
        x1 = xy1[0];
        y1 = xy1[1];
        x2 = xy2[0];
        y2 = xy2[1];
        x = x2 - x1;
        y = y2 - y1;
    }

    //getters

    /**
     * Check if this vector is a multiple of another one.
     * @param o
     *      Another Segment to compare with.
     * @return
     *      If the 2 segments are multiples of each other.
     */
    public boolean isMultipleOf(Segment o){
        double[] xy1 = o.getFrom();
        double[] xy2 = o.getTo();
        double deltaX, deltaY;
        deltaX = xy2[0] - xy1[0];
        deltaY = xy2[1] - xy1[1];

        if (Math.abs(deltaX) < 1e-4 && Math.abs(deltaY) < 1e-4)
            return false;
        else if (Math.abs(deltaX) < 1e-4)
            //o is a vertical line
            return (Math.abs(x) < 1e-4); //this also is a vertical line
        else if (Math.abs(deltaY) < 1e-4)
            //o is a horizontal line
            return (Math.abs(y) < 1e-4); //this also is a horizontal line;
        else
            return (Math.abs(x/deltaX - y/deltaY) < 1e-4);
    }

    /**
     * Get the factor between 2 segments if they are multiple of each other.
     * @param o
     *      The other segment to campare with.
     * @return
     *      The factor between the 2 vectors.
     */
    public Double getFactor(Segment o){
        double[] xy1 = o.getFrom();
        double[] xy2 = o.getTo();
        double deltaX, deltaY;
        deltaX = xy2[0] - xy1[0];
        deltaY = xy2[1] - xy1[1];

        if (isMultipleOf(o)){
            if (Math.abs(deltaX) < 1e-4 && Math.abs(deltaY) < 1e-4)
                return 0.0;
            else if (Math.abs(deltaX) < 1e-4)
                //o is a vertical line
                if (Math.abs(x) < 1e-4)
                    //this also is a vertical line
                    return y/deltaY;
            else if (Math.abs(deltaY) < 1e-4)
                //o is a horizontal line
                if (Math.abs(y) < 1e-4)
                    //this also is a horizontal line;
                    return x/deltaX;
            else
                if (Math.abs(x/deltaX - y/deltaY) < 1e-4)
                    //the factor is the same for x and y component
                    return x/deltaX;
        }

        return null;
    }

    /**
     * Get segment x component.
     * @return
     *      x component.
     */
    public double getX(){
        return x;
    }

    /**
     * Get segment y component.
     * @return
     *      y component.
     */
    public double getY(){
        return y;
    }

    /**
     * Get a perpendicular vector (90 degrees to the right) from the beginning of this vector.
     * @return
     *      A new vector representing the perpendicular.
     */
    public Vector getPerp(){
        return new Vector(new Segment(x1, y1, x1 + y2 -y1, y1 - x2 + x1));
    }
}
