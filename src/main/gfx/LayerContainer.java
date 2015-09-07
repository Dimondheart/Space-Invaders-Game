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
			layers[i] = new Layer(Gfx.getRenderAreaWidth(), Gfx.getRenderAreaHeight());
			layers[i].setPreferredSize(
					new Dimension(Gfx.getRenderAreaWidth(), Gfx.getRenderAreaHeight())
					);
			add(layers[i], i);
		}
	}
	
	/** Gets the drawing surface for the specified layer. */
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
	
	protected synchronized void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		// Delegate the rendering to the game state
		gameState.render();
		// Clear the graphics context
		g2.setBackground(Color.black);
		g2.clearRect(0, 0, Gfx.getRenderAreaWidth(), Gfx.getRenderAreaHeight());
		/* Temporary dummy implementation of rendering layers. */
		for (int i = 0; i < Gfx.NUM_LAYERS; ++i)
		{
			layers[i].flip(g2);
		}
	}
}
