package main.entity.entitycomponent;

public class Weapon
{
	/** Minimum amount of time between shots, in milliseconds. */
	private static final long FIRE_INTERVAL = 500;
	
	/** The time the previous shot was fired. */
	private long lastFireTime;
	
	/** Basic constructor. */
	public Weapon()
	{
		lastFireTime = System.currentTimeMillis();
	}
	
	/** Fires the weapon, if it has passed it's cooldown interval. */
	public void fire()
	{
		if (System.currentTimeMillis() - lastFireTime >= FIRE_INTERVAL)
		{
			lastFireTime = System.currentTimeMillis();
			// TODO: Implement firing
		}
	}
}
