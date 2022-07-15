package DataStructure;

public class Pair<L,R> {
    private L l;
    private R r;

    @Override
    public String toString(){
        return "<" + l + "; " + r + ">";
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Pair))
            return false;

        return (((Pair<?, ?>) o).getL().equals(l) && ((Pair<?, ?>) o).getR().equals(r));
    }

    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }

    public void setL(L l) {
        this.l = l;
    }

    public void setR(R r) {
        this.r = r;
    }

    public L getL() {
        return l;
    }

    public R getR() {
        return r;
    }
}
