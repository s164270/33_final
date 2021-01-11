package chancecard;

import game.GameBoard;
import game.PropertyField;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChanceJail extends Chance
{

    public ChanceJail(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }


    public ChanceJail(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2)
    {
        super(board, gui, player, text, col1, col2);

    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        gui.showMessage(text);
        currentPlayer.setInPrison(true);
        currentPlayer.setPosition(10);
    }
}
