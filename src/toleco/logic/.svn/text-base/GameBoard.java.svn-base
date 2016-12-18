package toleco.logic;

import toleco.controller.Player;
import toleco.terrain.Terrain;
import toleco.terrain.TerrainFactory;
import toleco.unit.Unit;
import toleco.unit.UnitFactory;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

/**
 * This is all of the game's logic, and all of the game data that doesn't
 * have to do with the GUI.
 * Essentially this is the game, except for the interface. The GameBoard maintains
 * the map, the TerrainFactory and UnitFactory, and the currently selected Terrain.
 * GameBoard is responsible for loading and saving maps, moving Units, and
 * attacking Units.
 *
 * @author Andrew Barton (Javadocs)
 * @author Eriq Augustine (Implementation)
 * @version 1.1
 */
public class GameBoard extends Observable
{
    /**
     * The number of rows that a board has.
     */
    public static final int kNumRows = 10;

    /**
     * The number of cols that a board has.
     */
    public static final int kNumCols = 10;

    /**
    * The map, a two dimensional array of Terrain that represent the game board.
    * The methods that directly modify the map are loadMap(String), and
    * resetUnits(Player). The map may also be indirectly modified by modifying
    * the Terrain referenced by selection.
    */
    private Terrain[][] map;
    
    /**
    * The currently selected Terrain, note that the terrain still exists in the
    * map, this is a reference to it used by several methods. The reference can be
    * changed by selectTerrain(int, int) and move(int, int). The referenced Terrain
    * can be changed by move(int, int), attack(int, int), setTerrain(Terrain),
    * and indirectly by resetUnits(Player).
    */
    private Terrain selection;
    
    /**
    * The UnitFactory made upon construction of the GameBoard. The UnitFactory
    * is used during the game upon map load, and to give the UI the list of
    * possible Units. It is used during Editor mode whenever a Terrain with a
    * Unit is to be placed on the map.
    */
    private UnitFactory uFactory;
    
    /**
    * The TerrainFactory made upon construction of the GameBoard. The TerrainFactory
    * is used during the game upon map load, and to give the UI the list of
    * possible Terrains. It is used during Editor mode whenever a Terrain is to
    * placed onto the map.
    */
    private TerrainFactory tFactory;
    
    /**
    * A backstory that may be uniqe to every map.
    */
    private String backStory;
    
    /**
    * Instantiates this GameBoard's UnitFactory and TerrainFactory.
    *
    * @post uFactory and tFactory will have the Unit types and Terrain types
    * loaded from the files in the Unit and Terrain folder
    *
    */
    public GameBoard()
    {
        //Initialize map as a 10 x 10 array of Terrain.
        map = new Terrain[kNumRows][kNumCols];
        //Initialize selection to null.
        selection = null;
        //Initialize backStory to be an empty String.
        backStory = "";
        //Create a new UnitFactory.
        uFactory = new UnitFactory();
        //Create a new TerrainFactory.
        tFactory = new TerrainFactory();
    }
    
    /**
     * Loads the map in the file who has the given file name into map.
     *
     * @pre fName is a valid Toleco map file
     * @post every value in map will be a valid Terrain
     *
     * @param fName the name of the file containing the map to be loaded
     *
     * @throws java.io.FileNotFoundException if the filename represents a
     * bad Toleco map file
     *
     * @return the player whose turn the game will start on
     */
    public Player loadMap(String fName) throws java.io.FileNotFoundException
    {
        //Map format:
        //NOTE: commas are delimiters within lines.
        //NOTE: All lines begniing with a '#' are ignored.
        //Player enum (kPlayer1 or kPlayer2) //on the first line by itself.
        //All Terrains in a grid layout (each row on its own line).
        //Terrains are represented by whatever they return from a call to
        // myTerrain.getType().
        //All units, each on their own line.
        //Units are in the format format: '~',xCoord,yCoord,myUnit.toStringForFile()
        //Note that comma is the delimiter used within lines.
        //All other lines are the backstory.

        Player activePlayer = Player.kPlayer1;
        
        //Clear the backStory.
        backStory = "";
        
        //Try to open a file of the given name for reading.
        try
        {
            File inFile = new File(fName);
            
            //Create a Scanner based off of the open file.
            Scanner sc = new Scanner(inFile);
            
            //Create a temporary String;
            String temp = null;
            
            //DO
            do
            {
                //Read a line from the Scanner and trim it.
                temp = sc.nextLine().trim();
                
                //IF the line does not begin with a '#'
                if (temp.charAt(0) != '#')
                {
                    //Create a Player represented by that String.
                    activePlayer = Player.valueOf(temp);
                }
                //WHILE the read line does not begin with a '#'.
            } while (temp.charAt(0) == '#');
            //ENDWHILE
            
            readBoard(sc);
            
            //WHILE there are still lines in the file
            while (sc.hasNextLine())
            {
                //Read a line and trim it.
                temp = sc.nextLine().trim();
                
                //IF the line does not begin with a '#'.
                if (temp.charAt(0) != '#')
                {
                    //IF the line begins with a '~'.
                    if (temp.charAt(0) == '~')
                    {
                        //Create a scanner from the read line.
                        Scanner lineScanner = new Scanner(temp);
                        
                        //Use "," as the delimiters for the new line scanner.
                        lineScanner.useDelimiter(",");
                        
                        //Consume the '~'.
                        lineScanner.next();
                        
                        //Read the unit's x-coordinate from the line (',' is delimiter).
                        int xCoord = lineScanner.nextInt();
                        
                        //Read the unit's y-coordinate from the line (',' is delimiter).
                        int yCoord = lineScanner.nextInt();
                        
                        //Read the unit's information
                        String unitInfo = lineScanner.nextLine();
                        
                        //Ask UnitFactory to return a new instance of Unit.
                        Unit newUnit = uFactory.build(unitInfo);
                        
                        //Place the Unit into the map at the read location.
                        map[xCoord][yCoord].setUnit(newUnit);
                    }
                    //ELSE
                    else
                    {
                        //Concat the line to backStory.
                        backStory += temp;
                    }
                    //ENDIF
                }
                //ENDIF
            }
            //ENDWHILE
        }
        //CATCH any Exception
        catch (Exception e)
        {
            //Print an error message.
            System.err.println("Error loading map: " + e.getMessage());

            //Throw a new java.io.FileNotFoundException
            throw new java.io.FileNotFoundException();
        }


        //Set the oberservable status to changed.
        //Fix Defect # 170
        setChanged();

        //Notify any observers.
        notifyObservers(activePlayer);
        
        //Return whose turn it is.
        return activePlayer;
    }

    /**
     * Read a board specified by the given Scanner and place the Terrains in the
     * board.
     *
     * @param sc the scanner to read the board from
     */
    private void readBoard(Scanner sc)
    {
        //Declare a temporary String.
        String temp = null;

        //FOR the number of rows in the map.
        for (int row = 0; row < map.length; row++)
        {
            //Read a line from the file and trim it.
            temp = sc.nextLine().trim();

            //Create a Scanner based off of the read in line.
            Scanner lineScanner = new Scanner(temp);

            //Set the line scanner's delimiter to be ','
            lineScanner.useDelimiter(",");

            //IF the line does not begin with a '#'.
            if (temp.charAt(0) != '#')
            {
                //FOR the number of columns in the map.
                for (int col = 0; col < map[row].length; col++)
                {
                    //Read a token (using commas as delimiters).
                    String token = lineScanner.next();
                    //Using the read token ask terrainFactory to give you a
                    // Terrain instance.
                    Terrain tempTerrain = tFactory.build(token, row, col);
                    //Place the Terrain instance into the current location.
                    map[row][col] = tempTerrain;
                }
                //ENDFOR
            }
            //ENDIF

        }
        //ENDFOR
    }

    /**
    * Saves the current map into a file with the given file name.
    *
    * @param fName the name of the file to contain the map being saved
    * @param activePlayer the current Player
    */
    public void saveMap(String fName, Player activePlayer)
    {
        //Map format:
        //NOTE: commas are delimiters within lines.
        //NOTE: All lines begniing with a '#' are ignored.
        //Player enum (kPlayer1 or kPlayer2) //on the first line by itself.
        //All Terrains in a grid layout (each row on its own line).
        //Terrains are represented by whatever they return from a call to
        // myTerrain.getType().
        //All units, each on their own line.
        //Units are in the format format: '~',xCoord,yCoord,myUnit.toStringForFile()
        //Note that comma is the delimiter used within lines.
        //All other lines are the backstory.
        
        //Try to open a filewritter of the given name for writing.
        try
        {
            //Create a File writter.
            BufferedWriter out = new BufferedWriter(new FileWriter(fName));
            
            //Write whose turn it is along with a newLine.
            out.write(activePlayer.toString() + "\n");
            
            //FOR the number of rows in the map.
            for (int row = 0; row < map.length; row++)
            {
                //FOR the number of columns in the map.
                for (int col = 0; col < map[row].length; col++)
                {
                    //Write the result of Terrain.getType().
                    out.write(map[row][col].getType());
                    //IF this is the last column
                    if (col == map[row].length - 1)
                    {
                        //Write a newline to the file.
                        out.write("\n");
                    }
                    //ELSE
                    else
                    {
                        //Write a comma to the file.
                        out.write(",");
                    }
                    //ENDIF
                }
                //ENDFOR
            }
            //ENDFOR
            
            //FOR all rows on the map.
            for (int row = 0; row < map.length; row++)
            {
                //FOR all cols on the map.
                for (int col = 0; col < map[row].length; col++)
                {
                    //IF the current location is occupied by a unit.
                    if (map[row][col].getUnit() != null)
                    {
                        //Write the unit identifier, '~', followed by a comma.
                        out.write("~,");
                        //Write the unit's x-coordinate followed by a comma.
                        out.write(row + ",");
                        //Write the unit's y-coordinate followed by a comma.
                        out.write(col + ",");
                        //Write Unit's information using Unit.toStringForFile()
                        // followed by a newline.
                        out.write(map[row][col].getUnit().toStringForFile() + "\n");
                    }
                    //ENDIF
                }
                //ENDFOR
            }
            //ENDFOR
            
            //Write backStory to the file.
            out.write(backStory);
            
            //Close the file.
            out.close();
        }
        //CATCH any Exception
        catch (Exception ex)
        {
            //Print an error message.
            System.err.println("Error saving map: " + ex.getMessage());

            //Throw a new RuntimeException
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    /**
    * Gets the entireMap, a two-dimensional array of Terrains.
    *
    * @pre the map has been instantiated by calling loadMap(String)
    *
    * @return a two-dimensional array of Terrain representing the game map
    */
    public Terrain[][] getMap()
    {
        //Return map.
        return map;
    }
    
    /**
    * Gets the terrain at a given X and Y coordinates.
    *
    * @pre the map has been instantiated by calling loadMap(String)
    *
    * @param xCoord The X coordinate of the Terrain to be returned
    * @param yCoord The Y coordinate of the Terrain to be returned
    * @return the Terrain at the supplied Coordinates
    */
    public Terrain getTerrain(int xCoord, int yCoord)
    {
        //Return the Terrain pointed to by xCoord and yCoord.
        return map[xCoord][yCoord];
    }
    
    /**
    * Gets the currently selected Terrain. Since a Terrain knows where it is,
    * this also gives you the currently selected position.
    *
    * @pre the map has been instantiated by calling loadMap(String)
    * @pre there is a selected Terrain, as there won't one be right as a map is loaded
    *
    * @return the currently selected Terrain
    */
    public Terrain getSelection()
    {
        //Return the currently selected terrain.
        return selection;
    }
    
    /**
    * Return the map's backstory that was obtained from the map's file.
    *
    * @pre the map has been instantiated by calling loadMap(String)
    *
    * @return the map's backstory
    */
    public String getBackStory()
    {
        return backStory;
    }
    
    /**
    * Sets the backstory for the map.
    *
    * @param newBackStory the new backstory for the current map
    */
    public void setBackStory(String newBackStory)
    {
        //Set the backstory to the given String.
        backStory = newBackStory;
    }
    
    /**
    * Sets the selected Terrain to a given Terrain.
    *
    * @pre the map has been instantiated by calling loadMap(String)
    * @pre there is a selected Terrain, as there won't be right as a map is loaded
    *
    * @post map[selected Terrain.xCoord][selected Terrain.yCoord] will be set to
    * the given terrain
    *
    * @param terrain the name of the terrain to be placed
    * @param unit the name of the unit to be placed, null if no Unit
    * @param player the Player for the unit to belong to
    */
    public void setTerrain(String terrain, String unit, Player player)
    {
        //Get a Terrain from TerrainFactory matching the param, terrain.
        Terrain tempTerrain =
                tFactory.build(terrain, selection.getX(), selection.getY());
        
        //IF the passed Unit name is not null
        if (unit != null)
        {
            //Get a Unit from UnitFactory matching the unit and the param (player).
            Unit tempUnit = uFactory.build(unit, player);
            
            //Add the aquired unit to Unit to the Terrain.
            tempTerrain.setUnit(tempUnit);
        }
        //ENDIF
        
        //Set the selected location to the new Terrain.
        map[selection.getX()][selection.getY()] = tempTerrain;
        
        //Set the oberservable status to changed.
        //Fix Defect # 170
        setChanged();

        //Notify any observers.
        notifyObservers(null);
    }
    
    /**
     * Select the Terrain at the given coordinates or deselect if -1, -1
     * is supplied.
     * At game start there is no selected Terrain.
     *
     * @pre a map has been loaded by calling loadMap(String)
     * @pre the two inputs are either -1 or between 0 and the size of the
     * board - 1
     *
     * @param xCoord the X coordinate of the Terrain to be selected
     * @param yCoord the Y coordinate of the Terrain to be selected
     */
    public void selectTerrain(int xCoord, int yCoord)
    {
        //IF the passed in coordinates are -1 and -1
        if (xCoord == -1 && yCoord == -1)
        {
            //Set the selection to null.
            selection = null;
        }
        //ELSE
        else
        {
            //Make the Terrain pointed to by xCoord and yCoord the selected Terrain.
            selection = map[xCoord][yCoord];
        }
        
        //Set the oberservable status to changed.
        //Fix Defect # 170
        setChanged();

        //Notify any observers.
        notifyObservers(null);
    }
    
    /**
     * Move the currently selected Terrain's Unit to another Terrain. X increases
     * to the right and Y increases downward.
     *
     * @pre the move is valid, that means that the selected Terrain has a Unit,
     * it has enough moves, and won't move off the map or onto a Terrain that
     * already has a Unit
     *
     * @post the Terrain selected at the call will not have a Unit on it, the
     * Terrain at map[selected Terrain.xCoord + xChange][selected Terrain.yCoord +
     * yChange] will become the selected Terrain and will have the Unit from the
     * previous selected Terrain
     *
     * @param xChange the number of Terrains to move the Unit on the selected
     *  Terrain in the X direction
     * @param yChange the number of Terrains to move the Unit on the selected
     *  Terrain in the Y direction
     */
    public void move(int xChange, int yChange)
    {
        //Remove the unit from the currently selected terrain.
        Unit tempUnit = selection.removeUnit();
        //Place the unit just removed onto the location at 
        // (currentlySelectedXCoordinate + xChange, currentlySelectedYCoordinate +
        // yChange).
        map[selection.getX() + xChange][selection.getY() + yChange].setUnit(tempUnit);
        
        //Fixes defect #214
        //Decrement the units move the cost of the tarrain moved onto.
        tempUnit.decrementMove(map[selection.getX() +
            xChange][selection.getY() + yChange].getMoveCost());

        //Set the oberservable status to changed.
        //Fix Defect # 170
        setChanged();

        //Notify any observers.
        notifyObservers(null);
    }
    
    /**
    * Attack the Unit at xCoord, yCoord with the Unit at the selected Terrain.
    * Then, if the attacking unit is within the defending unit's range have the
    * defending unit counter-attack the attacking unt.
    *
    * @pre the attack is valid, there is a Unit on the selected Terrain,
    * there is a Unit on the Terrain with the given coords, and the defender is
    * in the attacker's attack range
    *
    * @post the unit on map[xCoord][yCoord] will have its health decremented by
    * at least one
    *
    * @param xCoord the X coordinate of the Terrain to be attacked
    * @param yCoord the Y coordinate of the Terrain to be attacked
    *
    * @return an ArrayList<Integer> containing the amount of damage done in the attack
    * and if a counter-attack occurs, the amount of damage done in that counter-attack.
    */
    public ArrayList<Integer> attack(int xCoord, int yCoord)
    {
        //Craete an ArrayList of Integers to hold the damage dealt.
        ArrayList<Integer> damages = new ArrayList<Integer>();
        
        //Get the attacking unit (the unit on the selected location).
        Unit attacker = selection.getUnit();

        //Get the defending unit (the unit on the location pointed to by
        // (xCoord, yCoord).
        Unit defender = map[xCoord][yCoord].getUnit();
        
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
            (double)(map[xCoord][yCoord].getDefMod()))));

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
        
        //Inflict the amount of damage calculated by the damage equation on the
        // defending unit.
        defender.takeDamage(damage);
        
        //Consume the attacking unit's attack.
        attacker.useAttack();
        
        //Add the amount of damage dealt to the ArrayList of Integers.
        damages.add(new Integer(damage));
        
        //IF the defending Unit is dead (health == 0)
        if (defender.getCurrentHealth() <= 0)
        {
            //remmove that unit from the board.
            map[xCoord][yCoord].removeUnit();
        }
        //ELSE IF the attacker is in range of the defender attempt to perform a
        // counter-attack
        else if (Math.abs(xCoord - selection.getX()) +
                Math.abs(yCoord - selection.getY()) <= defender.getAttackRange())
        {
            //Get the numeric modifier for the defenders attack type against the
            // attackers armor type.
            mod = defender.getAttackType().modAgainstType(attacker.getArmorType());
            
            //Obtain the amount of damage by performing the damage equation.
            damage = (int)(mod * (1.0 - (((double)defender.getMaxHealth() -
                (double)defender.getCurrentHealth()) / defender.getMaxHealth()) / 2.0)
                * ((double)defender.getAttackValue() -
                ((double)attacker.getArmorValue() +
                (double)selection.getDefMod())));
            
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
 
            //Inflict the amount of damage calculated by the damage equation on
            // the defending unit.
            attacker.takeDamage(damage);
            
            //IF the "attacking" (the Unit being counter-attacked) Unit is
            // dead (health == 0)
            if (attacker.getCurrentHealth() <= 0)
            {
                //Remove that unit from the board.
                selection.removeUnit();
            }
            //ENDIF
            
            //Consume the attacking unit's attack.
            defender.useAttack();
            
            //Add the amount of damage dealt to the ArrayList of Integers.
            damages.add(new Integer(damage));
        }
        //ENDIF

        //Set the oberservable status to changed.
        //Fix Defect # 170
        setChanged();

        //Notify any observers.
        notifyObservers(null);
        
        //Return the amount of damage the defending unit took.
        return damages;
    }
    
    /**
    * Get a list of the enemies in range of the Unit on the selected Terrain.
    *
    * @pre the map has been instantiated by calling loadMap(String)
    * @pre the selected Terrain has a Unit
    *
    * @return an ArrayList containing the coordinates of all of the units in range
    * of the selected Terrain Unit's range
    */
    public ArrayList<Point> getEnemiesInRange()
    {
        //Create a new ArrayList of Points.
        ArrayList<Point> enemies = new ArrayList<Point>();
        
        //Get the currently selected Unit's attack range.
        int attkRange = selection.getUnit().getAttackRange();
        
        //FOR all map rows.
        for (int row = 0; row < map.length; row++)
        {
            //FOR all map cols.
            for (int col = 0; col < map[row].length; col++)
            {
                //IF the terrain is in range and has an enemy unit.
                if (map[row][col].getUnit() != null &&
                    map[row][col].getUnit().getOwner() !=
                    selection.getUnit().getOwner() &&
                    (Math.abs(row - selection.getX()) +
                    Math.abs(col - selection.getY()) <= attkRange))
                {
                    //Add the enemy's location to the ArrayList of Points.
                    enemies.add(new Point(row, col));
                }
                //ENDIF
            }
            //ENDFOR
        }
        //ENDFOR

        //Set the oberservable status to changed.
        //Fix Defect # 170
        setChanged();

        //Notify any observers with the ArrayList of Points.
        notifyObservers(enemies);
        
        //Return the ArrayList of Points.
        return enemies;
    }
    
    /**
    * Gets the list of Unit type names from the UnitFactory.
    *
    * @return an ArrayList of all of the Unit types that can be constructed by
    * UnitFactory
    */
    public ArrayList<String> getUnitNames()
    {
        //Return an ArrayList of all the Unit's names according to uFactory.
        return uFactory.getUnitNames();
    }
    
    /**
    * Gets of list of Terrain type names from the TerrainFactory.
    *
    * @return an ArrayList of all of the Terrain types that can be constructed
    * by TerrainFactory
    */
    public ArrayList<String> getTerrainNames()
    {
        //Return an ArrayList of all the Terrain's names according to tFactory.
        return tFactory.getTerrainNames();
    }
    
    /**
    * Resets ALL of the Units on the board, not just those belonging to the given
    * Player. The Player arguement is instead used to tell the GameButtonView
    * what player's turn it is, so it can display the correct color. This method
    * MUST notify its Observers and give the nextPlayer as the blind pass.
    *
    * @pre the map has been instantiated by calling loadMap(String)
    * @post every Unit on the map has had its moves set to maxMoves, and canAttack
    * set to true
    *
    *
    * @param nextPlayer the player whose turn is will become after the Units
    * are reset. This will not be used in the resetUnits, but is required for
    * the GUI to know what player's turn it is
    */
    public void resetUnits(Player nextPlayer)
    {
        //FOR all the rows in map.
        for (int row = 0; row < map.length; row++)
        {
            //FOR all the cols in map.
            for (int col = 0; col < map[row].length; col++)
            {
                //IF the current terrain contains a Unit.
                if (map[row][col].getUnit() != null)
                {
                    //Reset that unit.
                    map[row][col].getUnit().reset();
                }
                //ENDIF
            }
            //ENDFOR
        }
        //ENDFOR

        //Set the oberservable status to changed.
        //Fix Defect # 170
        setChanged();

        //Notify any observers with the nextPlayer.
        notifyObservers(nextPlayer);
    }
}
