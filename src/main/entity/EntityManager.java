package main.entity;

import java.awt.Graphics2D;
import java.util.LinkedList;

import main.entity.Entity.EntityType;
import main.gfx.Gfx;

/** Manages instances of entities and interacting with them. */
public class EntityManager
{
	/** The entity ID of the player entity. */
	public static final int PLAYER_ID = 0;
	/** Maximum number of bullets that can be created. */
	private static final int MAX_BULLETS = 200;
	/** List of bullet entities. */
	private static LinkedList<Bullet> bullets;
	/** List of simple entities, which can all be handled the same way. */
	private LinkedList<Entity> entities;
	
	public EntityManager()
	{
		entities = new LinkedList<Entity>();
		bullets = new LinkedList<Bullet>();
		// Create the player
		entities.add(new PlayerShip());
		// Create test barriers
		for (int x = 200-8*3; x < 200+8*3; x += 8)
		{
			entities.add(new Barrier(x, 100));
			entities.add(new Barrier(x, 92));
			entities.add(new Barrier(x, 84));
			entities.add(new Barrier(x, 76));
		}
		// Test Entities
		entities.add(new BasicEnemyShip(150, 400-20-4));
		entities.add(new BasicEnemyShip(175, 400-20-4));
		entities.add(new BasicEnemyShip(200, 400-20-4));
		entities.add(new BasicEnemyShip(225, 400-20-4));
		entities.add(new BasicEnemyShip(250, 400-20-4));
		entities.add(new BasicEnemyShip(175, 400-40-4));
		entities.add(new BasicEnemyShip(200, 400-40-4));
		entities.add(new BasicEnemyShip(225, 400-40-4));
	}
	
	/** Updates all entities; performs AI, etc. */
	public synchronized void updateEntities()
	{
		// Update bullets
		for (int i = 0; i < bullets.size(); ++i)
		{
			Bullet bullet = bullets.get(i);
			// Update the bullet
			bullet.update();
			// Check if any bullets hit one of their enemies
			for (Entity entity : entities)
			{
				// If touching the entity
				if (bullet.body.isTouching(entity.body))
				{
					// If the bullet was not fired by this type of entity
					if (bullet.getWhoFired() != entity.getType())
					{
						entity.health.hit();
						// Destroy the enemy if it is out of hp
						if (entity.health.isDestroyed())
						{
							entity.markForDestruction();
						}
						// Destroy the bullet
						bullet.markForDestruction();
						System.out.println(
								"Hit Entity. Type: " + entity.type + ". New HP: " + entity.health.getHP()
								);
					}
				}
			}
			// Destroy a "dead" bullet
			if (bullet.destroy())
			{
				bullets.remove(i);
			}
		}
		// Update standard entities
		for (int i = 0; i < entities.size(); ++i)
		{
			Entity entity = entities.get(i);
			if (entity.destroy())
			{
				entities.remove(i);
				continue;
			}
			entity.update();
		}
		// TODO: Make this generic for all enemy types
		// Update enemies if one hit the wall
		if (BasicEnemyShip.hitWall())
		{
			for (Entity entity : entities)
			{
				if (entity.getType() != EntityType.ENEMY)
				{
					continue;
				}
				((BasicEnemyShip) entity).moveDown();
			}
			BasicEnemyShip.resetHitWall();
		}
	}
	
	/** Render all entities. */
	public synchronized void renderAll()
	{
		// Render bullets
		for (Bullet bullet : bullets)
		{
			bullet.render(Gfx.getLayerSurface(2));
		}
		// Render other entities
		for (Entity entity : entities)
		{
			entity.render(Gfx.getLayerSurface(3));
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
