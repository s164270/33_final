package chancecard;

import game.GameBoard;
import game.PropertyField;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChanceMoveNumber extends Chance
{
    int distance;

    public ChanceMoveNumber(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChanceMoveNumber(GameBoard board, GUI gui, Player[] player, String text)
    {
        super(board, gui, player, text);
    }

    public ChanceMoveNumber(GameBoard board, GUI gui, Player[] player, String text, int distance)
    {
        super(board, gui, player, text);
        this.distance=distance;
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        gui.displayChanceCard(text);
       // gui.showMessage(text);
        int dist = (distance);
        board.movePlayer(currentPlayer,dist);
    }
}
