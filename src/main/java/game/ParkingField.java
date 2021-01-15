package game;

import player.Player;
import gui_main.GUI;

public class ParkingField extends Field
{
    protected final GUI gui;
    private String name;

    public ParkingField(String name,GUI gui){
        super(name,gui);
        this.gui = gui;

    }

    @Override
    public void landOnField(Player player) {
        gui.showMessage(player.getName() + " parkerede gratis");
    }
}
