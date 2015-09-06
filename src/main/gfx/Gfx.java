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
	/** The name of the primary game frame.
	 * Use this to select the correct primary frame to render to.
	 */
	public static final String MAIN_FRAME_NAME = "Space Invaders";
	/** The frame of the primary/most important window. */
	private static JFrame mainFrame;
	/** Contains all layers used to render graphics. */
	private static LayerContainer layerContainer;
	private static int renderAreaWidth;
	private static int renderAreaHeight;
	/** The thread for updating graphics. */
	private Thread gfxThread;
	/** The clock cycle handler. */
	private ThreadClock clock;
	
	/** Constructor sets up the main window.
	 * @param gameState the current game state
	 */
	public Gfx(GameState gameState)
	{
		renderAreaWidth = 400;
		renderAreaHeight = 400;
		Dimension dim = new Dimension(renderAreaWidth, renderAreaHeight);
		// Create the frame & layered pane
		mainFrame = new JFrame(MAIN_FRAME_NAME);
		// Create the container of layers
		layerContainer = new LayerContainer(gameState);
		layerContainer.setPreferredSize(dim);
		mainFrame.add(layerContainer);
		mainFrame.pack();
		mainFrame.setVisible(true);
		// Create & start the thread
		gfxThread = new Thread(this);
		clock = new ThreadClock();
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
	
	// TODO: Make constants or enums for each layer
	/** Gets the drawing surface for the specified layer. 
	 * @param layer the layer ID (bottom layer is 0)
	 */
	public static Graphics2D getLayerSurface(int layer)
	{
		return layerContainer.getDrawingSurface(layer);
	}
	
	/** Returns the width of the render area.
	 * @returns (int) the width in pixels
	 */
	public static int getRenderAreaWidth()
	{
		return renderAreaWidth;
	}
	
	/** Returns the height of the render area.
	 * @returns (int) the height in pixels
	 */
	public static int getRenderAreaHeight()
	{
		return renderAreaHeight;
	}
	
	public static void updateGameState(GameState newGameState)
	{
		layerContainer.updateGameState(newGameState);
	}
}
