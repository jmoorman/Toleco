package toleco.unit;

import toleco.controller.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

/**
* This singleton class is responsible for instantiating and setting the values inside
* of a new Unit object. It also keeps a list of all of the possible Units.
* It does not keep a list of all of the Units it has built, only the ones it can
* build in the future.
*
* @author Andrew Barton (both javadocs and implementation)
* @version 1.0
*/
public class UnitFactory
{
    /**
    * A lookup table mapping a unit name to an instance of the unit.
    */
    private HashMap<String, Unit> unitMap;
    
    
    //this fancy statement just makes a new ArrayList
    //filled with the names of the properties to check
    //that they're all there when we read in the file.
    private static final ArrayList<String> kUnitPropertyNames =
        new ArrayList<String>(Arrays.asList(new String[]{
            "type", "maxHealth", "maxMoves",
            "attackType", "attackValue", "attackRange", "armorType", "armorValue"}));

    /**
     * The path for the directory that units' properties files are located.
     */
    private static final String kUnitDirectory = "units";
    
    /**
    * Although it takes no parameters, the constructor must read in all of the files
    * from the Unit folder and attempt to create an instance of each unit.
    * The UnitFactory can only get new Units from the Unit Folder, with the exception of
    * DefaultUnit.
    */
    public UnitFactory()
    {
        //Create a File.
        File unitDir = null;

        //TRY
        try
        {
            //Create a new File with the proper directory.
            unitDir = new File(kUnitDirectory);
        }
        //CATCH
        catch (Exception ex)
        {
            //Print an error about not being able to find the unit directory.
            System.err.println("Unable to load unit directory.");
        }

        //Initialie a HashMap<String, Unit> for units and their names
        unitMap = new HashMap<String, Unit>();
        //TRY
        try
        {
            //if the unit directory exists and is a dir
            if(unitDir.isDirectory())
            {
                //initialize an array of files from the directory's list of files
                File[] unitFiles = unitDir.listFiles();
                //FOR every file in the directory
                for(File unit:unitFiles)
                {
                    //Check if it is a proper properties file
                    if(unit.isFile()
                        && unit.getCanonicalPath().toLowerCase().contains(".properties"))
                    {
                        //Call UnitFromFile with the filename
                        unitFromFile(unit);
                    }
                    //ENDIF
                }//END FOR
            }
            //ELSE
            else
            {
                //unitDir isn't a directory?
                //FAIL! (do nothing)
            }
            //ENDIF
        }

        //CATCH ioexception
        catch(IOException e)
        {
            //Fixes defect #146
            System.err.println("UnitFactory Constructor: " + e);
        }
        //END catch
    }
    
    /**
    * Get a list of Strings of all of the different Unit types that this
    * UnitFactory can build.
    * @return an ArrayList of Strings of all of the different Unit types that this
    * UnitFactory can build
    */
    public ArrayList<String> getUnitNames()
    {
        return new ArrayList<String>(unitMap.keySet());
    }
    
    /**
    * Given a name (unique to the type of Unit, not the individual Unit),
    * the Player that this Unit will belong to, the currentHealth of the Unit,
    * whether the Unit can attack this turn or not, and the current number of moves
    * this unit will have, constructs a Unit of type <name> from the list of
    * Units that UnitFactory has. UnitFactory will construct a Unit with all of
    * its attributes, and will return DefaultUnit if it
    * cannot find a Unit by the given name.
    * @pre currentHealth can not be greater than the unit's maxHealth
    * @param unitName the name of the Unit type to be created
    * @param player the Player that the Unit will belong to
    * @param currentHealth the currentHealth that the Unit will have
    * @param canAttack whether the Unit will be able to attack this turn or not
    * @param currentMoves the current number of Moves that this Unit will have
    * @return the constructed Unit
    */
    public Unit build(String unitName, Player player,
        int currentHealth, boolean canAttack,
        int currentMoves)
    {
        Unit unit;
        //Create a Unit unit
        //IF unitName is in unitMap
        if(unitMap.containsKey(unitName))
        {
            //Set unit to a new instance of the unit Mapped to by unitName with
            //parameters from the archtype except for the owning player,
            //current health, whether it can still attack and its current
            //movement points
            Unit archetype = unitMap.get(unitName);
            unit = new Unit(archetype.getType(),
                player, archetype.getMaxHealth(),
                currentHealth,
                archetype.getMaxMoves(),
                currentMoves,
                archetype.getAttackType(),
                archetype.getAttackValue(),
                archetype.getAttackRange(),
                archetype.getArmorType(),
                archetype.getArmorValue(),
                canAttack);
        }
        //ELSE
        else
        {
            //Set unit to a new instance of the DefaultUnit
            unit = new DefaultUnit();
        } //END IF
        //Return unit
        return unit;
    }
    
    /**
    * Given a name (unique to the type of Unit, not the individual Unit) and the
    * the Player that this Unit will belong to,
    * constructs a Unit of type <name> from the list of
    * Units that UnitFactory has. UnitFactory will put all of the values
    * into the Unit except for the Unit, and will return DefaultUnit if it
    * cannot find a Unit by the given name.
    * @param unitName the name of the Unit type to be created
    * @param player the Player that the Unit will belong to
    * @return the constructed Unit
    */
    public Unit build(String unitName, Player player)
    {
        //Create a Unit unit
        Unit unit;
        //IF unitName is in unitMap
        if(unitMap.containsKey(unitName))
        {
            //Set unit to a new instance of the unit Mapped to by unitName with
            //parameters from the archtype except for the owning player
            Unit archetype = unitMap.get(unitName);
            unit = new Unit(archetype.getType(),
                player, archetype.getMaxHealth(),
                archetype.getMaxHealth(),
                archetype.getMaxMoves(),
                archetype.getMaxMoves(),
                archetype.getAttackType(),
                archetype.getAttackValue(),
                archetype.getAttackRange(),
                archetype.getArmorType(),
                archetype.getArmorValue(),
                true);
        }
        //ELSE
        else
        {
          
            //Set unit to a new instance of the DefaultUnit
            unit = new DefaultUnit();
        }
        //END IF
        //Return Unit
        return unit;
    }
    
    /**
    * Given a string generated by a Units toStringForFile method regenerate
    * the Unit that generated the string.
    * @param fromFile the string generated by a toStringForFile call
    * @return the Unit re-constructed by the string
    */
    public Unit build(String fromFile)
    {
        //Extract the Unit properties from the string
        Scanner in = new Scanner(fromFile);
        //SET the scanner's delimiter
        in.useDelimiter(",");
        //GET the unit's type
        String type = in.next();
        //GET the unit's owner
        Player owner = Player.valueOf(in.next());
        //GET the unit's current health
        int health = Integer.valueOf(in.next());
        //GET the unit's current moves
        int moves = Integer.valueOf(in.next());
        //GET if the unit can attack or not
        boolean canAttack = Boolean.valueOf(in.next());
        
        //Call build with the parameters from the string
        return build(type, owner, health, canAttack, moves);
    }
    
    /**
    * Given a name of a file to look in, gets the data about a particular Unit
    * and adds it to the UnitMap.
    * @pre file with name fileName exists, and is in the proper directory
    * @param unitFile the file from which to read the Unit data
    */
    private void unitFromFile(File unitFile)
    {
        //Create a new Properties variable unitProps
        Properties unitProps = new Properties();
        //TRY
        try
        {
            unitProps.load(new FileInputStream(unitFile));
            //IF all properties are there
            if(unitProps.keySet().containsAll(kUnitPropertyNames)
                && !unitProps.getProperty("type").equals("")
                && !unitProps.getProperty("type").equals("DefaultUnit"))
            {
                //read all of the properties
                Unit archetype = new Unit(unitProps.getProperty("type"),
                    Player.kPlayer1, //player doesn't matter, it will be set again later.
                    Integer.valueOf(unitProps.getProperty("maxHealth")),
                    Integer.valueOf(unitProps.getProperty("maxHealth")),
                    Integer.valueOf(unitProps.getProperty("maxMoves")),
                    Integer.valueOf(unitProps.getProperty("maxMoves")),
                    AttackType.valueOf(unitProps.getProperty("attackType")),
                    Integer.valueOf(unitProps.getProperty("attackValue")),
                    Integer.valueOf(unitProps.getProperty("attackRange")),
                    ArmorType.valueOf(unitProps.getProperty("armorType")),
                    Integer.valueOf(unitProps.getProperty("armorValue")),
                    true);
                unitMap.put(archetype.getType(), archetype);
            }
            
        }
        //catch exception caused by bad file(file itself is bad, not the data contained)
        catch (IOException ioException)
        {
            //Fixes defect #146
            System.err.println("UnitFactory.unitFromFile(): " + ioException);
        }
        //END catch
        //catch exception caused by bad numerical values
        catch(NumberFormatException numFormatException)
        {
            //Fixes defect #146
            System.err.println("UnitFactory.unitFromFile(): " + numFormatException);
        }
        //END catch
        //catch  exception caused by a bad attackType or bad armorType
        catch(IllegalArgumentException exception)
        {
            //Fixes defect #146
            System.err.println("UnitFactory.unitFromFile(): " + exception);
        }
    }
}
