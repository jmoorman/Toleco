package toleco.unit;

import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for AttackType
 * @author adam
 */
public class AttackTypeTest
{
    private AttackType attack;
    private ArmorType armor;
    private HashMap<String,Double> modifiers;

    public AttackTypeTest()
    {
        attack = AttackType.kCrush;
        armor = ArmorType.kHide;
        modifiers = new HashMap<String, Double>();
        //Hard-coded values must change with changes to AttackType map values
        modifiers.put("kCrush:kPadded", 0.5);
        modifiers.put("kCrush:kHide", 1.0);
        modifiers.put("kCrush:kBone", 1.5);
        modifiers.put("kPierce:kHide", 0.5);
        modifiers.put("kPierce:kBone", 1.0);
        modifiers.put("kPierce:kPadded", 1.5);
        modifiers.put("kMaul:kBone", 0.5);
        modifiers.put("kMaul:kPadded", 1.0);
        modifiers.put("kMaul:kHide", 1.5);
    }

    @Before
    public void setUp() 
    {
        attack = AttackType.kCrush;
        armor = ArmorType.kHide;
        modifiers = new HashMap<String,Double>();
        //Hard-coded values must change with changes to AttackType map values
        modifiers.put("kCrush:kPadded", 0.5);
        modifiers.put("kCrush:kHide", 1.0);
        modifiers.put("kCrush:kBone", 1.5);
        modifiers.put("kPierce:kHide", 0.5);
        modifiers.put("kPierce:kBone", 1.0);
        modifiers.put("kPierce:kPadded", 1.5);
        modifiers.put("kMaul:kBone", 0.5);
        modifiers.put("kMaul:kPadded", 1.0);
        modifiers.put("kMaul:kHide", 1.5);
    }

    /**
     * Test of modAgainstType method, of class AttackType.
     */
    @Test
    public void testModAgainstType()
    {
        System.out.println("modAgainstType");

        assertEquals(1.0, attack.modAgainstType(armor), .001);
        armor = ArmorType.kPadded;
        assertEquals(0.5, attack.modAgainstType(armor), .001);
        armor = ArmorType.kBone;
        assertEquals(1.5, attack.modAgainstType(armor), .001);
        attack = AttackType.kPierce;
        armor = ArmorType.kHide;
        assertEquals(0.5, attack.modAgainstType(armor), .001);
        armor = ArmorType.kPadded;
        assertEquals(1.5, attack.modAgainstType(armor), .001);
        armor = ArmorType.kBone;
        assertEquals(1.0, attack.modAgainstType(armor), .001);
        attack = AttackType.kMaul;
        armor = ArmorType.kHide;
        assertEquals(1.5, attack.modAgainstType(armor), .001);
        armor = ArmorType.kPadded;
        assertEquals(1.0, attack.modAgainstType(armor), .001);
        armor = ArmorType.kBone;
        assertEquals(0.5, attack.modAgainstType(armor), .001);
    }

}