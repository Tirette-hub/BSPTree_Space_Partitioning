package DataStructure;

import Console.TestMain;

import javax.management.InvalidAttributeValueException;

public class Eye {
    private Point2D position;
    private double direction, angle;
    private boolean visible;


    //Constructors

    public Eye(){
        this(new Point2D());
    }

    public Eye(Point2D position){
        this.position = position;
        direction = 0;
        angle = 90;
        visible = false;
    }

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

    public void setPosition(Point2D position){
        this.position = position;
    }

    public void setDirection(double direction){
        this.direction = -(direction - 90);
    }

    public void setAngle(double angle){
        this.angle = angle;
    }

    public void setVisible(boolean flag) {
        visible = flag;
    }

    //Getters

    public Point2D getPosition(){
        return position;
    }

    public double getDirection(){
        return -direction + 90;
    }

    public double getAngle() {
        return angle;
    }

    public boolean isInSight(Point2D pt) throws InvalidAttributeValueException {
        if (angle == 0)
            return false;

        //en voyant les segments comme des vecteurs, il est plus facile de dire si un point est dans le champ de vision grâce aux angles
        IVector vector = getDirectionLine();
        IVector line = new Segment(position, pt);
        double alpha = Segment.getAngle(vector, line);

        return !(Math.abs(alpha) > angle / 2.0);
    }

    public Segment getDirectionLine(){
        return getDirectionLine(1);
    }

    public Segment getDirectionLine(double multiplier){
        double dx = Math.cos(Math.toRadians(direction))*multiplier;
        double dy = Math.sin(Math.toRadians(direction))*multiplier;
        return new Segment(position.getX(), position.getY(), position.getX() + dx, position.getY() + dy);
    }

    public Pair<Segment, Segment> getFOVLine(){
        return getFOVLine(1);
    }

    public Pair<Segment, Segment> getFOVLine(double multiplier){
        double angleL = direction - angle/2.0, angleR = direction + angle/2.0;
        double dx = Math.cos(Math.toRadians(angleL))*multiplier, dy = Math.sin(Math.toRadians(angleL))*multiplier;

        Segment l = new Segment(position.getX(), position.getY(), position.getX() + dx, position.getY() + dy);

        dx = Math.cos(Math.toRadians(angleR))*multiplier; dy = Math.sin(Math.toRadians(angleR))*multiplier;

        Segment r = new Segment(position.getX(), position.getY(), position.getX() + dx, position.getY() + dy);

        return new Pair<>(l, r);
    }

    public boolean isVisible(){
        return visible;
    }

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
