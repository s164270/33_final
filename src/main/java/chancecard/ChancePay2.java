package chancecard;

import game.GameBoard;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChancePay2 extends Chance
{

    public ChancePay2(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChancePay2(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2)
    {
        super(board, gui, player, text, col1, col2);
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        gui.showMessage(text);
        currentPlayer.addPoints(-2);
    }
}
