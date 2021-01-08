package chancecard;

import game.GameBoard;
import game.PropertyField;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChancePayOrGet extends Chance
{
    int money;

    public ChancePayOrGet(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChancePayOrGet(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2)
    {
        super(board, gui, player, text, col1, col2);
    }

    public ChancePayOrGet(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2, int money)
    {
        super(board, gui, player, text, col1, col2);
        this.money=money;
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
    gui.showMessage(text);
     currentPlayer.addPoints(money);
    }
}
