package main.gfx;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.*;

import main.gamestate.GameState;
import main.ThreadClock;

/** Class that handles or delegates all events or actions directly on or by
 * the primary display frame/window.
 * <br>Is threaded.
 */
public class Gfx implements Runnable
{
	/* TODO: Add a way to pause rendering when the window is
	 * minimized/iconified.
	 */
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
		System.out.println("Setting Up Graphics System...");
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
	
	// TODO?: Modify this to be some kind of override in ThreadClock
	/** Starts this thread after any final initialization operations. */
	public void start()
	{
		System.out.println("Starting Graphics System...");
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
	
	/** Returns the width of the render area.
	 * @returns (int) the width in pixels
	 */
	public static int getFrameWidth()
	{
		return renderAreaWidth;
	}
	
	/** Returns the height of the render area.
	 * @returns (int) the height in pixels
	 */
	public static int getFrameHeight()
	{
		return renderAreaHeight;
	}
	
	/** Gets the primary JFrame for the game.
	 * 
	 * @return (JFrame) a reference to the current primary frame
	 */
	public static JFrame getPrimaryFrame()
	{
		return mainFrame;
	}
	
	/** Gets the drawing surface for the specified layer.
	 * <br>There are 7 layers in total.
	 * They should be used as follows:
	 * <br>
	 * <br>0-1: Background Layers
	 * <br>2-4: Main Content Layers
	 * <br>5-6: Foreground Layers (Like a GUI)
	 * <br>
	 * <br> Using sub-layers (meaning within a layer, first drawn =
	 * lowest sub-layer, last drawn = highest sub-layer) should be preferred
	 * where possible.  Use layers to simplify graphics operations, like
	 * rendering a tile of a tile grid at about the same time as the entities
	 * in that tile, or rendering background animations over a
	 * background image that doesn't change.
	 * @param layer the index of the layer.
	 * @return (Graphics2D) The graphics context to render to.
	 */
	public static Graphics2D getLayerSurface(int layer)
	{
		return layerContainer.getDrawingSurface(layer);
	}
	
	/** Updates the local reference to the game state.  Used to call game state
	 * render methods.
	 * @param newGameState the new game state
	 */
	public static void updateGameState(GameState newGameState)
	{
		layerContainer.updateGameState(newGameState);
	}
	
	/** Clears the drawing surface of the specified layer.
	 * After clearing, any references to the drawing surface may need to be
	 * updated with {@link #getLayerSurface(int)}.
	 * @param layer the index of the layer
	 * @see {@link #getLayerSurface(int)}
	 */
	public static synchronized void clearLayer(int layer)
	{
		layerContainer.clearLayer(layer);
	}
	
	/** Clears all layers drawing surfaces.
	 * @see {@link #getLayerSurface(int)}
	 */
	public static synchronized void clearAllLayers()
	{
		layerContainer.clearAllLayers();
	}
	
	/** Clears only the specified layers.
	 * @param layers the list of indexes of layers to clear
	 * @see {@link #getLayerSurface(int)}
	 */
	public static synchronized void clearLayers(int[] layers)
	{
		layerContainer.clearLayers(layers);
	}
	
	/** Clears all layers starting with <tt>start</tt> layer and stopping with
	 * <tt>stop</tt> layer.
	 * @param start the first layer to clear
	 * @param stop the last layer to clear
	 */
	public static synchronized void clearLayersInRange(int start, int stop)
	{
		layerContainer.clearLayersInRange(start, stop);
	}
}
