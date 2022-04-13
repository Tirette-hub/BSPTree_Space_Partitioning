package Console;

/**
 * Timer used to calculate time in nanoseconds.
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class Timer {
    private long in;
    private long out;

    /**
     * Constructor.
     */
    public Timer(){

    }

    /**
     * Set the start time of the timer.
     */
    public void start(){
        in = System.nanoTime();
    }

    /**
     * Set the stop time of the timer.
     */
    public void stop(){
        out = System.nanoTime();
    }

    /**
     * Get the delta time between the beginning and the ending of the timer.
     * @return
     *      Delta time in nanoseconds.
     */
    public double getDelta(){
        return out - in;
    }
}
