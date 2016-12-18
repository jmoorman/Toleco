package toleco.controller;

import java.util.Scanner;

/**
* An enum representing all possible actions that a player may take on their
* turn. The possible actions are: moving up, down, left and right, selecting
* a grid location, clicking the attack button to enter attack mode, clicking
* the End Turn button, clicking the Save button and clicking the Quit button.
*
* @author Evan Ralston (Javadocs)
* @author Adam Armstrong (Implementation)
* @version 1.0
*/
public enum PlayerAction
{
    /**
    * An action representing moving the selected unit up one grid location.
    */
    kMoveUp,
    
    /**
    * An action representing moving the selected unit right one grid location.
    */
    kMoveRight,
    
    /**
    * An action representing moving the selected unit left one grid location.
    */
    kMoveLeft,
    
    /**
    * An action representing moving the selected unit down one grid location.
    */
    kMoveDown,
    
    /**
    * An action representing clicking on a grid location.
    */
    kSelect,
    
    /**
    * An action representing clicking the Attack button.
    */
    kAttack,
    
    /**
    * An action representing clicking the End Turn button.
    */
    kEndTurn,
    
    /**
    * An action representing clicking the Save button.
    */
    kSave,
    
    /**
    * An action representing clicking the Quit button.
    */
    kQuit,
    
    /**
    * An action representing clicking the Cancel (attack) button.
    */
    kCancelAttack;
    
    /**
    * Constructs a PlayerAction based off the first token in name.
    *
    * @pre the first token of name must be one of: kMoveUp, kMoveRight,
    * kMoveLeft, kMoveDown, kSelect, kAttack, kEndTurn, kSave, kQuit
    * @param name the string to be converted into a PlayerAction
    * @return the PlayerAction corresponding to the first token in name
    */
    public PlayerAction makeAction(String name)
    {
        //Create a scanner with name
        Scanner sc = new Scanner(name);
        
        //Get the first token of name
        String token = sc.next();
        
        //Return the result of calling valueOf with the specified token
        return valueOf(token);
    }
}
