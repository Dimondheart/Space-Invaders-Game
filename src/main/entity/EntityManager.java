package main.entity;

import java.awt.Graphics2D;
import java.util.LinkedList;

/** Manages instances of entities and interacting with them. */
public class EntityManager
{
	/** The entity ID of the player entity. */
	public static final int PLAYER_ID = 0;
	/** Maximum number of bullets that can be created. */
	private static final int MAX_BULLETS = 200;
	private Entity[] entities;
	private static LinkedList<Bullet> bullets;
	
	public EntityManager()
	{
		entities = new Entity[40];
		bullets = new LinkedList<Bullet>();
		// Create the player
		entities[PLAYER_ID] = new PlayerShip();
		// Create test barrier
		entities[1] = new Barrier();
		entities[1].body.setPos(200, 200);
	}
	
	/** Updates all entities; performs AI, etc. */
	public synchronized void updateEntities()
	{
		// Update entities
		for (Entity entity : entities)
		{
			if (entity == null)
			{
				continue;
			}
			entity.update();
		}
		// Update bullets
		for (int i = 0; i < bullets.size(); ++i)
		{
			Bullet bullet = bullets.get(i);
			// Update the bullet
			bullet.update();
			// Check if any bullets hit one of their enemies
			for (Entity entity : entities)
			{
				if (entity == null)
				{
					continue;
				}
				// If touching the entity
				if (bullet.body.isTouching(entity.body))
				{
					// If the bullet was not fired by this type of entity
					if (bullet.getWhoFired() != entity.getType())
					{
						entity.health.hit();
						// Destroy the bullet
						bullet.markForDestruction();
						System.out.println(
								"Hit Entity. Type: " + entity.type + ". New HP: " + entity.health.getHP()
								);
					}
				}
			}
			// TODO: Implement destruction functionality for all entities
			// Destroy a "dead" bullet
			if (bullet.destroy())
			{
				bullets.remove(i);
			}
		}
	}
	
	/** Render all entities. */
	public synchronized void renderAll(Graphics2D g2)
	{
		// Render invaders
		for (Entity entity : entities)
		{
			if (entity == null)
			{
				continue;
			}
			entity.render(g2);
		}
		// Render bullets
		for (int i = 0; i < bullets.size(); ++i)
		{
			bullets.get(i).render(g2);
		}
	}
	
	/** Adds the specified bullet to the list of bullets. */
	public static synchronized void addBullet(Bullet bullet)
	{
		// Don't add a null bullet and don't add too many bullets
		if (bullet != null && bullets.size() < MAX_BULLETS)
		{
			// Add bullet to the list
			bullets.add(bullet);
		}
	}
	
	/** Clears all statically stored information. */
	public static synchronized void reset()
	{
		bullets = null;
	}
}
