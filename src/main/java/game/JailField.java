package game;

import player.Player;
import gui_main.GUI;

public class JailField extends Field {

    private int jailCost;
    protected final GUI gui;
    private String name;
    private final boolean jailType;
    public JailField(String name, int jailCost, boolean jailType, GUI gui)
    {
        super(name, null);
        this.jailCost = jailCost;
        this.jailType = jailType;
        this.gui = gui;

    }


    @Override
    public void landOnField(Player player) {
        if(jailType)
        {
            player.setInPrison(true);
            player.setPosition(10);
            gui.showMessage(player.getName() + " " + "landede på fængselsfeltet og blev sendt til fængslet");
        }
        else
        {
            gui.showMessage(player.getName() + " " + "besøgte fængslet");
        }
    }
}
