import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;

public abstract class Creature implements Drawable, MoveToNewLocation, RefreshRate
{
	protected Point2D.Double spawnPoint;
	protected Environment world;
	protected BufferedImage image;
	protected BufferedImage image1;
	protected BufferedImage image2;
	protected BufferedImage deathImage;
	protected HashMap<Point2D.Double, Creature> jobQueue;
	public boolean isDead = false;
	public int count = 0;
	public int deathCount = 0;
	public int Factor;
	protected AudioInputStream inputStream;
	
	public Creature(Environment world, HashMap<Point2D.Double, Creature> jobQueue)
	{
		this.world = world;
	}
	
	@Override
	public BufferedImage getImage()
	{
		return this.image;
	}
	@Override
	public abstract void moveToNewPoint();

	@Override
	public Point2D.Double getSpawnPoint()
	{
		return this.spawnPoint;
	}
	@Override
	public abstract void timePassed();
	public abstract void takeDamage();
}
