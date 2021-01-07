package player;

import chancecard.ChanceMovePlayer;
import game.GameBoard;
import gui_fields.GUI_Car;
import gui_fields.GUI_Player;
import gui_main.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

public class Player
{
    private int position = 0;
    private Account account;
    private String playerName;
    private ChanceMovePlayer chanceCard;
    private GUI_Player gui_player;
    private boolean FreePrison;
    private boolean inPrison;
    private boolean broke;
    private static String[] colors = {"BLUE",
            "CYAN",
            "DARK_GRAY",
            "GRAY",
            "GREEN",
            "LIGHT_GRAY",
            "MAGENTA",
            "ORANGE",
            "PINK",
            "RED",
            "WHITE",
            "YELLOW"};



    public Player(GUI gui)
    {
        try
        {
            account = new Account(0);
            FreePrison = false;
            inPrison = false;
            broke = false;
            playerName = gui.getUserString("Input player name");
            String col = gui.getUserSelection("Choose color for " + playerName,
                    colors
            );

            Color i = (Color) Color.class.getDeclaredField(col).get(null);
            GUI_Car car = new GUI_Car();
            car.setPrimaryColor(i);
            List<String> list = new ArrayList<>(Arrays.asList(colors));
            list.removeAll(Collections.singleton((col)));
            colors = new String[list.size()];
            colors = list.toArray(colors);
            gui_player = new GUI_Player(playerName, account.getBalance(), car);
        } catch (IllegalAccessException | NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }
    public Player(String playerName)
    {
        this.playerName=playerName;
        account = new Account(20);
        GUI_Car car = new GUI_Car();
        car.setPrimaryColor(Color.RED);
        gui_player = new GUI_Player(playerName, account.getBalance(), car);
    }

    public int getPosition() {
        return position;
    }

    public void sendPoints(Player recipient, int amount)
    {
        if(amount < 0)
        {
            throw new IllegalArgumentException("sendPoints received a negative amount");
        }
        else
        {
            int temp = account.withdraw(amount);
            recipient.addPoints(temp);
            gui_player.setBalance(account.getBalance());
            if(temp < amount)
            {
                broke = true;
            }
        }
    }

    public void move(int distance)
    {
        if(distance < 0 || distance > GameBoard.NFIELDS)
            throw new IllegalArgumentException("distance can't be <0 or be >" +  GameBoard.NFIELDS);
        if(position + distance < GameBoard.NFIELDS)
            position = position + distance;
        else
            position = (position + distance) - GameBoard.NFIELDS;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName()
    {
        return playerName;
    }

    public int getPoints()
    {
        return account.getBalance();
    }

    public boolean getFreePrison() {return FreePrison;}
    public void setFreePrison(boolean jail) { FreePrison=jail;}

    public boolean isBroke() {
        return broke;
    }

    public void setInPrison(boolean inPrison) {
        this.inPrison = inPrison;
    }

    public boolean isInPrison() {
        return inPrison;
    }

    public void addPoints(int points)
    {
        if (points > 0)
        {
            account.deposit(points);
        }
        else
        {
            int temp = account.withdraw(Math.abs(points));
            if(temp < Math.abs(points))
            {
                broke = true;
            }
        }
        this.gui_player.setBalance(account.getBalance());
    }

    public GUI_Player getGuiPlayer()
    {
        return gui_player;
    }

    public ChanceMovePlayer getChanceCard()
    {
        return chanceCard;
    }

    public void setChanceCard(ChanceMovePlayer chanceCard)
    {
        this.chanceCard = chanceCard;
    }
}
