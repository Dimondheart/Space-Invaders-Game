package main.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLayeredPane;

import main.gamestate.GameState;

/** Handles the layers. Basically a thin layer over a JLayeredPane that
 * works with the rendering delegation used by this program.
 */
public class LayerContainer extends JLayeredPane
{
	private static final long serialVersionUID = 1L;
	/** Number of layers to create in the primary window. */
	public static final int NUM_LAYERS = 6;
	/** Layers in the layered pane.  Used to organize graphics without
	 * having to draw them in a specific order.
	 */
	private Layer[] layers;
	/** Reference to the game state to which rendering is delegated. */
	private GameState gameState;
	
	// TODO: Attempt to decouple this (remove the GameState argument)
	/** Constructor, sets the reference to the current game state.
	 * @param newGameState the new game state
	 */
	public LayerContainer(GameState newGameState)
	{
		// Setup the layers
		layers = new Layer[NUM_LAYERS];
		for (int i = 0; i < NUM_LAYERS; ++i)
		{
			layers[i] = new Layer(Gfx.getRenderAreaWidth(), Gfx.getRenderAreaHeight());
			layers[i].setPreferredSize(
					new Dimension(Gfx.getRenderAreaWidth(), Gfx.getRenderAreaHeight())
					);
			add(layers[i], i);
		}
		updateGameState(newGameState);
	}
	
	public synchronized void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		// Delegate the rendering to the game state
		gameState.render();
		// Clear the graphics context
		g2.setBackground(Color.black);
		g2.clearRect(0, 0, Gfx.getRenderAreaWidth(), Gfx.getRenderAreaHeight());
	}
	
	/** Updates the game state that render is called from.
	 * @param newGameState the new game state
	 */
	public synchronized void updateGameState(GameState newGameState)
	{
		gameState = newGameState;
	}

	/** Gets the drawing surface for the specified layer.
	 * @param layer the index of the layer (bottom is 0)
	 * @return (Graphics2D) The drawing surface
	 */
	public Graphics2D getDrawingSurface(int layer)
	{
		return layers[layer].getDrawingSurface();
	}
}
