package chancecard;

import game.GameBoard;
import game.Ownable;
import game.PropertyField;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public class ChancePayPropertyTax extends Chance
{
    String[] options;
    boolean taxType;

    public ChancePayPropertyTax(GameBoard board, GUI gui, Player[] player)
    {
        super(board,gui,player);
    }

    public ChancePayPropertyTax(GameBoard board, GUI gui, Player[] player, String text)
    {
        super(board, gui, player, text);
    }

    public ChancePayPropertyTax(GameBoard board, GUI gui, Player[] player, String text, boolean taxType)
    {
        super(board, gui, player, text);
        this.options=options;
        this.taxType = taxType;
    }


    @Override
    public void executeChance(Player currentPlayer)
    {
        int houseCount = currentPlayer.getHouseCount();
        int hotelCount = currentPlayer.getHotelCount();
        gui.displayChanceCard(text);

        if(taxType)
        {
            currentPlayer.sendPoints(null, houseCount * 500 + hotelCount * 2000);
        }
        else
        {
            currentPlayer.sendPoints(null, houseCount * 800 + hotelCount * 2300);
        }

    }
}
