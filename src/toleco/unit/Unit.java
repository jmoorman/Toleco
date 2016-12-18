package toleco.unit;

import toleco.controller.Player;

/**
* A Unit is a generic unit that all of the different unit types will be
* represented with. Unit types differ in the name that they have and all units of
* the same type will only differ in their mutable properties. Units will differ by
* their various attributes including a String which represents the unit's type.
* A unit is mostly an aggregate of data with only a few methods to operate on it
* such as taking damage and resetting its moves and attack.
*
* @author Jon Moorman - javadoc's
* @author Evan Ralston - implementation
* @version 1.0
*/
public class Unit
{
    /**
    * The type of unit which corresponds to its immutable properties.
    */
    private String type;
    
    /**
    * The player who owns the unit and may control it.
    */
    private Player owner;
    
    /**
    * The maximum health that the unit can have.
    */
    private int maxHealth;
    
    /**
    * The amount of health the unit currently has.
    */
    private int currentHealth;
    
    /**
    * The maximum number of moves a unit can have.
    * This is the number of moves a unit will be given each turn.
    */
    private int maxMoves;
    
    /**
    * The number of moves a unit currently has.
    */
    private int currentMoves;
    
    /**
    * The type of attack that a unit will use when dealing damage.
    * The attack type is matched against an armor type in the damage calculation.
    */
    private AttackType attackType;
    
    /**
    * The base attack value that a unit has.
    * This value is used in the damage calculation.
    */
    private int attackValue;
    
    /**
    * The maximum distance a unit can be from an enemy and still attack it.
    * This distance is a Manhattan distance so diagonal cells are not adjacent.
    */
    private int attackRange;
    
    /**
    * The type of armor that a unit has.
    * The armor type is matched against the attack type in the damage calculation.
    */
    private ArmorType armorType;
    
    /**
    * The base armor value that a unit has.
    * Effects how much the incoming damage will be reduced by in the damage calculation.
    */
    private int armorValue;
    
    /**
    * Determines whether or not a unit may still attack.
    */
    private boolean canAttack;

    //CHECKSTYLE:OFF - Ignore the large number of params for the Unit constructor.
    // Ignore authorized by Dr. Dalbey

    /**
    * The constructor for a Unit.
    * @param type the type of unit being represented
    * @param owner the Player who owns this unit
    * @param maxHealth the maximum health of the unit
    * @param currentHealth the current health of the unit
    * @param maxMoves the maximum number of moves of the unit
    * @param currentMoves the current number of moves of the unit
    * @param attackType the type of attack of the unit
    * @param attackValue the attack value of the unit
    * @param attackRange the attack range of the unit
    * @param armorType the type of armor that the unit has
    * @param armorValue the armor value of the unit
    * @param canAttack whether or not the unit can currently attack
    */
    public Unit(String type, Player owner, int maxHealth, int currentHealth,
        int maxMoves, int currentMoves, AttackType attackType,
        int attackValue, int attackRange, ArmorType armorType,
        int armorValue, boolean canAttack)
    {
        //Initialize this unit's type with the param type
        this.type = type;
        //Initialize this unit's owner with the param owner
        this.owner = owner;
        //Initialize this unit's max health with the param max health
        this.maxHealth = maxHealth;
        //Initialize this unit's current health with the param current health
        this.currentHealth = currentHealth;
        //Initialize this unit's max moves with the param max moves
        this.maxMoves = maxMoves;
        //Initialize this unit's current moves with the param current moves
        this.currentMoves = currentMoves;
        //Initialize this unit's attack type with the param attack type
        this.attackType = attackType;
        //Initialize this unit's attack value with the param attack value
        this.attackValue = attackValue;
        //Initialize this unit's attack range with the param attack range
        this.attackRange = attackRange;
        //Initialize this unit's armor type with the param armor type
        this.armorType = armorType;
        //Initialize this unit's armor value with the param armor value
        this.armorValue = armorValue;
        //Initialize this unit's ability to attack with the param canAttack
        this.canAttack = canAttack;
    }

    //CHECKSTYLE:ON

    /**
    * Accessor method to type.
    * @return the type of unit that is being represented
    */
    public String getType()
    {
        return type;
    }
    
    /**
    * Accessor method to owner.
    * @return the Player object that owns the unit
    */
    public Player getOwner()
    {
        return owner;
    }
    
    /**
    * Accessor method to maxHealth.
    * @return the maximum health of a unit
    */
    public int getMaxHealth()
    {
        return maxHealth;
    }
    
    /**
    * Accessor method to currentHealth.
    * @return the current health of a unit
    */
    public int getCurrentHealth()
    {
        return currentHealth;
    }
    
    /**
    * Accessor method to maxMoves.
    * @return the maximum number of moves of a unit
    */
    public int getMaxMoves()
    {
        return maxMoves;
    }
    
    /**
    * Accessor method to currentMoves.
    * @return the current number of moves of a unit has left
    */
    public int getCurrentMoves()
    {
        return currentMoves;
    }
    /**
    * Accessor method to attackType.
    * @return the AttackType of a unit
    */
    public AttackType getAttackType()
    {
        return attackType;
    }
    
    /**
    * Accessor method to attackValue.
    * @return the base attack value of a unit
    */
    public int getAttackValue()
    {
        return attackValue;
    }
    
    /**
    * Accessor method to attackRange.
    * @return the attack range of a unit
    */
    public int getAttackRange()
    {
        return attackRange;
    }
    
    /**
    * Accessor method to armorType.
    * @return the ArmorType of a unit
    */
    public ArmorType getArmorType()
    {
        return armorType;
    }
    
    /**
    * Accessor method to armorValue.
    * @return the base armor value of a unit
    */
    public int getArmorValue()
    {
        return armorValue;
    }
    
    /**
    * A method that returns whether or not the unit can attack.
    * @return true if the unit can attack, otherwise false
    */
    public boolean canAttack()
    {
        return canAttack;
    }
    
    /**
    * Reduces the current health of the unit by the specified amount. A unit is
    * considered dead if its health becomes 0 or negative.
    * @param dmg the amount of health to be removed from the unit
    */
    public void takeDamage(int dmg)
    {
        //IF dmg is greater than or equal to 0
        if(dmg >= currentHealth)
        {
            //SET currentHealth to 0
            currentHealth = 0;
        }
        //ELSE
        else
        {
            //Subtract dmg from currentHealth
            currentHealth -= dmg;
        }
        //END IF
    }
    
    /**
    * Removes a unit's ability to attack.
    */
    public void useAttack()
    {
        //Set the unit's ability to attack to false
        canAttack = false;
    }
    
    /**
    * Reduces the units current number of moves left by moveCost.
    * @param moveCost the number of moves being used by the unit
    * @pre moveCost is less than currentMoves
    */
    public void decrementMove(int moveCost)
    {
        //Decrement current moves by moveCost
        currentMoves -= moveCost;
    }
    
    /**
    * Resets the unit's current number of moves to its maximum and sets whether or
    * not the unit can attack to true.
    */
    public void reset()
    {
        //Set the unit's current moves to the unit's max moves
        currentMoves = maxMoves;
        //Set the unit's ability to attack to true
        canAttack = true;
    }
    
    /**
    * Creates a String with a units properties.
    * @return the String containing the units properties
    */
    @Override
    public String toString()
    {
        //Return a string with all unit status information
        return type+"\nHealth: "+currentHealth+"/"+maxHealth+"\nMoves: "+
            currentMoves+"/"+maxMoves+"\nAttack Value: "+attackValue+
            "\nAttack Range: "+attackRange+"\nArmor Value: "+armorValue;
    }
    
    /**
    * Creates a String representation of the unit that will be used for storage
    * in a map file.
    * @return the String representing this unit
    */
    public String toStringForFile()
    {
        //Return a string representing the unit in a file
        return type+","+owner.toString()+","+currentHealth+","+currentMoves+","
            +canAttack;
    }
}
