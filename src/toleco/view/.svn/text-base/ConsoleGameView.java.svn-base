package toleco.view;

import toleco.controller.I_Controller;
import toleco.controller.PlayerAction;
import toleco.logic.GameBoard;
import toleco.terrain.Terrain;
import toleco.unit.Unit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;
import javax.swing.JPanel;
import toleco.controller.Player;

/**
* A ConsoleGameView controls all of the user interaction when the game is
* being run in console mode.
*
* @author Evan Ralston - Javadocs
* @author Matt Tognetti - implementation
* @version 1.0
*/
public class ConsoleGameView extends Observable implements I_GameView
{
    /**
    * Board holds the the game logic.
    */
    private GameBoard board;

    /**
     * Controller responds to user interaction
     */
    private I_Controller controller;

    /**
     * The player who's turn it currently is
     */
    private Player activePlayer;

    /**
     * Whether or not the map needs to be redrawn
     */
    private Boolean redraw;

    /**
     * if the latest command entered was a select command
     */
    private Boolean isSelect;

    /**
     * if the latest command entered was a move command
     */
    private Boolean isDoubleUpdateCommand;


    /**
     * Constructor, initializes private variables.
     */  
    public ConsoleGameView()
    {
        //SET redraw to false
        redraw = false;
        //SET isSelect to false
        isSelect = false;
        //SET isMove to false
        isDoubleUpdateCommand = false;
    }

    //CHECKSTYLE:OFF - Ignore Cyclomatic Complexity
    // Ignore Authorized by Dr. Dalbey

    /**
     * Waits for and handles user input for console mode.
     * @pre the controller has already been set using setController
     */
    public void start()
    {
        //CHECKSTYLE:ON

        //PRINT out a help message
        System.out.println("Type 'H' to see a list of available" +
                " commands and how to use them.");
        //draw the map
        drawMap();
        //PRINT command prompt
        System.out.print("\nCommand: ");
        //INIT a Scanner to stdin
        Scanner in = new Scanner(System.in);
        //WHILE true
        while(in.hasNextLine())
        {
            //Store the line
            String line = in.nextLine();
            //IF line length is greater than 0 (check for EOF)
            if (line == null || line.length() == 0)
            {
                doQuit();
            }
            System.out.println("-------------------------------------");
            //Pull out the command
            char command = line.charAt(0);

            //SWITCH based on the command
            switch(command)
            {
                //CASE select command
                case 'S':
                    //CALL doSelect
                    doSelect(line);
                    break;
                //CASE attack command
                case 'A':
                    //CALL doAttack
                    doAttack(line);
                    break;
                //CASE move command
                case 'M':
                    //CALL doMove
                    doMove(line);
                    break;
                //CASE end turn command
                case 'E':
                    //CALL doEndTurn
                    doEndTurn();
                    break;
                //CASE help command
                case 'H':
                    //CALL doHelp
                    doHelp();
                    break;
                //CASE about command
                case 'O':
                    //CALL doAbout
                    doAbout();
                    break;
                //CASE quit command
                case 'Q':
                    //CALL doQuit
                    doQuit();
                    break;
                //Default
                default:
                    //PRINT help message
                    System.out.println("Type H for a list of available commands");
                    break;
            }
            //END CASE
            //PRINT command prompt
            System.out.print("\nCommand: ");
        }
    }

    /*
     * Selects a terrain from the map.
     * @pre coordinates from the console command (argument line) must be valid
     */
    private void doSelect(String line)
    {
        //TRY to pull x and y coordinates out of command string
        try
        {
            //GET x coordinate
            int xCoord = Integer.valueOf(line.substring(line.indexOf(' '),
                    line.indexOf(',')).trim());
            //GET y coordinates
            int yCoord = Integer.valueOf(line.substring(line.indexOf(',')+1,
                    line.length()).trim());

            //SET redraw to true
            redraw = true;
            //SET isSelect to true
            isSelect = true;

            //CALL setChanged and notify observer with the player action
            setChanged();
            notifyObservers(PlayerAction.kSelect.toString() +
                    " " + xCoord + " " + yCoord);
        }
        //END TRY
        //CATCH any exceptions
        catch (Exception exception)
        {
            //PRINT the proper way to type the select command
            System.out.println("Select command must be in the format 'S x,y'" +
                    " and valid coordinates must be entered");
        }
        //END CATCH
    }

    /*
     * Sends a kAttack event to the controller with the coordinates from the
     * console command.
     * @pre a terrain with a unit on it must already be selected and the
     * coordinates must be valid
     */
    private void doAttack(String line)
    {
        //TRY to get x and y coordinates from command string
        try
        {
            //GET x coordinate
            int xCoord = Integer.valueOf(line.substring(line.indexOf(' '),
                    line.indexOf(',')).trim());
            //GET y coordinate
            int yCoord = Integer.valueOf(line.substring(line.indexOf(',')+1,
                    line.length()).trim());

            //SET redraw to true
            isDoubleUpdateCommand = true;

            //CALL setChanged and notifyObservers with the attack string and
            //x,y coordinates
            setChanged();
            notifyObservers(PlayerAction.kAttack.toString() +
                    " " + xCoord + " " + yCoord);
            //CALL setChanged and notifyObservers with a select string and
            //x,y coordinates
            setChanged();
            notifyObservers(PlayerAction.kSelect.toString() +
                    " " + xCoord + " " + yCoord);
        }
        //END TRY
        //CATCH any exception
        catch (Exception exception)
        {
            //PRINT proper way to enter attack command
            System.out.println("Attack command must be in the format 'A x,y'" +
                    " and a terrain holding a unit under your\n control must have" +
                    " already been selected");
        }
        //END CATCH
    }

    /***
     * Perform the move command.
     * @param the command string input from the console
     */
    private void doMove(String line)
    {
        //INIT a playerAction
        PlayerAction action;
        //IF user wants to move down
        if (line.contains("d"))
        {
            //SET action to correct PlayerAction
            action = PlayerAction.kMoveDown;
        }
        //ELSE IF user wants to move up
        else if (line.contains("u"))
        {
            //SET action to correct PlayerAction
            action = PlayerAction.kMoveUp;
        }
        //ELSE IF user wants to move left
        else if (line.contains("l"))
        {
            //SET action to correct PlayerAction
            action = PlayerAction.kMoveLeft;
        }
        //ELSE IF user wants to move right
        else if (line.contains("r"))
        {
            //SET action to correct PlayerAction
            action = PlayerAction.kMoveRight;
        }
        //ELSE
        else
        {
            //PRINT how to correctly enter move command
            System.out.println("Move command must be in the format 'M d' and a" +
                    " terrain with a unit under your control with enough move" +
                    " points must already be selected");
            //RETURN
            return;
        }
        //END IF

        //SET is double update command to true
        isDoubleUpdateCommand = true;

        //CALL setChanged and notifyObservers with the action String
        setChanged();
        notifyObservers(action.toString());

    }

    /***
     * Ends the current players turn.
     */
    private void doEndTurn()
    {
        System.out.println("End turn");
        //SET isDoubleUpdateCommand to true
        isDoubleUpdateCommand = true;

        //CALL setChanged and notifyObservers with the endTurn String
        setChanged();
        notifyObservers(PlayerAction.kEndTurn.toString());
    }

    /*
     * Displays a list of available commands for console mode and instructions
     * and restrictions on using them.
     */
    private void doHelp()
    {
        //PRINT descriptions of all available commands
        System.out.println("Toleco Console Commands");
        System.out.println("-----------------------");
        //PRINT selection command requirements, must in the form "S x,y"
        System.out.println("Select: 'S x,y' - x and y are coordinates of terrain" +
                " you want to select");
        //PRINT attack command format: "A x,y" and requirements:a valid unit
        //must already be selected.
        System.out.println("Attack: 'A x,y' - x and y are coordinates of the" +
                " terrain you want to attack.\n  A terrain with a unit on it " +
                "must be selected prior to using this command");
        //PRINT move format: "M d" where d is a direction char (l, r, u, d) and
        //requirements: valid unit must already be selected
        System.out.println("Move: 'M d' - d is the direction you would like to" +
                " move.\n  left:l, right:r, up:u, down:d\n  A terrain with a unit" +
                " under your control must have aready been selected.");
        //PRINT end turn format: "E"
        System.out.println("End Turn: 'E' - ends the current player's turn");
        //PRINT quit format: "Q"
        System.out.println("Quit: 'Q' - exits the Toleco App");
        //PRINT help format: "H"
        System.out.println("Help: 'H' - displays this help text");
        //PRINT About format: "O"
        System.out.println("About: 'O' - displays the about test");
    }


    private void doAbout()
    {
        System.out.println("Toleco v1.0 by Team Ocelot");
        System.out.println("Team Members:");
        System.out.println("Adam Armstrong\nEriq Augustine\nAndrew Barton\n" +
                "Jon Moorman\nEvan Ralston\nMatt Tognetti");
    }

    /**
     * Quits the application.
     */
    private void doQuit()
    {
        //PRINT goodbye message
        System.out.println("Goodbye!");
        //QUIT program
        System.exit(0);
    }
    /**
    * Displays a battle summary in the console.
    *
    * @param attacker not used when running the application in console mode
    * @param defender not used when running the application in console mode
    * @param summary an overview of the battle, including damage dealt to both
    * units and any unit deaths
    */
    public void displayBattleSummary(Unit attacker, Unit defender, String summary)
    {
        //Print the summary to the screen
        System.out.println("\n----Battle Summary----");
        //PRINT attacker name
        System.out.println("Attacker: " + attacker.getType());
        //PRINT defender name
        System.out.println("Defender: " + defender.getType());
        //PRINT battle summary
        System.out.print("Summary: " + summary);
    }
    
    /**
    * Displays the terrain and units that represent the current state
    * of the map.
    */
    public void drawMap()
    {
        //DECLARE terrain to temporarily hold terrain on the board
        Terrain terrain;
        //Get the current map from the game board
        Terrain[][] map = board.getMap();
        //PRINT the column numbers
        System.out.format("    0    1    2    3    4    5    6 " +
                "   7    8    9\n");
        //FOR each row in the map
        for (int row = 0; row < map.length; row++)
        {
            //PRINT the row number
            System.out.print(row + " ");
            //FOR each column in the map
            for (int column = 0; column < map[row].length; column++)
            {
                //GET the terrain at this cell
                terrain = map[row][column];
                //PRINT out return value from getTerrainInfo
                System.out.print(getTerrainInfo(terrain));
            }
            //END FOR
            //PRINT newline
            System.out.print("\n");
        }
        //END FOR
        printGameInfo();
    }

    /**
     * Get information about a terrain to print to the console.
     * @param terrain terrain to get information about
     * @pre terrain must not be null
     * @return a string describing the terrain in the format [T,U], where
     * T is the first letter of the terrain name and U is the first letter of
     * the unit name
     */
    private String getTerrainInfo(Terrain terrain)
    {
        //SET terrain info to first char of terrain type
        char terrainInfo = terrain.getType().charAt(0);
        //SET unit info to a space
        char unitInfo = ' ';
        //GET the unit on this terrain
        Unit unit = terrain.getUnit();
        //IF there is a unit
        if ( unit != null )
        {
            //SET unit description to be first letter of unit's name
            unitInfo = unit.getType().charAt(0);
        }
        //END IF
        //RETURN cell info string in format [terrain info:unit info]
        return "[" + terrainInfo + ":" + unitInfo + "]";
    }

    /**
     * Print some basic game state information including currently selected
     * terrain and whose turn it is.
     */
    private void printGameInfo()
    {
        //IF activePlayer is not null
        if ( activePlayer != null )
        {
            //PRINT the name of the active player
            System.out.println("Current Player: " + activePlayer.toString());
        }
        //ELSE
        else
        {
            //PRINT that the active player is unknown
            System.out.println("Current Player: unknown!");
        }
        //END IF
        //GET selected terrain from board
        Terrain selected = board.getSelection();
        //INIT selectionString to 'none'
        String selectionString = "none";
        //IF a terrain is selected
        if ( selected != null )
        {
            //Add terrain information to selection string by calling getCellInfo
            selectionString = getTerrainInfo(selected);
            //Add terrain location information to string
            selectionString += " @ (" + selected.getX() + "," + selected.getY() +
                    ")";
        }
        //END IF
        //Print out selected cell information
        System.out.println("Selected cell: " + selectionString);
    }
    
    /**
    * Not used when running the application in console mode.
    *
    * @param name not used
    * @return null
    */
    public BufferedImage getImage(String name)
    {
        return null;
    }
    
    /**
    * {@inheritDoc}
    */
    public void update(Observable obs, Object obj)
    {
        //IF the board is null
        if ( board == null )
        {
            //SET board to the observable
            board = (GameBoard)obs;
        }
        //IF obj is a Player enum
        if (obj != null && obj.getClass() == Player.class)
        {
            //SET activePlayer to obj
            activePlayer = (Player)obj;
        }

        //IF the map needs to be redrawn
        if (redraw)
        {
            //set redraw flag to false
            redraw = false;
            //draw the map
            drawMap();
        }
        //IF the last command was a select
        if (isSelect)
        {
            //set status flag to false
            isSelect = false;
            //print status
            printStatus();
        }
        //IF last command was a move
        if (isDoubleUpdateCommand)
        {
            //SET isMove to false
            isDoubleUpdateCommand = false;
            //SET redraw to true
            redraw = true;
        }
    }
    
    /**
    * Prints the contents of the queried terrain. The terrain may either have
    * a Unit on it or not.
    */
    public void printStatus()
    {
        //GET the current selected terrain
        Terrain selection = board.getSelection();
        //IF the selection is not null
        if ( selection != null )
        {
            //GET the unit on the selected terrain
            Unit unit = selection.getUnit();
            //DECLARE a unit info string
            String unitInfo;
            //PRINT terrain info
            System.out.println("\n---Selected Cell Status---");
            System.out.println(getTerrainInfo(selection) + " @ (" +
                    selection.getX() + ", " + selection.getY() + ")");
            System.out.println("--Terrain--");
            //PRINT type
            System.out.println("Terrain type: " + selection.getType());
            //PRINT defense modifier
            System.out.println("Defense mod: "+ selection.getDefMod());
            //PRINT move cost
            System.out.println("Move cost:" + selection.getMoveCost());
            //IF there is no unit on this terrain
            if (unit == null)
            {
                //SET unit info to no unit
                unitInfo = "no unit";
            }
            //ELSE there is a unit
            else
            {
                //SET unit info using the unit.toString method
                unitInfo = unit.toString();
            }
            //END IF
            //PRINT unit info
            System.out.print("--Unit--\n" + unitInfo + "\n");
        }
        //END IF
    }

    /**
     * A setter method for the controller.
     * @param controller A GameController
     */
    public void setController(I_Controller controller)
    {
        //SET controller
        this.controller = controller;
    }

    /**
    * {inheritdoc}
    */
    public void removeHighlights()
    {
        //Not implemented.
    }

    /**
     * {@inheritDoc}
     */
    public JPanel getPanel()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void acceptAction(String action)
    {
        //Not implemented
    }

    /**
     * {@inheritDoc}
     */
    public void displayBackStory(String story)
    {
        //PRINT the backstory
        System.out.println(story);
    }

    /**
     * {@inheritDoc}
     */
    public void displayTerrainSelected(Player currentPlayer)
    {
        //Not needed for ConsoleGameView
    }

    /**
     * {@inheritDoc}
     */
    public void displayGameOver(String winner)
    {
        //PRINT gameover message
        System.out.println("Game over! Winner is: " + winner);
        //CALL doQuit;
        doQuit();
    }
}
