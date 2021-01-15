package chancecard;

import game.GameBoard;
import game.PropertyField;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChanceMoveNumber extends Chance {
    int distance;

    public ChanceMoveNumber(GameBoard board, GUI gui, Player[] player) {
        super(board, gui, player);
    }

    public ChanceMoveNumber(GameBoard board, GUI gui, Player[] player, String text) {
        super(board, gui, player, text);
    }

    public ChanceMoveNumber(GameBoard board, GUI gui, Player[] player, String text, int distance) {
        super(board, gui, player, text);
        this.distance = distance;
    }


    @Override
    public void executeChance(Player currentPlayer) {
        gui.showMessage(text);
        int dist = (distance);
        if (distance < 0) {
            currentPlayer.setInPrison(true);
            if(currentPlayer.getPosition()+distance<0){
                board.movePlayerPosition(currentPlayer, 39);

            }
            else{
                board.movePlayerPosition(currentPlayer, currentPlayer.getPosition()-3);

            }
            if(currentPlayer.getPosition()!=10){
                currentPlayer.setInPrison(false);
            }

        }

        else {
            board.movePlayer(currentPlayer, dist);
        }
    }
}
