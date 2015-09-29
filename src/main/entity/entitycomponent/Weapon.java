package main.entity.entitycomponent;

import main.entity.Entity.EntityType;
import main.entity.Bullet;
import main.entity.EntityManager;

public class Weapon
{
	/* TODO: Implement weapons being able to store multiple charges. */
	/** Minimum amount of time between shots, in milliseconds. */
	private static final long FIRE_INTERVAL = 500;
	
	/* TODO: Fix bug where pausing allows weapons to reload 'instantly.' */
	/** The time the previous shot was fired. */
	private long lastFireTime;
	
	/** Basic constructor. */
	public Weapon()
	{
		lastFireTime = System.currentTimeMillis();
	}
	
	/** Fires the weapon, if it has passed it's cooldown interval.
	 * @param body the body of the firing entity.
	 */
	public void fire(Body body, EntityType whoFired)
	{
		if (System.currentTimeMillis() - lastFireTime >= FIRE_INTERVAL)
		{
			lastFireTime = System.currentTimeMillis();
			Bullet bullet = new Bullet(whoFired);
			bullet.body.setPos(body.getX(), body.getY());
			bullet.body.setVectorX(0);
			if (whoFired == EntityType.PLAYER)
			{
				bullet.body.setVectorY(10);
			}
			else
			{
				bullet.body.setVectorY(-10);
			}
			EntityManager.addBullet(bullet);
		}
	}
}
