package DataStructure;

import Console.TestMain;

public class Point2D extends Pair<Double, Double>{

    public Point2D(double x, double y){
        super(x, y);
    }

    public Point2D(){
        this(0,0);
    }

    public double getX(){return getL();}

    public double getY(){return getR();}

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Point2D))
            return false;

        Point2D other = (Point2D) o;

        return Math.abs(other.getX() - getX()) < TestMain.epsilon && Math.abs(other.getY() - getY()) < TestMain.epsilon;
    }

    public boolean on(Segment s){
        double[] abc = Segment.getCutlineParameters(s.getFrom(), s.getTo());
        double a = abc[0], b = abc[1], c = abc[2];

        return Math.abs(a * getX() + b * getY() + c) < TestMain.epsilon;
    }
}
