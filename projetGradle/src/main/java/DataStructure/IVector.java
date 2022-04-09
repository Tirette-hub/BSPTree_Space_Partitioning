package DataStructure;

public interface IVector {
    double getDistance();
    double getX();
    double getY();
    boolean isMultipleOf(Segment o);
    Double getFactor(Segment o);
    IVector getPerp();
}
