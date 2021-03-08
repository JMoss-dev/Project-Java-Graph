package src.console;

public abstract class Command {
    private final String name;
    private final String[] parameters;
    private final String description;

    public Command(String name, String[] parameters, String description) {
        this.name = name;
        this.parameters = parameters == null ? new String[0] : parameters;
        this.description = description;
    }

    public abstract void onCommand(String[] parameters);

    public String getName()
    {
        return name;
    }

    public String[] getParameters()
    {
        return parameters;
    }

    public String getDescription()
    {
        return description;
    }
}
