package main.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import main.entity.entitycomponent.*;
import main.gfx.Gfx;

/** The simplest enemy space ship. */
public class BasicEnemyShip extends Entity
{
	/** Change to fire, out of 1000. */
	private static final int FIRE_CHANCE = 5;
	/** Used to indicate when an enemy has hit a wall. */
	private static boolean hitWall = false;
	/** Random number generator used to make random decisions. */
	private Random rng;
	
	/** Basic constructor. */
	public BasicEnemyShip(int newX, int newY)
	{
		renderColor = Color.red;
		type = EntityType.ENEMY;
		body = new Body(10, newX, newY);
		body.setVector(2, 0);
		body.setStopAtEdge(false);
		health = new Health(1);
		weapon = new Weapon();
		// Random number generator
		rng = new Random();
	}

	@Override
	public void update()
	{
		int rand = rng.nextInt(1001);
		if (FIRE_CHANCE >= rand)
		{
			weapon.fire(body, type);
		}
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
