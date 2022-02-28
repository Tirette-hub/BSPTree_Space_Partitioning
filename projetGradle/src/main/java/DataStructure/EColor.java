package DataStructure;

import javafx.scene.paint.Color;

/**
 * Enumeration of Color used by segments.
 * Stores the name of the color and its rgb values.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public enum EColor {
    RED("Rouge"),
    GREEN("Vert"),
    BLUE("Bleu"),
    BLACK("Noir");

    private final double r,g,b;
    private final String name;

    /**
     * Constructor.
     * @param name
     *      Color name.
     */
    private EColor(String name){
        this.name = name;
        switch (name){
            case "Rouge":
                r = 1; g = 0; b = 0;
                break;
            case "Vert":
                r = 0; g = 1; b = 0;
                break;
            case "Bleu":
                r = 0; g = 0; b = 1;
                break;
            case "Noir":
            default:
                name = "Black";
                r = 0; g = 0; b = 0;
                break;
        }
    }

    /**
     * Get the color name.
     * @return
     *      Color name.
     */
    public String toString(){
        return name;
    }

    /**
     * Get the JavaFX Color associated to the color.
     * @return
     *      JavaFX color Object associated to the RGB value.
     */
    public Color getColor(){
        return new Color(r,g,b,1);
    }

    /**
     * Get the Enum object of the color.
     * @param name
     *      Name of the color to return.
     * @return
     *      EColor Enum Object associated to the color name.
     */
    public static EColor getEColorByName(String name){
        switch (name) {
            case "Rouge":
                return RED;
            case "Vert":
                return GREEN;
            case "Bleu":
                return BLUE;
            case "Noir":
            default:
                return BLACK;
        }
    }
}
