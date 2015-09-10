package main.entity.entitycomponent;

/** Handles rendering of an entity's graphics. */
public class Render
{
	/** The name of this sprite's graphics. */
	private String spriteName;
	
	/** Basic constructor. */
	public Render()
	{
		spriteName = "Default";
	}
	
	/** Constructor; takes a string specifying the name/group name of 
	 * the graphics to use.
	 */
	public Render(String spriteName)
	{
		this.spriteName = spriteName;
	}
	
	/** Renders the entity. */
	public synchronized void render()
	{
		System.out.println("In entity.render.render...");
		// TODO: Implement rendering
	}
}
