package game;

import gui_fields.GUI_Ownable;
import player.Player;
import gui_fields.GUI_Field;

public class PropertyField extends Field{
    private int cost;
    private int rent[];
    private int num_houses;
    private boolean paired = false;

    private PropertyField neighbor[];
    private Player owner;

    public PropertyField()
    {
        this.cost = 0;
    }

    public PropertyField(String name, int cost, int rent[])
    {
        super(name);
        this.cost = cost;
        this.rent = rent;
        this.num_houses = 0;
    }

    public PropertyField(String name, int cost, int rent[], PropertyField neighbor[])
    {
        super(name);
        this.cost = cost;
        this.rent = rent;

        boolean self_neighbor =true;
        for (int i = 0; i < neighbor.length; i++)
        {
            self_neighbor = self_neighbor && neighbor[i] == this;
        }

        this.setNeighbor(neighbor);
        this.num_houses = 0;
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

    public void setNeighbor(PropertyField neighbor[]) {
        this.neighbor=neighbor;
    }
    public void setNeighbors(PropertyField neighbor[]) {
        for (int i = 0; i < neighbor.length; i++)
        {
            neighbor[i].setNeighbor(neighbor);
        }
    }
    @Override
    public String landOnField(Player player) {


        if(owner == null) //player has to buy the property
        {
            player.addPoints(-cost);
            setOwner(player);
            boolean pair =true;
            for (int i = 0; i < neighbor.length; i++)
            {
                pair = pair &&neighbor[i].getOwner() == this.owner;
                neighbor[i].setPaired(true);
            }

            for (int i = 0; i < neighbor.length; i++)
            {
                neighbor[i].setPaired(pair);
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
