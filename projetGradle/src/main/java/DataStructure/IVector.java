package DataStructure;

import javax.management.InvalidAttributeValueException;

public interface IVector {
    double getDistance();
    double getX();
    double getY();
    boolean isMultipleOf(IVector o);
    boolean isNull();
    boolean hasSameSens(IVector o);
    Double getFactor(IVector o);
    IVector getPerp();
}
