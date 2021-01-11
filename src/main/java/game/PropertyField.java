package game;

import gui_fields.GUI_Ownable;
import gui_main.GUI;
import player.Player;
import gui_fields.GUI_Field;

import java.util.ArrayList;

public class PropertyField extends Field{
    private int cost;
    private int rent[];
    private int num_houses;
    private boolean paired = false;

    private PropertyField neighbor[];
    private Player owner;
    private Auction auction;

    public PropertyField()
    {
        this.cost = 0;
    }

    public PropertyField(String name, int cost, int rent[], GUI gui, Auction auction)
    {
        super(name, gui);
        this.cost = cost;
        this.rent = rent;
        this.num_houses = 0;
        this.auction = auction;
    }

    public PropertyField(String name, int cost, int rent[], PropertyField neighbor[], GUI gui, Auction auction)
    {
        super(name, gui);
        this.cost = cost;
        this.rent = rent;
        this.num_houses = 0;
        this.auction = auction;
        PropertyField temp_neighbor[] = new PropertyField[neighbor.length+1];
        for (int i = 0; i < neighbor.length; i++)
        {
            temp_neighbor[i]=neighbor[i];
        }
        temp_neighbor[neighbor.length]=this;
        this.setNeighbors(temp_neighbor);
    }

    public Player getOwner() {
        return owner;
    }
    public void setOwner(Player player) {
        owner=player;
        ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
    }

    public void setPaired(boolean paired) {
        this.paired = paired;
    }

    public boolean isPaired() {
        return paired;
    }

    public void setNeighbor(PropertyField neighbor[]) {
        this.neighbor=neighbor;
    }
    public void setNeighbors(PropertyField neighbor[]) {
        for (int i = 0; i < neighbor.length; i++)
        {
            neighbor[i].setNeighbor(neighbor);
        }
    }

    private void buyProperty(Player player)
    {
        player.addPoints(-cost);
        setOwner(player);
        boolean pair =true;
        for (int i = 0; i < neighbor.length; i++)
        {
            pair = pair && neighbor[i].getOwner() == this.getOwner();
            neighbor[i].setPaired(true);
        }
        for (int i = 0; i < neighbor.length; i++)
        {
            neighbor[i].setPaired(pair);
        }
    }

    public void buyHouse()
    {
        // TODO make housePrice part of constructer so it is defined when the property is created
        int housePrice = 2000;
       this.getOwner().addPoints(-housePrice);
        // TODO make sure that all prices are added before this is implemented
       this.num_houses+=1;
    }

    public void buyProperty(Player player, int auctionPrice)
    {
        player.addPoints(-auctionPrice);
        setOwner(player);
        boolean pair =true;
        for (int i = 0; i < neighbor.length; i++)
        {
            pair = pair && neighbor[i].getOwner() == this.getOwner();
            neighbor[i].setPaired(true);
        }
        for (int i = 0; i < neighbor.length; i++)
        {
            neighbor[i].setPaired(pair);
        }
    }

    @Override
    public String landOnField(Player player) {
        if(owner == null) //player has to buy the property
        {
            switch (gui.getUserButtonPressed("Vil du købe " + guiField.getTitle() + "for " + cost + "?", "JA", "NEJ"))
            {
                case "JA":
                    buyProperty(player);
                    break;
                case "NEJ":
                    auction.startAuction(this);
                    break;
                default:
                    System.out.println("FEJL");
            }
        }
        else if(owner != player)
        {
            if(paired)
            {
                player.sendPoints(owner, 2*rent[num_houses]);
            }
            else
            {
                player.sendPoints(owner,rent[num_houses]);
            }
        }
        return player.getName() + " " +  "landede på ejendomsfeltet" + " " + guiField.getTitle();
    }
}
