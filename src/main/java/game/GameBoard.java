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
    public static final int NFIELDS = 40;

    private final Field[] fields;
    private final GUI_Field[] guiFields;
    private GUI gui;

    public GameBoard()
    {
        fields = new Field[NFIELDS];
        guiFields = new GUI_Field[NFIELDS];
        createGuiFields();
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

    public void createFields()
    {
        fields[0] = new StartField();
        fields[1] = new PropertyField("PropertyField 1", 1200,new int[]{50, 250, 750,2250,4000,6000}, gui);
        fields[3] = new PropertyField("PropertyField 3", 1200, new int[]{50, 750,22500, 4000,6000},  new PropertyField[]{(PropertyField) fields[1]}, gui);
        fields[4] = new TaxField("", 0, true, gui);
        fields[5] = new ParkingField();
        fields[6] = new PropertyField("PropertyField 6", 2000,new int[]{ 100,600,1800,5400,8000,11000}, gui);
        fields[8] = new PropertyField("PropertyField 8", 2000, new int[]{ 100,600,1800,5400,8000,11000}, gui);
        fields[9] = new PropertyField("PropertyField 9", 2400,new int[]{ 150,800,2000,6000,9000,12000},new PropertyField[]{(PropertyField) fields[6],(PropertyField) fields[8]}, gui);

        fields[10] = new JailField("felt nummer 10", 0,false);

        fields[11] = new PropertyField("PropertyField 11", 2800,new int[]{ 200,1000,3000,9000,12500,15000}, gui);
        fields[12] = new ParkingField();
        fields[13] = new PropertyField("PropertyField 13", 2800, new int[]{ 200,1000,3000,9000,12500,15000}, gui);
        fields[14] = new PropertyField("PropertyField 14", 3200,new int[]{ 250,1250,3750,10000,14000,18000},new PropertyField[]{(PropertyField) fields[11], (PropertyField) fields[13]}, gui);

        fields[15] = new ParkingField();
        fields[16] = new PropertyField("PropertyField 16", 3600,new int[]{ 300,1400,4000,11000,15000,19000}, gui);
        fields[18] = new PropertyField("PropertyField 18", 3600,new int[]{ 300,1400,4000,11000,15000,19000}, gui);
        fields[19] = new PropertyField("PropertyField 19", 4000,new int[]{ 350,1600,4400,12000,16000,20000},new PropertyField[]{ (PropertyField) fields[16], (PropertyField) fields[18]}, gui);

        fields[20] = new ParkingField();
        fields[21] = new PropertyField("PropertyField 21", 4400,new int[]{ 350,1800,5000,14000,17500}, gui);
        fields[23] = new PropertyField("PropertyField 23", 4400,new int[]{ 350,1800,5000,14000,17500,21000}, gui);
        fields[24] = new PropertyField("PropertyField 24", 4800,new int[]{ 400,2000,6000,15000,18500,22000},new PropertyField[]{ (PropertyField) fields[21],(PropertyField) fields[23]}, gui);

        fields[25] = new ParkingField();

        fields[26] = new PropertyField("PropertyField 26", 5200,new int[]{ 450,2200,6600,16000,19500,23000}, gui);
        fields[27] = new PropertyField("PropertyField 27",5200,new int[]{ 450,2200,6600,16000,19500,23000}, gui);
        fields[28] = new ParkingField();
        fields[29] = new PropertyField("PropertyField 29",5600,new int[]{ 500,2400,7200,17000,20500,24000}, new PropertyField[]{(PropertyField) fields[26],(PropertyField) fields[27]}, gui);

        fields[30] = new JailField("felt nummber 30", 1000, true);

        fields[31] = new PropertyField("PropertyField 31", 6000,new int[]{ 550,2600,7800,18000,22000,25000}, gui);
        fields[32] = new PropertyField("PropertyField 32", 6000,new int[]{ 550,2600,7800,18000,22000,25000}, gui);
        fields[34] = new PropertyField("PropertyField 34", 6400,new int[]{ 600,3000,9000,20000,24000,28000},new PropertyField[]{(PropertyField) fields[31],(PropertyField) fields[32]}, gui);

        fields[35] = new ParkingField();

        fields[37] = new PropertyField("PropertyField 37", 7000,new int[]{ 700,3500,10000,22000,26000,30000}, gui);
        fields[38] = new TaxField("", 2000, false, gui);
        fields[39] = new PropertyField("PropertyField 39", 8000,new int[]{ 1000,4000,12000,28000,34000,40000},new PropertyField[]{(PropertyField) fields[37]}, gui);




        for(int i = 0; i < NFIELDS; i++)
        {
            if(fields[i] != null)  {
                fields[i].setGuiField(guiFields[i]);
            }
        }
    }

    public void createChanceFields(ChanceCards card)
    {
        fields[2] = new ChanceField("ChanceField 2", card);
        fields[7] = new ChanceField("ChanceField 7", card);
        fields[17] = new ChanceField("ChanceField 17", card);
        fields[22] = new ChanceField("ChanceField 22", card);
        fields[33] = new ChanceField("ChanceField 33", card);
        fields[36] = new ChanceField("ChanceField 36", card);

        fields[2].setGuiField(guiFields[2]);
        fields[7].setGuiField(guiFields[7]);
        fields[17].setGuiField(guiFields[17]);
        fields[22].setGuiField(guiFields[22]);
        fields[33].setGuiField(guiFields[33]);
        fields[36].setGuiField(guiFields[36]);
    }
    private void createGuiFields()
    {
        // Get the absolut file path for images in a way that works cross platform
        String IMAGE_DIR_PATH = System.getProperty("user.dir") + File.separator + "src"  + File.separator + "main"  + File.separator + "resources"  + File.separator+ "img"  + File.separator;
        guiFields[0] = new GUI_Shipping(IMAGE_DIR_PATH+"start.png","Start", "", "Modtag kr. 4000", "", Color.WHITE, Color.BLACK);
        guiFields[1] = new GUI_Shipping(IMAGE_DIR_PATH+"","Rødovrevej", "kr. 1200", "", "", Color.BLUE, Color.BLACK);
        guiFields[2] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[3] = new GUI_Shipping(IMAGE_DIR_PATH+"","Hvidovrevej", "kr. 1200", "", "", Color.BLUE, Color.BLACK);
        guiFields[4] = new GUI_Shipping(IMAGE_DIR_PATH+"","Skat", "", "", "", Color.CYAN, Color.BLACK);
        guiFields[5] = new GUI_Shipping(IMAGE_DIR_PATH+"","Scandilines", "kr. 4000", "", "", Color.BLUE, Color.BLACK);
        guiFields[6] = new GUI_Shipping(IMAGE_DIR_PATH+"","Roskildevej", "kr. 2.000", "", "", Color.ORANGE, Color.BLACK);
        guiFields[7] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[8] = new GUI_Shipping(IMAGE_DIR_PATH+"","Valby Langgade", "kr. 2000", "", "", Color.ORANGE, Color.BLACK);
        guiFields[9] = new GUI_Shipping(IMAGE_DIR_PATH+"","Allegade", "kr. 2400", "", "", Color.ORANGE, Color.BLACK);
        guiFields[10] = new GUI_Shipping(IMAGE_DIR_PATH+"inprison.png","I Fængsel", "kr. 1000", "", "", Color.BLACK, Color.WHITE);
        guiFields[11] = new GUI_Shipping(IMAGE_DIR_PATH+"","Frederiksberg Alle","kr. 2.800", "", "", Color.PINK, Color.BLACK);
        guiFields[12] = new GUI_Shipping(IMAGE_DIR_PATH+"","Squash", "", "kr. 3.000", "", Color.RED, Color.BLACK);
        guiFields[13] = new GUI_Shipping(IMAGE_DIR_PATH+"","Bulowsvej", "kr. 2.800", "", "", Color.PINK, Color.BLACK);
        guiFields[14] = new GUI_Shipping(IMAGE_DIR_PATH+"","Gl. Kongevej", "kr. 3.200", "", "", Color.PINK, Color.BLACK);
        guiFields[15] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Mols-Linien", "kr. 4.000", "", "", Color.RED, Color.BLUE);
        guiFields[16] = new GUI_Shipping(IMAGE_DIR_PATH+"","Bernstorffsvej", "kr. 3.600", "", "", Color.GRAY, Color.BLACK);
        guiFields[17] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[18] = new GUI_Shipping(IMAGE_DIR_PATH+"","Hellerupvej", "kr. 3.600", "", "", Color.GRAY, Color.BLACK);
        guiFields[19] = new GUI_Shipping(IMAGE_DIR_PATH+"","Strandvejen", "kr. 4.000", "", "", Color.GRAY, Color.BLACK);
        guiFields[20] = new GUI_Shipping(IMAGE_DIR_PATH+"","Gratis", "Parkering", "Gratis Parkering", "", Color.WHITE, Color.BLACK);
        guiFields[21] = new GUI_Shipping(IMAGE_DIR_PATH+"","Trianglen", "kr. 4.400", "", "", Color.RED, Color.BLACK);
        guiFields[22] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[23] = new GUI_Shipping(IMAGE_DIR_PATH+"","Østerbrogade", "kr. 4.400", "", "", Color.RED, Color.BLACK);
        guiFields[24] = new GUI_Shipping(IMAGE_DIR_PATH+"","Grønningen", "kr 4.800", "", "", Color.RED, Color.BLACK);
        guiFields[25] = new GUI_Shipping(IMAGE_DIR_PATH+"","Scandilines", "kr. 4000", "", "", Color.BLUE, Color.BLACK);
        guiFields[26] = new GUI_Shipping("","Bredgade","kr. 5.200","","",Color.WHITE,Color.BLACK);
        guiFields[27] = new GUI_Shipping("","Kgs. Nytorv","kr. 5.200","","",Color.WHITE,Color.BLACK);
        guiFields[28] = new GUI_Shipping("","Coca Cola","","","",Color.RED,Color.WHITE);
        guiFields[29] = new GUI_Shipping("","Østergade","kr. 5.600","","",Color.WHITE,Color.BLACK);
        guiFields[30] = new GUI_Shipping("","De fængsles","","","",Color.BLACK,Color.WHITE);
        guiFields[31] = new GUI_Shipping("","Amagertorv","kr. 6.000","","",Color.YELLOW,Color.BLACK);
        guiFields[32] = new GUI_Shipping("","Vimmelskaftet","kr. 6.000 ","","",Color.YELLOW,Color.BLACK);
        guiFields[33] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[34] = new GUI_Shipping("","Nygade","kr. 6.400 ","","",Color.YELLOW,Color.BLACK);
        guiFields[35] = new GUI_Shipping("","Scandilines","kr. 4.000 ","","",Color.BLUE,Color.BLACK);
        guiFields[36] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[37] = new GUI_Shipping("","Frederiksberg-gade","kr. 7.000 ","","",Color.MAGENTA,Color.BLACK);
        guiFields[38] = new GUI_Shipping("","Skat", "", "", "", Color.CYAN, Color.BLACK);
        guiFields[39] = new GUI_Shipping("","Rådhuspladsen","kr. 8.000 ","","",Color.MAGENTA,Color.BLACK);





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

