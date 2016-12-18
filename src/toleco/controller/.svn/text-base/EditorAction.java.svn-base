package toleco.controller;

import java.util.Scanner;

/**
* An enum representing the possible actions that an editor can perform.
* The possible actions are choosing a type of terrain, choosing a type of unit,
* choosing a player, selecting a cell, saving, loading, or quitting.
*
* @author Eriq Augustine (Javadocs)
* @author Adam Armstrong (Implementation)
* @version 1.0
*/
public enum EditorAction
{
    /**
    * An action to choose a terrain.
    */
    kChooseTerrain,
    
    /**
    * An action to choose a unit.
    */
    kChooseUnit,
    
    /**
    * An action to choose a player.
    */
    kChoosePlayer,
    
    /**
    * An action to select a cell on the game board.
    */
    kSelectCell,
    
    /**
    * An action to save the current map.
    */
    kSave,
    
    /**
    * An action to load a specified map.
    */
    kLoad,
    
    /**
    * An action to quit the map editor.
    */
    kQuit;
    
    /**
    * Constructs and returns an EditorAction based off of the first token in name.
    *
    * @pre the first token of name must be kChooseTerrain, kChooseUnit,
    * kChoosePlayer, kSelectCell, kSave, or kQuit
    * @param name the String to be converted
    * @return an EditorAction matching the first token of name
    */
    public static EditorAction makeAction(String name)
    {
        //Create a scanner of the param name
        Scanner sc = new Scanner(name);
        
        //Read the first token
        String token = sc.next();
        
        //Return the result of calling valueOf with the token
        return valueOf(token);
    }
}
