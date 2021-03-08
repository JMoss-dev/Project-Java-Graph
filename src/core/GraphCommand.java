package src.core;

import src.console.Command;

public abstract class GraphCommand extends Command
{
    private final boolean unweightedOnly;

    public GraphCommand(String name, String[] parameters, boolean unweightedOnly, String description){
        super(name,parameters,description);
        this.unweightedOnly = unweightedOnly;
    }

    public boolean isUnweightedOnly()
    {
        return unweightedOnly;
    }
}
