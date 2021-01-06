package chancecard;

import game.GameBoard;
import game.PropertyField;
import gui_main.GUI;
import player.Player;

import java.awt.*;
import java.util.ArrayList;

public class ChanceMovePlayer extends Chance
{
    Player cardPlayer;

    public ChanceMovePlayer(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChanceMovePlayer(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2)
    {
        super(board, gui, player, text, col1, col2);
    }

    public ChanceMovePlayer(GameBoard board, GUI gui, Player[] player, String text, Color col1, Color col2, Player cardPlayer)
    {
        super(board, gui, player, text, col1, col2);
        this.cardPlayer=cardPlayer;
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        gui.showMessage(text);
        for (int i = 0; i < player.length; i++)
        {
            if(player[i]==cardPlayer)
            {
                player[i].setChanceCard(this);
            }
        }
    }

    public void executeChance()
    {
        boolean allPropertyOwned=false;
        String choice;
        ArrayList<String> array = new ArrayList<String>();
        for (int i = 0; i < board.getField().length; i++)
        {
            if(board.getField()[i].getName().contains("PropertyField"))
            {
                PropertyField field = (PropertyField) board.getField()[i];
                if(field.getOwner()==null)
                {
                    array.add(board.getGuiFields()[i].getTitle());
                }
            }
        }

        if(array.size()==0)
        {
            for (int i = 0; i < board.getField().length; i++)
            {
                if (board.getField()[i].getName().contains("PropertyField"))
                {
                    PropertyField field = (PropertyField) board.getField()[i];
                    if (field.getOwner() == null)
                    {
                        array.add(board.getGuiFields()[i].getTitle());
                    }
                }
            }
            allPropertyOwned=true;
        }

        String[] options = new String[array.size()];
        options = array.toArray(options);

        if(!allPropertyOwned)
        {
            choice = gui.getUserSelection(cardPlayer.getName() + " du må hoppe hen på hvilken som helst ledigt felt og købe det", options);
        }
        else
        {
            choice = gui.getUserSelection("Der er ikke nogen ejendomme til salg, så du må købe den af en anden spiller", options);
        }
        for (int i = 0; i < board.getGuiFields().length; i++)
        {
            if(board.getGuiFields()[i].getTitle().equals(choice))
            {
                board.movePlayerPosition(cardPlayer,i);
                cardPlayer.setChanceCard(null);
                return;
            }
        }
    }
}

