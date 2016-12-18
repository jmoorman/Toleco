/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package toleco.view.gui.editor;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import org.junit.Test;
import toleco.controller.EditorAction;
import toleco.logic.GameBoard;
import toleco.view.gui.editor.EditorButtonView;
import toleco.view.gui.editor.EditorGameView;
import static org.junit.Assert.*;


/**
 *
 * @author Andrew Barton
 */
public class EditorButtonViewTest extends JFrame {



    private String whatImTesting = "nothing";
    private final String kTestingSave = "save";
    private final String kTestingQuit = "quit";
    private final String kTestingLoad = "load";
    private boolean correctAction = false;



    private static final String kSavePressed = "save";
    private static final String kQuitPressed = "quit";
    private static final String kLoadPressed = "load";


    public EditorButtonViewTest() {
    }
    @Test
    public void testQuit()
    {
        System.out.println("quitPresed");
        whatImTesting = kTestingQuit;
        EditorButtonView instance = new EditorButtonView();
        instance.setGameView(new EditorButtonViewTestView());
        instance.actionPerformed(new ActionEvent(this, 0,
                kQuitPressed));
        if(correctAction != true)
        {
            fail("wrong action was returned by GameButtonView" +
                    "during quitButton tesing");
        }
    }
    @Test
    public void testSave()
    {
        System.out.println("Save Presed");
        whatImTesting = kTestingSave;
        EditorButtonView instance = new EditorButtonView();
        instance.setGameView(new EditorButtonViewTestView());
        instance.actionPerformed(new ActionEvent(this, 0,
                kSavePressed));
        if(correctAction != true)
        {
            fail("wrong action was returned by GameButtonView" +
                    "during Save Button tesing");
        }
    }
    @Test
    public void testLoad()
    {
        System.out.println("Load Presed");
        whatImTesting = kTestingLoad;
        EditorButtonView instance = new EditorButtonView();
        instance.setGameView(new EditorButtonViewTestView());
        instance.actionPerformed(new ActionEvent(this, 0,
                kLoadPressed));
        if(correctAction != true)
        {
            fail("wrong action was returned by GameButtonView" +
                    "during Load Button tesing");
        }
    }



    private class EditorButtonViewTestView extends EditorGameView
    {
        public EditorButtonViewTestView()
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
                if(action.contains(EditorAction.kSave + " "))
                {
                    correctAction = true;
                }
            }
            if(whatImTesting.equals(kTestingQuit))
            {
                if(action.contains(EditorAction.kQuit.toString()))
                {
                    correctAction = true;
                }
            }
            if(whatImTesting.equals(kTestingLoad))
            {
                if(action.contains(EditorAction.kLoad.toString()))
                {
                    correctAction = true;
                }
            }
        }
    }
}