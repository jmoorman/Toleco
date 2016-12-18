
package toleco.controller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Test for PlayerAction
 * @author adam
 */
public class PlayerActionTest extends TestCase
{
    private PlayerAction action;

    public PlayerActionTest() 
    {
        action = PlayerAction.kMoveUp;
    }

    @Before
    public void setUp() 
    {
        action = PlayerAction.kMoveUp;
    }

    /**
     * Test of makeAction method, of class PlayerAction.
     */
    @Test
    public void testMakeAction() {
        System.out.println("makeAction");

        String up = "kMoveUp";
        String down = "kMoveDown";
        String badName = "kBadName";

        assertTrue(PlayerAction.kMoveUp == action.makeAction(up));

        assertTrue(PlayerAction.kMoveDown == action.makeAction(down));

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