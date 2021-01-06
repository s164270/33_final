package game;

import player.Player;

public class StartField extends Field{

    @Override
    public void visitField(Player player) {
        player.addPoints(2);
     }

    @Override
    public String landOnField(Player player) {
        return player.getName() + " landede på start";
    }
}
