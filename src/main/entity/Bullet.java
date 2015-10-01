package main.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import main.entity.entitycomponent.*;
import main.gfx.Gfx;

/** The result of firing a weapon. */
public class Bullet extends Entity
{
	/** The type of entity that fired this bullet instance. */
	private EntityType firedBy;
	
	/** Basic constructor, specify the type of entity that fired it. */
	public Bullet(EntityType firedBy)
	{
		renderColor = Color.cyan;
		body = new Body(new Dimension(3,6));
		body.setStopAtEdge(false);
		this.firedBy = firedBy;
	}
	
	@Override
	public void loadGraphics()
	{
		Gfx.loadGraphic("entities/bullet/basic1.png");
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
	
	@Override
	public void renderEntity(Graphics2D g2)
	{
		g2.setColor(renderColor);
		g2.drawImage(
				Gfx.getScaledGraphic(
						"entities/bullet/basic1.png",
						Gfx.getLayerScaleFactor(3),
						Gfx.getLayerScaleFactor(3)
						),
				scrX,
				scrY,
				null
				);
	}
	
	/** Returns the type of the entity who fired this bullet. */
	public EntityType getWhoFired()
	{
		return firedBy;
	}
}
