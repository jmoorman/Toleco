/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.logic;

import toleco.unit.Unit;
import toleco.controller.Player;
import toleco.terrain.DefaultTerrain;
import toleco.terrain.Terrain;
import toleco.unit.DefaultUnit;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import toleco.unit.ArmorType;
import toleco.unit.AttackType;
import static org.junit.Assert.*;

/**
 *
 * @author eirq
 */
public class GameBoardTest {

    private GameBoard testBoard;

    public GameBoardTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        testBoard = new GameBoard();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test Constructor and getters.
     */
    @Test
    public void testConstructor()
    {
        System.out.println("Constructor");

        assertEquals("", testBoard.getBackStory());
        assertEquals(10, testBoard.getMap().length);
        assertEquals(10, (testBoard.getMap())[0].length);
        assertNull(testBoard.getSelection());
    }

    /**
     * Test of loadMap method, of class GameBoard.
     */
    @Test
    public void testLoadMap() {
        System.out.println("loadMap");
        try
        {
            testBoard.loadMap("test/toleco/logic/testMap.ocem");
        }
        catch (Exception e)
        {
        }
        assertEquals("Once upon a time there was a backstory!", testBoard.getBackStory());
        assertEquals("Default", testBoard.getMap()[0][0].getType());
        assertEquals("Default", testBoard.getMap()[0][0].getUnit().getType());
        assertNull(testBoard.getMap()[1][1].getUnit());
    }


    /**
     * Test of setBackStory method, of class GameBoard.
     */
    @Test
    public void testSetBackStory() {
        System.out.println("setBackStory");
        testBoard.setBackStory("Lets do this!");
        assertEquals("Lets do this!", testBoard.getBackStory());
    }

    /**
     * Test of saveMap method, of class GameBoard.
     */
    @Test
    public void testSaveMap() {
        System.out.println("saveMap");
        try
        {
            Player activePlayer = testBoard.loadMap("test/toleco/logic/testMap.ocem");
        }
        catch (Exception e)
        {
        }
        testBoard.setBackStory("Lets do this!");
        Terrain temp = new DefaultTerrain(1, 1);
        temp.setUnit(new DefaultUnit());
        testBoard.getMap()[1][1] = temp;
        testBoard.getMap()[0][0].removeUnit();

        testBoard.saveMap("test/toleco/logic/myNewMap.ocem", Player.kPlayer2);

        try
        {
            testBoard.loadMap("test/toleco/logic/testMap.ocem");
            testBoard.loadMap("test/toleco/logic/myNewMap.ocem");
        }
        catch (Exception e)
        {
        }

        assertEquals("Lets do this!", testBoard.getBackStory());
        assertEquals("Default", testBoard.getMap()[0][0].getType());
        assertEquals("Default", testBoard.getMap()[1][1].getUnit().getType());
        assertNull(testBoard.getMap()[0][0].getUnit());
    }

    /**
     * Test of selectTerrain method, of class GameBoard.
     */
    @Test
    public void testSelectTerrain() {
        System.out.println("selectTerrain");

        try
        {
            testBoard.loadMap("test/toleco/logic/testMap.ocem");
        }
        catch (Exception e)
        {
        }
        testBoard.selectTerrain(0, 0);

        assertEquals("Default", testBoard.getSelection().getType());

        testBoard.selectTerrain(-1, -1);

        assertNull(testBoard.getSelection());
    }

    /**
     * Test of setTerrain method, of class GameBoard.
     */
    @Test
    public void testSetTerrain() {
        System.out.println("setTerrain");

        try
        {
            testBoard.loadMap("test/toleco/logic/testMap.ocem");
        }
        catch (Exception e)
        {
        }
        testBoard.selectTerrain(0, 0);
        testBoard.setTerrain("Default", null, Player.kPlayer1);

        assertEquals("Default", testBoard.getSelection().getType());
        assertEquals("Default", testBoard.getMap()[0][0].getType());
        assertNull(testBoard.getMap()[0][0].getUnit());
        
        testBoard.setTerrain("Default", "Default", Player.kPlayer1);
        assertEquals("Default", testBoard.getSelection().getType());
        assertEquals("Default", testBoard.getSelection().getUnit().getType());
    }
    /**
     * Test of move method, of class GameBoard.
     */
    @Test
    public void testMove() {
        System.out.println("move");

        try
        {
            testBoard.loadMap("test/toleco/logic/testMap.ocem");
        }
        catch (Exception e)
        {
        }
        testBoard.selectTerrain(0, 0);
        assertEquals("Default", testBoard.getMap()[0][0].getUnit().getType());
        assertNull(testBoard.getMap()[0][1].getUnit());

        testBoard.move(0, 1);

        assertEquals("Default", testBoard.getMap()[0][1].getUnit().getType());
        assertNull(testBoard.getMap()[0][0].getUnit());
    }

    /**
     * Test of attack method, of class GameBoard.
     */
    @Test
    public void testAttack() {
        System.out.println("attack");

        try
        {
            testBoard.loadMap("test/toleco/logic/testAttackMap.ocem");
        }
        catch (Exception e)
        {
            fail();
        }

        Unit attacker = testBoard.getMap()[0][0].getUnit();
        Unit defender = testBoard.getMap()[0][1].getUnit();
        //Get would-be damages.
        int terrMod = testBoard.getMap()[0][0].getDefMod();
        int counterTerrMod = testBoard.getMap()[0][1].getDefMod();
        int damage = calculateDamage(attacker, defender, terrMod);
        int counterDamage = calculateCounterDamage(attacker, defender, terrMod, counterTerrMod);

        //Attack overwhelms the defender (no-counterattack)
        testBoard.selectTerrain(0, 0);
        assertTrue(testBoard.getMap()[0][0].getUnit().canAttack());
        testBoard.attack(0, 1);
        assertFalse(testBoard.getMap()[0][0].getUnit().canAttack());

        //Check damage
        assertEquals(attacker.getMaxHealth() - counterDamage, attacker.getCurrentHealth());
        assertEquals(defender.getMaxHealth() - damage, defender.getCurrentHealth());

        //Standard Attack and counter-attack
        testBoard.selectTerrain(9,9);
        testBoard.setTerrain("Default", "Brute", Player.kPlayer1);
        testBoard.selectTerrain(9,8);
        testBoard.setTerrain("Default", "Brute", Player.kPlayer2);

        attacker = testBoard.getMap()[9][9].getUnit();
        defender = testBoard.getMap()[9][8].getUnit();
        //Get would-be damages.
        terrMod = testBoard.getMap()[9][8].getDefMod();
        counterTerrMod = testBoard.getMap()[9][9].getDefMod();
        damage = calculateDamage(attacker, defender, terrMod);
        counterDamage = calculateCounterDamage(attacker, defender, terrMod, counterTerrMod);

        testBoard.selectTerrain(9,9);
        assertTrue(testBoard.getMap()[9][9].getUnit().canAttack());
        testBoard.attack(9, 8);
        assertFalse(testBoard.getMap()[9][9].getUnit().canAttack());

        //Check damage
        assertEquals(attacker.getMaxHealth() - counterDamage, attacker.getCurrentHealth());
        assertEquals(defender.getMaxHealth() - damage, defender.getCurrentHealth());

        //Attack would do no damage (is rounded to 1) and counter-attack would
        // do no damage. (will bypass UnitFactory)
        Unit testUnit1 = new Unit("Test", Player.kPlayer1, 100, 100, 100, 100,
            AttackType.kCrush, 0, 2, ArmorType.kPadded, 100000, true);
        Unit testUnit2 = new Unit("Test", Player.kPlayer2, 100, 100, 100, 100,
            AttackType.kCrush, 0, 2, ArmorType.kPadded, 100000, true);
        testBoard.getMap()[0][9].setUnit(testUnit1);
        testBoard.getMap()[2][9].setUnit(testUnit2);

        attacker = testBoard.getMap()[0][9].getUnit();
        defender = testBoard.getMap()[2][9].getUnit();
        //Get would-be damages.
        terrMod = testBoard.getMap()[2][9].getDefMod();
        counterTerrMod = testBoard.getMap()[0][9].getDefMod();
        damage = calculateDamage(attacker, defender, terrMod);
        counterDamage = calculateCounterDamage(attacker, defender, terrMod, counterTerrMod);

        testBoard.selectTerrain(0, 9);
        assertTrue(testBoard.getMap()[0][9].getUnit().canAttack());
        testBoard.attack(2, 9);
        assertFalse(testBoard.getMap()[0][9].getUnit().canAttack());

        //Check damage
        assertEquals(attacker.getMaxHealth() - counterDamage, attacker.getCurrentHealth());
        assertEquals(defender.getMaxHealth() - damage, defender.getCurrentHealth());

        //Defender overwhelms attacker
        Unit testUnit3 = new Unit("Test", Player.kPlayer2, 100, 100, 100, 100,
            AttackType.kCrush, 0, 2, ArmorType.kPadded, 1, true);

        testBoard.selectTerrain(9, 1);
        testBoard.setTerrain("Default", "Clown", Player.kPlayer1);
        testBoard.getMap()[9][0].setUnit(testUnit3);

        attacker = testBoard.getMap()[9][0].getUnit();
        defender = testBoard.getMap()[9][1].getUnit();
        //Get would-be damages.
        terrMod = testBoard.getMap()[9][1].getDefMod();
        counterTerrMod = testBoard.getMap()[9][0].getDefMod();
        damage = calculateDamage(attacker, defender, terrMod);
        counterDamage = calculateCounterDamage(attacker, defender, terrMod, counterTerrMod);

        testBoard.selectTerrain(9, 0);
        assertTrue(testBoard.getMap()[9][0].getUnit().canAttack());
        testBoard.attack(9, 1);
        assertFalse(testBoard.getMap()[9][0].getUnit().canAttack());

        //Check damage
        assertEquals(attacker.getMaxHealth() - counterDamage, attacker.getCurrentHealth());
        assertEquals(defender.getMaxHealth() - damage, defender.getCurrentHealth());

        //Attacker kills Defender
        Unit superUnit = new Unit("Test", Player.kPlayer2, 10000, 10000, 100, 100,
            AttackType.kCrush, 100000, 2, ArmorType.kPadded, 10000, true);
        Unit badUnit = new Unit("Test", Player.kPlayer2, 1, 1, 100, 100,
            AttackType.kCrush, 0, 2, ArmorType.kPadded, 1, true);

        testBoard.getMap()[4][5].setUnit(superUnit);
        testBoard.getMap()[4][4].setUnit(badUnit);

        attacker = testBoard.getMap()[4][5].getUnit();
        defender = testBoard.getMap()[4][4].getUnit();
        //Get would-be damages.
        terrMod = testBoard.getMap()[4][4].getDefMod();
        counterTerrMod = testBoard.getMap()[4][5].getDefMod();
        damage = calculateDamage(attacker, defender, terrMod);
        counterDamage = calculateCounterDamage(attacker, defender, terrMod, counterTerrMod);

        testBoard.selectTerrain(4, 5);
        assertTrue(testBoard.getMap()[4][5].getUnit().canAttack());
        testBoard.attack(4, 4);
        assertFalse(testBoard.getMap()[4][5].getUnit().canAttack());

        //Check damage
        assertEquals(attacker.getMaxHealth() - counterDamage, attacker.getCurrentHealth());
        assertEquals(defender.getMaxHealth() - damage, defender.getCurrentHealth());

        //Defender kills Attacker
        superUnit = new Unit("Test", Player.kPlayer2, 10000, 10000, 100, 100,
            AttackType.kCrush, 100000, 2, ArmorType.kPadded, 10000, true);
        badUnit = new Unit("Test", Player.kPlayer2, 1, 1, 100, 100,
            AttackType.kCrush, 0, 2, ArmorType.kPadded, 1, true);

        testBoard.getMap()[6][7].setUnit(badUnit);
        testBoard.getMap()[6][6].setUnit(superUnit);

        attacker = testBoard.getMap()[6][7].getUnit();
        defender = testBoard.getMap()[6][6].getUnit();
        //Get would-be damages.
        terrMod = testBoard.getMap()[6][6].getDefMod();
        counterTerrMod = testBoard.getMap()[6][7].getDefMod();
        damage = calculateDamage(attacker, defender, terrMod);
        counterDamage = calculateCounterDamage(attacker, defender, terrMod, counterTerrMod);

        testBoard.selectTerrain(6, 7);
        assertTrue(testBoard.getMap()[6][7].getUnit().canAttack());
        testBoard.attack(6, 6);
        assertNull(testBoard.getMap()[6][7].getUnit());

        //Check damage
        assertEquals(attacker.getMaxHealth() - counterDamage, attacker.getCurrentHealth());
        assertEquals(defender.getMaxHealth() - damage, defender.getCurrentHealth());
    }

    /**
     * Test of getEnemiesInRange method, of class GameBoard.
     */
    @Test
    public void testGetEnemiesInRange() {
        System.out.println("getEnemiesInRange");

        try
        {
            testBoard.loadMap("test/toleco/logic/testAttackMap.ocem");
        }
        catch (Exception e)
        {
        }
        testBoard.selectTerrain(0, 0);

        ArrayList<Point> enemies = testBoard.getEnemiesInRange();
        assertEquals(1, enemies.size());
        assertEquals(0, enemies.get(0).x);
        assertEquals(1, enemies.get(0).y);
    }

    /**
     * Test of resetUnits method, of class GameBoard.
     */
    @Test
    public void testResetUnits() {
        System.out.println("resetUnits");

        try
        {
            testBoard.loadMap("test/toleco/logic/testAttackMap.ocem");
        }
        catch (Exception e)
        {
        }
        testBoard.selectTerrain(0, 0);

        testBoard.attack(0, 1);

        assertFalse(testBoard.getMap()[0][0].getUnit().canAttack());

        testBoard.resetUnits(Player.kPlayer1);

        assertTrue(testBoard.getMap()[0][0].getUnit().canAttack());
    }

    /**
     * Test a bad (not found) file.
     */
    @Test
    public void testBadFiles()
    {
        System.out.println("Bad File Test");

        //loading from badfile
        try
        {
            testBoard.loadMap("/ID_onTExist.ocem");
            fail();
        }
        catch (Exception ex)
        {
            assertTrue(true);
        }

        //Saving to bad loation
        try
        {
            testBoard.loadMap("test/toleco/logic/testMap.ocem");
        }
        catch (Exception ex)
        {
        }

        try
        {
            testBoard.saveMap("/", Player.kPlayer1);
            fail();
        }
        catch (Exception ex)
        {
            assertTrue(true);
        }
    }

    /**
     * Tests for defect #170 not calling setChanged before any call to notifyObservers.
     */
    @Test
    public void testDefect170()
    {
        System.out.println("Defect #170");

        Watcher watch = new Watcher();
        testBoard.addObserver(watch);

        try
        {
            testBoard.loadMap("test/toleco/logic/testAttackMap.ocem");
            fail();
        }
        catch (FunException ex)
        {
            assertEquals("player", ex.getMessage());
        }
        catch (Exception e)
        {
            fail();
        }

        try
        {
            testBoard.selectTerrain(0, 0);
        }
        catch (FunException ex)
        {
            assertEquals("other", ex.getMessage());
        }

        try
        {
            testBoard.getEnemiesInRange();
            fail();
        }
        catch (FunException ex)
        {
            assertEquals("getEnemiesInRange", ex.getMessage());
        }

        try
        {
            testBoard.resetUnits(Player.kPlayer1);
            fail();
        }
        catch (FunException ex)
        {
            assertEquals("player", ex.getMessage());
        }
    }

    /**
     * Tests for defect #214: Moves not decremently correctly.
     */
    @Test
    public void testDefect214()
    {
        System.out.println("Defect #214");

        try
        {
            testBoard.loadMap("test/toleco/logic/testMap.ocem");
        }
        catch (Exception e)
        {
        }

        testBoard.getMap()[0][1] = new Terrain("TestTerrain", 1, 5, 0, 2);
        Unit mover = new Unit("Mover", Player.kPlayer1, 100, 100, 100, 100,
            AttackType.kCrush, 100, 100, ArmorType.kPadded, 100, true);
        testBoard.getMap()[0][0].setUnit(mover);

        testBoard.selectTerrain(0, 0);

        assertEquals(100, mover.getCurrentMoves());

        testBoard.move(0, 1);

        assertEquals(95, mover.getCurrentMoves());
    }

    /**
     * Test for defect #225: Terrain not accounted for in damage equaltion.
     */
    @Test
    public void testDefect225()
    {
        System.out.println("Defect #225");

        try
        {
            testBoard.loadMap("test/toleco/logic/testAttackMap.ocem");
        }
        catch (Exception e)
        {
            fail();
        }

        Unit attacker = new Unit("Test225-1", Player.kPlayer1, 100, 100, 100, 100,
            AttackType.kCrush, 20, 20, ArmorType.kPadded, 100, true);
        Unit defender = new Unit("Test225-2", Player.kPlayer2, 100, 100, 100, 100,
            AttackType.kCrush, 20, 20, ArmorType.kPadded, 100, true);
        
        Terrain atkTerrain = new Terrain("Test225Terrain-1", 0, 0, 9, 9);
        Terrain defTerrain = new Terrain("Test225Terrain-2", 20, 0, 9, 8);

        atkTerrain.setUnit(attacker);
        defTerrain.setUnit(defender);

        testBoard.getMap()[9][9] = atkTerrain;
        testBoard.getMap()[9][8] = defTerrain;

        //Get would-be damages.
        int terrMod = 20;
        int counterTerrMod = 0;
        int damage = calculateDamage(attacker, defender, terrMod);
        int counterDamage = calculateCounterDamage(attacker, defender, terrMod, counterTerrMod);

        testBoard.selectTerrain(9, 9);
        testBoard.attack(9, 8);

        //Check damage
        assertEquals(attacker.getMaxHealth() - counterDamage, attacker.getCurrentHealth());
        assertEquals(defender.getMaxHealth() - damage, defender.getCurrentHealth());
    }

    private class Watcher implements Observer
    {
        public void update(Observable obs, Object obj)
        {
            if (obj instanceof ArrayList)
            {
                throw new FunException("getEnemiesInRange");
            }
            else if (obj instanceof Player)
            {
                throw new FunException("player");
            }
            else
            {
                throw new FunException("other");
            }
        }
    }

    private class FunException extends RuntimeException
    {
        public FunException(String message)
        {
            super(message);
        }
    }

    /**
     * Helps testAttack by calculating the damage that should be done.
     */
    private int calculateDamage(Unit attacker, Unit defender, int terrMod)
    {
        //Get the numeric modifier for the attackers attack type against the
        // defenders armor type.
        double mod = attacker.getAttackType().modAgainstType(defender.getArmorType());

        //Damage Equation (Code-named: Jon's Equation)
        //AttackVDefenseModifier*(1-((TotalHealth-CurrentHealth)/TotalHealth)/2)*
        // (AttackValue - DefenseValue)

        //Obtain the amount of damage by performing the damage equation.
        int damage = (int)(mod * (1.0 - (((double)attacker.getMaxHealth() -
            attacker.getCurrentHealth()) / (double)attacker.getMaxHealth()) / 2.0) *
            ((double)attacker.getAttackValue() -
            ((double)defender.getArmorValue() +
            (double)(terrMod))));

        //IF damage to be dealt is less than 1.
        if (damage < 1)
        {
            //Set the damage to be done to 1.
            damage = 1;
        }
        //ELSE IF damage to be dealt is greater than the defneding Unit's health.
        else if (damage > defender.getCurrentHealth())
        {
            //Reduce the damage to the defensing units health.
            damage = defender.getCurrentHealth();
        }

        return damage;
    }

    /**
     * Helps testAttack by calculating the counter-attack damage that should be done.
     */
    private int calculateCounterDamage(Unit attacker, Unit defender, int terrMod, int counterTerrMod)
    {
        //FIRST! Calculate the damage that is to be done to the defender.
        int initialDamage = calculateDamage(attacker, defender, terrMod);
        int defendersHealth = defender.getCurrentHealth() - initialDamage;

        if (defendersHealth == 0)
        {
            return 0;
        }
        
        //Get the numeric modifier for the defenders attack type against the
        // attackers armor type.
        double mod = defender.getAttackType().modAgainstType(attacker.getArmorType());

        //Obtain the amount of damage by performing the damage equation.
        int damage = (int)(mod * (1.0 - (((double)defender.getMaxHealth() -
            (double)defendersHealth) / defender.getMaxHealth()) / 2.0)
            * ((double)defender.getAttackValue() - ((double)attacker.getArmorValue() +
            (double)counterTerrMod)));

        //IF damage to be dealt is less than 1.
        if (damage < 1)
        {
            //Set the damage to be done to 1.
            damage = 1;
        }
        //ELSE IF damage to be dealt is greater than the defneding Unit's health.
        else if (damage > attacker.getCurrentHealth())
        {
            //Reduce the damage to the defensing units health.
            damage = attacker.getCurrentHealth();
        }
        //ENDIF

        return damage;
    }

}