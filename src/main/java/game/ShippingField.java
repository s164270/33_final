package game;

import gui_fields.GUI_Ownable;
import gui_main.GUI;
import player.Player;

public class ShippingField extends Field {

    private final String name;
    private final int cost;
    private final int[] rent;

    protected final GUI gui;

    private Player owner;

    ShippingField(String name, int cost, GUI gui,  int... rent) {
        this.name = name;
        this.cost = cost;
        this.gui = gui;
        this.rent = rent;
    }

    @Override
    public String landOnField(Player player) {
        if (owner == null) {

            String option = gui.getUserButtonPressed("Vil du købe " + name + " for " + cost + "?", "Ja", "Nej");
            if (option.toLowerCase().equals("ja")) {
                if (player.getPoints() >= cost) {
                    setOwner(player);
                    player.addPoints(-cost);
                    return player.getName() + " har købt " + name;
                } else {
                    return player.getName() + " har ikke råd til at købe " + name;
                }
            } else {
                return player.getName() + " valgte ikke at købe " + name;
            }

        }

        if (owner != player) {
            player.sendPoints(owner, rent[owner.getRedderier().size() - 1]);
            return player.getName() + " landede på " + owner.getName() + "'s felt: " + name + " og skal betale " + rent[owner.getRedderier().size() - 1] + " kr";
        }

        return player.getName() + " landede på eget felt";
    }


    @Override
    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public GUI getGui() {
        return gui;
    }

    public int[] getRent() {
        return rent;
    }

    public void buyProperty(Player player) {

    }

    public void setOwner(Player player) {
        owner = player;
        owner.getRedderier().add(this);
        ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
    }

}
