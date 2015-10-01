package main.entity.entitycomponent;

import main.entity.Entity.EntityType;
import main.entity.Bullet;
import main.entity.Entity;
import main.entity.EntityManager;

public class Weapon
{
	/* TODO: Implement weapons being able to store multiple charges. */
	/** Minimum amount of time between charges, in milliseconds. */
	private static final long CHARGE_INTERVAL = 500;
	/** Minimum amount of time between fires, in milliseconds. */
	private static final long FIRE_INTERVAL = 100;
	/** Max number of charges a weapon can store. */
	private static final int MAX_CHARGES = 2;
	/** How many charges are currently stored in this weapon. */
	private int charges = 0;
	/** The time the previous charges were added. */
	private long lastChargeTime;
	/** The time the previous shot was fired. */
	private long lastFireTime;
	
	/** Basic constructor. */
	public Weapon()
	{
		lastChargeTime = System.currentTimeMillis();
		lastFireTime = System.currentTimeMillis();
	}
	
	/** Fires the weapon, if it has passed it's cooldown interval.
	 * @param entity the entity firing this weapon.
	 */
	public void fire(Entity entity)
	{
		// Add how many charges have loaded
		for (
				long time = System.currentTimeMillis() - lastChargeTime;
				time >= CHARGE_INTERVAL;
				time -= CHARGE_INTERVAL
				)
		{
			if (charges < MAX_CHARGES)
			{
				++charges;
			}
			lastChargeTime = System.currentTimeMillis();
		}
		// Fire a stored charge after the weapon cooldown period
		if (charges>0 && System.currentTimeMillis()-lastFireTime >= FIRE_INTERVAL)
		{
			--charges;
			Bullet bullet = new Bullet(entity.getType());
			bullet.body.setPos(entity.body.getX(),entity.body.getY());
			bullet.body.setVector(0,16);
			if (entity.getType() == EntityType.PLAYER)
			{
				bullet.body.setY(bullet.body.getY()+entity.body.getHeight()/2);
			}
			else
			{
				// Reverse direction when fired by enemy
				bullet.body.setVectorY(-bullet.body.getVectorY());
				bullet.body.setY(bullet.body.getY()-entity.body.getHeight()/2);
			}
			// Add the bullet
			EntityManager.addBullet(bullet);
			// Set the last fire time to now
			lastFireTime = System.currentTimeMillis();
		}
	}
}
