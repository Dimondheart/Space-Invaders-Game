package main.entity.entitycomponent;

/** Handles miscellaneous stuff related to entity rendering. */
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
}
