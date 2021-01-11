package chancecard;

import game.GameBoard;
import gui_main.GUI;
import player.Player;

import java.awt.*;

public abstract class Chance
{
    protected Color col1;
    protected Color col2;
    protected String text;
    protected GameBoard board;
    protected GUI gui;
    protected Player[] player;

    public Chance(GameBoard board, GUI gui, Player[] player)
    {
        this.text = "";
        this.board=board;
        this.gui=gui;
        this.player=player;
    }

    public Chance(GameBoard board, GUI gui, Player[] player, String text)
    {

        this.text = text;
        this.board=board;
        this.gui=gui;
        this.player=player;
    }

    public abstract void executeChance(Player currentPlayer);
}
