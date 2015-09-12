package main.entity;

import java.awt.Graphics2D;

import main.entity.entitycomponent.*;

/** The simplest enemy space ship. */
public class BasicEnemyShip extends Entity
{
	/** Basic constructor. */
	public BasicEnemyShip()
	{
		body = new Body(10);
		health = new Health(1);
		weapon = new Weapon();
	}

	@Override
	public void renderEntity(Graphics2D g2)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
}
