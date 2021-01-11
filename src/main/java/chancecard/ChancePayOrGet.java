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

    public ChancePayOrGet(GameBoard board, GUI gui, Player[] player, String text)
    {
        super(board, gui, player, text);
    }

    public ChancePayOrGet(GameBoard board, GUI gui, Player[] player, String text, int money)
    {
        super(board, gui, player, text);
        this.money=money;
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
    gui.showMessage(text);
     currentPlayer.addPoints(money);
    }
}
