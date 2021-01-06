package game;

import chancecard.ChanceCards;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Shipping;
import gui_main.GUI;
import player.Player;

import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class GameBoard
{
    private final int NFIELDS = 24;
    private Field[] fields;
    private GUI_Field[] guiFields;
    private GUI gui;

    public GameBoard()
    {
        fields = new Field[NFIELDS];
        guiFields = new GUI_Field[NFIELDS];
        createGuiFields();
        createFields();
    }

    public void setGui(GUI gui)
    {
        this.gui = gui;
    }

    public void movePlayer(Player player, int distance){
        String msg;
        int tempPosition = player.getPosition();
        while(distance > 0)
        {
            player.move(1);
            fields[player.getPosition()].visitField(player);
            distance--;
        }
        guiFields[tempPosition].setCar(player.getGuiPlayer(), false);
        guiFields[player.getPosition()].setCar(player.getGuiPlayer(), true);
        tempPosition = player.getPosition();
        msg = fields[player.getPosition()].landOnField(player);
        if(player.getPosition() != tempPosition) //do a second GUI update if the player was moved
        {
            guiFields[tempPosition].setCar(player.getGuiPlayer(), false);
            guiFields[player.getPosition()].setCar(player.getGuiPlayer(), true);
        }
        gui.showMessage(msg);
    }

    public void movePlayerPosition(Player player, int position){
        String msg;
        int tempPosition = player.getPosition();
        while(player.getPosition()!= position)
        {
            player.move(1);
            fields[player.getPosition()].visitField(player);
        }
        guiFields[tempPosition].setCar(player.getGuiPlayer(), false);
        guiFields[player.getPosition()].setCar(player.getGuiPlayer(), true);
        tempPosition = player.getPosition();
        msg = fields[player.getPosition()].landOnField(player);
        if(player.getPosition() != tempPosition) //do a second GUI update if the player was moved
        {
            guiFields[tempPosition].setCar(player.getGuiPlayer(), false);
            guiFields[player.getPosition()].setCar(player.getGuiPlayer(), true);
        }
        gui.showMessage(msg);
    }

    public GUI_Field[] getGuiFields() {
        return guiFields;
    }

    public Field[] getField()
    {
        return fields;
    }
    public void setField(int fieldIndex, Field field)
    {
        fields[fieldIndex] = field;
    }

    private void createFields()
    {
        fields[0] = new StartField();
        fields[1] = new PropertyField("PropertyField 1", 1);
        fields[2] = new PropertyField("PropertyField 2", 1, (PropertyField) fields[1]);

        fields[4] = new PropertyField("PropertyField 4", 1);
        fields[5] = new PropertyField("PropertyField 5", 1, (PropertyField) fields[4]);
        fields[6] = new JailField("felt nummer 6", 0, false);
        fields[7] = new PropertyField("felt nummer 7", 2);
        fields[8] = new PropertyField("felt nummer 8", 2, (PropertyField) fields[7]);

        fields[10] = new PropertyField("PropertyField 10", 2);
        fields[11] = new PropertyField("PropertyField 11", 2, (PropertyField) fields[10]);
        fields[12] = new ParkingField();
        fields[13] = new PropertyField("PropertyField 13", 3);
        fields[14] = new PropertyField("PropertyField 14", 3, (PropertyField) fields[13]);

        fields[16] = new PropertyField("PropertyField 16", 3);
        fields[17] = new PropertyField("PropertyField 17", 3, (PropertyField) fields[16]);
        fields[18] = new JailField("felt nummer 18", 1,true);
        fields[19] = new PropertyField("PropertyField 19", 4);
        fields[20] = new PropertyField("PropertyField 20", 4, (PropertyField) fields[19]);

        fields[22] = new PropertyField("PropertyField 22", 5);
        fields[23] = new PropertyField("PropertyField 23", 5, (PropertyField) fields[22]);

        for(int i = 0; i < NFIELDS; i++)
        {
            if(fields[i] != null)  {
                fields[i].setGuiField(guiFields[i]);
            }
        }
    }

    public void createChanceFields(ChanceCards card)
    {
        fields[3] = new ChanceField("ChanceField 3", card);
        fields[9] = new ChanceField("ChanceField 9", card);
        fields[15] = new ChanceField("ChanceField 15", card);
        fields[21] = new ChanceField("ChanceField 21", card);

        fields[3].setGuiField(guiFields[3]);
        fields[9].setGuiField(guiFields[9]);
        fields[15].setGuiField(guiFields[15]);
        fields[21].setGuiField(guiFields[21]);
    }
    private void createGuiFields()
    {
        // Get the absolut file path for images in a way that works cross platform
        String IMAGE_DIR_PATH = System.getProperty("user.dir") + File.separator + "src"  + File.separator + "main"  + File.separator + "resources"  + File.separator+ "img"  + File.separator;
        guiFields[0] = new GUI_Shipping(IMAGE_DIR_PATH+"start.png","Start", "", "Modtag $2", "", Color.WHITE, Color.BLACK);
        guiFields[1] = new GUI_Shipping(IMAGE_DIR_PATH+"burgerbar.png","Burgerbaren", "$1", "", "", Color.ORANGE, Color.BLACK);
        guiFields[2] = new GUI_Shipping(IMAGE_DIR_PATH+"pizzahouse.png","Pizzariaet", "$1", "", "", Color.ORANGE, Color.BLACK);
        guiFields[3] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[4] = new GUI_Shipping(IMAGE_DIR_PATH+"candystore.png","Slikbutikken", "$1", "", "", Color.CYAN, Color.BLACK);
        guiFields[5] = new GUI_Shipping(IMAGE_DIR_PATH+"icecreamshop.png","Iskiosken", "$1", "", "", Color.CYAN, Color.BLACK);
        guiFields[6] = new GUI_Shipping(IMAGE_DIR_PATH+"inprison.png","I Fængsel", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[7] = new GUI_Shipping(IMAGE_DIR_PATH+"museum.png","Museet", "$2", "", "", Color.MAGENTA, Color.BLACK);
        guiFields[8] = new GUI_Shipping(IMAGE_DIR_PATH+"library.png","Biblioteket", "$2", "", "", Color.MAGENTA, Color.BLACK);
        guiFields[9] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[10] = new GUI_Shipping(IMAGE_DIR_PATH+"skatepark.png","Skateparken", "$2", "", "", Color.PINK, Color.BLACK);
        guiFields[11] = new GUI_Shipping(IMAGE_DIR_PATH+"swimmingpool.png","Swimmingpoolen", "$2", "", "", Color.PINK, Color.BLACK);
        guiFields[12] = new GUI_Shipping(IMAGE_DIR_PATH+"freepark.png","Gratis", "Parkering", "Gratis Parkering", "", Color.WHITE, Color.BLACK);
        guiFields[13] = new GUI_Shipping(IMAGE_DIR_PATH+"arcade.png","Spillehallen", "$3", "Spillehallen", "", Color.RED, Color.BLACK);
        guiFields[14] = new GUI_Shipping(IMAGE_DIR_PATH+"cinema.png","Biografen", "$3", "", "", Color.RED, Color.BLACK);
        guiFields[15] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[16] = new GUI_Shipping(IMAGE_DIR_PATH+"toystore.png","Legetøjsbutik", "$3", "", "", Color.YELLOW, Color.BLACK);
        guiFields[17] = new GUI_Shipping(IMAGE_DIR_PATH+"petstore.png","Dyrehandlen", "$3", "", "", Color.YELLOW, Color.BLACK);
        guiFields[18] = new GUI_Shipping(IMAGE_DIR_PATH+"gotoprison.png","Gå i", "Fængsel", "", "", Color.WHITE, Color.BLACK);
        guiFields[19] = new GUI_Shipping(IMAGE_DIR_PATH+"bowling.png","Bowlinghallen", "$4", "", "", Color.GREEN, Color.BLACK);
        guiFields[20] = new GUI_Shipping(IMAGE_DIR_PATH+"zoo.png","Zoo", "$4", "", "", Color.GREEN, Color.BLACK);
        guiFields[21] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[22] = new GUI_Shipping(IMAGE_DIR_PATH+"waterpark.png","Vandlandet", "$5", "", "", Color.BLUE, Color.BLACK);
        guiFields[23] = new GUI_Shipping(IMAGE_DIR_PATH+"beach.png","Strandpromenaden", "$5", "", "+650", Color.BLUE, Color.BLACK);

        //guiFields[12].setForeGroundColor(Color.RED);

    }

    @Override
    public String toString() {
        for(int i=0; i<fields.length; i++)
        {
            System.out.println(guiFields[i].getTitle());
        }
        return "";
    }
}
