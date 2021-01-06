package chancecard;

import game.GameBoard;
import gui_fields.GUI_Chance;
import gui_main.GUI;
import player.Player;

import java.awt.*;
import java.util.Random;

public class ChanceCards
{
    private GameBoard board;
    private GUI gui;
    private Player[] player;
    private Chance[] chances= new Chance[20];
    public ChanceCards(GameBoard board, GUI gui, Player[] player)
    {
        this.board=board;
        this.gui=gui;
        this.player=player;
        createChance();
    }


    public void createChance()
    {
        chances[0] = new ChanceMoveStart(board, gui, player,"Ryk frem til START\n Modtag M2", Color.BLUE,Color.ORANGE);
        chances[1] = new ChanceMove5(board, gui, player,"Ryk op til 5 felter frem", Color.BLUE,Color.ORANGE);
        chances[2] = new ChanceBirthday(board, gui, player,"Det er din fødselsdag! Alle giver dig M1!\n Tillykke med fødselsdagen!", Color.BLUE,Color.ORANGE);
        chances[3] = new ChanceFreeJail(board, gui, player,"Du løslades uden omkostninger\n Behold dette kort intil du får brug for det.", Color.BLUE,Color.ORANGE);
        chances[4] = new ChanceGet2(board, gui, player,"Du har lavet alle dine lektier!\n Modtag M2 fra banken", Color.BLUE,Color.ORANGE);
        chances[5] = new ChanceMoveToBeach(board, gui, player,"Ryk frem til STRANDPROMENADEN", Color.BLUE,Color.ORANGE);
        chances[6] = new ChancePay2(board, gui, player,"Du har spist for meget slik\n BETAL M2 til banken", Color.BLUE,Color.ORANGE);
        chances[7] = new ChanceMoveColor(board, gui, player,"Ryk frem til orange eller grønt felt.", Color.BLUE,Color.ORANGE, new String[]{"Burgerbaren","Pizzariaet","Bowlinghallen","Zoo"});
        chances[8] = new ChanceMoveColor(board, gui, player,"Ryk frem til lyseblåt felt.", Color.BLUE,Color.ORANGE, new String[]{"Slikbutikken","Iskiosken"});
        chances[9] = new ChanceMoveColor(board, gui, player,"Ryk frem til pink eller mørkeblåt felt", Color.BLUE,Color.ORANGE, new String[]{"Skateparken","Swimmingpoolen","Vandlandet","Strandpromenaden"});
        chances[10] = new ChanceMoveColor(board, gui, player,"Ryk frem til et orange felt", Color.BLUE,Color.ORANGE, new String[]{"Burgerbaren","Pizzariaet"});
        chances[11] = new ChanceMoveColor(board, gui, player,"Ryk frem til rød felt", Color.BLUE,Color.ORANGE, new String[]{"Spillehallen","Biografen"});
        chances[12] = new ChanceMoveColor(board, gui, player,"Ryk frem til Skateparken", Color.BLUE,Color.ORANGE, new String[]{"Skateparken"});
        chances[13] = new ChanceMoveColor(board, gui, player,"Ryk frem til lyseblåt eller rød", Color.BLUE,Color.ORANGE, new String[]{"Slikbutikken","Iskiosken","Spillehallen","Biografen"});
        chances[14] = new ChanceMoveColor(board, gui, player,"Ryk frem til lilla eller gul", Color.BLUE,Color.ORANGE, new String[]{"Museet","Biblioteket","Legetøjsbutik","Dyrehandlen"});
        for (int i = 15; i < chances.length; i++)
        {
           int j = i % player.length;
            chances[i] = new ChanceMovePlayer(board, gui, player,"Giv dette kort til "+ player[j].getName(), Color.BLUE,Color.ORANGE, player[j]);

        }
    }

    public Chance getRandomChance() {
        final Random random = new Random();
        int number;
        number = random.nextInt(20);
        return chances[number];
    }

}
