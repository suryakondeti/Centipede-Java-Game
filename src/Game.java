import java.io.IOException;

public class Game
{
	Environment world = new Environment();;
	GameComponent gameComponent = new GameComponent(this.world);
	private int height = 940;
	private int width = 900;
	
	public Game() throws IOException
	{
		this.world.setSize(this.width, this.height);
		new MapGenerator();
		new Level(this.world, this.gameComponent);
	}
	
}
