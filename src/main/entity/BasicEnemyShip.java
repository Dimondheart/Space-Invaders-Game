package main.entity;

import main.entity.entitycomponent.*;

/** The simplest enemy space ship. */
public class BasicEnemyShip extends Entity
{
	/** Basic constructor. */
	public BasicEnemyShip()
	{
		body = new Body(10);
		render = new Render("BasicEnemyShip");
		health = new Health(1);
		weapon = new Weapon();
	}
}
