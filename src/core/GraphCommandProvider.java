package src.core;

import src.console.Command;
import src.console.ConsoleHelper;
import src.graph.Graph;
import src.graph.Knot;
import src.graph.UnweightedGraph;
import src.graph.WeightedGraph;

public class GraphCommandProvider extends ConsoleHelper
{

    private Graph graph;

    public GraphCommandProvider(){
        super();
    }

    /*
     * returns Character.MIN_VALUE = '0' if given String is not a char
     */

    private char toChar(String s) {
        char[] chars = s.toCharArray();
        if (chars.length > 1) {
            System.out.println("Invalid parameters. Please provide a valid name.");
            return Character.MIN_VALUE;
        }
        return chars[0];
    }

    @Override
    protected Command[] initCommands()
    {
        return new Command[]{
                new GraphCommand("insert", new String[] { "knot_letter" }, false,  "Inserts a Knot into the Graph.") {
                   @Override
                   public void onCommand(String[] parameters) {
                       char letter1 = toChar(parameters[0]);
                       if (letter1 == Character.MIN_VALUE)
                           return;
                       if (graph.insertKnot(new Knot(letter1)))
                           System.out.println("Inserted Knot " + letter1 + ".");

                   }
               },
                new GraphCommand("delete", new String[] { "knot_letter" }, false,  "Deletes a Knot from the Graph.") {
           @Override
           public void onCommand(String[] parameters) {
               char letter1 = toChar(parameters[0]);
               if (letter1 == Character.MIN_VALUE)
                   return;
               if (graph.deleteKnot(graph.searchKnot(letter1, true)))
                   System.out.println("Deleted Knot " + letter1 + ".");

           }
       },
                new GraphCommand("print", new String[0], false,  "Prints a table containing the current matrix.") {
           @Override
           public void onCommand(String[] parameters) {
               graph.print();
           }
       },
                new GraphCommand("reset", new String[0], false, "Resets the current matrix.") {
           @Override
           public void onCommand(String[] parameters) {
               graph.resetData();
               System.out.println("Reset data.");
           }
       },
                new GraphCommand("printNeighbors", new String[] { "knot_letter" }, false,
               "Prints every connection by that Knot with its value.") {
           @Override
           public void onCommand(String[] parameters) {
               char letter1 = toChar(parameters[0]);
               if (letter1 == Character.MIN_VALUE)
                   return;
               graph.printNeighbors(graph.searchKnot(letter1, true));

           }
       },
                new GraphCommand("search", new String[] { "knot_letter" }, false,  "Not available yet.") {
           @Override
           public void onCommand(String[] parameters) {
               System.out.println("Currently not available.");
           }
       },
                new GraphCommand("properties", new String[0], false,  "Prints the properties of the Graph.") {
           @Override
           public void onCommand(String[] parameters) {
               graph.printProperties();

           }
       },
                new GraphCommand("edit", new String[] { "knot_letter", "knot_letter", "value" }, false,
               "Edits the value of a connection between two Knots.") {
           @Override
           public void onCommand(String[] parameters) {
               if (graph instanceof WeightedGraph) {

                   char letter1 = toChar(parameters[0]);
                   if (letter1 == Character.MIN_VALUE)
                       return;
                   char letter2 = toChar(parameters[1]);
                   if (letter2 == Character.MIN_VALUE)
                       return;
                   int value;
                   try {
                       value = Integer.parseInt(parameters[2]);
                   } catch (Exception e) {
                       System.out.println(e);
                       return;
                   }
                   if (((WeightedGraph) graph).edit(graph.searchKnot(letter1, true),
                           graph.searchKnot(letter2, true), value)) {
                       System.out.println("Edited value between Knot " + letter1 + " and " + letter2 + ".");
                   }

               } else {
                   System.out.println("This command can only be used on weighted graphs.");
               }

           }
       },
               new GraphCommand("connect", new String[] { "knot_letter", "knot_letter" }, true, "Connects two Knots.") {
                   @Override
                   public void onCommand(String[] parameters) {
                       if (graph instanceof UnweightedGraph) {
                           char letter1 = toChar(parameters[0]);
                           if (letter1 == Character.MIN_VALUE)
                               return;
                           char letter2 = toChar(parameters[1]);
                           if (letter2 == Character.MIN_VALUE)
                               return;
                           if (((UnweightedGraph) graph).connect(graph.searchKnot(letter1, true),
                                   graph.searchKnot(letter2, true)))
                               System.out.println("Connected Knot " + letter1 + " and " + letter2 + ".");
                       } else {
                           System.out.println("This command can only be used on unweighted graphs.");
                       }

                   }
               },
                new GraphCommand("disconnect", new String[] { "knot_letter", "knot_letter" }, true,
               "Disconnects two Knots.") {
           @Override
           public void onCommand(String[] parameters) {
               if (graph instanceof UnweightedGraph) {
                   char letter1 = toChar(parameters[0]);
                   if (letter1 == Character.MIN_VALUE)
                       return;
                   char letter2 = toChar(parameters[1]);
                   if (letter2 == Character.MIN_VALUE)
                       return;
                   if (((UnweightedGraph) graph).disconnect(graph.searchKnot(letter1, true),
                           graph.searchKnot(letter2, true)))
                       System.out.println("Disconnected Knot " + letter1 + " and " + letter2 + ".");
               } else {
                   System.out.println("This command can only be used on unweighted graphs.");
               }
           }
       },
                new Command("help", new String[] { "command" }, "Prints every command with its syntax.") {
           @Override
           public void onCommand(String[] parameters) {
               if (parameters.length > 0) {

                   Command command = getCommand(parameters[0]);
                   if (command == null) {
                       onCommandNotFound(this.getName());
                       return;
                   }
                   System.out.println(command.getDescription());
                   System.out.print("usage: ");
                   printParameters(command);
                   System.out.println();

               } else {
                   for (Command command : getCommands()) {
                       System.out.print("-" + command.getName());
                       System.out.println();

                   }
               }

           }
       },
                new Command("create", new String[] { "orientation", "connection", },
               "Creates a Graph with the given orientation and connection.") {
           @Override
           public void onCommand(String[] parameters) {
               String orientation;
               if (parameters[0].equals("directed")) {
                   orientation = "directed";
               } else if (parameters[0].equals("undirected")) {
                   orientation = "undirected";
               } else {
                   System.out.println("Orientation of a graph can only be undirected or directed.");
                   return;
               }
               if (parameters[1].equals("weighted")) {
                   graph = new WeightedGraph(orientation.equals("directed") ? Graph.orientation.directed
                           : Graph.orientation.undirected);
               } else if (parameters[1].equals("unweighted")) {
                   graph = new UnweightedGraph(orientation.equals("directed") ? Graph.orientation.directed
                           : Graph.orientation.undirected);
               } else {
                   System.out.println("Connection of a graph can only be weighted or unweighted.");
                   return;
               }
               System.out.println("Successfully created a new graph.");
           }
       },
                new Command("exit", new String[0], "Exits the program.") {
           @Override
           public void onCommand(String[] parameters) {
               System.exit(0);
           }
       }, };
    }

    @Override
    protected boolean onHandleInput(Command command, String[] parameters)
    {

        if (command instanceof GraphCommand && graph == null) {
            System.out.println("Please create a graph first with the create command.");
            return false;
        }
        if (command.getParameters().length  != parameters.length && !command.getName().equals("help")) {
            onInvalidArguments(command);
            return false;
        }
        if(command instanceof GraphCommand){
            if (((GraphCommand)command).isUnweightedOnly() && graph instanceof WeightedGraph) {
                System.out.println("Cannot use commands for unweighted graphs on a weighted graph.");
                return false;
            }
        }

        return true;
    }
}
