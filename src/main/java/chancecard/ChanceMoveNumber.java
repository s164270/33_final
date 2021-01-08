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

    public ChanceMoveNumber(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2)
    {
        super(board, gui, player, text, col1, col2);
    }

    public ChanceMoveNumber(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2, int distance)
    {
        super(board, gui, player, text, col1, col2);
        this.distance=distance;
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        gui.showMessage(text);
        int dist = (distance);
        board.movePlayer(currentPlayer,dist);
    }
}
