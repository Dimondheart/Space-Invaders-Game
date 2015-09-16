package main.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.entity.entitycomponent.*;
import main.gfx.Gfx;

/** The result of firing a weapon. */
public class Bullet extends Entity
{
	/** The type of entity that fired this bullet instance. */
	private EntityType firedBy;
//	/** If this bullet needs to be destroyed. */
//	private boolean destroy = false;
	
	/** Basic constructor, specify the type of entity that fired it. */
	public Bullet(EntityType firedBy)
	{
		body = new Body(3);
		body.setStopAtEdge(false);
		this.firedBy = firedBy;
	}

	@Override
	public void renderEntity(Graphics2D g2)
	{
		// Get the center x
		int x = body.getX();
		// Get the center y and adjust it to screen coordinates
		int y = Gfx.getFrameHeight() - body.getY();
		// Change x and y to the upper left corner
		x = x - body.getRadius();
		y = y - body.getRadius();
		// Draw a placeholder graphic
		g2.setColor(Color.green);
		g2.fillOval(x, y, body.getRadius()*2, body.getRadius()*2);
	}

	@Override
	public void update()
	{
		// Mark the bullet for deletion if it has passed a map edge
		if (body.isPastEdge())
		{
			markForDestruction();
			return;
		}
		// Update the bullets position
		body.move();
	}
	
	/** Returns the type of the entity who fired this bullet. */
	public EntityType getWhoFired()
	{
		return firedBy;
	}
	
//	/** Marks this entity to be destroyed. */
//	public void markForDestruction()
//	{
//		destroy = true;
//	}
//	
//	/** Check if this entity should be destroyed. */
//	public boolean destroy()
//	{
//		return destroy;
//	}
}
