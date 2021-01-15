package chancecard;

import game.GameBoard;
import game.PropertyField;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChanceMove extends Chance
{
    String[] options;

    public ChanceMove(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChanceMove(GameBoard board, GUI gui, Player[] player, String text)
    {
        super(board, gui, player, text);
    }

    public ChanceMove(GameBoard board, GUI gui, Player[] player, String text, String[] options)
    {
        super(board, gui, player, text);
        this.options=options;
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        gui.displayChanceCard(text);
        String choice = gui.getUserSelection(text,options);
        for (int i = 0; i < board.getGuiFields().length; i++)
        {
            if(board.getGuiFields()[i].getTitle().equals(choice))
            {
                board.movePlayerPosition(currentPlayer,i);
                return;
            }
        }
    }
}
