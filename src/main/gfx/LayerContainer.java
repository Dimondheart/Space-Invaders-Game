package main.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLayeredPane;

/** Handles the layers. Basically a thin layer over a JLayeredPane that
 * works with the rendering delegation used by this program.
 */
public class LayerContainer extends JLayeredPane
{
	private static final long serialVersionUID = 1L;
	/** Reference to the game state to which rendering is delegated. */
	private static main.gamestate.GameState gameState;
	/** List of the components representing each layer. */
	private Layer[] layers;
	/** The ratio CurrentWidth/DefaultWidth for the width of this layer. */
	private double scaleFactor;
	
	/** Basic constructor. */
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
	 */
	public Graphics2D getDrawingSurface(int layer)
	{
		return layers[layer].getDrawingSurface();
	}
	
	/** Return the width of a layer. */
	public int getLayerWidth(int layer)
	{
		// TODO: Give each layer its own adjustable width
		return this.getWidth();
	}
	
	/** Return the height of a layer. */
	public int getLayerHeight(int layer)
	{
		// TODO: Give each layer its own adjustable height
		return this.getHeight();
	}
	
	/** Return the scale factor for the size of the game area. */
	public double getScaleFactor()
	{
		return scaleFactor;
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
		g2.setColor(Color.blue);
		g2.drawRect(0, 0, Gfx.DEFAULT_WINDOW_DIM, Gfx.DEFAULT_WINDOW_DIM);
	}
	
	/** Changes the size of all the layers using newDims as the maximum
	 * allowed dimensions.
	 * @param newDims the maximum dimensions of the layers
	 */
	protected synchronized void adjustSize(Dimension newDims)
	{
		Dimension adjustedDims = new Dimension(0,0);
		// Adjust the new dimensions to make the container have equal sides
		if (newDims.getHeight() <= newDims.getWidth())
		{
			adjustedDims.setSize(newDims.getHeight(), newDims.getHeight());
		}
		else
		{
			adjustedDims.setSize(newDims.getWidth(), newDims.getWidth());
		}
		this.setSize(adjustedDims);
		for (Layer layer : layers)
		{
			layer.adjustSize(adjustedDims);
		}
		updateScaleFactor();
	}
	
	private void updateScaleFactor()
	{
		scaleFactor = (double)getLayerWidth(0)/(double)Gfx.DEFAULT_WINDOW_DIM;
	}
}
