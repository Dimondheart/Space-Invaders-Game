package main.entity.entitycomponent;

import java.awt.Dimension;

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
	/** Use to access an X value in an xy pair. */
	private static final int X = 0;
	/** Use to access a Y value in an xy pair. */
	private static final int Y = 1;
	/** The x position. */
	private int x;
	/** The y position. */
	private int y;
	/** The movement vector of this entity. */
	private int[] vector = new int[2];
	/** Width of this entity. */
	private int width;
	/** Height of this entity. */
	private int height;
	/** Sets if the entity should be stopped by map edges. Default is true. */
	private boolean stopAtEdge = true;
	/** Specifies if this body is touching an edge of the map/grid. */
	private boolean touchingEdge = false;
	
	/** Each corner of the rectangular collision box. */
	private enum Corner
	{
		UPPER_LEFT,
		UPPER_RIGHT,
		LOWER_RIGHT,
		LOWER_LEFT
	}
	
	/** Constructor, takes arguments for the width and height. */
	public Body(Dimension dims)
	{
		setWidth(dims.width);
		setHeight(dims.height);
		setVector(0, 0);
		setPos(200, getHeight() + 4);
	}
	
	/** Constructor, takes arguments for dimensions and initial position. */
	public Body(int newX, int newY, Dimension dims)
	{
		setWidth(dims.width);
		setHeight(dims.height);
		vector = new int[2];
		setVector(0, 0);
		setPos(newX, newY);
	}
	
	public boolean stopAtEdges()
	{
		return stopAtEdge;
	}
	
	/** Checks if this body has passed an edge of the map/grid. */
	public boolean isPastEdge()
	{
		// Don't bother to check if the entity is stopped by edges
		if (stopAtEdges())
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
	
	/** Get the width of this body. */
	public int getWidth()
	{
		return width;
	}
	
	/** Get the height of this body. */
	public int getHeight()
	{
		return height;
	}
	
	/** Gets the x component of the vector. */
	public int getVectorX()
	{
		return vector[0];
	}
	
	/** Gets the y component of the vector. */
	public int getVectorY()
	{
		return vector[1];
	}
	
	/** Get the map/grid coordinates of the specified corner. */
	public int[] getCornerCoords(Corner corner)
	{
		int[] coords = {0,0};
		switch (corner)
		{
		case LOWER_LEFT:
			coords[0] = getX()-getWidth()/2;
			coords[1] = getY()-getHeight()/2;
			break;
		case LOWER_RIGHT:
			coords[0] = getX()+getWidth()/2;
			coords[1] = getY()-getHeight()/2;
			break;
		case UPPER_LEFT:
			coords[0] = getX()-getWidth()/2;
			coords[1] = getY()+getHeight()/2;
			break;
		case UPPER_RIGHT:
			coords[0] = getX()+getWidth()/2;
			coords[1] = getY()+getHeight()/2;
			break;
		default:
			break;
		}
		return coords;
	}
	
	/** Checks if the other specified body is touching this one.
	 * <br>See the file "RectCollisionsExplained.md" for info on how
	 * this works.
	 * @param otherBody the other body to check for a collision with
	 */
	public synchronized boolean isTouching(Body otherBody)
	{
		// Get certain coords of the other body to compare to
		int[] a = otherBody.getCornerCoords(Corner.UPPER_LEFT);
		int[] d = otherBody.getCornerCoords(Corner.LOWER_RIGHT);
		// Check if any of this bodies corners are inside/on other bod. bounds
		for (Corner c : Corner.values())
		{
			int[] myC = getCornerCoords(c);
			if ((a[X]<=myC[X] && myC[X]<=d[X]) && (d[Y]<=myC[Y] && myC[Y]<=a[Y]))
			{
				return true;
			}
		}
		// Get certain coords of this body to compare to
		a = getCornerCoords(Corner.UPPER_LEFT);
		d = getCornerCoords(Corner.LOWER_RIGHT);
		// Check if any of other bodies corners are inside/on this bod. bounds
		for (Corner c : Corner.values())
		{
			int[] otherC = otherBody.getCornerCoords(c);
			if ((a[X]<=otherC[X] && otherC[X]<=d[X]) && (d[Y]<=otherC[Y] && otherC[Y]<=a[Y]))
			{
				return true;
			}
		}
		// No collisions detected
		return false;
	}
	
	public synchronized boolean isTouchingEdge()
	{
		return touchingEdge;
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
		setVectorX(x);
		setVectorY(y);
	}
	
	/** Sets only the x component of the movement vector.
	 * @param x the new x component
	 */
	public synchronized void setVectorX(int x)
	{
		vector[0] = x;
	}
	
	/** Sets only the y component of the movement vector.
	 * @param y the new y component
	 */
	public synchronized void setVectorY(int y)
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
	
	/** Set the width of this body. */
	private synchronized void setWidth(int w)
	{
		width = w;
	}
	
	/** Set the height of this body. */
	private synchronized void setHeight(int h)
	{
		height = h;
	}
	
	/** Set if the body is touching a map/grid edge. */
	private synchronized void setTouchingEdge(boolean touching)
	{
		touchingEdge = touching;
	}
	
	/** Moves the body using its movement vector. */
	public void move()
	{
		// Determine new coords
		int newX = getX() + getVectorX();
		int newY = getY() + getVectorY();
		// Flag for if touching a map/grid edge
		boolean touchEdge = false;
		// If the body is set to not stop at edges
		if (!stopAtEdge)
		{
			setX(newX);
			setY(newY);
		}
		// Otherwise stop at any edges
		else
		{
			// Move x
			if (0 <= newX-getWidth()/2 && newX+getWidth()/2 <= 400)
			{
				setX(newX);
			}
			else
			{
				touchEdge = true;
				if (getVectorX() < 0)
				{
					for (int maxMove = getVectorX()+1; maxMove < 0; ++maxMove)
					{
						newX = getX()+maxMove;
						if (0 <= newX-getWidth()/2 && newX+getWidth()/2 <= 400)
						{
							setX(newX);
							break;
						}
					}
				}
				else if(getVectorX() > 0)
				{
					for (int maxMove = getVectorX()-1; maxMove > 0; --maxMove)
					{
						newX = getX()+maxMove;
						if (0 <= newX-getWidth()/2 && newX+getWidth()/2 <= 400)
						{
							setX(newX);
							break;
						}
					}
				}
			}
			// Move y
			if (0 < newY && newY < 400)
			{
				setY(newY);
			}
			else
			{
				touchEdge = true;
				if (getVectorY() < 0)
				{
					for (int maxMove = getVectorY()+1; maxMove < 0; ++maxMove)
					{
						newY = getY()+maxMove;
						if (0 <= newY-getHeight()/2 && newY+getHeight()/2 <= 400)
						{
							setY(newY);
							break;
						}
					}
				}
				else if(getVectorY() > 0)
				{
					for (int maxMove = getVectorY()-1; maxMove > 0; --maxMove)
					{
						newY = getY()+maxMove;
						if (0 <= newY-getHeight()/2 && newY+getHeight()/2 <= 400)
						{
							setY(newY);
							break;
						}
					}
				}
			}
		}
		setTouchingEdge(touchEdge);
	}
}
