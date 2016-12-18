/**
 *
 * @author eralston
 */

package toleco.unit;

import toleco.unit.ArmorType;
import toleco.unit.AttackType;
import toleco.unit.Unit;
import toleco.controller.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class UnitTest {

    public UnitTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void takeDamageTest()
    {
        Unit un = new Unit("Test",Player.kPlayer1, 10, 10, 2, 2, AttackType.kMaul,
                10, 1, ArmorType.kBone, 10, true);
        un.takeDamage(1);
        assertEquals(9, un.getCurrentHealth());
        un.takeDamage(2);
        assertEquals(7, un.getCurrentHealth());
        un.takeDamage(100);
        assertEquals(0, un.getCurrentHealth());
    }

    @Test
    public void canAttackTest()
    {
        Unit un = new Unit("Test",Player.kPlayer1, 10, 10, 2, 2, AttackType.kMaul,
                10, 1, ArmorType.kBone, 10, true);
        assertTrue(un.canAttack());
        un.useAttack();
        assertFalse(un.canAttack());
    }

    @Test
    public void decrementMoveTest()
    {
        Unit un = new Unit("Test",Player.kPlayer1, 10, 10, 2, 2, AttackType.kMaul,
                10, 1, ArmorType.kBone, 10, true);
        un.decrementMove(1);
        assertEquals(1, un.getCurrentMoves());
    }

    @Test
    public void resetTest()
    {
        Unit un = new Unit("Test",Player.kPlayer1, 10, 10, 2, 2, AttackType.kMaul,
                10, 1, ArmorType.kBone, 10, true);
        un.useAttack();
        un.decrementMove(5);
        un.reset();
        assertTrue(un.canAttack());
        assertEquals(un.getMaxMoves(), un.getCurrentMoves());
    }

    @Test
    public void toStringTest()
    {
        String out = "Test\nHealth: 10/10\nMoves: 2/2\nAttack Value: "+
                "10\nAttack Range: 1\nArmor Value: 10";
        Unit un = new Unit("Test",Player.kPlayer1, 10, 10, 2, 2, AttackType.kMaul,
                10, 1, ArmorType.kBone, 10, true);
        assertEquals(out, un.toString());
    }

    @Test
    public void toStringForFileTest()
    {
        Unit un = new Unit("Test",Player.kPlayer1, 10, 10, 2, 2, AttackType.kMaul,
                10, 1, ArmorType.kBone, 10, true);
        String out = "Test,kPlayer1,10,2,true";
        assertEquals(out, un.toStringForFile());
    }

    @Test
    public void gettersTest()
    {
        Unit un = new Unit("Test",Player.kPlayer1, 10, 10, 2, 2, AttackType.kMaul,
                10, 1, ArmorType.kBone, 10, true);
        assertEquals(10, un.getMaxHealth());
        assertEquals("Test", un.getType());
        assertEquals(Player.kPlayer1, un.getOwner());
        assertEquals(AttackType.kMaul, un.getAttackType());
        assertEquals(ArmorType.kBone, un.getArmorType());
    }

}