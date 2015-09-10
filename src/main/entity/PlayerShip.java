package main.entity;

import main.entity.entitycomponent.*;

/** The player controlled space ship. */
public class PlayerShip extends Entity
{
	/** Basic constructor. */
	public PlayerShip()
	{
		body = new Body(10);
		render = new Render("PlayerShip");
		health = new Health(3);
		weapon = new Weapon();
	}
}
