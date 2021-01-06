package chancecard;

import game.GameBoard;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChanceMoveToBeach extends Chance
{

    public ChanceMoveToBeach(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChanceMoveToBeach(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2)
    {
        super(board, gui, player, text, col1, col2);
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        gui.showMessage(text);
        board.movePlayerPosition(currentPlayer,23);
    }
}
