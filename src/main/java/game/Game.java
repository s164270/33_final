package game;

import chancecard.*;
import dice.DiceCup;
import gui_main.GUI;
import player.Account;
import player.Player;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Game
{


    private final DiceCup dice;
    private final GameBoard board;
    private GUI gui;
    private Auction auction;
    private Player[] player;
    private Player currentPlayer;
    private ChanceCards chanceCards;
    private boolean gameOver;

    public Game()
    {
        dice = new DiceCup();
        GUI.setNull_fields_allowed(true);

        board = new GameBoard();
        gui = new GUI(board.getGuiFields(), Color.WHITE);
        board.setGui(gui);
        gameOver = false;

        gui.showMessage("Velkommen til spillet!\n");
        createPlayers();
        chanceCards=new ChanceCards(board, gui, player);
        chanceCards.createChance();
        board.createChanceFields(chanceCards);

        auction = new Auction(gui, player);
        board.createFields(auction);

        gui.showMessage("Okay " + currentPlayer.getName() + ", du starter.");
    }

    public Game(String playerName1, String playerName2)
    {
        // class creation for testing
        dice = new DiceCup();
        GUI.setNull_fields_allowed(true);

        board = new GameBoard();
        gui = new GUI(board.getGuiFields(), Color.WHITE);
        board.setGui(gui);

        gameOver = false;

        player=new Player[2];
        player[0] = new Player(playerName1);
        player[1] = new Player(playerName2);
        gui.addPlayer(player[0].getGuiPlayer());
        gui.addPlayer(player[1].getGuiPlayer());
        currentPlayer = player[0];
        chanceCards=new ChanceCards(board, gui, player);
        chanceCards.createChance();
        board.createChanceFields(chanceCards);
        auction = new Auction(gui, player);
        board.createFields(auction);

    }

    private void createPlayers()
    {
        int numPlayers = gui.getUserInteger("Vælg antal spillere",2,6);
        player = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++)
        {
            player[i] = new Player(gui);
            gui.addPlayer(player[i].getGuiPlayer());
            player[i].addPoints(30000);
        }

        //The starting player is chosen randomly
        currentPlayer = player[new Random().nextInt(numPlayers)];
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void close() {
        this.gui.close();
    }

    public void rollDice()
    {
        dice.rollDice();
        gui.setDice(dice.getDice1(), dice.getDice2());
    }

    public void turn(Player player)
    {
        gui.showMessage("Det er " + player.getName() + "'s tur");

        if(player.getChanceCard()!=null)
        {
            player.getChanceCard().executeChance();
            player.setChanceCard(null);
        }
        else
        {
            checkJail(player);
            gameOver();
            if(!gameOver)
            {
                rollDice();
                board.movePlayer(player, dice.getSum());
            }
        }
        gameOver();
        changePlayer();
    }

    public void gameOver() {
        for (int i = 0; i < player.length; i++) {
            if (player[i].isBroke()) {
                this.gameOver = true;
            }
        }
    }

    public void checkJail(Player player) {
        if (player.isInPrison()) {
            if (player.getFreePrison()) {
                player.setInPrison(false);
                player.setFreePrison(false);
            } else {
                player.addPoints(-1);
            }
        }
    }

    public void endGame()
    {
        this.gameOver = true;
        Player currentWinner = player[0];
        int currentMax = currentWinner.getPoints();

        for (int i = 1; i < player.length; i++) {
            if (player[i].getPoints() > currentMax)
            {
                currentMax = player[i].getPoints();
                currentWinner = player[i];
            }
        }

        gui.showMessage("Spillet er slut, " + currentWinner.getName() + " har vundet!!");
        gui.close();
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void changePlayer()
    {
        int index =Arrays.asList(player).indexOf(currentPlayer);
        if (index>= player.length-1)
        {
            currentPlayer=player[0];
        }
        else
        {
            currentPlayer=player[index+1];
        }
    }


    private void loadBoard()
    {

    }

    public void testFunction()
    {
        //Chance card= new ChanceMovePlayer(board, gui, player,"Ryk frem til et orange felt", Color.BLUE,Color.ORANGE, currentPlayer);
        //chanceCards.getRandomChance().executeChance(currentPlayer);
        board.movePlayerPosition(currentPlayer,15);
        board.movePlayerPosition(currentPlayer,15);
        board.movePlayerPosition(currentPlayer,15);
        board.movePlayerPosition(currentPlayer,15);
        board.movePlayerPosition(currentPlayer,15);
        board.movePlayerPosition(currentPlayer,15);
        board.movePlayerPosition(currentPlayer,15);
        board.movePlayerPosition(currentPlayer,15);
        //currentPlayer.getChanceCard().executeChance();
        //board.movePlayerPosition(currentPlayer,15);
    }

}
