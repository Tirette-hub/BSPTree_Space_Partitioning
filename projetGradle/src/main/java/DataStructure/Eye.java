package DataStructure;

import Console.TestMain;

import javax.management.InvalidAttributeValueException;

/**
 * This Class represents the eye object that can be placed in the scene to be able to get a projection of what it sees in this scene.
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class Eye {
    private Point2D position;
    private double direction, angle;
    private boolean visible;


    //Constructors

    /**
     * Constructor.
     */
    public Eye(){
        this(new Point2D());
    }

    /**
     * Constructor.
     * @param position
     *      Position of the eye in the scene.
     */
    public Eye(Point2D position){
        this.position = position;
        direction = 0;
        angle = 90;
        visible = false;
    }

    /**
     * Constructor.
     * @param position
     *      Position of the eye in the scene.
     * @param direction
     *      Angle where the eye is looking at.
     * @param angle
     *      Angle of the Field of View (FOV).
     */
    public Eye(Point2D position, double direction, double angle){
        this.position = position;
        visible = false;
        if (0 <= direction && direction < 360)
            this.direction = -(direction - 90);
        else
            throw new IllegalArgumentException("Direction must be a double value between 0 (included) and 360 (excluded).");

        if (0 <= angle && angle < 360)
            this.angle = angle;
        else
            throw new IllegalArgumentException("Angle must be a double between 0 (included) and 360 (excluded).");
    }

    //Setters

    /**
     * Set a new position for the eye.
     * @param position
     *      New position of the eye.
     */
    public void setPosition(Point2D position){
        this.position = position;
    }

    /**
     * Set a direction for the eye.
     * @param direction
     *      Angle where the eye is looking at.
     */
    public void setDirection(double direction){
        this.direction = -(direction - 90);
    }

    /**
     * Set the FOV angle.
     * @param angle
     *      Angle of the FOV.
     */
    public void setAngle(double angle){
        this.angle = angle;
    }

    /**
     * Set a flag to know if the eye is visible or not
     * @param flag
     *      Boolean value.
     */
    public void setVisible(boolean flag) {
        visible = flag;
    }

    //Getters

    /**
     * Get the position of the eye.
     * @return
     *      Position of the eye.
     */
    public Point2D getPosition(){
        return position;
    }

    /**
     * Get the value of the angle where the eye is looking at.
     * @return
     *      Angle where the eye is looking at.
     */
    public double getDirection(){
        return -direction + 90;
    }

    /**
     * Get the value of the FOV angle.
     * @return
     *      FOV angle.
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Get the flag to know if the eye is visible or not.
     * @return
     *      True if the eye is visible.
     */
    public boolean isVisible(){
        return visible;
    }

    /**
     * Check if the eye sees a given point.
     * @param pt
     *      Point to check.
     * @return
     *      True if the eye can see the given point.
     */
    public boolean isInSight(Point2D pt){
        if (angle == 0)
            return false;

        //en voyant les segments comme des vecteurs, il est plus facile de dire si un point est dans le champ de vision grâce aux angles
        IVector vector = getDirectionLine();
        IVector line = new Segment(position, pt);
        double alpha;
        try {
            alpha = Segment.getAngle(vector, line);
        }catch (InvalidAttributeValueException e){
            //impossible to determinate an angle between the point, the eye, and where it's looking at
            //then the point and the eye are located at the same place.
            return false;
        }

        return !(Math.abs(alpha) > angle / 2.0);
    }

    /**
     * Get a Segment representing where the eye is looking at.
     * @return
     *      Segment representing where the eye is looking at.
     */
    public Segment getDirectionLine(){
        return getDirectionLine(1);
    }

    /**
     * Get a Segment representing where the eye is looking at. Mainly used for GUI purpose.
     * @param multiplier
     *      Allow to make the segment longer.
     * @return
     *      Segment representing where the eye is looking at.
     */
    public Segment getDirectionLine(double multiplier){
        double dx = Math.cos(Math.toRadians(direction))*multiplier;
        double dy = Math.sin(Math.toRadians(direction))*multiplier;
        return new Segment(position.getX(), position.getY(), position.getX() + dx, position.getY() + dy);
    }

    /**
     * Get a Pair of Segments representing the edge of where the eye is looking at.
     * @return
     *      Pair of Segments representing the edge of where the eye is looking at.
     */
    public Pair<Segment, Segment> getFOVLine(){
        return getFOVLine(1);
    }

    /**
     * Get a Pair of Segments representing the edge of where the eye is looking at. Mainly used for GUI purpose.
     * @param multiplier
     *      Allow to make the segment longer.
     * @return
     *      Pair of Segments representing the edge of where the eye is looking at.
     */
    public Pair<Segment, Segment> getFOVLine(double multiplier){
        double angleL = direction - angle/2.0, angleR = direction + angle/2.0;
        double dx = Math.cos(Math.toRadians(angleL))*multiplier, dy = Math.sin(Math.toRadians(angleL))*multiplier;

        Segment l = new Segment(position.getX(), position.getY(), position.getX() + dx, position.getY() + dy);

        dx = Math.cos(Math.toRadians(angleR))*multiplier; dy = Math.sin(Math.toRadians(angleR))*multiplier;

        Segment r = new Segment(position.getX(), position.getY(), position.getX() + dx, position.getY() + dy);

        return new Pair<>(l, r);
    }

    //Overrides

    /**
     * Check if another object is equivalent to this eye.
     * @param o
     *      The other object.
     * @return
     *      True if the other object is equivalent to this eye.
     */
    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Eye))
            return false;

        Eye other = (Eye) o;

        if (other.getPosition().equals(position) && Math.abs(other.getAngle() - angle) < TestMain.epsilon && Math.abs(other.getDirection() - direction) < TestMain.epsilon)
            return true;

        return false;
    }
}
