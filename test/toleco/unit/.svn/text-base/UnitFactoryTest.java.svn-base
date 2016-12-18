package toleco.unit;

import toleco.unit.ArmorType;
import toleco.unit.UnitFactory;
import toleco.unit.AttackType;
import toleco.unit.Unit;
import toleco.controller.Player;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Properties;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andrew Barton
 */
public class UnitFactoryTest
{

   private static Unit testUnit1;
   private static Unit testUnit2;
   private static ArrayList<String> fileNameList;

    public UnitFactoryTest()
    {
    }

   @BeforeClass
   public static void setUpClass() throws Exception
   {
      /*
       * testUnits 1 and 2 are properly formatted units that are different from one another.
       * testBadUnit 1 tests a blank name
       * testBadUnit 2 tests a name of "Defualt Unit"
       * testBadUnit 3 tests a bad armorType
       * testBadUnit 4 tests a bad attackType
       * testBadUnit 5 tests a bad numerical value
       * No tests needed for every numerical value, since we are guarenteed that they
       * will all act the same by java.
       *
       * NOTE: If these bad units don't show up in the factory, then we've succedded
       */
      testUnit1 = new Unit(
              "TestUnit1",
              Player.kPlayer1, 67, 67,
              12, 12, AttackType.kCrush,
              99, 3, ArmorType.kPadded, 7, false);

      testUnit2 = new Unit(
              "TestUnit2",
              Player.kPlayer2, 76, 76, 21,  21,
              AttackType.kMaul, 3, 99, ArmorType.kBone, 4,
              false);

      Unit testBadUnit1 = new Unit(
              "",
              Player.kPlayer2, 76, 76, 21, 21, AttackType.kMaul,
              3, 99, ArmorType.kBone, 4, false);

      Unit testBadUnit2 = new Unit(
              "DefaultUnit", Player.kPlayer2,76,76, 21, 21, AttackType.kMaul,
              3, 99, ArmorType.kBone, 4, false);

      Unit testBadUnit3 = new Unit(
              "TestBadUnit3", Player.kPlayer2,  76, 76, 21, 21,
              AttackType.kMaul, 3, 99, ArmorType.kBone, 4, false);

      Unit testBadUnit4 = new Unit(
              "TestBadUnit4", Player.kPlayer2, 76, 76, 21, 21,
              AttackType.kMaul, 3, 99, ArmorType.kBone,
              4, false);

      Unit testBadUnit5 = new Unit(
              "TestBadUnit5", Player.kPlayer2, 76, 76, 21,
              21, AttackType.kMaul, 3, 99, ArmorType.kBone,
              4, false);


      
      File unitDir = new File("units");
      
      if(!unitDir.exists())
      {
         unitDir.mkdir();
      }
      fileNameList = new ArrayList<String>();

      FileWriter out = writerHelper(testUnit1.getType());
      Properties unitProps = propertiesHelper(testUnit1);
      unitProps.store(out, "");
      out.close();
      
      out = writerHelper(testUnit2.getType());
      unitProps = propertiesHelper(testUnit2);
      unitProps.store(out, "");
      out.close();

      out = writerHelper(testBadUnit1.getType());
      unitProps = propertiesHelper(testBadUnit1);
      unitProps.store(out, "");
      out.close();

      out = writerHelper(testBadUnit2.getType());
      unitProps = propertiesHelper(testBadUnit2);
      unitProps.store(out, "");
      out.close();

      out = writerHelper(testBadUnit3.getType());
      unitProps = propertiesHelper(testBadUnit3);
      unitProps.setProperty("armorType", "BAD_ARMOR_TYPE");
      unitProps.store(out, "");
      out.close();

      out = writerHelper(testBadUnit4.getType());
      unitProps = propertiesHelper(testBadUnit4);
      unitProps.setProperty("attackType", "BAD_ATTACK_TYPE");
      unitProps.store(out, "");
      out.close();

      out = writerHelper(testBadUnit5.getType());
      unitProps = propertiesHelper(testBadUnit5);
      unitProps.setProperty("maxHealth", "NOT_A_NUMBER");
      unitProps.store(out, "");
      out.close();



   }

   private static FileWriter writerHelper(String unitName) throws Exception
   {
      String fileName = "units/"+unitName+".properties";
      fileNameList.add(fileName);
      File testFile = new File(fileName);
      if(testFile.exists())
      {
         testFile.delete();
      }
      return new FileWriter(testFile);
      
   }


   private static Properties propertiesHelper(Unit unit)
   {
      Properties unitProps = new Properties();

      unitProps.setProperty("type",unit.getType());
      unitProps.setProperty("maxHealth", unit.getMaxHealth()+"");
      unitProps.setProperty("maxMoves", unit.getMaxMoves()+"");
      unitProps.setProperty("attackType", unit.getAttackType().toString());
      unitProps.setProperty("attackValue", unit.getAttackValue()+"");
      unitProps.setProperty("attackRange", unit.getAttackRange()+"");
      unitProps.setProperty("armorType", unit.getArmorType().toString());
      unitProps.setProperty("armorValue", unit.getArmorValue()+"");

      return unitProps;
   }

   @AfterClass
   public static void tearDownClass()
   {
      for(String fileName:fileNameList)
      {
         File deleteFile = new File(fileName);
         if(deleteFile.exists())
         {
            deleteFile.delete();
         }
         
      }
   }

   /**
    * Test of getUnitNames method, of class UnitFactory.
    */
   @Test
   public void testGetUnitNames()
   {
      System.out.println("getUnitNames");

      UnitFactory instance = new UnitFactory();

      ArrayList<String> badUnits = new ArrayList<String>();
      badUnits.add("");
      badUnits.add("DefaultUnit");
      badUnits.add("TestBadUnit3");
      badUnits.add("TestBadUnit4");
      badUnits.add("TestBadUnit5");

      ArrayList<String> expResult = new ArrayList<String>();
      expResult.add("TestUnit1");
      expResult.add("Clown");
      expResult.add("TestUnit2");

      ArrayList<String> result = instance.getUnitNames();

      for (String goodUnit : expResult)
      {
          assertTrue(result.contains(goodUnit));
      }

      for (String badUnit : badUnits)
      {
          assertFalse(result.contains(badUnit));
      }
   }

   /**
    * Test of build method, of class UnitFactory.
    */
   @Test
   public void testBuild_5args()
   {
      Unit expResult = new Unit(
              "TestUnit1",
              Player.kPlayer1,
              67,
              66,
              12,
              11,
              AttackType.kCrush,
              99,
              3,
              ArmorType.kPadded,
              7,
              false);
      System.out.println("build with 5 args");
      String unitName = "TestUnit1";
      Player player = Player.kPlayer1;
      int currentHealth = 66;
      boolean canAttack = true;
      int currentMoves = 11;
      UnitFactory instance = new UnitFactory();
      Unit result = instance.build(unitName, player, currentHealth, canAttack, currentMoves);
      assertEquals(expResult.toString(), result.toString());
   }

   /**
    * Test of build method, of class UnitFactory.
    */
   @Test
   public void testBuild_String_Player()
   {
      System.out.println("build with player");
      String unitName = "TestUnit1";
      Player player = Player.kPlayer1;
      UnitFactory instance = new UnitFactory();
      Unit expResult = testUnit1;
      Unit result = instance.build(unitName, player);
      assertEquals(expResult.toString(), result.toString());

      unitName = "TestUnit2";
      expResult = testUnit2;
      result = instance.build(unitName,Player.kPlayer2);
      assertEquals(expResult.toString(), result.toString());
   }

   /**
    * Test of build method, of class UnitFactory.
    */
   @Test
   public void testBuild_String()
   {
      System.out.println("build from string");
      UnitFactory instance = new UnitFactory();
      Unit result = instance.build(testUnit2.toStringForFile());
      assertEquals(testUnit2.toString(), result.toString());
   }

}