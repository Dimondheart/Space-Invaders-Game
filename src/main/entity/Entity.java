package main.entity;

import java.awt.Graphics2D;

import main.entity.entitycomponent.*;

/** An entity is the most common object to represent something in a level.
 * This is the abstract base class for all entities.
 */
public abstract class Entity
{
	public Body body;
	public Health health;
	public Weapon weapon;
	
	protected EntityType type;
	
	/** Generic entity types. */
	public enum EntityType
	{
		ENEMY,
		PLAYER,
		AMBIENT
	}
	
	/** Basic constructor. */
	public Entity()
	{
		// Default type if not already set
		if (type == null)
		{
			type = EntityType.AMBIENT;
		}
	}
	
	/** Render this entity. 
	 * @param g2 the surface to draw on.
	 */
	protected abstract void renderEntity(Graphics2D g2);
	/** Does the "thinking" for this entity. */
	public abstract void update();
	
	public EntityType getType()
	{
		return type;
	}
	
	public void render(Graphics2D g2)
	{
		renderEntity(g2);
	}
}
