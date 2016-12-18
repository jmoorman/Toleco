package toleco.controller;

import toleco.logic.GameBoard;
import toleco.TolecoApp;
import toleco.view.I_GameView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;
import toleco.terrain.Terrain;
import toleco.unit.Unit;

/**
* A GameController is a module that handles all communications between the
* GameBoard and the logic while playing the game. The GameController will keep
* track of whose turn it is, a collection of all the players playing the
* game, the board the game is being played on, the controller for the
* GameView, and the state that the game is in.
*
* @author Evan Ralston (Javadocs)
* @author Adam Armstrong (Implementation)
* @version 1.0
*/
public class GameController implements I_Controller
{

    /**
    * The player who is eligible to move his/her units and attack with them.
    */
    private Player activePlayer;

    /**
    * A collection of all players on the board mapped to their names.
    */
    private HashMap<Player, String> playerNames;

    /**
    * The playing area that the units and terrain occupy.
    */
    private GameBoard board;

    /**
    * Controls what is displayed by the UI.
    */
    private I_GameView view;

    /**
    * Represents the state that the game is currently in.
    */
    private GameState state;

    /**
    * The reference to the TolecoApp that created this controller.
    */
    private TolecoApp app;

    /**
     * Used to construct a new GameController.
     * @param board supplied game board for the controller to pass on calls to
     */
    public GameController(GameBoard board)
    {
        //Initialoze this.board to the passed in board.
        this.board = board;

        //Set the current state to kNoneSelected.
        state = GameState.kNoneSelected;

        //Set the active player to kPlayer1.
        activePlayer = Player.kPlayer1;

        //Initialize playerNames to be a new HashMap<Player, String>.
        playerNames = new HashMap<Player, String>();

        //Enter a name for kPlayer1 into playerNames.
        playerNames.put(Player.kPlayer1, "Leaftron");

        //Enter a name for kPlayer2 into playerNames.
        playerNames.put(Player.kPlayer2, "Cromagmar");
    }

    /**
     * Sets the view the controller uses to manipulate the game display.
     * @param view I_GameView that the controller maintains a reference to
     */
    public void setView(I_GameView view)
    {
        this.view = view;
    }

    /**
    * Changes the current state to kAttack.
    */
    private void attackClicked()
    {
        //set state to kAttack
        state = GameState.kAttackMode;
    }

    /**
    * Switches the active player to the other team.
    */
    private void endTurn()
    {
        //CALL board.selectTerrain with -1, -1.
        board.selectTerrain(-1, -1);

        //IF active player is player 1
        if(activePlayer == Player.kPlayer1)
        {
            //CALL board.resetUnits with player 2
            board.resetUnits(Player.kPlayer2);
            //Set activePlayer to player 2
            activePlayer = Player.kPlayer2;
        }
        //ELSE
        else
        {
            //CALL board.resetUnits with player 1
            board.resetUnits(Player.kPlayer1);
            //Set activePlayer to player 1
            activePlayer = Player.kPlayer1;
        }
        //ENDIF
    }

    /**
    * Ends the game causing the application to return to the main menu.
    */
    private void gameOver(String winner)
    {

        //SET state to GameState.gameOver
        state = GameState.kGameOver;

        //CALL view.displayGameOver with the appropriate player.
        view.displayGameOver(winner);

        //CALL quit()
        quit();
    }

    /**
    * Tells the gameboard to move the unit on the selected terrain if it
    * belongs to the player currently taking his/her turn.
    *
    * @pre moveDirection must be a kMoveUp, kMoveDown, kMoveLeft or kMoveRight
    * @param moveDirection is the direction to move the unit
    */
    private void move(PlayerAction moveDirection)
    {
        //Fixes defext #226.
        //IF state equals GameState.kTerrainSelected and the selected location is
        //not null and the selected location has a unit
        if(state == GameState.kTerrainSelected && board.getSelection() != null &&
                board.getSelection().getUnit() != null)
        {
            //IF selectedCell's Unit's player equals activePlayer THEN
            if(board.getSelection().getUnit().getOwner() == activePlayer)
            {
                //Get the number of moves the selected unit has remaining
                int moves = board.getSelection().getUnit().getCurrentMoves();

                //CASE moveDirection OF
                switch (moveDirection)
                {
                    //kMoveUp: CALL moveUp with moves
                    case kMoveUp:
                        moveUp(moves);
                        break;
                    //kMoveDown: CALL moveDown with moves
                    case kMoveDown:
                        moveDown(moves);
                        break;
                    //kMoveLeft: CALL moveLeft with moves
                    case kMoveLeft:
                        moveLeft(moves);
                        break;
                    //kMoveRight: CALL movesRight with moves
                    case kMoveRight:
                        moveRight(moves);
                        break;
                    default:
                        break;
                }
                //ENDCASE
            }
            //ENDIF
        }
        //ENDIF
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
    public void save(String fileName)
    {
        //CALL board.saveMap with fileName and the current active player
        board.saveMap(fileName, activePlayer);
    }

    /**
    * {@inheritDoc}
    */
    public void selectCell(int xCoord, int yCoord)
    {
        //SET state to kTerrainSelected
        state = GameState.kTerrainSelected;
        //CALL board.selectCell(int, int) with xCoord, yCoord
        board.selectTerrain(xCoord, yCoord);
        //CALL view.displayTerrainSelected
        view.displayTerrainSelected(activePlayer);
    }

    //CHECKSTYLE:OFF - Ignore the cyclomatic complexity of this method.
    // Ignore authorized by Dr. Dalbey.
    /**
    * {@inheritDoc}
    */
    public void update(Observable observe, Object obj)
    {
        //CHECKSTYLE:ON

        //IF object passed to update is an instance of String
        if(obj instanceof String)
        {
            //Cast the object to a String
            String actionStr = (String)obj;

            //CREATE a Scanner of the action command string
            Scanner actionSc = new Scanner(actionStr);
            //CREATE a PlayerAction from the first token read from the Scanner
            PlayerAction action = PlayerAction.valueOf(actionSc.next());
            //CASE playerAction OF
            switch (action)
            {
                //CASE kSelect : COMPUTE xCoord, yCoord from event's actionCommand
                case kSelect:
                    int xCoord = actionSc.nextInt();
                    int yCoord = actionSc.nextInt();

                    //IF state equals GameState.kAttackMode THEN
                    if(state == GameState.kAttackMode)
                    {
                        //CALL runAttackMode with the X and Y coordinates
                        runAttackMode(xCoord, yCoord);
                    }
                    //ELSE
                    else
                    {
                        //CALL selectCell with xCoord, yCoord
                        selectCell(xCoord, yCoord);
                    }
                    //END IF
                    break;
                //CASE kMoveUp : CALL move with playerAction
                case kMoveUp:
                    move(action);

                    //SET state to kTerrainSelected
                    state = GameState.kTerrainSelected;
                    break;
                //CASE kMoveDown : CALL move with playerAction
                case kMoveDown:
                    move(action);

                    //SET state to kTerrainSelected
                    state = GameState.kTerrainSelected;
                    break;
                //CASE kMoveLeft : CALL move with playerAction
                case kMoveLeft:
                    move(action);

                    //Update the board's selected terrain
                    state = GameState.kTerrainSelected;
                    break;
                //CASE kMoveRight : CALL move with playerAction
                case kMoveRight:
                    move(action);

                    //SET state to kTerrainSelected
                    state = GameState.kTerrainSelected;
                    break;
                //CASE kQuit : CALL quit()
                case kQuit:
                    quit();
                    break;
                //CASE kSave : COMPUTE fileName from the event's actionCommand
                case kSave:
                    //Fixes defext #230
                    //CALL save with fileName making sure to trim
                    // the aquired String and replace all spaces with '_'s.
                    save(actionSc.nextLine().trim().replaceAll(" ", "_"));
                    break;
                //CASE kAttack : SET state to GameState.attackMode
                case kAttack:
                    state = GameState.kAttackMode;
                    
                    //CALL board.getEnemiesInRange
                    board.getEnemiesInRange();
                    break;
                //CASE kEndTurn : CALL endTurn
                case kEndTurn:
                    endTurn();
                    break;
                //CASE kCancelAttack : SET state to kTerrainSelected
                case kCancelAttack:
                    state = GameState.kTerrainSelected;

                    //CALL (view as SwingGameView) remove highlights
                    view.removeHighlights();
                    break;
                default:
                    break;
            }
            //ENDCASE
        }
        //END IF
    }

    /**
     * Sets the application from which the controller was created.
     * @param app the application that created this controller
     */
    public void setApp(TolecoApp app)
    {
        //SET this controller's app to given app
        this.app = app;
    }

    /**
     * Sets the game board which the controller interacts with.
     * @param board the board to set the controller's board with
     */
    public void setBoard(GameBoard board)
    {
        //SET this controller's board to the given board
        this.board = board;
    }

    /**
     * Get the game view from this controller.
     * @return this controller's game view
     */
    public I_GameView getView()
    {
        return view;
    }

    /**
     * Get the game state from this controller.
     * @return this controller's game state
     */
    public GameState getState()
    {
        return state;
    }

    /**
     * Set the state of the game within this controller.
     * @param state the state of the game
     */
    public void setState(GameState state)
    {
        this.state = state;
    }

    /**
     * Sets the active player in the game.
     * @param player active player in the game for use by GameController
     */
    public void setActivePlayer(Player player)
    {
        activePlayer = player;
    }

    /**
     * Checks game over conditon on the map.
     * @return true if one player has no units otherwise false
     */
    private boolean isGameOver()
    {
        //CALL board.getMap and store the map
        Terrain[][] map = board.getMap();
        //Create boolean value for player 1
        boolean player1Units = false;
        //Create boolean value for player 2
        boolean player2Units = false;

        //FOR each Terrain array in the map
        for(Terrain[] terrainArr : map)
        {
            //FOR each Terrain in the Terrain array
            for(Terrain terrain : terrainArr)
            {
                //IF terrain.getUnit is not null
                if(terrain.getUnit() != null)
                {
                    //IF player one owns the unit
                    if(terrain.getUnit().getOwner() == Player.kPlayer1)
                    {
                        //SET player 1's boolean value to true
                        player1Units = true;
                    }
                    //ELSE
                    else
                    {
                        //SET player 2's boolean value to true
                        player2Units = true;
                    }
                    //ENDIF
                }
            }
        }

        //Return if player 1 or player 2 are out of units
        return (!player1Units || !player2Units);
    }

    /**
     * Load a map from the specified file name and sets the active player.
     * @param filename path to the map file
     */
    public void loadMap(String filename) throws java.io.FileNotFoundException
    {
        //Set activePlayer with a call to board.loadMap with the fileName
        activePlayer = board.loadMap(filename);

        //CALL view.displayBackStory with the back story returned from board
        view.displayBackStory(board.getBackStory());
    }

    //Fixes defect #161
    /**
     * Form a battle summary string based off of the passed in information
     * following this format:
     * "<attacking player's name> attacked <defending player's name> for
     * <damage> damage.\n
     * [<defending player's name> counter-attacked <attacking player's name>
     * for <damage> damage.]"
     *
     * @pre both Units and the ArrayList are non-null, and the ArrayList has
     * atleast one element.
     *
     * @param damageDealt an ArrayList containing the damage dealt to the defener
     *          and the damage dealt to the attacker (if any).
     * @return a String representing the battle summary.
     */
    private String formBattleSummary(ArrayList<Integer> damageDealt,
        Unit attacker, Unit defender)
    {
        //Create an empty String.
        String rtn = "";

        //Add the attacking player's name (not the enum, look it up in the name table).
        rtn += playerNames.get(attacker.getOwner());

        //Add " attacked " to the String.
        rtn += " attacked ";

        //Add the defending player's name to the String.
        rtn += playerNames.get(defender.getOwner());

        //Add " for <damageDealt.get(0)> damage.\n" to the String.
        rtn += (" for " + damageDealt.get(0) + " damage\n");

        //IF there was a counter-attack.
        if (damageDealt.size() == 2)
        {
            //Add the defending player's name to the String.
            rtn += playerNames.get(defender.getOwner());

            //Add " counter-attacked " to the String.
            rtn += " counter-attacked ";

            //Add the attacking player's name to the String.
            rtn += playerNames.get(attacker.getOwner());

            //Add " for <damageDealt.get(1)> damage.\n" to the String.
            rtn += (" for " + damageDealt.get(1) + " damage\n");
        }
        //ENDIF

        //Return the build String.
        return rtn;
    }

    /**
     * Analyzes and carries out the attack.
     * @param xCoord x-coordinate on the map
     * @param yCoord y-coordinate on the map
     */
    private void runAttackMode(int xCoord, int yCoord)
    {
        //IF the selected unit can attack, the unit getting attacked
        //is within range, the grid space getting attacked has a unit,
        //and the unit getting attacked is owned by the other player
        if(board.getSelection().getUnit().canAttack() &&
            board.getSelection().getUnit().getAttackRange() >=
            (Math.abs(xCoord - board.getSelection().getX()) +
            Math.abs(yCoord - board.getSelection().getY())) &&
            board.getTerrain(xCoord, yCoord).getUnit() != null &&
            board.getTerrain(xCoord, yCoord).getUnit().getOwner()
            != activePlayer)
        {
            //Get the attacking unit
            Unit attacker = board.getSelection().getUnit();
            //Get the defending unit
            Unit defender = board.getTerrain(xCoord, yCoord).getUnit();

            //CALL board.attack with xCoord, yCoord and
            //keep the returned ArrayList.
            ArrayList<Integer> damageDealt = board.attack(xCoord, yCoord);

            //CALL formBattleSummary with the result from attack.
            String battleSummary = formBattleSummary(damageDealt,
                    attacker, defender);

            //CALL view.displayBattleSummary with the battle summary
            view.displayBattleSummary(attacker, defender, battleSummary);

            //CALL view.removeHighLights
            view.removeHighlights();

            //IF game is over
            if(isGameOver())
            {
                //Create a String to hold the name of the winning Player.
                String winner = new String();

                //IF damageDealt has move than one entry
                // A counter-attack occured, so the active player could
                // not have won.
                if (damageDealt.size() == 1)
                {
                    //The winner is the currently active player.
                    winner = playerNames.get(activePlayer);
                }
                //ELSE
                else
                {
                    //The winner is the player who is not currently active.
                    winner = playerNames.get(Player.values()
                        [(activePlayer.ordinal() + 1) % 2]);
                }
                //ENDIF

                //CALL gameOver with the winning player's name.
                gameOver(winner);
            }
            //END IF

            //SET state to kTerrainSelected
            state = GameState.kTerrainSelected;
        }
        //ENDIF
    }

    /**
     * Move the unit up one location on the map.
     * @param moves current moves the unit has remaining
     */
    private void moveUp(int moves)
    {
        //IF move is not out of top bounds and terrain cost
        //is not too much
        if(board.getSelection().getX() != 0 &&
                board.getTerrain(board.getSelection().getX() - 1,
                board.getSelection().getY()).getMoveCost() <= moves
                && board.getTerrain(board.getSelection().getX() - 1,
                    board.getSelection().getY()).getUnit() == null)
        {
            //CALL board.move with -1 and 0 as arguments
            board.move(-1, 0);
            //Update the board's selected terrain
            selectCell(board.getSelection().getX() - 1,
                       board.getSelection().getY());
        }
        //END IF
    }

    /**
     * Move the unit down one location on the map.
     * @param moves current moves the unit has remaining
     */
    private void moveDown(int moves)
    {
        //IF move is not out of bottom bounds and terrain cost
        //is not too much
        if(board.getMap()[0].length != board.getSelection().getX() + 1 &&
            board.getTerrain(board.getSelection().getX() + 1,
                board.getSelection().getY()).getMoveCost() <= moves &&
                board.getTerrain(board.getSelection().getX() + 1,
                board.getSelection().getY()).getUnit() == null)
        {
            //CALL board.move with 1 and 0 as arguments
            board.move(1, 0);
            //Update the board's selected terrain
            selectCell(board.getSelection().getX() + 1,
                       board.getSelection().getY());
        }
        //END IF
    }

    /**
     * Move the unit left one location on the map.
     * @param moves current moves the unit has remaining
     */
    private void moveLeft(int moves)
    {
        //IF move is not out of left bounds and terrain cost
        //is not too much
        if(board.getSelection().getY() != 0 &&
            board.getTerrain(board.getSelection().getX(),
                board.getSelection().getY() - 1).getMoveCost() <= moves
                && board.getTerrain(board.getSelection().getX(),
                    board.getSelection().getY() - 1).getUnit() == null)
        {
            //CALL board.move with 0 and -1 as arguments
            board.move(0, -1);
            //Update the board's selected terrain
            selectCell(board.getSelection().getX(),
                        board.getSelection().getY() - 1);
        }
        //END IF
    }

    /**
     * Move the unit right one location on the map.
     * @param moves current moves the unit has remaining
     */
    private void moveRight(int moves)
    {
        //IF move is not out of right bounds and terrain cost
        //is not too much
        if(board.getMap().length != board.getSelection().getY() + 1 &&
            board.getTerrain(board.getSelection().getX(),
                board.getSelection().getY() + 1).getMoveCost() <= moves
                && board.getTerrain(board.getSelection().getX(),
                    board.getSelection().getY() + 1).getUnit() == null)
        {
            //CALL board.move with 0 and 1 as arguments
            board.move(0, 1);
            //Update the board's selected terrain
            selectCell(board.getSelection().getX(),
                        board.getSelection().getY() + 1);
        }
        //END IF
    }
}