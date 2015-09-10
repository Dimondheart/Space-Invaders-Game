package main.entity.entitycomponent;

/** Handles all entity details having to do with the grid and collisions. */
public class Body
{
	/* TODO: Idea for rectangular collisions.
	 * Specify width and height instead of radius, and x and y are still
	 * at the center.
	 * Use the coordinates of the corners for some kind of distance checking.
	 */
	/** Radius of the entities collision area. */
	private int radius;
	/* Remember to keep a grid separate from the screen for position stuff. */
	/** The x position. */
	private int x;
	/** The y position. */
	private int y;
	/** The movement vector of this entity. */
	private int[] vector;
	
	/** Basic constructor. */
	public Body()
	{
		radius = 0;
		vector = new int[2];
		vector[0] = 0;
		vector[1] = 0;
	}
	
	/** Constructor, takes an argument for the collision area radius. */
	public Body(int newRadius)
	{
		radius = newRadius;
		vector = new int[2];
		vector[0] = 0;
		vector[1] = 0;
	}
	
	/** Sets the x position of this entity. */
	public void setX(int newX)
	{
		// Don't go outside the game area
		if (newX > 0)
		{
			x = newX;
		}
	}
	
	/** Sets the y position of this entity. */
	public void setY(int newY)
	{
		// Don't go outside the game area
		if (newY > 0)
		{
			y = newY;
		}
	}
	
	/** Checks if the other specified entity is touching this one. */
	public boolean isTouching(Body otherBody)
	{
		// TODO: Stub method
		return false;
	}
}
