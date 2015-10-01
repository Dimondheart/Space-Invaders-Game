package main.entity;

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
	
	/** Basic constructor. */
	public EntityManager()
	{
		entities = new LinkedList<Entity>();
		bullets = new LinkedList<Bullet>();
	}
	
	/** Adds the specified bullet to the list of bullets. */
	public static synchronized void addBullet(Bullet bullet)
	{
		// Don't add too many bullets
		if (bullets.size() < MAX_BULLETS)
		{
			// Add bullet to the list
			bullets.add(bullet);
		}
	}
	
	/** Clears all statically stored information. */
	public static synchronized void reset()
	{
		bullets.clear();
	}
	
	/** Gets the number of enemies remaining. */
	public int numEnemiesLeft()
	{
		return numEntityTypeLeft(EntityType.ENEMY);
	}
	
	/** Gets the number of players remaining. */
	public int numPlayersLeft()
	{
		return numEntityTypeLeft(EntityType.PLAYER);
	}
	
	/** Get how many of the specified type of entity there currently are. */
	private synchronized int numEntityTypeLeft(EntityType type)
	{
		int num = 0;
		for (Entity entity : entities)
		{
			if (entity.getType() == type)
			{
				++num;
			}
		}
		return num;
	}
	
	/** Add the player entity. */
	public synchronized void addPlayer(Entity player)
	{
		entities.addFirst(player);
	}
	
	/** Add an enemy entity, like an enemy ship. */
	public synchronized void addEnemy(Entity enemy)
	{
		entities.add(enemy);
	}
	
	/** Add an ambient entity, like a barrier. */
	public synchronized void addAmbient(Entity ambient)
	{
		entities.add(ambient);
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
								"Hit Entity. Type: "
								+ entity.type
								+ ". New HP: "
								+ entity.health.getHP()
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
		// Update enemies if one hit the wall
		if (BasicEnemyShip.hitWall())
		{
			for (Entity entity : entities)
			{
				if (entity.getType() == EntityType.ENEMY)
				{
					((BasicEnemyShip) entity).moveDown();
				}
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
}
