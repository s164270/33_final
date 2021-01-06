package dice;

import dice.DiceCup;
import game.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DiceCupTest
{

    DiceCup diceCup;
    @BeforeEach
    public void beforeEachTestMethod() {
        diceCup = new DiceCup();
    }

    @AfterEach
    public void afterEachTestMethod() {
    }

    @Test
    void rollDice()
    {
        for (int i = 0; i < 1000; i++)
        {
            diceCup.rollDice();
            assertTrue(diceCup.getDice1()>=1); ;
            assertTrue(diceCup.getDice1()<=6); ;
            assertTrue(diceCup.getDice2()>=1); ;
            assertTrue(diceCup.getDice2()<=6); ;
        }
    }

    @Test
    void getDice1()
    {
        assertEquals(0, diceCup.getDice1()); ;
        diceCup.rollDice();
        assertTrue(diceCup.getDice1()>=1); ;
        assertTrue(diceCup.getDice1()<=6); ;

    }

    @Test
    void getDice2()
    {
        assertEquals(0, diceCup.getDice2()); ;
        diceCup.rollDice();
        assertTrue(diceCup.getDice2()>=1); ;
        assertTrue(diceCup.getDice2()<=6); ;
    }

    @Test
    void getSum()
    {
        assertEquals(0, diceCup.getSum()); ;
        for (int i = 0; i < 1000; i++)
        {
            diceCup.rollDice();
            assertTrue(diceCup.getDice1() + diceCup.getDice2()== diceCup.getSum()); ;
        }
    }

    @Test
    void isSimiliar()
    {
        assertTrue(diceCup.isSimiliar()); ;
        for (int i = 0; i < 1000; i++)
        {
            diceCup.rollDice();
            assertTrue((diceCup.getDice1() == diceCup.getDice2())==diceCup.isSimiliar()); ;
        }
    }

    @Test
    void twoOne()
    {
        assertFalse(diceCup.twoOne()); ;
        for (int i = 0; i < 1000; i++)
        {
            diceCup.rollDice();
            assertTrue((diceCup.getDice1() == 1 &&  diceCup.getDice2()==1)==diceCup.twoOne()); ;
        }
    }
}