package chancecard;

import game.GameBoard;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChanceFreeJail extends Chance
{

    public ChanceFreeJail(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChanceFreeJail(GameBoard board, GUI gui, Player[] player, String text)
    {
        super(board, gui, player, text);
    }

    @Override
    public void executeChance(Player currentPlayer)
    {
        gui.displayChanceCard(text);
        // gui.showMessage(text);
        currentPlayer.setFreePrison(true);
    }
}
