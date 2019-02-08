import java.awt.Graphics2D;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;


public class Spider extends Enemy
{
	protected MOVE_DIRECTION xDirection;
	protected MOVE_DIRECTION yDirection;
	protected int hit_times = 0;
	public Spider(Environment world, HashMap<Point2D.Double, Creature> jobQueue, double xPos, double yPos)
	{
		super(world, jobQueue);
		this.world = world;
		this.jobQueue = jobQueue;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		this.xDirection= MOVE_DIRECTION.LEFT;
		this.yDirection= MOVE_DIRECTION.UP;
		this.Factor = 20;
		try 
		{
			this.image1 = ImageIO.read(new File("./img/spider.png"));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
	}

	@Override
	public void moveToNewPoint()
	{
		if (this.spawnPoint.x<=0)
		{
			this.xDirection=MOVE_DIRECTION.RIGHT;
		}
		
		if (this.spawnPoint.x>=835)
		{
			this.xDirection=MOVE_DIRECTION.LEFT;
		}
		
		if (this.spawnPoint.y<=700)
		{
			this.yDirection=MOVE_DIRECTION.DOWN;
		}
		
		if (this.spawnPoint.y>=835)
		{
			this.yDirection=MOVE_DIRECTION.UP;
		}
		
		if (this.xDirection== MOVE_DIRECTION.LEFT && this.yDirection== MOVE_DIRECTION.UP)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x -Level.GAP, this.spawnPoint.y - Level.GAP);
			
			if (this.jobQueue.get(newPoint) != null)
			{
				if (this.jobQueue.get(newPoint).getClass() == Mario.class)
				{
					this.world.removeCreature(this.jobQueue.get(newPoint)); 
					this.jobQueue.get(newPoint).takeDamage();
				}	
			} 
			else
			{
				this.jobQueue.put(newPoint, this);
			}
			
			if (this.jobQueue.get(this.spawnPoint) == this)
			{
				this.jobQueue.put(this.spawnPoint, null);
			}
			
			this.spawnPoint = newPoint;
		}
		
		if (this.xDirection== MOVE_DIRECTION.RIGHT && this.yDirection== MOVE_DIRECTION.UP)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x +Level.GAP, this.spawnPoint.y-Level.GAP);
			
			if (this.jobQueue.get(newPoint) != null)
			{
				if (this.jobQueue.get(newPoint).getClass() == Mario.class)
				{
					this.world.removeCreature(this.jobQueue.get(newPoint));
					this.jobQueue.get(newPoint).takeDamage();
				}	
			} 
			else
			{
				this.jobQueue.put(newPoint, this);
			}
			
			if (this.jobQueue.get(this.spawnPoint) == this)
			{
				this.jobQueue.put(this.spawnPoint, null);
			}
			
			this.spawnPoint = newPoint;
		}
		
		if (this.xDirection== MOVE_DIRECTION.LEFT && this.yDirection== MOVE_DIRECTION.DOWN)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x -Level.GAP, this.spawnPoint.y+Level.GAP);
			
			if (this.jobQueue.get(newPoint) != null)
			{
				if (this.jobQueue.get(newPoint).getClass() == Mario.class)
				{
					this.world.removeCreature(this.jobQueue.get(newPoint));
					this.jobQueue.get(newPoint).takeDamage();
				}	
			} 
			else
			{
				this.jobQueue.put(newPoint, this);
			}
			
			if (this.jobQueue.get(this.spawnPoint) == this)
			{
				this.jobQueue.put(this.spawnPoint, null);
			}
			
			this.spawnPoint = newPoint;
		}
		
		if (this.xDirection== MOVE_DIRECTION.RIGHT && this.yDirection== MOVE_DIRECTION.DOWN)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x +Level.GAP, this.spawnPoint.y+Level.GAP);
			
			if (this.jobQueue.get(newPoint) != null)
			{
				if (this.jobQueue.get(newPoint).getClass() == Mario.class)
				{
					this.world.removeCreature(this.jobQueue.get(newPoint));
					this.jobQueue.get(newPoint).takeDamage();
				}	
			} 
			else
			{
				this.jobQueue.put(newPoint, this);
			}
			
			if (this.jobQueue.get(this.spawnPoint) == this)
			{
				this.jobQueue.put(this.spawnPoint, null);
			}
			
			this.spawnPoint = newPoint;
		}
	}

	@Override
	public void timePassed()
	{
		if (this.isDead)
		{
			this.deathCount++;
			this.image = this.deathImage;
			this.xDirection  = MOVE_DIRECTION.STILL;
			this.yDirection  = MOVE_DIRECTION.STILL;
			if (this.deathCount % 20 == 0)
			{
				this.world.removeCreature(this);
			}
		}
		else
		{
			if (this.count % this.Factor == 0) 
			{
				moveToNewPoint();
				this.count = 0;
			}
			
			this.image = this.image1;
			this.count++;
		}
	}

	@Override
	public void drawOn(Graphics2D g)
	{		
	}

	@Override
	public void takeDamage()
	{
		if (this.hit_times == 0)
		{
			this.hit_times++;
			this.world.score += 100;
		}
		else {
			this.isDead = true;
			this.world.score += 600;
			if (this.jobQueue.get(this.spawnPoint) == this)
				this.jobQueue.put(this.spawnPoint, null);	
		}
	}

}
