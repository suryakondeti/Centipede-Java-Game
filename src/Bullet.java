import java.awt.Graphics2D;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Bullet extends Creature
{

	public Bullet(Environment world, HashMap<Point2D.Double, Creature> jobQueue, Point2D.Double spawnPoint) throws UnsupportedAudioFileException, LineUnavailableException
	{
		super(world, null);
		this.world = world;
		this.spawnPoint = spawnPoint;
		this.jobQueue = jobQueue;
		this.Factor = 1;
		try 
		{
		    this.image = ImageIO.read(new File("./img/bullet.png"));
		    this.inputStream = AudioSystem.getAudioInputStream(new File("./audio/gunshot.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
        } 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load Audio");
		}
		
	}
	@Override
	public void moveToNewPoint()
	{
		Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x, this.spawnPoint.y - Level.GAP);
		if (this.jobQueue.get(newPoint) != null)
		{
			this.world.removeCreature(this);
			this.jobQueue.get(newPoint).takeDamage();
			System.out.println(this.world.score);
		
		}
		else
			this.spawnPoint = new Point2D.Double(this.spawnPoint.x, this.spawnPoint.y - Level.GAP);
	}


	@Override
	public void timePassed()
	{
		if (this.count % this.Factor == 0)
		{
			moveToNewPoint();	
			this.count = 0;
		}
		
		this.count++;
	}

	@Override
	public void takeDamage()
	{
		// Nothing
	}
	

	@Override
	public void drawOn(Graphics2D g)
	{
		// Nothing
	}


}
