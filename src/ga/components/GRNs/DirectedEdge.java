package ga.components.GRNs;

/**
 * Created by Zhenyue Qin on 26/03/2017.
 * The Australian National University.
 */

public class DirectedEdge {

    private final Integer left;
    private final Integer right;

    public DirectedEdge(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public Integer getLeft() { return left; }
    public Integer getRight() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DirectedEdge)) return false;
        DirectedEdge pairo = (DirectedEdge) o;
        return this.left.equals(pairo.getLeft()) &&
                this.right.equals(pairo.getRight());
    }

    @Override
    public String toString() {
        return "(" + left + " -> " + right + ")";
    }
}
