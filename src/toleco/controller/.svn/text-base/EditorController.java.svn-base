package toleco.controller;

import java.io.FileNotFoundException;
import toleco.logic.GameBoard;
import toleco.TolecoApp;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;
import toleco.view.I_GameView;

/**
* An EditorController is the device that controls the interactions between the logic
* and UI  for the map editor mode. An EditorController will keep track of the
* currently chosen terrain, unit, and player. When the user indicates that the
* chosen combination of terrain, unit,
* and player is to be placed at a selected location,
* the EditorController will update the game board to represent this.
* The map editor is a "stamping" system. This means that once a "stamp"
*(combination of terrain, unit, and player)
* is placed on the map it cannot be edited. However, the "stamp" can be overwritten
* with another "stamp". If there is no unit chosen then the chosen player is ignored.
* If a unit is chosen, then that unit will be associated with the chosen player.
*
* @author Eriq Augustine (Javadocs)
* @author Adam Armstrong (Implementation)
* @version 1.0
*/
public class EditorController implements I_Controller
{
    /**
    * The playing area that units and terrain occupy.
    * This is the Model in the MVC architecture.
    */
    private GameBoard board;
    
    /**
    * A collection of every Player mapped to their name.
    */
    private HashMap<Player, String> playerNames;
    
    /**
    * The view that displays that gameboard.
    * This is the View in the MVC architecture.
    */
    private I_GameView view;
    
    /**
    * The chosen player to associate with the chosen unit.
    */
    private Player chosenPlayer;
    
    /**
    * The chosen terrain that is to be placed.
    */
    private String chosenTerrain;
    
    /**
    * The chosen unit that is to be placed.
    */
    private String chosenUnit;
    
    /**
    * The app that created this controller.
    */
    private TolecoApp app;

    /**
     * Used to construct a new GameController.
     * @param board supplied game board for the controller to pass on calls to
     */
    public EditorController(GameBoard board)
    {
        //Initialoze this.board to the passed in board.
        this.board = board;

        //Set the chosen player to kPlayer1.
        chosenPlayer = Player.kPlayer1;

        //Initialize playerNames to be a new HashMap<Player, String>.
        playerNames = new HashMap<Player, String>();

        //Enter a name for kPlayer1 into playerNames.
        playerNames.put(Player.kPlayer1, "Leaftron");

        //Enter a name for kPlayer2 into playerNames.
        playerNames.put(Player.kPlayer2, "Cromagmar");
    }

    /**
    * {@inheritDoc}
    */
    public void save(String fileName)
    {
        //CALL board.saveMap with fileName
        board.saveMap(fileName, chosenPlayer);
    }
    
    /**
    * {@inheritDoc}
    */
    public void quit()
    {
        //CALL app.cleanUp
        app.cleanUp();
    }
    
    /**
    * {@inheritDoc}
    */
    public void selectCell(int xCoord, int yCoord)
    {
        //CALL board.selectCell with xCoord, yCoord
        board.selectTerrain(xCoord, yCoord);

        //IF there is a chosen unit
        if(chosenUnit != null)
        {
            //Set the chosen terrain to null
            chosenTerrain = null;
        }

        //IF chosen terrain is null
        if(chosenTerrain == null)
        {
            //Use the coordinates to get the String of that map location
            chosenTerrain = board.getTerrain(xCoord, yCoord).getType();
        }

        //CALL board.setTerrain with terrain, unit, player
        board.setTerrain(chosenTerrain, chosenUnit, chosenPlayer);
    }
    
    /**
     * {@inheritDoc}
     */
    public void update(Observable observe, Object obj)
    {
        //IF object is instance of String
        if(obj instanceof String)
        {
            //CAST obj to a String
            String actionCmd = (String)obj;

            //Create a scanner with the actionCmd
            Scanner actionSc = new Scanner(actionCmd);

            //COMPUTE what EditorAction this event is
            EditorAction action = EditorAction.valueOf(actionSc.next());
            //CASE playerAction OF
            switch (action)
            {
                //kSelect: COMPUTE xCoord, yCoord from event's actionCommand
                case kSelectCell:
                    int xCoord = actionSc.nextInt();
                    int yCoord = actionSc.nextInt();

                    //CALL selectCell with xCoord, yCoord
                    selectCell(xCoord, yCoord);

                    break;
                //kQuit: CALL quit()
                case kQuit:
                    quit();
                    break;
                //kChooseUnit: COMPUTE unit name from actionCommand
                case kChooseUnit:
                    String unitName = actionSc.next();

                    //Set the chosen terrain to null
                    chosenTerrain = null;

                    //CALL SetCurrentUnit
                    this.setCurrentUnit(unitName);

                    break;
                //kLoad: COMPUTE fileName from the event's actionCommand
                case kLoad:
                    String loadName = actionSc.next();
                    //TRY
                    try
                    {
                        //CALL loadMap with fileName
                        loadMap(loadName);
                    }
                    //CATCH
                    catch(FileNotFoundException ex)
                    {
                        System.err.println("File not able to be loaded.");
                    }
                    break;
                //kChooseTerrain: COMPUTE terrain name from actionCommand
                case kChooseTerrain:
                    String terrainName = actionSc.next();

                    //Set the chosen unit to null
                    chosenUnit = null;

                    //CALL setCurrentTerrain with terrain
                    this.setCurrentTerrain(terrainName);

                    break;
                //kChoosePlayer: COMPUTE chosenPlayer from actionCommand
                case kChoosePlayer:
                    String playerName = actionSc.next();

                    //SET chosenPlayer to new player
                    this.setChosenPlayer(Player.valueOf(playerName));

                    break;
                //kSave: COMPUTE fileName from the event's actionCommand
                case kSave:
                    //Fixes defect#230
                    //CALL save with fileName making sure to trim
                    // the aquired String and replace all spaces with '_'s.
                    save(actionSc.nextLine().trim().replaceAll(" ", "_"));

                    break;
                default:
                    break;
            }
            //ENDCASE
        }
        //END IF
    }
    
    /**
    * Set the currently chosen player to the specified Player.
    *
    * @param chosen the Player to set the chosen player to
    */
    public void setChosenPlayer(Player chosen)
    {
        //SET chosenPlayer to chosen
        this.chosenPlayer = chosen;
    }
    
    /**
    * Set the currently chosen terrain to the specified Terrain.
    *
    * @param chosen the Terrain to set the chosen terrain to
    */
    private void setCurrentTerrain(String chosen)
    {
        //SET chosenTerrain to chosen
        this.chosenTerrain = chosen;
    }
    
    /**
    * Set the currently chosen unit to the specified Unit.
    *
    * @param chosen the Unit to set the chosen unit to
    */
    private void setCurrentUnit(String chosen)
    {
        //SET chosenUnit to chosen
        this.chosenUnit = chosen;
    }
    
    /**
    * Edits board to represent the map stored in the file pointed to by fileName.
    *
    * @pre fileName points to an existing file that was created using the Toleco app
    * @param fileName the path the the file representing the map to load
    */
    public void loadMap(String fileName) throws java.io.FileNotFoundException
    {
        //CALL board.loadMap with fileName
        board.loadMap(fileName);
    }

    /**
     * {@inheritDoc}
     */
    public void setApp(TolecoApp app)
    {
        //SET this controller's app to given app
        this.app = app;
    }


    /**
     * {@inheritDoc}
     */
    public void setBoard(GameBoard board)
    {
        //SET this controller's board to the given board
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    public void setView(I_GameView view)
    {
        this.view = view;
    }
}
