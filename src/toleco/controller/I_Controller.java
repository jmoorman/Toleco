package toleco.controller;

import java.util.Observer;
import toleco.logic.GameBoard;
import toleco.TolecoApp;
import toleco.view.I_GameView;

/**
* The I_Controller interface models basic behavior that a controller for Toleco
* must provide. An implementer of I_Contoller controls the interaction between
* the UI and the logic of Toleco.
*
* Toleco design follows the Model-View-Controller architecture, 
* and I_Controler specifies the requirements for the Contoller portion.
*
* A Controller in Toleco must be listening updates from its view component
 *and able to handle a request to either quit a battle,
* save a battle, or select a cell on the game board.
*
* @author Eriq Augustine (Javadocs)
* @author Matt Tognetti (Implementation)
* @version 1.0
*/
public interface I_Controller extends Observer
{
    /**
    * Quits the current battle.
    */
    public void quit();
    
    /**
    * Saves the current battle.
    *
    * @pre fileName is not null and is a valid file name 
    * (does not contain characters illegal to the operating system running
    * the Toleco app)
    * @param fileName the path location to save the battle to
    */
    public void save(String fileName);
    
    /**
    * Handles a cell being selected. The given coordinates (xCoord and yCoord) are
    * relative to an orgin located at the upper-left most corner of the game board.
    *
    * @param xCoord the number of rows from the origin that the cell being
    *  selected is located
    * @param yCoord the number of columns from the origin that the cell being
    *  selected is located
    */
    public void selectCell(int xCoord, int yCoord);
    
    /**
    * Sets the app that this controller uses to quit to main menu.
    * @param app the app that created this controller
    */
    public void setApp(TolecoApp app);
    
    /**
    * Sets the board that this controller uses.
    * @param board the board that this controller will be controlling
    */
    public void setBoard(GameBoard board);

    /**
     * Loads a map onto the board and gets the current player.
     * @param filename the name of the file to be read
     * @throws java.io.FileNotFoundException if the filename represents a
     * bad Toleco map file
     */
    public void loadMap(String filename) throws java.io.FileNotFoundException;

    /**
     * Gives the controller a reference to the view component.
     * @param view the view that this controller will be using
     */
    public void setView(I_GameView view);
}
