package player;

import game.CompanyField;
import game.ShippingField;
import game.*;
import gui_fields.GUI_Car;
import gui_fields.GUI_Player;
import gui_main.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static game.GameBoard.NFIELDS;

public class Player
{

    private final ArrayList<ShippingField> shipping = new ArrayList<>();
    private final ArrayList<CompanyField>  company = new ArrayList<>();
    private final ArrayList<Field>  ownedFields = new ArrayList<>();

    private int diceSum;
    private GUI gui;
    private Game game;
    private int position = 0;
    private int prisonRolls = 0;
    private Account account;
    private String playerName;
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

            this.gui = gui;
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
    public Player(String playerName, GUI gui)
    {
        this.gui = gui;
        this.playerName=playerName;
        account = new Account(30000);
        GUI_Car car = new GUI_Car();
        if (playerName.contains("2"))
        {
            car.setPrimaryColor(Color.GREEN);
        }
        else
        {
            car.setPrimaryColor(Color.RED);
        }
        gui_player = new GUI_Player(playerName, account.getBalance(), car);
    }

    public void setGameRef(Game game)
    {
        this.game = game;
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
            System.out.println("Actual worth: " + getActualWorth());
            System.out.println("Amount to send: " + amount);

            if(amount > getActualWorth())
            {
                goingBroke(recipient); //go broke
            }
            else if(amount > getPoints())
            {
                brokeMenu(amount);
                addPoints(-amount);
                if(recipient!=null)
                    recipient.addPoints(amount);
            }
            else
            {
                addPoints(-amount);
                if(recipient!=null)
                    recipient.addPoints(amount);
            }
        }
    }

    public void move(int distance)
    {
        if(distance < 0 || distance > NFIELDS)
            throw new IllegalArgumentException("distance can't be <0 or be >" +  NFIELDS);
        if(position + distance < NFIELDS)
            position = position + distance;
        else
            position = (position + distance) - NFIELDS;
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
        }
        this.gui_player.setBalance(account.getBalance());
    }

    public void brokeMenu(int cost)
    {
        String btnChoice;
        while (getPoints() < cost)
        {
            btnChoice = gui.getUserSelection(getName() + " skal sælge ud af sine ejendomme for at kunne betale "+ cost,
                    "Pantsæt ejendomme", "Sælg huse", "Sælg hoteller");

            switch (btnChoice)
            {
                case "Pantsæt ejendomme":
                    String[] selection = game.getBoard().getFieldString(getPawnableProperties());
                    if (selection!= null)
                    {
                        String prop= gui.getUserSelection("Vælg ejendom som du vil pantsætte",selection);
                        Ownable f = (Ownable) game.getBoard().getFieldFromString(prop);
                        f.pawnOff();
                    }
                    else
                    {
                        gui.showMessage("Du har ingen ejendomme du kan pantsætte");
                    }
                    break;
                case "Sælg huse":
                    game.sellHouses(this);
                    break;
                case "Sælg hoteller":
                    game.sellHotel(this);
                    break;
            }
        }
    }


    public void goingBroke(Player playerReceive)
    {
        for (int i = 0; i < getOwnedFields().size(); i++)
        {
            if(getOwnedFields().get(i) instanceof PropertyField)
            {
                PropertyField prop = (PropertyField) getOwnedFields().get(i);
                if (prop.isHotelBuild())
                {
                    prop.sellHotel();
                }
                prop.sellHouses(prop.getTotalHouses());
            }
            Ownable field= (Ownable) getOwnedFields().get(i);
            if (!field.isPawned())
                field.pawnOff();
            field.setOwner(playerReceive);
        }
        if (!(playerReceive ==null))
        {
            playerReceive.addPoints(getPoints());
            addPoints(-getPoints());
        }
        else
            addPoints(-getPoints());

        broke = true;
    }


    public GUI_Player getGuiPlayer()
    {
        return gui_player;
    }

    public ArrayList<CompanyField> getCompany() {
        return company;
    }

    public ArrayList<ShippingField> getShipping() {
        return shipping;
    }

    public ArrayList<Field> getOwnedFields() {
        return ownedFields;
    }

    public Field[] getPawnableProperties(){
        Field temp[] = new Field[NFIELDS];
        Field result[] =null;
        int counter = 0;
        for (int i = 0; i < ownedFields.size(); i++)
        {
            Ownable f = (Ownable)ownedFields.get(i);
            if (f.isPawnable())
            {
                temp[counter]= ownedFields.get(i);
                counter++;
            }
        }
        if (counter>0)
        {
            result = new Field[counter];
            for (int i = 0; i < counter; i++)
            {
                result[i] = temp[i];
            }
        }
        return result;
    }

    public Field[] getPawnedProperties() {
        Field temp[] = new Field[NFIELDS];
        Field result[] =null;
        int counter = 0;

        for (int i = 0; i < ownedFields.size(); i++)
        {
            Ownable f = (Ownable)ownedFields.get(i);
            if (f.isPawned() && getPoints()> (int) ((f.getPrice()/2)*1.1))
            {
                temp[counter]= ownedFields.get(i);
                counter++;
            }
        }
        if (counter>0)
        {
            result = new Field[counter];
            for (int i = 0; i < counter; i++)
            {
                result[i] = temp[i];
            }
        }
        return result;
    }

    public int getDiceSum() {
        return diceSum;
    }

    public int getTotalWorth() {
        Ownable temp;
        int totalPrice = 0;
        for (int i = 0; i < ownedFields.size(); i++)
        {
            temp = (Ownable) ownedFields.get(i);
            totalPrice += temp.totalPrice();
        }
        return totalPrice + getPoints();
    }
    public int getActualWorth() {
        Ownable temp;
        int totalPrice = 0;
        for (int i = 0; i < ownedFields.size(); i++)
        {
            temp = (Ownable) ownedFields.get(i);
            if(!temp.isPawned())
                totalPrice += (temp.totalPrice())/2;
        }
        return totalPrice + getPoints();
    }

    public void setDiceSum(int sum) {
        this.diceSum = sum;
    }

    public void setPrisonRolls(int num)
    {
        prisonRolls = num;
    }
    public int getPrisonRolls()
    {
        return prisonRolls;
    }
}
