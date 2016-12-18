package toleco.unit;

import toleco.controller.Player;

/**
* This Unit is a default unit only to be used when UnitFactory cannot find a
* Unit it has been asked to build. UnitFactory may be unable to find a Unit either
* because the Unit is in a map but not in the Unit folder, or the file in the
* Unit folder is malformed.
*
* @author Jon Moorman (Javadocs)
* @author Andrew Barton (implementation)
* @version 1.0
*/
public class DefaultUnit extends Unit
{
    /**
    * The constructor for a DefaultUnit.
    */
    public DefaultUnit()
    {
        super("Default", Player.kPlayer1, 1, 1, 0, 0, AttackType.kCrush,
                0, 0, ArmorType.kHide, 0, true);
    }
}
