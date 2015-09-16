package main.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.entity.entitycomponent.*;
import main.gfx.Gfx;

/** The simplest enemy space ship. */
public class BasicEnemyShip extends Entity
{
	/** Used to indicate when an enemy has hit a wall. */
	private static boolean hitWall = false;
	
	/** Basic constructor. */
	public BasicEnemyShip(int newX, int newY)
	{
		type = EntityType.ENEMY;
		body = new Body(10, newX, newY);
		body.setVector(2, 0);
		body.setStopAtEdge(false);
		health = new Health(1);
		weapon = new Weapon();
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
		g2.setColor(Color.red);
		// Draw a placeholder graphic
		g2.fillOval(x, y, body.getRadius()*2, body.getRadius()*2);
	}

	@Override
	public void update()
	{
		weapon.fire(body, type);
		body.move();
		if (body.getX() <= 0 || body.getX() >= 400)
		{
			hitWall = true;
		}
	}
	
	/** Gets if an enemy has hit a wall. */
	public static boolean hitWall()
	{
		return hitWall;
	}
	
	/** Moves the enemy down a set amount and reverses its direction. */
	public void moveDown()
	{
		// TODO: Decouple this from body vector
		body.setVector(-body.getVectorX(), -body.getRadius()*2);
		body.move();
		body.setVectorY(0);
	}
	
	/** Resets the hit wall status of enemies. */
	public static void resetHitWall()
	{
		hitWall = false;
	}
}
