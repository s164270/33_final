package game;

import gui_fields.GUI_Field;
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

    public void landOnField(Player player) {

        if (TaxType) {
            String option = gui.getUserSelection("Vælg hvad du vil betale af","10%","kr. 4.000");
            {
                if(option.equals("10%")) {
                    int taxCost= (int) ((player.getTotalWorth())*0.1);
                    player.sendPoints(null, taxCost);
                    gui.showMessage("Det kostede "+taxCost+" kr.");
                } else {
                    player.sendPoints(null, 4000);
                }
                gui.showMessage(option.equals("10%") ? "Du valgte at betale 10% af din balance" : "Du valgte at betale 4000 kr.");
            }
        }
        else
        {
            player.sendPoints(null,2000);
            gui.showMessage("Du skal betale ekstraordinær skat på 2000 kr.");
        }
    }

    @Override
    public void setGuiField(GUI_Field guiField) {
        this.guiField = guiField;
        String description = "";
        if(this.TaxType)
        {
            description="Enten betal 4000 kr eller 10% af alt du ejer i skat";
        }
        else
        {
            description="Betal 2000 kr i skat";
        }

        guiField.setDescription(description);
    }
}