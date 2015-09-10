package main.entity;

import main.entity.entitycomponent.*;

/** An entity is the most common object to represent something in a level.
 * This is the abstract base class for all entities.
 */
public abstract class Entity
{
	public Body body;
	public Render render;
	public Health health;
	public Weapon weapon;
	
	/** Basic constructor. */
	public Entity()
	{
	}
}
