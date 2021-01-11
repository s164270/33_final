package chancecard;

import game.GameBoard;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChanceGetPaid extends Chance
{
    int getpaid;

    public ChanceGetPaid(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChanceGetPaid(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2)
    {
        super(board, gui, player, text, col1, col2);
    }

    public ChanceGetPaid(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2, int getpaid)
    {
        super(board, gui, player, text, col1, col2);
        this.getpaid=getpaid;
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        gui.showMessage(text);
        for (int i = 0; i < player.length; i++) {
            if(player[i] != currentPlayer)
            {
                player[i].sendPoints(currentPlayer, getpaid);
            }
        }
    }
}
