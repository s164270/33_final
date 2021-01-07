package game;

import player.Player;

public class StartField extends Field{

    @Override
    public void visitField(Player player) {
        if (player.isInPrison()) {
            System.out.println(player.getName() + " var i fængsel og vil derfor ikke modtage 4000 kr.");
            return;
        }
        player.addPoints(4000);
     }

    @Override
    public String landOnField(Player player) {
        return player.getName() + " landede på start";
    }
}
