package DataStructure;

/**
 * Data Structure of a Pair of data.
 * @param <L>
 *      Data Type of the first data stored.
 * @param <R>
 *      Data Type of the second data stored.
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class Pair<L,R> {
    private L l;
    private R r;

    /**
     * Convert the pair of data to a string to show it.
     * @return
     *      String with the 2 data formatted.
     */
    @Override
    public String toString(){
        return "[" + l + "; " + r + "]";
    }

    /**
     * Check if the other object is equivalent to this pair of data.
     * @param o
     *      Other object to compare.
     * @return
     *      True if the other object is equivalent to this pair of data.
     */
    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Pair))
            return false;

        Pair<L, R> other = (Pair<L,R>) o;

        if (!other.getL().equals(l))
            return false;

        return other.getR().equals(r);
    }

    /**
     * Constructor.
     * @param l
     *      Left data to store.
     * @param r
     *      Right data to store.
     */
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }

    /**
     * Set new left data.
     * @param l
     *      New left data.
     */
    public void setL(L l) {
        this.l = l;
    }

    /**
     * Set new right data.
     * @param r
     *      New right data.
     */
    public void setR(R r) {
        this.r = r;
    }

    /**
     * Get left data.
     * @return
     *      Left data.
     */
    public L getL() {
        return l;
    }

    /**
     * Get right data.
     * @return
     *      Right data.
     */
    public R getR() {
        return r;
    }
}
