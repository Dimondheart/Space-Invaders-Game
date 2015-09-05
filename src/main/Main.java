package main;

public class Main
{
	// The game session object
	public static Game game;
	
	public static void main(String[] args)
	{
		game = new Game();
		game.start();
	}
}
