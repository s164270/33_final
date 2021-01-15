package game;

import player.Player;
import gui_main.GUI;

public class StartField extends Field{
    protected final GUI gui;

    StartField(GUI gui){
        this.gui = gui;
    }


    @Override
    public void visitField(Player player) {
        if (player.isInPrison()) {
            System.out.println(player.getName() + " var i fængsel og vil derfor ikke modtage 4000 kr.");
            return;
        }
        player.addPoints(4000);
     }

    @Override
    public void landOnField(Player player) {
        gui.showMessage(player.getName() + " landede på start");
    }
}
