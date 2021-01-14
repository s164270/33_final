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
    private boolean cheatDice =false;

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
        board.blacken();

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
        player[0] = new Player(playerName1, gui);
        player[1] = new Player(playerName2, gui);
        player[0].setGameRef(this);
        player[1].setGameRef(this);
        gui.addPlayer(player[0].getGuiPlayer());
        gui.addPlayer(player[1].getGuiPlayer());
        currentPlayer = player[0];
        chanceCards=new ChanceCards(board, gui, player);
        chanceCards.createChance();
        board.createChanceFields(chanceCards);
        auction = new Auction(gui, player);
        board.createFields(auction);
        board.blacken();

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
            player[i].setGameRef(this);
        }

        //The starting player is chosen randomly
        currentPlayer = player[new Random().nextInt(numPlayers)];
    }

    public GameBoard getBoard()
    {
        return board;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void close() {
        this.gui.close();
    }

    public void rollDice()
    {
        if (!cheatDice)
        {
            dice.rollDice();
        }
        else
        {
            dice.setDice1(gui.getUserInteger("vælg terningslag 1", 1,6));
            dice.setDice2(gui.getUserInteger("vælg terningslag 2", 1,6));
        }
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
                        "Slå dig fri", "Betal dig fri", "Brug chancekort", "Byg huse", "Byg hotel", "Sælg huse", "Sælg hotel", "Pantsæt", "Genkøb");
            }
            else if(mustRoll)
            {
                btnChoice = gui.getUserSelection("Det er " + player.getName() + "'s tur. Hvad vil du foretage dig?",
                        "Slå", "Byg huse", "Byg hotel", "Sælg huse", "Sælg hotel", "Pantsæt", "Genkøb");
            }
            else
            {
                btnChoice = gui.getUserSelection("Det er " + player.getName() + "'s tur. Hvad vil du foretage dig?",
                        "Afslut tur", "Byg huse", "Byg hotel", "Sælg huse", "Sælg hotel", "Pantsæt", "Genkøb");
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
                            gui.showMessage("Du har slået to ens for mange gange i træk, du ryger derfor i fængsel");
                            mustRoll = false;
                            twoOfAKindCounter = 0;
                            player.setInPrison(true);
                            board.movePlayerPosition(player,10);
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
                case "Sælg hotel":
                    sellHotel(player);
                    break;
                case "Sælg huse":
                    sellHouses(player);
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
                    if(player.getPrisonRolls()<3)
                    {
                        rollDice();
                        if (dice.isSimiliar())
                        {
                            player.setPrisonRolls(0);
                            player.setInPrison(false);
                            board.movePlayer(player, dice.getSum());
                        } else
                        {
                            player.setPrisonRolls(player.getPrisonRolls()+1);
                            mustRoll = false;
                        }
                    }
                    else
                    {
                        gui.showMessage("Du kan ikke slå dig fri længere, du skal betale dig fri");
                    }
                    break;
                case "Betal dig fri":
                    if(player.getPoints() < 1000)
                    {
                        gui.showMessage("Det har du ikke råd til");
                        player.sendPoints(null,1000);
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
                if(player.getPoints() < hotelProperty.getHousePrice()) {
                    gui.showMessage("Du har ikke råd til at bygge dette hotel");
                }
                else {
                    hotelProperty.buyHotel();
                }
            }
        }
    }

    public void sellHotel(Player player)
    {
        int propertyCount = 0;
        for(int i = 0; i < board.getField().length; i++)
        {
            if(board.getField()[i] instanceof PropertyField
                    && ((PropertyField)board.getField()[i]).getOwner() == player
                    && ((PropertyField)board.getField()[i]).isHotelBuild()) {
                propertyCount++;
            }
        }

        if(propertyCount < 1) {
            gui.showMessage("Du ejer ikke nogen hoteller der kan sælges");
        }
        else
        {
            String[] userOptions = new String[propertyCount + 1]; // + 1 to include the option to go back

            for(int i = 0, j = 0; i < board.getField().length; i++)
            {
                if(board.getField()[i] instanceof PropertyField
                        && ((PropertyField)board.getField()[i]).getOwner() == player
                        && ((PropertyField)board.getField()[i]).isHotelBuild()) {
                    userOptions[j] = ((PropertyField)board.getField()[i]).getName();
                    j++;
                }
            }
            userOptions[userOptions.length - 1] = "Tilbage";

            String hotelSelection = gui.getUserSelection("Vælg hvilket hotel der skal sælges", userOptions);
            if(!hotelSelection.equals("Tilbage"))
            {
                ((PropertyField)board.getFieldFromString(hotelSelection)).sellHotel();
            }
        }

    }

    public void sellHouses(Player player)
    {
        //count the number of properties with sellable houses owned by the player
        int propertyCount = 0;
        for(int i = 0; i < board.getField().length; i++)
        {
            if(board.getField()[i] instanceof PropertyField)
            {
                if(((PropertyField)board.getField()[i]).getOwner() == player
                        && ((PropertyField)board.getField()[i]).housesCanBeSold())
                {
                    propertyCount++;
                }
            }
        }
        if(propertyCount < 1)
        {
            gui.showMessage("Ingen af dine huse kan sælges på nuværende tidspunkt.");
        }
        else
        {
            String[] userOptions = new String[propertyCount + 1]; // + 1 to include the option to go back

            for(int i = 0, j = 0; i < board.getField().length; i++)
            {
                if(board.getField()[i] instanceof PropertyField)
                {
                    if(((PropertyField)board.getField()[i]).getOwner() == player
                            && ((PropertyField)board.getField()[i]).housesCanBeSold())
                    {
                        userOptions[j] = ((PropertyField)board.getField()[i]).getName();
                        j++;
                    }
                }
            }
            userOptions[userOptions.length - 1] = "Tilbage";

            String hotelSelection = gui.getUserSelection("Vælg hvilken grund der skal sælges huse fra", userOptions);
            if(!hotelSelection.equals("Tilbage"))
            {
                int housesToSell = gui.getUserInteger("Hvor mange vil du sælge?", 0, 12);
                ((PropertyField)board.getFieldFromString(hotelSelection)).sellHouses(housesToSell);
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
        int brokePlayers=0;
        for (int i = 0; i < player.length; i++) {
            if (player[i].isBroke()) {
                brokePlayers++;
            }
        }
        if(brokePlayers== player.length-1)
        this.gameOver = true;
    }

    public void endGame()
    {
        this.gameOver = true;
        Player currentWinner = null;
        for (int i = 0; i < player.length; i++) {
            if (!player[i].isBroke())
            {
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

        if(currentPlayer.isBroke())
        {
            changePlayer();
        }
    }

    public void testFunction()
    {
        boolean turnDone=true;
        String btnChoice;
        int userInt;

        while (turnDone)
        {
            btnChoice = gui.getUserSelection(currentPlayer.getName() + " i TEST MODE. Hvad vil du foretage dig?",
                    "move",
                    "add money",
                    "change player",
                    "normal turn",
                    "random chance card",
                    "activate cheat dice",
                    "deactivate cheat dice",
                    "send to prison",
                    "start normal game",
                    "end");

            switch (btnChoice)
            {
                case "move":
                    userInt = gui.getUserInteger("vælg feltnummer 0 - 39", 0,39);
                    board.movePlayerPosition(currentPlayer, userInt);
                    break;
                case "change player":
                    changePlayer();
                    break;
                case "add money":
                    userInt = Integer.parseInt(gui.getUserString("vælg pengesum"));
                    currentPlayer.addPoints(userInt);
                    break;
                case "activate cheat dice":
                    cheatDice=true;
                    break;
                case "deactivate cheat dice":
                    cheatDice=false;
                    break;
                case "normal turn":
                    turn(currentPlayer);
                    break;
                case "send to prison":
                    board.movePlayerPosition(currentPlayer, 30);
                    break;
                case "random chance card":
                    chanceCards.getRandomChance().executeChance(currentPlayer);
                    break;
                case "end":
                    turnDone=false;
                    break;
                case "start normal game":
                    while(!isGameOver())
                    {
                        turn(getCurrentPlayer());
                    }
                    endGame();
                    turnDone=false;
                    break;
            }
        }

    }

}
