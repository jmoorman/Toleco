/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.view.gui;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import org.junit.Test;
import toleco.controller.GameController;
import toleco.controller.PlayerAction;
import toleco.logic.GameBoard;
import static org.junit.Assert.*;

/**
 *
 * @author Andrew Barton
 */
public class GameButtonViewTest extends JFrame
{
    private String whatImTesting = "nothing";
    private final String kTestingSave = "save";
    private final String kTestingQuit = "quit";
    private final String kTestingAttack = "attack";
    private final String kTestingCancel = "cancel";
    private final String kTestingEndTurn = "end";
    private boolean correctAction = false;

    //TODO: the *Pressed tests require that the GameButtonView's
    //internal action strings match those in this test

    private static final String kCancelPressed = "cancel";
    private static final String kAttackPressed = "attack";
    private static final String kSavePressed = "save";
    private static final String kQuitPressed = "quit";
    private static final String kEndTurnPressed = "endTurn";
    
    

    /**
     * Test of switchToDisabledAttack method, of class GameButtonView.
     */
    @Test
    public void testSwitchToDisabledAttack()
    {
        System.out.println("switchToDisabledAttack");
        GameButtonView instance = new GameButtonView();
        instance.switchToDisabledAttack();
    }

    /**
     * Test of switchToEnabledAttack method, of class GameButtonView.
     */
    @Test
    public void testSwitchToEnabledAttack()
    {
        System.out.println("switchToEnabledAttack");
        GameButtonView instance = new GameButtonView();
        instance.switchToEnabledAttack();
    }

    /**
     * Test of switchToCancel method, of class GameButtonView.
     */
    @Test
    public void testSwitchToCancel()
    {
        System.out.println("switchToCancel");
        GameButtonView instance = new GameButtonView();
        instance.switchToCancel();
        
    }

    @Test
    public void testAttackPressed()
    {
        System.out.println("attackPressed");
        whatImTesting = kTestingAttack;
        GameButtonView instance = new GameButtonView();
        instance.setGameView(new GameButtonViewTestView());

        instance.actionPerformed(new ActionEvent(new Object(), 0,
                kAttackPressed));
        if(correctAction != true)
        {
            fail("wrong action was returned by GameButtonView" +
                    "during attackButton tesing");
        }
    }
    @Test
    public void testCancelPressed()
    {
        System.out.println("cancelPressed");
        whatImTesting = kTestingCancel;
        GameButtonView instance = new GameButtonView();
        instance.setGameView(new GameButtonViewTestView());
        instance.actionPerformed(new ActionEvent(new Object(), 0,
               kCancelPressed));
        if(correctAction != true)
        {
            fail("wrong action was returned by GameButtonView" +
                    "during Cancel button testing");
        }
    }
    @Test
    public void testEndTurnPressed()
    {
        System.out.println("End Turn Pressed");
        whatImTesting = kTestingEndTurn;
        GameButtonView instance = new GameButtonView();
        instance.setGameView(new GameButtonViewTestView());
        instance.actionPerformed(new ActionEvent(new Object(), 0,
               kEndTurnPressed));
        if(correctAction != true)
        {
            fail("wrong action was returned by GameButtonView" +
                    "during EndTurn Pressed testing");
        }
    }
    @Test
    public void testSavePressed()
    {
        System.out.println("Save Pressed");
        whatImTesting = kTestingSave;
        GameButtonView instance = new GameButtonView();
        instance.setGameView(new GameButtonViewTestView());
        instance.actionPerformed(new ActionEvent(new Object(), 0,
               kSavePressed));
        if(correctAction != true)
        {
            fail("wrong action was returned by GameButtonView" +
                    "during Save button testing");
        }
    }
    @Test
    public void testQuitPressed()
    {
        System.out.println("Quit Pressed");
        whatImTesting = kTestingQuit;
        GameButtonView instance = new GameButtonView();
        instance.setGameView(new GameButtonViewTestView());
        instance.actionPerformed(new ActionEvent(new Object(), 0,
               kQuitPressed));
        if(correctAction != true)
        {
            fail("wrong action was returned by GameButtonView" +
                    "during Quit button testing");
        }
    }

    

    private class GameButtonViewTestView extends SwingGameView
    {
        public GameButtonViewTestView()
        {
            super(new GameBoard());
        }
        @Override
        public void acceptAction(String action)
        {
            correctAction = false;
            if(whatImTesting.equals(kTestingSave))
            {
                //note to self, make this smarter
                if(action.contains(PlayerAction.kSave + " "))
                {
                    correctAction = true;
                }
            }
            if(whatImTesting.equals(kTestingQuit))
            {
                if(action.contains(PlayerAction.kQuit.toString()))
                {
                    correctAction = true;
                }
            }
            if(whatImTesting.equals(kTestingAttack))
            {
                if(action.contains(PlayerAction.kAttack.toString()))
                {
                    correctAction = true;
                }
            }
            if(whatImTesting.equals(kTestingCancel))
            {
                if(action.contains(PlayerAction.kCancelAttack.toString()))
                {
                    correctAction = true;
                }
            }
            if(whatImTesting.equals(kTestingEndTurn))
            {
                if(action.contains(PlayerAction.kEndTurn.toString()))
                {
                    correctAction = true;
                }
            }
        }
    }
}