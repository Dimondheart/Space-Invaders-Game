package main.entity.entitycomponent;

/** Handles all entity details having to do with the grid and collisions.
 * Development note; many of the unused values/methods in here are for when
 * I get around to implementing collision boxes instead of collision circles.
 */
public class Body
{
	/* TODO: Idea for rectangular collisions.
	 * Specify width and height instead of radius, and x and y are still
	 * at the center.
	 * Use the coordinates of the corners for some kind of distance checking.
	 */
	/* Remember to keep a grid separate from the screen for position stuff. */
	/** The x position. */
	private int x;
	/** The y position. */
	private int y;
	/** The movement vector of this entity. */
	private int[] vector;
	/** Radius of the entities collision area. */
	private int radius;
	/** Width of this entity. */
	private int width;
	/** Height of this entity. */
	private int height;
	/** Sets if the entity should be stopped by map edges. Default is true. */
	private boolean stopAtEdge = true;
	
	/** Constructor, takes an argument for the collision area radius. */
	public Body(int newRadius)
	{
		radius = newRadius;
		vector = new int[2];
		setVector(0, 0);
		setPos(200, getRadius() + 4);
	}
	
	/** Constructor, takes arguments for radius and initial position. */
	public Body(int newRadius, int newX, int newY)
	{
		radius = newRadius;
		vector = new int[2];
		setVector(0, 0);
		setPos(newX, newY);
	}
	
	/** Checks if this body has passed an edge of the map/grid. */
	public boolean isPastEdge()
	{
		// Don't bother to check if the entity is stopped by edges
		if (stopAtEdge)
		{
			return false;
		}
		else if (getX() < 0 || getX() > 400 || getY() < 0 || getY() > 400)
		{
			return true;
		}
		return false;
	}
	
	/** Gets the current x coordinate. */
	public int getX()
	{
		return x;
	}
	
	/** Gets the current y coordinate. */
	public int getY()
	{
		return y;
	}
	
	/** Gets the radius of this body. */
	public int getRadius()
	{
		return radius;
	}
	
	/** Set the position of this body.
	 * Using <tt>move()</tt> along with adjusting the vector should be used
	 * for normal movement.
	 * Note that the y axis is flipped relative to the drawing surfaces,
	 * so (0,0) is in the lower left corner of this grid.
	 * @param newX the new x coordinate
	 * @param newY the new y coordinate
	 */
	public synchronized void setPos(int newX, int newY)
	{
		setX(newX);
		setY(newY);
	}
	
	/** Sets the x coordinate of this body.
	 * @param newX the new x coordinate
	 */
	public synchronized void setX(int newX)
	{
		x = newX;
	}
	
	/** Sets the y coordinate of this body.
	 * Note that the y axis is flipped relative to the drawing surfaces,
	 * so (0,0) is in the lower left corner of this grid.
	 * @param newY the new y coordinate
	 */
	public synchronized void setY(int newY)
	{
		y = newY;
	}
	
	/** Sets the movement vector of this body.
	 * @param x the new x component
	 * @param y the new y component
	 */
	public synchronized void setVector(int x, int y)
	{
		setXVector(x);
		setYVector(y);
	}
	
	/** Sets only the x component of the movement vector.
	 * @param x the new x component
	 */
	public synchronized void setXVector(int x)
	{
		vector[0] = x;
	}
	
	/** Sets only the y component of the movement vector.
	 * @param y the new y component
	 */
	public synchronized void setYVector(int y)
	{
		vector[1] = y;
	}
	
	/** Specify if this body should stop or not when it hits a map edge.
	 * @param stop (boolean) <tt>true</tt> to stop at edges
	 */
	public synchronized void setStopAtEdge(boolean stop)
	{
		stopAtEdge = stop;
	}
	
	/** Checks if the other specified body is touching this one.
	 * @param otherBody the other body to check for a collision with
	 */
	public synchronized boolean isTouching(Body otherBody)
	{
		// Get the distance between the two body centers
		int distance = (int)Math.hypot(
				(double)(getX() - otherBody.getX()),
				(double)(getY() - otherBody.getY())
				);
		// See if they would be closer than their total radius distance
		if (distance <= getRadius() + otherBody.getRadius())
		{
			return true;
		}
		return false;
	}

	/** Moves the body using its movement vector. */
	public void move()
	{
		// Determine new coords
		int newX = getX() + vector[0];
		int newY = getY() + vector[1];
		// If the body is set to not stop at edges
		if (!stopAtEdge)
		{
			setX(getX() + vector[0]);
			setY(getY() + vector[1]);
		}
		// Otherwise stop at any edges
		else if ((0 <= newX && newX <= 400) && (0 < newY && newY < 400))
		{
			setX(getX() + vector[0]);
			setY(getY() + vector[1]);
		}
	}
}
