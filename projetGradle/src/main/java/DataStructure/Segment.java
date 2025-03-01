package DataStructure;

import Console.TestMain;

import javax.management.InvalidAttributeValueException;

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
    protected boolean firstOnEdge = false, secondOnEdge = false;
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
    public boolean isMultipleOf(IVector o){
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

    public boolean isNull(){
        return Math.abs(getX()) < TestMain.epsilon && Math.abs(getY()) < TestMain.epsilon;
    }

    @Override
    public boolean hasSameSens(IVector o) {
        double dx = o.getX(), dy = o.getY();

        if (dx < 0 && getX() < 0) {
            return (dy < 0 && getY() < 0) || (dy > 0 && getY() > 0) || (Math.abs(dy) < TestMain.epsilon && Math.abs(getY()) < TestMain.epsilon);
        }
        else if (dx > 0 && getX() > 0){
            return (dy < 0 && getY() < 0) || (dy > 0 && getY() > 0) || (Math.abs(dy) < TestMain.epsilon && Math.abs(getY()) < TestMain.epsilon);
        }
        else if (Math.abs(dx) < TestMain.epsilon && Math.abs(getX()) < TestMain.epsilon){
            return (dy < 0 && getY() < 0) || (dy > 0 && getY() > 0) || (Math.abs(dy) < TestMain.epsilon && Math.abs(getY()) < TestMain.epsilon);
        }

        return false;
    }

    /**
     * Get the factor between 2 segments if they are multiple of each other.
     * @param o
     *      The other segment to campare with.
     * @return
     *      The factor between the 2 vectors.
     */
    public Double getFactor(IVector o){
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
     * @param from
     *      One point of the segment (origin).
     * @param to
     *      Another point of the segment (end).
     */
    public Segment(Point2D from, Point2D to){
        this(from.getX(), from.getY(), to.getX(), to.getY());
    }

    /**
     * Constructor.
     * @param from
     *      One point of the segment (origin).
     * @param to
     *      Another point of the segment (end).
     * @param color
     *      Enum value representing the color of the segment.
     */
    public Segment(Point2D from, Point2D to, EColor color){
        this(from.getX(), from.getY(), to.getX(), to.getY(), color);
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
     * @param firstOnEdge
     *      flag that tell if the first point is on an edge.
     * @param secondOnEdge
     *      flag that tell if the second point is on an edge.
     * @param color
     *      Enum value representing the color of the segment.
     */
    public Segment(double x1, double y1, double x2, double y2, boolean firstOnEdge, boolean secondOnEdge, EColor color){
        this(x1, y1, x2, y2, color);
        if (firstOnEdge)
            this.firstOnEdge = true;
        if (secondOnEdge)
            this.secondOnEdge = true;
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

        if (!seg.getFrom().equals(getFrom()) || !seg.getTo().equals(getTo()))
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

    /**
     * Check if this segment is a free split segment.
     * @return
     *      True if this segment is a free split segment.
     */
    public boolean isFreeSplit(){
        return (firstOnEdge && secondOnEdge);
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
    static public Pair<Segment, Segment> cut(Segment lineToCut, Segment cutLine){
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


        return new Pair<>(new Segment(x12, y12, x, y, false, true, lineToCut.getEColor()), new Segment(x, y, x22, y22, true, false, lineToCut.getEColor()));
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
     * Get the intersection point coordinates between two lines.
     * @param abc1
     *      Hyperplane 1 equation parameters.
     * @param abc2
     *      Hyperplane 2 equation parameters.
     * @return
     *      Coordinates of the intersection.
     */
    static public Point2D getIntersection(double[] abc1, double[] abc2){
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

        return new Point2D(x, y);
    }

    /**
     * Get the angle in degrees between a reference line and a point.
     * @param dirLine
     *      Segment representing the reference line
     * @param pt
     *      Point to get the angle.
     * @return
     *      Angle in degrees.
     * @throws InvalidAttributeValueException
     *      If dirLine vector is null or if pt is on dirLine.
     */
    static public Double getAngle(Segment dirLine, Point2D pt) throws InvalidAttributeValueException {
        Segment newLine = new Segment(dirLine.getFrom(), pt);
        return getAngle(dirLine, newLine);
    }

    /**
     * Get the angle in degrees between 2 vectors.
     * @param vectA
     *      Vector A.
     * @param vectB
     *     Vector B.
     * @return
     *      Angle in degrees.
     * @throws InvalidAttributeValueException
     *      If at least one of the given vectors is null.
     */
    public static Double getAngle(IVector vectA, IVector vectB) throws InvalidAttributeValueException {
        if (vectA.isNull() || vectA.isNull())
            throw new InvalidAttributeValueException("Vectors should not be null.");

        double dxA = vectA.getX(),
                dxB = vectB.getX(),
                dyA = vectA.getY(),
                dyB = vectB.getY();

        return Math.toDegrees(Math.acos(((dxA*dxB)+(dyA*dyB))/(Math.sqrt(Math.pow(dxA,2) + Math.pow(dyA,2))*Math.sqrt(Math.pow(dxB,2) + Math.pow(dyB,2))))); // on a le cosinus de l'angle associé?
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
     * @param eye
     *      Eye of the scene.
     * @param screenWidth
     *      Width of the screen where the point will be projected.
     * @return
     *      X position in the screen where the point is projected.
     */
    static public Double getProjection(Point2D pt, double a, double b, double c, double angle, Eye eye, double screenWidth){
        double result;

        double FOV = eye.getAngle();
        double left = screenWidth/2 - angle*screenWidth/FOV,
                right = screenWidth/2 + angle*screenWidth/FOV;

        boolean onH = Math.abs(a*pt.getX()+b*pt.getY()+c) < TestMain.epsilon;

        if (angle <= 180 || Math.abs(angle-360) < TestMain.epsilon)     //direction to the top, bottom or right
            if (onH)                                        //pt1 on the guide line
                result = screenWidth/2;
            else if (a*pt.getX()+b*pt.getY()+c > 0)                 // upper part of direction guideline is h+
                if(eye.getDirection() > 180 || eye.getDirection() == 0)
                    result = right;
                else
                    result = left;                              // so to the left of the eye
            else                                            // lower part of direction guideline is h-
                if(eye.getDirection() > 180 || eye.getDirection() == 0)
                    result = left;
                else
                    result = right;                             // so to the right of the eye
        else                                                //direction to the left
            if (onH)                                        //pt1 on the guide line
                result = screenWidth/2;
            else if (a*pt.getX()+b*pt.getY()+c > 0)                 // upper part of direction guideline is h+
                if(eye.getDirection() > 180 || eye.getDirection() == 0)
                    result = left;
                else
                    result = right;                             // so to the right of the eye
            else                                            // lower part of direction guideline is h-
                if(eye.getDirection() > 180 || eye.getDirection() == 0)
                    result = right;
                else
                    result = left;

        return result;
    }
}
