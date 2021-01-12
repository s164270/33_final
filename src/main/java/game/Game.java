package game;

import chancecard.*;
import dice.DiceCup;
import gui_main.GUI;
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
        currentPlayer.setDiceSum(dice.getSum());

        gui.setDice(dice.getDice1(), dice.getDice2());
    }

    public void turn(Player player)
    {
        boolean turnDone = false;
        boolean mustRoll = true; //rolling the dice is compulsory
        int twoOfAKindCounter = 0;
        //gui.showMessage("Det er " + player.getName() + "'s tur");
        while (!turnDone)
        {
            String btnChoice;
            String prop;
            Ownable f;

            if(player.isInPrison() && mustRoll)
            {
                btnChoice = gui.getUserSelection(player.getName() + " er i fængsel. Hvad vil du foretage dig?",
                        "Slå dig fri", "Betal dig fri", "Brug chancekort", "Byg", "Pantsæt", "Genkøb");
            }
            else if(mustRoll)
            {
                btnChoice = gui.getUserSelection("Det er " + player.getName() + "'s tur. Hvad vil du foretage dig?",
                        "Slå", "Byg", "Pantsæt", "Genkøb");
            }
            else
            {
                btnChoice = gui.getUserSelection("Det er " + player.getName() + "'s tur. Hvad vil du foretage dig?",
                        "Afslut tur", "Byg", "Pantsæt", "Genkøb");
            }

            switch (btnChoice)
            {
                case "Slå":
                    rollDice();
                    if(dice.isSimiliar())
                    {
                        twoOfAKindCounter++;
                        if(twoOfAKindCounter == 3)
                        {
                            mustRoll = false;
                            twoOfAKindCounter = 0;
                            player.setInPrison(true);
                        }
                        else
                        {
                            board.movePlayer(player, dice.getSum());
                        }
                    }
                    else
                    {
                        mustRoll = false;
                        board.movePlayer(player, dice.getSum());
                    }
                    break;
                case "Byg":
                    gui.showMessage("Byg er ikke implementeret endnu");
                    break;
                case "Pantsæt":
                    prop= gui.getUserSelection("Vælg ejendom som du vil pantsætte",board.getFieldString(player.getPawnableProperties()));
                    f = (Ownable) board.getFieldFromString(prop);
                    f.pawnOff();
                    break;
                case "Genkøb":
                    prop= gui.getUserSelection("Vælg ejendom som du vil genkøbe",board.getFieldString(player.getPawnedProperties()));
                    f = (Ownable) board.getFieldFromString(prop);
                    f.rebuy();
                    break;
                case "Afslut tur":
                    turnDone = true;
                    break;
                case "Slå dig fri":
                    rollDice();
                    if(dice.isSimiliar())
                    {
                        player.setInPrison(false);
                        board.movePlayer(player, dice.getSum());
                    }
                    else
                    {
                        mustRoll = false;
                    }
                    break;
                case "Betal dig fri":
                    if(player.getPoints() < 1000)
                    {
                        gui.showMessage("Det har du ikke råd til");
                    }
                    else
                    {
                        player.addPoints(-1000);
                        player.setInPrison(false);
                    }
                    break;
                case "Brug chancekort":
                    if(player.getFreePrison())
                    {
                        player.setInPrison(false);
                        player.setFreePrison(false);
                    }
                    else
                    {
                        gui.showMessage("Du har ikke det chancekort");
                    }
                    break;
                default:
                    System.out.println("ERROR IN SWITCH");
            }
            if(player.isBroke())
            {
                turnDone = true;
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
