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
	
	/** Basic constructor. */
	public LayerContainer()
	{
		// Setup the layers
		layers = new Layer[Gfx.NUM_LAYERS];
		for (int i = 0; i < Gfx.NUM_LAYERS; ++i)
		{
			layers[i] = new Layer(Gfx.getFrameWidth(), Gfx.getFrameHeight());
			layers[i].setPreferredSize(
					new Dimension(Gfx.getFrameWidth(), Gfx.getFrameHeight())
					);
			add(layers[i], i);
		}
	}
	
	/** Gets the drawing surface for the specified layer.
	 * @param layer the index of the layer
	 */
	public Graphics2D getDrawingSurface(int layer)
	{
		return layers[layer].getDrawingSurface();
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
	
	protected synchronized void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		// Delegate the rendering to the game state
		gameState.render();
		// Clear the graphics context
		g2.setBackground(Color.black);
		g2.clearRect(0, 0, Gfx.getFrameWidth(), Gfx.getFrameHeight());
		/* Temporary dummy implementation of rendering layers. */
		for (int i = 0; i < Gfx.NUM_LAYERS; ++i)
		{
			layers[i].flip(g2);
		}
	}
}
