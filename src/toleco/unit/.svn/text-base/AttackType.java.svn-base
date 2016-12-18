package toleco.unit;

import java.util.HashMap;

/**
* Represents the various AttackTypes that a Unit has. All units have an AttackType,
* and the different possible AttackTypes are fixed at compile time.
*
* @author Andrew Barton (Javadocs)
* @author Adam Armstrong (Implementation)
* @version 1.0
*/
public enum AttackType
{
    /**
    * Represents the AttackType of a unit using a crushing weapon, like a club.
    */
    kCrush,
    
    /**
    * Represents the AttackType of a unit using a piercing weapon, like a spear.
    */
    kPierce,
    
    /**
    * Represents the AttackType of a unit using a mauling weapon, like a
    * Sabertooth Tiger's jaw.
    */
    kMaul;

    /**
     * The attack modifier if the attacking unit is weak against the defending unit.
     */
    private static final double kWeak = 0.5;

    /**
     * The attack modifier if the attacking unit is neutral against the defending 
     * unit.
     */
    private static final double kNeutral = 1.0;

    /**
     * The attack modifier if the attacking unit is strong against the defending 
     * unit.
     */
    private static final double kStrong = 1.5;
    
    /**
    * Internally stores the modifiers for AttackType vs ArmorType. This
    * must be instantiated at program start by AttackType.init(). The
    * String will be a composite of the 'This'.toString(), ':', and
    * the given armorType.toString().
    */
    private static HashMap<String, Double> modMap = init();
    
    /**
     * Given an ArmorType to compare to, returns the modifier to the attackValue.
     * The valid modifiers are strong, neutral, and weak.
     * @param armorType the ArmorType to compare this AttackType to
     * @return the modifier to multiply the attackValue by
     */
    public double modAgainstType(ArmorType armorType)
    {
        //Create a string with the current attack type and given armor type
        //seperated by a ':'
        String types = this + ":" + armorType;
        //Create a double modifier
        Double modifier;
        //IF the string is in the HashMap
        if(modMap.containsKey(types))
        {
            //Set modifier to the value the string maps to in modMap
            modifier = modMap.get(types);
        }
        //ELSE
        else
        {
            //Set modifier to 1.0
            modifier = kNeutral;
        }
        //END IF
        
        //Return modifier
        return modifier;
    }
    
    /**
    * Must be called at program start to get the value of modMap. The HashMap
    * key of String will be a composite of a particular AttackType.toString() +
    * ':' + a particular ArmorType.toString().
    * @return the statically created HashMap containing the mappings of
    * AttackType,ArmorType to Double
    */
    private static HashMap<String, Double> init()
    {
        //Create a new HashMap<String,Double> modifiers
        HashMap<String, Double> modifiers = new HashMap<String, Double>();
        
        //Set the values of modifiers (hardcoded to help with maintainability)
        modifiers.put("kCrush:kPadded", kWeak);
        modifiers.put("kCrush:kHide", kNeutral);
        modifiers.put("kCrush:kBone", kStrong);
        modifiers.put("kPierce:kHide", kWeak);
        modifiers.put("kPierce:kBone", kNeutral);
        modifiers.put("kPierce:kPadded", kStrong);
        modifiers.put("kMaul:kBone", kWeak);
        modifiers.put("kMaul:kPadded", kNeutral);
        modifiers.put("kMaul:kHide", kStrong);
        
        //Return modifiers
        return modifiers;
    }
}

