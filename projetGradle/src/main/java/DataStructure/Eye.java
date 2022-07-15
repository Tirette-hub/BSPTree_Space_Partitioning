package DataStructure;

import javafx.scene.image.Image;

public class Eye {
    public static final Image image = new Image(Eye.class.getResource("/img/eye.png").toString());
    private Point2D position;
    private double direction, angle;


    //Constructors

    public Eye(Point2D position){
        this.position = position;
        direction = 0;
        angle = 90;
    }

    public Eye(Point2D position, double direction, double angle){
        this.position = position;
        if (0 <= direction && direction < 360)
            this.direction = direction;
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
        this.direction = direction;
    }

    public void setAngle(double angle){
        this.angle = angle;
    }

    //Getters

    public Point2D getPosition(){
        return position;
    }

    public double getDirection(){
        return direction;
    }

    public double getAngle() {
        return angle;
    }


    /*
     *
     *
     *
     *
     * TBC
     *
     *
     *
     *
     */
    public boolean isInSight(Segment seg){
        return false;
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
}
