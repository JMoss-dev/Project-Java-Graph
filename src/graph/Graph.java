package src.graph;

import java.util.ArrayList;

public abstract class Graph {
    protected ArrayList<Knot> knots = new ArrayList<Knot>();
    protected ArrayList<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer>>();

    public enum orientation {
        undirected, directed
    }

    protected orientation or;

    public enum connection {
        weighted, unweighted
    }

    // connection type set in derived class
    protected connection con;

    public Graph(orientation or) {
        this.or = or;
    }

    // -1: no connection
    // n: connection weight
    // 0: for AA; BB; etc.
    // returns true if knot was inserted successfully
    public boolean insertKnot(Knot knot) {

        if (searchKnot(knot.getLetter(), false) != null) {
            System.out.println("A Knot with this letter already exists.");
            return false;
        }
        knots.add(knot);
        // create a new row for knot with the right length
        ArrayList<Integer> row = new ArrayList<Integer>();
        for (int i = 0; i < knots.size(); i++) {
            row.add(-1);
        }
        // add row to table
        table.add(row);
        // add knot to existing rows
        for (int i = 0; i < table.size() - 1; i++) {
            table.get(i).add(-1);
        }

        // set data to 0 where needed
        int knotIndex = knots.indexOf(knot);
        table.get(knotIndex).set(knotIndex, 0);
        return true;
    }

    public boolean deleteKnot(Knot knot) {
        if (knot == null) {
            return false;
        }
        // find pos of knot
        int index = knots.indexOf(knot);
        // remove knot from knots
        knots.remove(index);
        // remove knot from table
        table.remove(index);
        // remove knot from every other element in table
        for (int i = 0; i < table.size(); i++) {
            table.get(i).remove(index);
        }
        return true;
    }

    // see Presentation/print/print().pdf
    public void print() {
        if (knots.size() == 0) {
            return;
        }
        System.out.println();
        for (int i = 0; i < knots.size(); i++) {
            System.out.print("\t" + " | " + knots.get(i).getLetter());
        }
        System.out.println();
        for (int i = 0; i < table.size(); i++) {
            System.out.print(knots.get(i).getLetter());
            for (int j = 0; j < table.get(i).size(); j++) {
                System.out.print("\t" + " | " + table.get(i).get(j));
            }
            System.out.println();
        }
        System.out.println();
    }

    public void resetData() {
        // loop through every element
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                // if i and j are equal -> connection between itself -> 0; else -1 for no
                // connection
                if (i != j) {
                    table.get(i).set(j, -1);
                } else {
                    table.get(i).set(j, 0);
                }
            }
        }
    };

    protected boolean editData(Knot startKnot, Knot endKnot, int value) {

        if (startKnot != null && endKnot != null) {
            if (value < -1) {
                System.out.println("Values below -1 are not allowed.");
                return false;
            }
            // get index
            int startIndex = knots.indexOf(startKnot);
            int endIndex = knots.indexOf(endKnot);

            // check for orientation
            if (or == orientation.undirected) {
                table.get(startIndex).set(endIndex, value);
                table.get(endIndex).set(startIndex, value);
            }

            if (or == orientation.directed) {
                table.get(startIndex).set(endIndex, value);
            }
            return true;
        }
        return false;

    }

    public boolean printNeighbors(Knot knot) {

        if (knot == null) {
            return false;
        }
        int neighbors = 0;
        System.out.println(knot.getLetter() + "'s neighbors are: ");
        int index = knots.indexOf(knot);
        ArrayList<Integer> row = table.get(index);
        for (int i = 0; i < row.size(); i++) {
            int value = row.get(i);
            if (value > 0) {
                neighbors++;
                System.out.print(knots.get(i).getLetter() + "\t");
            }
        }
        if (neighbors == 0) {
            System.out.println(knot.getLetter() + " does not have any neighbors.");
            return true;
        }
        System.out.println();
        for (int i = 0; i < table.get(index).size(); i++) {
            int value = table.get(index).get(i);
            if (value > 0) {
                System.out.print(value + "\t");
            }
        }
        System.out.println();
        return true;

    }

    public Knot searchKnot(char letter, boolean printMessage) {
        for (Knot knot : knots) {
            if (knot.getLetter() == letter)
                return knot;
        }
        if (printMessage)
            System.out.println("Knot with letter " + letter + " does not exist.");
        return null;
    };

    public void printProperties() {
        System.out.println("Orientation: \t" + or);
        System.out.println("Connection: \t" + con);
        System.out.println("Complete: \t" + complete());
    };

    private boolean complete() {
        for (int row = 0; row < table.size(); row++) {
            for (int column = 0; column < table.get(row).size(); column++) {
                // if one element isn't connected to another knot(value <= -1), then return
                // false
                if (table.get(row).get(column) <= -1) {
                    return false;
                }
            }
        }
        return true;
    }
}
