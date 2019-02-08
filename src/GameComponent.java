import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class GameComponent extends JComponent implements RefreshRate
{
	protected static final long serialVersionUID = -3783350103861335960L;
	protected static final int FPS = 30;
	protected static final long REPAINT_INTERVAL = 1000 / FPS;
	protected Environment world;
	
	public GameComponent(Environment world)
	{
		this.world = world;
		setPreferredSize(world.getSize());
		setMaximumSize(world.getSize());	
		Runnable repainter = new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
				{
					try {
						Thread.sleep(REPAINT_INTERVAL);
					} catch (InterruptedException e1) {
					}
					if (world.reloader == false)
					{
						try 
						{
							timePassed();
							world.timePassed();
							repaint();
						}
						catch (ConcurrentModificationException e)
						{
						}
					}
				}	
			}
		};
		new Thread(repainter).start();
	}

	@Override
	public void timePassed()
	{
		for (Creature creature: this.world.creatures)
		{
			creature.timePassed();
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (this.world.defeated == true)
		{
			try
			{
				BufferedImage image = ImageIO.read(new File("./img/gameover.jpg"));
				g.drawImage(image, 0, 0, this);	
			} 
			catch (IOException exception)
			{
			}
		}
		else if (this.world.victorious == true)
		{
			try
			{
				BufferedImage image = ImageIO.read(new File("./img/youwin.jpg"));
				g.drawImage(image, 0, 0, this);
					
			} 
			catch (IOException exception)
			{
			}
		}
		else
		{	
			try
			{
				List<Creature> drawableParts = this.world.getDrawableParts();
				for (Creature creature : drawableParts)
				{
					drawDrawable(g2, creature);
				}
			}
			catch (ConcurrentModificationException e)
			{
			}
		}
	}

	private static void drawDrawable(Graphics2D g2, Drawable d)
	{
		g2.drawImage(d.getImage(), null, (int) d.getSpawnPoint().x,
				(int) d.getSpawnPoint().y);
	}

}