package src.graph;

public class UnweightedGraph extends Graph {
    public UnweightedGraph(orientation or) {
        super(or);
        super.con = Graph.connection.unweighted;
    }

    public boolean connect(Knot startKnot, Knot endKnot) {
        return editData(startKnot, endKnot, 1);
    }

    public boolean disconnect(Knot startKnot, Knot endKnot) {
        return editData(startKnot, endKnot, -1);
    }
}
