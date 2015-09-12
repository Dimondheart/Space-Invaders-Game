package main.entity.entitycomponent;

import main.entity.Entity;
import main.entity.Bullet;
import main.entity.EntityManager;

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
	
	/** Fires the weapon, if it has passed it's cooldown interval.
	 * @param body the body of the firing entity.
	 */
	public void fire(Body body, Entity.EntityType whoFired)
	{
		if (System.currentTimeMillis() - lastFireTime >= FIRE_INTERVAL)
		{
			lastFireTime = System.currentTimeMillis();
			Bullet bullet = new Bullet(whoFired);
			bullet.body.setPos(body.getX(), body.getY());
			bullet.body.setVector(0, 10);
			EntityManager.addBullet(bullet);
		}
	}
}
