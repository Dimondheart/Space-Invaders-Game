package main.gamestate;

/** The game state that indicates that the game should quit.
 * The only important function here is {@link #cleanup() cleanup()}.
 */
public class QuitGame extends GameState
{
	@Override
	public void cycle()
	{
	}

	/** Cleans up the game before quitting by doing things like
	 * extra save operations.  Should be called before any other
	 * quit operations are executed.
	 */
	@Override
	public void cleanup()
	{
		System.out.println("Quitting");
		// Do any extra saving, etc. operations here.
	}

	@Override
	protected void initialize()
	{
	}

	@Override
	protected void renderState()
	{
	}
}
