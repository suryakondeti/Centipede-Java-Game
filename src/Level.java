import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JFrame;


public class Level implements KeyListener,MouseListener,MouseMotionListener
{
	private int xCoordinate;
	private int yCoordinate;
	protected static final int GAP = 44;
	private Environment world;
	private GameComponent gameComponent;
	private Mario player;
	protected HashMap<Point2D.Double, Creature> jobQueue;
	int numberOfLines = 20;
	
	
	public Level(Environment world, GameComponent gameComponent) 
	{
		this.world = world;
		this.gameComponent = gameComponent;
		this.jobQueue = new HashMap<>();
		this.initializeMap();		
		this.xCoordinate = 0;
		this.yCoordinate = 0;
		try
		{
			FileReader fileReader = new FileReader("./map/level_map.txt");
			String[] textData = OpenFile(fileReader);
			levelCreator(textData, this.world);
		}
		catch (IOException exception)
		{
		}
		
		this.world.add(this.gameComponent);
		this.world.addKeyListener(this);
		this.world.addMouseListener(this);
		this.world.addMouseMotionListener(this);
		this.world.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.world.setVisible(true);
	}
	
	public String[] OpenFile(FileReader fileReader) throws IOException
	{
		BufferedReader textReader = new BufferedReader(fileReader);
		String[] textData = new String[this.numberOfLines];
		for (int i = 0; i < this.numberOfLines; i++)
		{
			textData[i] = textReader.readLine();
		}
		textReader.close();
		return textData;
	}

	public void levelCreator(String[] textData, Environment world)
	{
		for (int i = 0; i < textData.length; i++)
		{
			for (int j = 0; j < textData.length; j++)
			{
				switch(textData[i].charAt(j))
				{
				case 'X':
					this.xCoordinate += GAP;
					break;
				case 'M':
					Mushroom shroom = new Mushroom(this.world, this.jobQueue, this.xCoordinate, this.yCoordinate);
					this.jobQueue.put(new Point2D.Double(this.xCoordinate, this.yCoordinate), shroom);
					this.world.addCreature(shroom);
					this.xCoordinate += GAP;
					break;
				case 'C':
					Centipede centipede = new Centipede(world, this.jobQueue, this.xCoordinate, this.yCoordinate);
					this.world.addCreature(centipede);
					for (CentipedeComponent centipedePart: centipede.bodyParts)
					{
						this.jobQueue.put(centipedePart.spawnPoint, centipedePart);
						this.world.addCreature(centipedePart);
					}
					this.xCoordinate += 5*GAP;
					break;
				case 'H':
					Mario player = new Mario(this.world, this.jobQueue, this.xCoordinate, this.yCoordinate);
					this.player = player;
					this.jobQueue.put(new Point2D.Double(this.xCoordinate, this.yCoordinate), this.player);
					this.world.addCreature(player);
					this.world.addKeyListener(player);
					this.xCoordinate += GAP;
					break;		
				case 'S':
					Spider spider = new Spider(world, this.jobQueue, this.xCoordinate, this.yCoordinate);
					this.jobQueue.put(new Point2D.Double(this.xCoordinate, this.yCoordinate), spider);
					this.world.addCreature(spider);
					this.xCoordinate += GAP;
					break;
				default:
					break;
				}
			}
			this.xCoordinate = 0;
			this.yCoordinate += GAP;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
	}
	
	public void initializeMap()
	{
		int xPlacement = 0;
		int yPlacement = 0;
		for (int i = 0; i < this.numberOfLines; i++)
		{
			for (int j = 0; j < this.numberOfLines; j++)
			{
				this.jobQueue.put(new Point2D.Double(xPlacement, yPlacement), null);
				xPlacement += GAP;
			}
			xPlacement = 0;
			yPlacement += GAP;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
