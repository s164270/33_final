package game;

import gui_fields.GUI_Field;
import gui_fields.GUI_Ownable;
import gui_main.GUI;
import player.Player;

public class CompanyField extends Field implements Ownable{

    private final String name;
    private final int cost;

    protected final GUI gui;

    private Player owner;
    private Auction auction;
    private boolean pawned;
    public int wealth;

    public CompanyField(String name, GUI gui, Auction auction, int cost) {
        this.name = name;
        this.gui = gui;
        this.cost = cost;
        this.auction = auction;
        this.pawned=false;

    }
    @Override
    public void setGuiField(GUI_Field guiField) {
        this.guiField = guiField;
        String description="Hvis 1 virksomhed ejes betales 100 gange så meget som øjene viser<br>";
        description +="Hvis 2 virksomhed ejes betales 200 gange så meget som øjene viser<br>";
        guiField.setDescription(description);

    }
    private void buyProperty(Player player) {
        player.addPoints(-cost);
        setOwner(player);

    }
    public void buyProperty(Player player, int auctionPrice) {
        player.addPoints(-auctionPrice);
        setOwner(player);
    }

    public void setOwner(Player player) {
        owner = player;
        if (owner!= null)
        {
            owner.getCompany().add(this);
            owner.getOwnedFields().add(this);
            ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
            for (int i = 0; i < player.getCompany().size(); i++)
            {
                player.getCompany().get(i).guiField.setSubText("kr. " + getRent());
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


    @Override
    public void landOnField(Player player) {

        if (owner == null) {
            gui.showMessage(player.getName() + " " + " landede på " + " " + name);

            String option = gui.getUserButtonPressed("Vil du købe " + name + " for " + cost + "?", "Ja", "Nej");

            if (option.toLowerCase().equals("ja")) {
                if (player.getPoints() >= cost) {
                    buyProperty(player);
                    gui.showMessage(player.getName() + " har købt " + name + " for " + cost + " kr.");
                } else {
                    gui.showMessage(player.getName() + " har ikke råd til at købe " + name);
                    auction.startAuction(this);
                }
            } else {
                auction.startAuction(this);
            }

        }
        else if( owner == player){
            gui.showMessage(player.getName() + " landede på sit eget felt" + " " + guiField.getTitle());

        }

        else if (pawned && owner != player) {
            gui.showMessage(player.getName() + " skal ikke betale leje til " + owner.getName() + " fordi ejendommen er pantsat");
        }

        else if (owner.isInPrison()) {
            gui.showMessage(player.getName() + " skal ikke betale leje til " + owner.getName() + " fordi de er i fængsel");
        }

        else if (owner != player) {
            int rent = player.getDiceSum() * getRent();
            player.sendPoints(owner, rent);
            gui.showMessage(player.getName() + " landede på  " + owner.getName() + "'s"  + " felt og skulle betale " + owner.getName() + " "  + rent + " kr. i leje");
        }
    }

    @Override
    public void pawnOff() {
        if(!pawned )
        {
            owner.addPoints(cost/2);
            pawned=true;
            guiField.setSubText("kr. " + getRent());
        }
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
        if(!pawned)
        {
            return (owner.getCompany().size() == 2 ? 200 : 100);
        }
        else
            return 0;
    }

    @Override
    public int totalPrice() {
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
