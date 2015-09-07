package main.gfx;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.*;

import main.gamestate.GameState;
import main.ThreadClock;

/** Class that handles or delegates all events or actions directly on or by
 * the primary display frame/window.
 */
public class Gfx implements Runnable
{
	/** The name of the primary game frame. */
	public static final String MAIN_FRAME_NAME = "Space Invaders";
	/** Number of layers in the primary window. */
	public static final int NUM_LAYERS = 7;
	/** The frame of the primary/most important window. */
	private static JFrame mainFrame;
	/** Contains all layers used to render graphics. */
	private static LayerContainer layerContainer;
	/** Width of the render area all components should use. */
	private static int renderAreaWidth;
	/** Height of the render area all components should use. */
	private static int renderAreaHeight;
	/** The thread for updating graphics. */
	private Thread gfxThread;
	/** The clock cycle handler. */
	private ThreadClock clock;
	
	/** Constructor sets up the main window.
	 * @param gameState the current game state
	 */
	public Gfx()
	{
		renderAreaWidth = 400;
		renderAreaHeight = 400;
		Dimension dim = new Dimension(renderAreaWidth, renderAreaHeight);
		// Create the frame & layered pane
		mainFrame = new JFrame(MAIN_FRAME_NAME);
		// Create the container of layers
		layerContainer = new LayerContainer();
		layerContainer.setPreferredSize(dim);
		mainFrame.add(layerContainer);
		// Create & start the thread
		gfxThread = new Thread(this);
		clock = new ThreadClock();
	}
	
	public void start()
	{
		// Finalize the window
		mainFrame.pack();
		mainFrame.setVisible(true);
		gfxThread.start();
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			// Repaint the window components
			mainFrame.repaint();
			clock.nextTick();
		}
	}
	
	/** Gets the primary window frame, used for most rendering.
	 * @return (JFrame) A reference to the primary frame
	 */
	public static JFrame getPrimaryFrame()
	{
		return mainFrame;
	}
	
	// TODO: Rename this so it's not soooo long
	/** Returns the width of the render area.
	 * @returns (int) the width in pixels
	 */
	public static int getRenderAreaWidth()
	{
		return renderAreaWidth;
	}
	
	// TODO: Rename this so it's not soooo long
	/** Returns the height of the render area.
	 * @returns (int) the height in pixels
	 */
	public static int getRenderAreaHeight()
	{
		return renderAreaHeight;
	}
	
	/** Gets the drawing surface for the specified layer.
	 * <br>There are 7 layers in total.
	 * They should be used as follows:
	 * <br>
	 * <br>0-1: Background Layers
	 * <br>2-4: Main Content Layers
	 * <br>5-6: Foreground Layers
	 * <br>
	 * <br> Using sub-layers (meaning within a layer, first drawn =
	 * lowest sub-layer, last drawn = highest sub-layer) should be preferred
	 * where possible; use layers to simplify graphics operations, like
	 * rendering a tile of a tile-based map at the same time as the entities
	 * that are in that tile.
	 * @param layer the index of the layer.
	 * @return (Graphics2D) The graphics context to render to.
	 */
	public static Graphics2D getLayerSurface(int layer)
	{
		return layerContainer.getDrawingSurface(layer);
	}
	
	public static void updateGameState(GameState newGameState)
	{
		layerContainer.updateGameState(newGameState);
	}
}
