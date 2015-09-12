package main.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.entity.entitycomponent.*;
import main.gfx.Gfx;

/** Those sometimes annoying, sometimes life-saving barriers located on the
 * players side of the screen.
 */
public class Barrier extends Entity
{
	/* TODO: Make sure these look like asteroids/space junk! */
	
	/** Basic constructor. */
	public Barrier()
	{
		body = new Body(4);
		health = new Health(1);
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
		g2.setColor(Color.gray);
		// Draw a placeholder graphic
		g2.fillOval(x, y, body.getRadius()*2, body.getRadius()*2);
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
}
