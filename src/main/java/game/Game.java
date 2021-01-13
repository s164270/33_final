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
            String selection[];
            Ownable f;

            if(player.isInPrison() && mustRoll)
            {

                btnChoice = gui.getUserSelection(player.getName() + " er i fængsel. Hvad vil du foretage dig?",
                        "Slå dig fri", "Betal dig fri", "Brug chancekort", "Byg huse", "Byg hotel", "Pantsæt", "Genkøb");
            }
            else if(mustRoll)
            {
                btnChoice = gui.getUserSelection("Det er " + player.getName() + "'s tur. Hvad vil du foretage dig?",
                        "Slå", "Byg huse", "Byg hotel", "Pantsæt", "Genkøb");
            }
            else
            {
                btnChoice = gui.getUserSelection("Det er " + player.getName() + "'s tur. Hvad vil du foretage dig?",
                        "Afslut tur", "Byg huse", "Byg hotel", "Pantsæt", "Genkøb");
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
                case "Byg huse":
                    buildHouses(player);
                    break;
                case "Byg hotel":
                    buildHotel(player);
                    break;
                case "Pantsæt":
                    selection = board.getFieldString(player.getPawnableProperties());
                    if (selection!= null)
                    {
                        prop= gui.getUserSelection("Vælg ejendom som du vil pantsætte",selection);
                        f = (Ownable) board.getFieldFromString(prop);
                        f.pawnOff();
                    }
                    else
                    {
                        gui.showMessage("Du har ingen ejendomme du kan pantsætte");
                    }
                    break;
                case "Genkøb":
                    selection = board.getFieldString(player.getPawnedProperties());
                    if (selection!= null)
                    {
                        prop= gui.getUserSelection("Vælg ejendom som du vil genkøbe",selection);
                        f = (Ownable) board.getFieldFromString(prop);
                        f.rebuy();
                    }
                    else
                    {
                        gui.showMessage("Du har ingen pantsatte ejendomme");
                    }
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

    public void buildHotel(Player player)
    {
        int propertyCount = 0;
        for(int i = 0; i < board.getField().length; i++)
        {
            if(board.getField()[i] instanceof PropertyField
                    && ((PropertyField)board.getField()[i]).getOwner() == player
                    && ((PropertyField)board.getField()[i]).isReadyForHotel()) {
                propertyCount++;
            }
        }

        if(propertyCount < 1) {
            gui.showMessage("Du ejer ikke nogen grunde der kan bygges hoteller på");
        }
        else
        {
            PropertyField[] properties = new PropertyField[propertyCount];;
            String[] userOptions = new String[propertyCount + 1]; // + 1 to include the option to go back

            for(int i = 0, j = 0; i < board.getField().length; i++)
            {
                if(board.getField()[i] instanceof PropertyField
                        && ((PropertyField)board.getField()[i]).getOwner() == player
                        && ((PropertyField)board.getField()[i]).isReadyForHotel()) {
                    properties[j] = (PropertyField)board.getField()[i];
                    userOptions[j] = properties[j].getName();
                    j++;
                }
            }
            userOptions[userOptions.length - 1] = "Tilbage";

            String hotelSelection = gui.getUserSelection("Vælg hvilken grund der skal bygges hotel på", userOptions);
            if(!hotelSelection.equals("Tilbage"))
            {
                PropertyField hotelProperty = (PropertyField)board.getFieldFromString(hotelSelection);
                if(player.getPoints() < hotelProperty.getHotelPrice()) {
                    gui.showMessage("Du har ikke råd til at bygge dette hotel");
                }
                else {
                    hotelProperty.buyHotel();
                }
            }
        }
    }

    public void buildHouses(Player player)
    {
        if(player.getPoints() < 1000)
        {
            gui.showMessage("Du har ikke råd til noget");
        }
        else
        {
            PropertyField[] properties;
            String[] userOptions;
            int propertyCount = 0;

            //count the number of properties owned by the player (that can be build on)
            for(int i = 0; i < board.getField().length; i++)
            {
                if(board.getField()[i] instanceof PropertyField)
                {
                    if(((PropertyField)board.getField()[i]).getOwner() == player
                            && ((PropertyField)board.getField()[i]).isPaired()
                            && !((PropertyField)board.getField()[i]).isHotelBuild()
                            && ((PropertyField)board.getField()[i]).getNum_houses() < 4)
                    {
                        propertyCount++;
                    }
                }
            }
            if(propertyCount < 1)
            {
                gui.showMessage("Du ejer ikke nogen grunde der kan bygges huse på");
            }
            else
            {
                properties = new PropertyField[propertyCount];
                userOptions = new String[propertyCount + 1]; // + 1 to include the option to go back
                for(int i = 0, j = 0; i < board.getField().length; i++)
                {
                    if(board.getField()[i] instanceof PropertyField)
                    {
                        if(((PropertyField)board.getField()[i]).getOwner() == player
                                && ((PropertyField)board.getField()[i]).isPaired()
                                && !((PropertyField)board.getField()[i]).isHotelBuild()
                                && ((PropertyField)board.getField()[i]).getNum_houses() < 4)
                        {
                            properties[j] = (PropertyField)board.getField()[i];
                            userOptions[j] = properties[j].getName();
                            j++;
                        }
                    }
                }
                userOptions[userOptions.length - 1] = "Tilbage";
                int selectionIndex = userOptions.length - 1;
                String propSelection = gui.getUserSelection("Vælg hvilken grund der skal bygges på", userOptions);

                for (int i = 0; i < properties.length; i++) {
                    if (properties[i].getName().equals(propSelection)) {
                        selectionIndex = i;
                        break;
                    }
                }
                if(selectionIndex < properties.length)
                {
                    int numberOfHouses = gui.getUserInteger("Hvor mange vil du købe?", 0, 12);
                    properties[selectionIndex].buyHouse(numberOfHouses);

                }
            }
        }
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
