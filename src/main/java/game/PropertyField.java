package game;

import gui_fields.GUI_Ownable;
import player.Player;
import gui_fields.GUI_Field;

public class PropertyField extends Field{
    private int cost;
    private boolean paired = false;

    private PropertyField neighbor;
    private Player owner;

    public PropertyField()
    {
        this.cost = 0;
    }

    public PropertyField(String name, int cost)
    {
        super(name);
        this.cost = cost;
    }

    public PropertyField(String name, int cost, PropertyField neighbor)
    {
        super(name);
        this.cost = cost;
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

    @Override
    public String landOnField(Player player) {

        if(owner == null) //player has to buy the property
        {
            player.addPoints(-cost);
            setOwner(player);

            if(neighbor.getOwner() == this.owner)
            {
                paired = true;
                neighbor.setPaired(true);
            }
        }
        else if(owner != player)
        {
            if(paired)
            {
                player.sendPoints(owner, 2*cost);
            }
            else
            {
                player.sendPoints(owner, cost);
            }
        }
        return player.getName() + " " +  "landede på ejendomsfeltet" + " " + guiField.getTitle();
    }
}
