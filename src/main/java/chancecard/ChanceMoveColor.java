package chancecard;

import game.GameBoard;
import game.PropertyField;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChanceMoveColor extends Chance
{
    String options[];

    public ChanceMoveColor(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChanceMoveColor(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2)
    {
        super(board, gui, player, text, col1, col2);
    }

    public ChanceMoveColor(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2, String[] options)
    {
        super(board, gui, player, text, col1, col2);
        this.options=options;
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        String choice = gui.getUserSelection(text,options);
        for (int i = 0; i < board.getGuiFields().length; i++)
        {
            if(board.getGuiFields()[i].getTitle().equals(choice))
            {
                if(board.getGuiFields()[i].getTitle().equals(choice))
                {
                    PropertyField field = (PropertyField) board.getField()[i];
                    if (field.getOwner()==null)
                    {
                        field.setOwner(currentPlayer);
                    }
                }
                board.movePlayerPosition(currentPlayer,i);
                return;
            }
        }
    }
}

