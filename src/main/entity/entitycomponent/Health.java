package main.entity.entitycomponent;

/** Handles the hitpoints of an entity.
 * This includes damaging the entity and indicating when it has been destroyed.
 */
public class Health
{
	/** The initial number of hits that can be taken. */
	private int maxHP;
	/** The current health of the entity. */
	private int hp;
	
	public Health()
	{
		this.maxHP = 0;
		hp = this.maxHP;
	}
	
	/** Constructor, takes an int for the initial hitpoints.
	 * Specify a max hp less than 0 for an indestructable entity.
	 */
	public Health(int maxHP)
	{
		this.maxHP = maxHP;
		hp = this.maxHP;
	}
	
	/** Get the current hp. */
	public int getHP()
	{
		return hp;
	}
	
	/** Checks if this entity is dead/taken its max hits. */
	public boolean isDestroyed()
	{
		return (hp == 0);
	}
	
	/** Damages the entities hitpoints. */
	public void hit()
	{
		if (hp > 0)
		{
			--hp;
		}
	}
}
