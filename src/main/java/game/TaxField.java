package game;

import gui_main.GUI;
import player.Player;

public class TaxField extends Field {

    private final String name;
    private final int taxCost;
    protected GUI gui;
    private final boolean TaxType;

    TaxField(String name, int taxCost, boolean TaxType, GUI gui) {
        this.name = name;
        this.taxCost = taxCost;
        this.TaxType = TaxType;
        this.gui = gui;
    }

    public String landOnField(Player player) {

        if (TaxType) {
            String option = gui.getUserSelection("Vælg hvad du vil betale af","10%","kr. 4.000");
            {
                if(option.equals("10%")) {
                    int taxCost= (int) ((player.getTotalWorth())*0.1);
                    player.addPoints(-taxCost);
                    gui.showMessage("Det kostede "+taxCost+" kr.");
                } else {
                    player.addPoints(-4000);
                }
                return option.equals("10%") ? "Du valgte at betale 10% af din balance" : "Du valgte at betale 4000 kr.";
            }
        }
        else
        {
            player.addPoints(-2000);
            return "Du skal betale ekstraordinær skat på 2000 kr.";
        }
    }
}