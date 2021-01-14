package game;

import chancecard.ChanceCards;
import gui_fields.GUI_Field;
import gui_fields.GUI_Ownable;
import gui_fields.GUI_Shipping;
import gui_fields.GUI_Street;
import gui_main.GUI;
import player.Player;

import java.awt.*;
import java.io.File;

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
        if(player.getPosition() != tempPosition)
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

    public void createFields(Auction auction)
    {
        fields[0] = new StartField();
        fields[1] = new PropertyField("PropertyField 1", 1200, 1000, new int[]{50, 250, 750,2250,4000,6000}, gui, auction);
        fields[3] = new PropertyField("PropertyField 3", 1200, 1000, new int[]{50, 250, 750,2250, 4000,6000},  new PropertyField[]{(PropertyField) fields[1]}, gui, auction);
        fields[4] = new TaxField("TaxField 4", 0, true, gui);
        fields[5] = new ShippingField("ShippingField Rederiet Lindinger", 4000, gui,auction, 500, 1000, 2000, 4000);
        fields[6] = new PropertyField("PropertyField 6", 2000, 1000, new int[]{ 100,600,1800,5400,8000,11000}, gui, auction);
        fields[8] = new PropertyField("PropertyField 8", 2000, 1000, new int[]{ 100,600,1800,5400,8000,11000}, gui, auction);
        fields[9] = new PropertyField("PropertyField 9", 2400, 1000, new int[]{ 150,800,2000,6000,9000,12000},new PropertyField[]{(PropertyField) fields[6],(PropertyField) fields[8]}, gui, auction);

        fields[10] = new JailField("JailField 10", 0,false);

        fields[11] = new PropertyField("PropertyField 11", 2800, 2000, new int[]{ 200,1000,3000,9000,12500,15000}, gui, auction);
        fields[12] = new CompanyField("CompanyField Squash",  gui,auction,3000);
        fields[13] = new PropertyField("PropertyField 13", 2800, 2000,  new int[]{ 200,1000,3000,9000,12500,15000}, gui, auction);
        fields[14] = new PropertyField("PropertyField 14", 3200, 2000, new int[]{ 250,1250,3750,10000,14000,18000},new PropertyField[]{(PropertyField) fields[11], (PropertyField) fields[13]}, gui, auction);

        fields[15] = new ShippingField("ShippingField Grenaa-Hundested", 4000, gui, auction, 500, 1000, 2000, 4000);
        fields[16] = new PropertyField("PropertyField 16", 3600, 2000, new int[]{ 300,1400,4000,11000,15000,19000}, gui, auction);
        fields[18] = new PropertyField("PropertyField 18", 3600, 2000, new int[]{ 300,1400,4000,11000,15000,19000}, gui, auction);
        fields[19] = new PropertyField("PropertyField 19", 4000, 2000,new int[]{ 350,1600,4400,12000,16000,20000},new PropertyField[]{ (PropertyField) fields[16], (PropertyField) fields[18]}, gui, auction);

        fields[20] = new ParkingField();
        fields[21] = new PropertyField("PropertyField 21", 4400, 3000, new int[]{ 350,1800,5000,14000,17500,21000}, gui, auction);
        fields[23] = new PropertyField("PropertyField 23", 4400, 3000, new int[]{ 350,1800,5000,14000,17500,21000}, gui, auction);
        fields[24] = new PropertyField("PropertyField 24", 4800, 3000, new int[]{ 400,2000,6000,15000,18500,22000},new PropertyField[]{ (PropertyField) fields[21],(PropertyField) fields[23]}, gui, auction);

        fields[25] = new ShippingField("ShippingField Mols-Linien", 4000, gui, auction, 500, 1000, 2000, 4000);

        fields[26] = new PropertyField("PropertyField 26", 5200, 3000, new int[]{ 450,2200,6600,16000,19500,23000}, gui, auction);
        fields[27] = new PropertyField("PropertyField 27",5200, 3000, new int[]{ 450,2200,6600,16000,19500,23000}, gui, auction);
        fields[28] = new CompanyField("CompanyField Coca Cola",  gui, auction,3000);
        fields[29] = new PropertyField("PropertyField 29",5600, 3000, new int[]{ 500,2400,7200,17000,20500,24000}, new PropertyField[]{(PropertyField) fields[26],(PropertyField) fields[27]}, gui, auction);

        fields[30] = new JailField("JailField 30", 1000, true);

        fields[31] = new PropertyField("PropertyField 31", 6000, 4000, new int[]{ 550,2600,7800,18000,22000,25000}, gui, auction);
        fields[32] = new PropertyField("PropertyField 32", 6000, 4000, new int[]{ 550,2600,7800,18000,22000,25000}, gui, auction);
        fields[34] = new PropertyField("PropertyField 34", 6400, 4000, new int[]{ 600,3000,9000,20000,24000,28000},new PropertyField[]{(PropertyField) fields[31],(PropertyField) fields[32]}, gui, auction);

        fields[35] = new ShippingField("ShippingField Skandinavisk Linietrafik", 4000, gui,  auction, 500, 1000, 2000, 4000);

        fields[37] = new PropertyField("PropertyField 37", 7000, 4000, new int[]{ 700,3500,10000,22000,26000,30000}, gui, auction);
        fields[38] = new TaxField("TaxField 38", 2000, false, gui);
        fields[39] = new PropertyField("PropertyField 39", 8000, 4000, new int[]{ 1000,4000,12000,28000,34000,40000},new PropertyField[]{(PropertyField) fields[37]}, gui, auction);




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
        guiFields[1] = new GUI_Street("Rødovrevej", "kr. 1200", "", "", Color.BLUE, Color.BLACK);
        guiFields[2] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[3] = new GUI_Street("Hvidovrevej", "kr. 1200", "", "", Color.BLUE, Color.BLACK);
        guiFields[4] = new GUI_Shipping(IMAGE_DIR_PATH+"","Skat", "", "", "", Color.CYAN, Color.BLACK);
        guiFields[5] = new GUI_Shipping(IMAGE_DIR_PATH+"","Rederiet Lindinger", "kr. 4000", "", "", Color.BLUE, Color.BLACK);
        guiFields[6] = new GUI_Street("Roskildevej", "kr. 2.000", "", "", Color.ORANGE, Color.BLACK);
        guiFields[7] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[8] = new GUI_Street("Valby Langgade", "kr. 2000", "", "", Color.ORANGE, Color.BLACK);
        guiFields[9] = new GUI_Street("Allegade", "kr. 2400", "", "", Color.ORANGE, Color.BLACK);
        guiFields[10] = new GUI_Shipping(IMAGE_DIR_PATH+"inprison.png","I Fængsel", "kr. 1000", "", "", Color.BLACK, Color.WHITE);
        guiFields[11] = new GUI_Street("Frederiksberg Alle","kr. 2.800", "", "", Color.PINK, Color.BLACK);
        guiFields[12] = new GUI_Shipping(IMAGE_DIR_PATH+"","Squash", "", "kr. 3.000", "", Color.RED, Color.BLACK);
        guiFields[13] = new GUI_Street("Bulowsvej", "kr. 2.800", "", "", Color.PINK, Color.BLACK);
        guiFields[14] = new GUI_Street("Gl. Kongevej", "kr. 3.200", "", "", Color.PINK, Color.BLACK);
        guiFields[15] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Grenaa-Hundested", "kr. 4.000", "", "", Color.RED, Color.BLUE);
        guiFields[16] = new GUI_Street("Bernstorffsvej", "kr. 3.600", "", "", Color.GRAY, Color.BLACK);
        guiFields[17] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[18] = new GUI_Street("Hellerupvej", "kr. 3.600", "", "", Color.GRAY, Color.BLACK);
        guiFields[19] = new GUI_Street("Strandvejen", "kr. 4.000", "", "", Color.GRAY, Color.BLACK);
        guiFields[20] = new GUI_Shipping(IMAGE_DIR_PATH+"","Gratis", "Parkering", "Gratis Parkering", "", Color.WHITE, Color.BLACK);
        guiFields[21] = new GUI_Street("Trianglen", "kr. 4.400", "", "", Color.RED, Color.BLACK);
        guiFields[22] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[23] = new GUI_Street("Østerbrogade", "kr. 4.400", "", "", Color.RED, Color.BLACK);
        guiFields[24] = new GUI_Street("Grønningen", "kr 4.800", "", "", Color.RED, Color.BLACK);
        guiFields[25] = new GUI_Shipping(IMAGE_DIR_PATH+"","Mols-Linien", "kr. 4000", "", "", Color.BLUE, Color.BLACK);
        guiFields[26] = new GUI_Street("Bredgade","kr. 5.200","","",Color.WHITE,Color.BLACK);
        guiFields[27] = new GUI_Street("Kgs. Nytorv","kr. 5.200","","",Color.WHITE,Color.BLACK);
        guiFields[28] = new GUI_Shipping("","Coca Cola","","","",Color.RED,Color.WHITE);
        guiFields[29] = new GUI_Street("Østergade","kr. 5.600","","",Color.WHITE,Color.BLACK);
        guiFields[30] = new GUI_Shipping("","De fængsles","","","",Color.BLACK,Color.WHITE);
        guiFields[31] = new GUI_Street("Amagertorv","kr. 6.000","","",Color.YELLOW,Color.BLACK);
        guiFields[32] = new GUI_Street("Vimmelskaftet","kr. 6.000 ","","",Color.YELLOW,Color.BLACK);
        guiFields[33] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[34] = new GUI_Street("Nygade","kr. 6.400 ","","",Color.YELLOW,Color.BLACK);
        guiFields[35] = new GUI_Shipping("","Skandinavisk Linietrafik","kr. 4.000 ","","",Color.BLUE,Color.BLACK);
        guiFields[36] = new GUI_Shipping(IMAGE_DIR_PATH+"chance.png","Chance", "", "", "", Color.WHITE, Color.BLACK);
        guiFields[37] = new GUI_Street("Frederiksberg gade","kr. 7.000 ","","",Color.MAGENTA,Color.BLACK);
        guiFields[38] = new GUI_Shipping("","Skat", "", "", "", Color.CYAN, Color.BLACK);
        guiFields[39] = new GUI_Street("Rådhuspladsen","kr. 8.000 ","","",Color.MAGENTA,Color.BLACK);
    }
    public void blacken()
    {
        for (int i = 0; i < NFIELDS; i++)
        {
            ((GUI_Ownable)guiFields[i]).setBorder(null);
        }
    }

    public String[] getFieldString(Field[] props) {
        if(props!=null)
        {
            String result[] = new String[props.length];
            for (int i = 0; i < props.length; i++)
            {
                result[i] = props[i].guiField.getTitle();
            }
            return result;
        }
        return null;
    }


    public Field getFieldFromString(String name) {
        Field result = null;
        for (int i = 0; i < NFIELDS; i++) {
            if (guiFields[i].getTitle().equals(name)) {
                result = fields[i];
            }
        }
        return result;
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

