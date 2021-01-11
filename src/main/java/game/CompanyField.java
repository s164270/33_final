package game;

import gui_fields.GUI_Ownable;
import gui_main.GUI;
import player.Player;

public class CompanyField extends Field {

    private final String name;
    private final int cost;

    protected final GUI gui;

    private Player owner;

    public CompanyField(String name, int cost, GUI gui) {
        this.name = name;
        this.gui = gui;
        this.cost = cost;
    }

    @Override
    public String landOnField(Player player) {
        if (owner == null) {

            String option = gui.getUserButtonPressed("Vil du købe " + name + " for " + cost + "?", "Ja", "Nej");

            if (option.toLowerCase().equals("ja")) {
                if (player.getPoints() >= cost) {
                    setOwner(player);
                    return player.getName() + " har købt " + name + " for " + cost + " kr.";
                } else {
                    return player.getName() + " har ikke råd til at købe " + name;
                }
            } else {
                return player.getName() + " valgte ikke at købe " + name;
            }

        }

        if (owner != player) {
            int rent = player.getDiceSum() * (owner.getCompany().size() == 2 ? 200 : 100);
            player.sendPoints(owner, rent);
            return player.getName() + " skulle betale " + owner.getName() + " "  + rent + " kr.";
        }

        return "Fejl";
    }

    public void setOwner(Player player) {
        owner = player;
        owner.addPoints(-cost);
        owner.getCompany().add(this);
        ((GUI_Ownable)guiField).setBorder(player.getGuiPlayer().getCar().getPrimaryColor());
    }

}
