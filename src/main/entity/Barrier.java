package main.entity;

import main.entity.entitycomponent.*;

/** Those sometimes annoying, sometimes life-saving barriers located on the
 * players side of the screen.
 */
public class Barrier extends Entity
{
	/* TODO: Make sure these look like asteroids/space junk! */
	
	/** Basic constructor. */
	public Barrier()
	{
		body = new Body(5);
		render = new Render("Barrier");
		health = new Health(1);
	}
}
