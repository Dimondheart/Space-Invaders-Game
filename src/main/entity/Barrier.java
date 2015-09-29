package main.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import main.entity.entitycomponent.*;

/** Those sometimes annoying, sometimes life-saving barriers located on the
 * players side of the screen.
 */
public class Barrier extends Entity
{
	/* TODO: Make sure these look like asteroids/space junk! */
	
	/** Constructor, takes an x and y for the initial position. */
	public Barrier(int newX, int newY)
	{
		renderColor = new Color(165,42,42);
		body = new Body(newX, newY, new Dimension(6,6));
		health = new Health(1);
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void renderEntity(Graphics2D g2)
	{
		g2.setColor(renderColor);
		g2.fillOval(scrX, scrY, scrW, scrH);
	}
}
