package main.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.entity.entitycomponent.*;
import main.gfx.Gfx;

/** An entity is the most common object to represent something in a level.
 * This is the abstract base class for all entities.
 */
public abstract class Entity
{
	public Body body;
	public Health health;
	public Weapon weapon;
	/** Color used to render an entity if not using graphics. */
	protected Color renderColor;
	
	/** The generic entity type of this entity instance. */
	protected EntityType type;
	
	/** If this entity needs to be destroyed. */
	private boolean destroy = false;
	
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
		// Default render color
		if (renderColor == null)
		{
			renderColor = Color.green;
		}
	}
	
	/** Does the "thinking" for this entity. */
	public abstract void update();
	
	/** Get what type of entity this entity is.
	 * @return (EntityType) the type of this entity
	 */
	public EntityType getType()
	{
		return type;
	}
	
	/** Renders this entity to the specified graphics context.
	 * @param g2 the graphics context/surface
	 */
	public void render(Graphics2D g2)
	{
		renderEntity(g2);
	}
	
	/** Marks this entity to be destroyed. */
	public void markForDestruction()
	{
		destroy = true;
	}
	
	/** Check if this entity should be destroyed. */
	public boolean destroy()
	{
		return destroy;
	}
	
	/** Render this entity. The entity positions itself on the provided
	 * surface. This function contains default rendering code, override
	 * it for custom rendering.
	 * @param g2 the surface to draw on.
	 */
	protected void renderEntity(Graphics2D g2)
	{
		// Get the center x
		int scrx = (int)((double)body.getX() * Gfx.getLayerScaleFactor());
		// Get the center y and adjust it to screen coordinates
		int scry = (int)(
				(double)(
						main.gamestate.level.Level.getLevelHeight()
						- body.getY()
						)
				* Gfx.getLayerScaleFactor()
				);
		int scrRadius = (int)((double)body.getRadius() * Gfx.getLayerScaleFactor());
		// Change x and y to the upper left corner
		scrx = scrx - scrRadius;
		scry = scry - scrRadius;
		// Draw a placeholder graphic
		g2.setColor(renderColor);
		g2.fillOval(scrx, scry, scrRadius*2, scrRadius*2);
	}
}
