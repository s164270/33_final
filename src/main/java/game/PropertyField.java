package game;

import gui_fields.GUI_Ownable;
import gui_main.GUI;
import player.Player;
import gui_fields.GUI_Field;

public class PropertyField extends Field{
    private int cost;
    private int rent;
    private boolean paired = false;

    private PropertyField neighbor;
    private Player owner;

    public PropertyField()
    {
        this.cost = 0;
    }

    public PropertyField(String name, int cost, int rent, GUI gui)
    {
        super(name, gui);
        this.cost = cost;
        this.rent = rent;
    }

    public PropertyField(String name, int cost, int rent, PropertyField neighbor, GUI gui)
    {
        super(name, gui);
        this.cost = cost;
        this.rent = rent;
        this.neighbor = neighbor;
        neighbor.setNeighbor(this);
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

    public void setNeighbor(PropertyField neighbor)
    {
        this.neighbor = neighbor;
    }

    private void buyProperty(Player player)
    {
        player.addPoints(-cost);
        setOwner(player);
        if(neighbor.getOwner() == this.owner)
        {
            paired = true;
            neighbor.setPaired(true);
        }
    }

    public void buyProperty(Player player, int auctionPrice)
    {
        player.addPoints(-auctionPrice);
        setOwner(player);
        if(neighbor.getOwner() == this.owner)
        {
            paired = true;
            neighbor.setPaired(true);
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
                    System.out.println("Ejendommen sættes på auktion");
                    break;
                default:
                    System.out.println("FEJL");
            }
        }
        else if(owner != player)
        {
            if(paired)
            {
                player.sendPoints(owner, 2*rent);
            }
            else
            {
                player.sendPoints(owner,rent);
            }
        }
        return player.getName() + " " +  "landede på ejendomsfeltet" + " " + guiField.getTitle();
    }
}
