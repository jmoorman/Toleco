package toleco.view;

import toleco.unit.Unit;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import toleco.controller.Player;

/**
* The I_GameView interface models the basic behavior supported by all Toleco
* GameViews. GameViews, and their sub-components, are the View portion of
* Toleco's Model-View-Controller architecture and make up
* the Toleco UI. Classes the implement I_GameView are responsible for displaying
* the game map, providing images to GUI sub-components, and updating the GUI
* based on changes in the state of the game.
*
* @author Matt Tognetti
* @version 1.0
*/
public interface I_GameView extends Observer
{
    
    /**
    * Draws the terrain, units, and selectors that represent the current state
    * of the map.
    */
    public void drawMap();
    
    /**
    * Retrieves an image representing a unit or terrain type. All images have a
    * have an associated name related to the unit or terrain they represent.
    *
    * @pre name must be a valid Unit or Terrain name
    * @param name name of the terrain or unit to get an image for
    * @return the image associated with the name passed in
    */
    public BufferedImage getImage(String name);
    
    /**
    * Displays a battle summary in the Status Panel section of the GUI.
    *
    * @param attacker the unit that initiated the attack
    * @param defender the unit that was attacked
    * @param summary an overview of the battle, including damage dealt to both
    * units and any unit deaths
    */
    public void displayBattleSummary(Unit attacker, Unit defender, String summary);

    /**
     * Displays information based on the terrain that is selected.
     * 
     * @param currentPlayer the player whose turn it currently is
     */
    public void displayTerrainSelected(Player currentPlayer);

    /**
    * {@inheritDoc}
    */
    public void update(Observable obs, Object obj);

    //Fixes defect #152.
    /**
     * Remove any highlights around cells.
     */
    public void removeHighlights();

    /**
     * Accept a specially formatted String that will used in an update.
     *
     * @param action the String to pass in the update.
     */
    public void acceptAction(String action);

    /**
     * Reutrns the JPanel that represents the SwingGameView.
     *
     * @return the JPanel that represents the SwingGameView
     */
    public JPanel getPanel();

    /**
     * Display the map's backstory.
     *
     * @param story the map's backstory.
     */
    public void displayBackStory(String story);

    /**
     * Display a wessage about the game being over and
     * the passed in player winning.
     *
     * @param winner the Player that has won the game
     */
    public void displayGameOver(String winner);
}
