
package toleco.controller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adam
 */
public class EditorActionTest extends TestCase
{
    private EditorAction action;

    public EditorActionTest() 
    {
        action = EditorAction.kChoosePlayer;
    }

    @Before
    public void setUp() 
    {
        action = EditorAction.kChoosePlayer;
    }

    /**
     * Test of makeAction method, of class EditorAction.
     */
    @Test
    public void testMakeAction()
    {
        System.out.println("makeAction");

        String player = "kChoosePlayer";
        String quit = "kQuit";
        String badName = "kBadName";

        assertTrue(EditorAction.kChoosePlayer == action.makeAction(player));

        assertTrue(EditorAction.kQuit == action.makeAction(quit));

        try
        {
           action.makeAction(badName);
           fail("Created a non-valid enum value");
        }
        catch(Exception ex)
        {

        }
    }

}