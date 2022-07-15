package DataStructure;

import javax.management.InvalidAttributeValueException;

public interface IVector {
    double getDistance();
    double getX();
    double getY();
    boolean isMultipleOf(Segment o);
    boolean isNull();
    Double getFactor(Segment o);
    IVector getPerp();
}
