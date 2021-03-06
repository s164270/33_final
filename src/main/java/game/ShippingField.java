package game;

import gui_fields.GUI_Field;
import gui_fields.GUI_Ownable;
import gui_main.GUI;
import player.Player;

public class ShippingField extends Field implements Ownable{

    private final String name;
    private final int cost;
    private final int[] rent;

    protected final GUI gui;

    private Player owner;
    private Auction auction;
    private boolean pawned;

    ShippingField(String name, int cost, GUI gui, Auction auction,  int... rent) {
        this.name = name;
        this.cost = cost;
        this.gui = gui;
        this.rent = rent;
        this.auction = auction;
        this.pawned=false;
    }

    @Override
    public void setGuiField(GUI_Field guiField) {
        this.guiField = guiField;
        String description=String.format("leje %d kr <br>",rent[0]);
        for (int i =2 ; i <=4 ; i++)
        {
            description += String.format("hvis %d rederier ejes %d kr <br>",i,rent[i-1]);
        }
        guiField.setDescription(description);
    }

    private void buyProperty(Player player) {
        player.addPoints(-cost);
        setOwner(player);

    }

    @Override
    public void setOwner(Player player) {
        owner = player;
        if (owner!= null)
        {
            owner.getShipping().add(this);
            owner.getOwnedFields().add(this);
            ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
            for (int i = 0; i < player.getShipping().size(); i++)
            {
                player.getShipping().get(i).guiField.setSubText("kr. " + getRent());
            }
        }
        else
        {
            ((GUI_Ownable)guiField).setBorder(null);
            pawned=false;
            guiField.setSubText("kr. " + getPrice());
        }
    }

    @Override
    public Player getOwner()
    {
        return owner;
    }

    public void buyProperty(Player player, int auctionPrice) {
        player.addPoints(-auctionPrice);
        setOwner(player);
    }

    @Override
    public void landOnField(Player player) {
        if (owner == null) {
            gui.showMessage(player.getName() + " landede på  " + "" + guiField.getTitle());

            String option = gui.getUserButtonPressed("Vil du købe " + guiField.getTitle() + " for " + cost + "?", "Ja", "Nej");
            if (option.toLowerCase().equals("ja")) {
                if (player.getPoints() >= cost) {
                    buyProperty(player);
                    gui.showMessage(player.getName() + " har købt " + guiField.getTitle() + " for " + cost + " kr.");
                } else {
                    gui.showMessage(player.getName() + " har ikke råd til at købe " + guiField.getTitle());
                    auction.startAuction(this);
                }
            } else {
                auction.startAuction(this);
            }
        }

        else if (pawned && owner != player) {
            gui.showMessage(player.getName() + " skal ikke betale leje til " + owner.getName() + " fordi ejendommen er pantsat");
        }
        else if( owner == player) {
            gui.showMessage(player.getName() + " landede på sit eget felt" + " " + guiField.getTitle());
        }

        else if (owner.isInPrison()) {
            gui.showMessage(player.getName() + " skal ikke betale leje til " + owner.getName() + " fordi de er i fængsel");
        }

        else if (owner != player) {
            player.sendPoints(owner,getRent() );
            gui.showMessage(player.getName() + " landede på " + owner.getName() + "'s felt " + guiField.getTitle() + " og skal betale " + rent[owner.getShipping().size() - 1] + " kr");
        }


    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void pawnOff() {
        owner.addPoints(cost/2);
        pawned=true;
        guiField.setSubText("kr. " + getRent());
    }

    @Override
    public void rebuy() {
        owner.addPoints(- (int) ((cost/2)*1.1));
        pawned=false;
        guiField.setSubText("kr. " + getRent());
    }

    @Override
    public int getPrice()
    {
        return cost;
    }

    @Override
    public int getRent()
    {
        if (!pawned)
            return rent[owner.getShipping().size() - 1];
        else
            return 0;
    }

    @Override
    public int totalPrice(){
        return cost;
    }

    @Override
    public boolean isPawned()
    {
        return pawned;
    }

    @Override
    public boolean isPawnable()
    {
        return !pawned;
    }

}
