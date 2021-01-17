package game;

import chancecard.ChanceCards;
import gui_fields.GUI_Field;
import player.Player;
import gui_main.GUI;
public class ChanceField extends Field {

    private String name;
    protected final GUI gui;
    private ChanceCards cards;
    public ChanceField(String name, ChanceCards cards,GUI gui)
    {
        this.name = name;
        this.cards = cards;
        this.gui = gui;
    }

    @Override
    public void setGuiField(GUI_Field guiField) {
        this.guiField = guiField;
        this.name = guiField.getTitle();
        String description="Træk en chance og gør hvad den siger<br>";
        guiField.setDescription(description);
    }

    @Override
    public void landOnField(Player player) {
        gui.showMessage( player.getName() + " " + "landede på " + name);
        cards.getRandomChance().executeChance(player);
    }

}
