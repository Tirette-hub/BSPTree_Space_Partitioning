package DataStructure;

import Console.TestMain;

public class Point2D {
    private double x, y;

    public Point2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point2D(){
        this(0,0);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Point2D))
            return false;

        Point2D other = (Point2D) o;

        if (Math.abs(other.getX() - getX()) < TestMain.epsilon && Math.abs(other.getY() - getY()) < TestMain.epsilon)
            return true;

        return false;
    }
}
