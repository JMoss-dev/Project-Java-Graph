package src.graph;

public class WeightedGraph extends Graph {

    public WeightedGraph(orientation or) {
        super(or);
        super.con = Graph.connection.weighted;
    }

    public boolean edit(Knot startKnot, Knot endKnot, int value){
        return editData(startKnot, endKnot, value);
    }
}
