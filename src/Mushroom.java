
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Mushroom extends Creature
{
	protected int damageState;
	public Mushroom(Environment world, HashMap<Point2D.Double, Creature> jobQueue, double xPos, double yPos)
	{
		super(world, jobQueue);
		this.damageState = 0;
		this.jobQueue = jobQueue;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		this.Factor = 20;
		try 
		{
		    this.image1 = ImageIO.read(new File("./img/mushroom.png"));
		    this.deathImage = null;
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
	}
	@Override
	public void takeDamage()
	{
		if (this.damageState == 0)
		{
			this.damageState++;
			this.world.score += 1;
		}
		else if (this.damageState == 1)
		{
			this.damageState++;
			this.world.score += 1;
		}

		else if (this.damageState == 2)
		{
			this.isDead = true;
			this.jobQueue.put(this.spawnPoint, null);
			this.world.score += 5;
		}
	}

	@Override
	public void moveToNewPoint()
	{
	}

	@Override
	public void timePassed()
	{
		if (this.count % this.Factor == 0) 
		{
			this.count = 0;
		}
		if (this.damageState == 0)
		{
			this.image = this.image1;
		}
		
		if (this.isDead)
		{
			this.deathCount++;
			this.image = this.deathImage;
			if (this.deathCount % 20 == 0)
			{
				this.world.removeCreature(this);
			}
		}	
		this.count++;
	}


	@Override
	public void drawOn(Graphics2D g)
	{		
	}

}
