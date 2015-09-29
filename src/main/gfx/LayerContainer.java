package main.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLayeredPane;

/** Handles the different layers of the graphics for this application. */
public class LayerContainer extends JLayeredPane
{
	private static final long serialVersionUID = 1L;
	/** Reference to the game state to which rendering is delegated. */
	private static main.gamestate.GameState gameState;
	/** List of the components representing each layer. */
	private Layer[] layers;
	
	// TODO: Figure out how to copy javadoc descriptions
	/** Handles the different layers of the graphics for this application. */
	public LayerContainer()
	{
		// Setup the layers
		layers = new Layer[Gfx.NUM_LAYERS];
		for (int i = 0; i < Gfx.NUM_LAYERS; ++i)
		{
			layers[i] = new Layer(Gfx.getFrameWidth(), Gfx.getFrameHeight());
//			add(layers[i], i);
		}
	}
	
	/** Gets the drawing surface for the specified layer.
	 * @param layer the index of the layer
	 * @return (Graphics2D) A reference to the drawing surface
	 */
	public Graphics2D getDrawingSurface(int layer)
	{
		return layers[layer].getDrawingSurface();
	}
	
	/** Return the width of the specified layer. */
	public int getLayerWidth(int layer)
	{
		return layers[layer].getWidth();
	}
	
	/** Return the height of the specified layer. */
	public int getLayerHeight(int layer)
	{
		return layers[layer].getHeight();
	}
	
	/** Return the scale factor for the specified layer. */
	public double getScaleFactor(int layer)
	{
		return layers[layer].getScaleFactor();
	}
	
	/** Updates the game state that render is called from.
	 * @param newGameState the new game state
	 */
	public synchronized void updateGameState(main.gamestate.GameState newGameState)
	{
		gameState = newGameState;
	}
	
	/** Clears the specified layer.
	 * @param layer the index of the layer.
	 */
	public synchronized void clearLayer(int layer)
	{
		layers[layer].clear();
	}
	
	/** Clears all layers. */
	public synchronized void clearAllLayers()
	{
		for (int i = 0; i < Gfx.NUM_LAYERS; ++i)
		{
			clearLayer(i);
		}
	}
	
	/** Clears only the specified layers. */
	public synchronized void clearLayers(int[] layersToClear)
	{
		for (int i : layersToClear)
		{
			clearLayer(i);
		}
	}
	
	/** Clears all layers starting with <tt>start</tt> layer and stopping with
	 * <tt>stop</tt> layer.
	 * @param start the first layer to clear
	 * @param stop the last layer to clear
	 */
	public synchronized void clearLayersInRange(int start, int stop)
	{
		for (int i = start; i <= stop; ++i)
		{
			clearLayer(i);
		}
	}
	
	@Override
	protected synchronized void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		// Delegate the rendering to the game state
		gameState.render();
		// Clear the graphics context
		g2.setBackground(Color.black);
		g2.clearRect(0, 0, Gfx.getFrameWidth(), Gfx.getFrameHeight());
		// Render the layers
		for (int i = 0; i < Gfx.NUM_LAYERS; ++i)
		{
			layers[i].flip(g2);
		}
		if (main.Game.debugEnabled())
		{
			g2.setColor(Color.blue);
			g2.drawRect(0, 0, Gfx.DEFAULT_WINDOW_DIM, Gfx.DEFAULT_WINDOW_DIM);
		}
	}
	
	/** Changes the size of all the layers using newDims as the maximum
	 * allowed dimensions.
	 * @param newDims the maximum dimensions of the layers
	 */
	protected synchronized void adjustSize(Dimension newDims)
	{
		Dimension adjustedDims = new Dimension(0,0);
		// Adjusted dimensions that cover the largest possible square on screen
		if (newDims.getHeight() <= newDims.getWidth())
		{
			adjustedDims.setSize(newDims.getHeight(), newDims.getHeight());
		}
		else
		{
			adjustedDims.setSize(newDims.getWidth(), newDims.getWidth());
		}
		// Layer container covers whole window
		this.setSize(newDims);
		// Background layers cover whole window
		for (int i = 0; i < 2; ++i)
		{
			layers[i].adjustSize(newDims);
		}
		// Main layers cover the largest square area
		for (int i = 2; i < 5; ++i)
		{
			layers[i].adjustSize(adjustedDims);
		}
		// GUI layers cover the entire screen
		for (int i = 5; i < 7; ++i)
		{
			layers[i].adjustSize(newDims);
		}
	}
}
