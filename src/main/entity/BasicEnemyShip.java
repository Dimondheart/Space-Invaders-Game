package main.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Random;

import main.entity.entitycomponent.*;

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
		body = new Body(newX, newY, new Dimension(21,15));
		body.setVector(2, 0);
		health = new Health(1);
		weapon = new Weapon();
		rng = new Random();
	}
	
	/** Gets if an enemy has hit a wall. */
	public static boolean hitWall()
	{
		return hitWall;
	}
	
	/** Resets the hit wall status of enemies. */
	public static void resetHitWall()
	{
		hitWall = false;
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
		if (body.isTouchingEdge())
		{
			hitWall = true;
		}
	}
	
	@Override
	public void renderEntity(Graphics2D g2)
	{
		g2.setColor(renderColor);
		g2.fillRect(scrX, scrY, scrW, scrH);
	}
	
	/** Moves the enemy down a set amount and reverses its direction. */
	public void moveDown()
	{
		body.setVector(-body.getVectorX(), -body.getHeight());
		// TODO: Make this less "hacky"
		body.move();
		body.setVectorY(0);
	}
}
