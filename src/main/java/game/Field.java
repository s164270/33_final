package game;

import gui_fields.GUI_Field;
import player.Player;

public abstract class Field {
    private String name;
    protected GUI_Field guiField;

    public void setGuiField(GUI_Field guiField) {
        this.guiField = guiField;
    }

    public Field()
    {
        name = "default";
    }

    public Field(String name)
    {
        this.name = name;
    }

    public String landOnField(Player player)
    {
        return player.getName() + " " + "landede på " + name;
    }

    public void visitField(Player player)
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
