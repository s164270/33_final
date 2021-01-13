package game;

import gui_fields.GUI_Field;
import gui_main.GUI;
import player.Player;

public abstract class Field {
    
    private String name;
    protected GUI_Field guiField;
    protected GUI gui;

    public void setGuiField(GUI_Field guiField) {
        this.guiField = guiField;
        this.name = guiField.getTitle();
    }

    public Field()
    {
        name = "default";
    }

    public Field(String name, GUI gui)
    {
        this.name = name;
        this.gui = gui;
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
