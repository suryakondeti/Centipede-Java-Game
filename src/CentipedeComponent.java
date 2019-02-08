import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class CentipedeComponent extends Enemy
{
	protected Environment world;
	protected int partNumber;
	protected MOVE_DIRECTION newDirection;
	protected MOVE_DIRECTION oldDirection;
	protected boolean isHead;
	protected int imgCount = 0;
	protected int imgFactor = 80;
	protected int hit_times = 0;
	protected BufferedImage bodyImage;
	protected BufferedImage headImageLeft;
	protected BufferedImage headImageRight;
	protected BufferedImage headImageUp;
	protected BufferedImage headImageDown;
	
	public CentipedeComponent(Environment world,
			HashMap<Point2D.Double, Creature> jobQueue, double xPos,
			double yPos, MOVE_DIRECTION direction, int partNumber)
	{
		super(world, jobQueue);
		this.world = world;
		this.partNumber = partNumber;
		this.newDirection = direction;
		this.oldDirection = direction;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		this.jobQueue = jobQueue;
		this.Factor = 10;
		this.hit_times = 0;
		
		try 
		{
			this.headImageLeft = ImageIO.read(new File("./img/headLeft.png"));
			this.headImageRight = ImageIO.read(new File("./img/headRight.png"));
			this.headImageUp = ImageIO.read(new File("./img/headUp.png"));
			this.headImageDown = ImageIO.read(new File("./img/headDown.png"));	
			this.bodyImage = ImageIO.read(new File("./img/body.png"));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Image Not Found");
		}
	}

	@Override
	public void moveToNewPoint()
	{
		if (this.newDirection == MOVE_DIRECTION.LEFT)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x - Level.GAP, this.spawnPoint.y);
			
			if (this.jobQueue.get(newPoint) != null && this.jobQueue.get(newPoint).getClass() != CentipedeComponent.class)
			{
				if (this.jobQueue.get(newPoint).getClass() == Mushroom.class)
				{
					this.oldDirection = MOVE_DIRECTION.LEFT;
					this.newDirection = MOVE_DIRECTION.DOWN;
				} 
				else if (this.jobQueue.get(newPoint).getClass() == Mario.class)
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
			
			if (newPoint.x < -1)
			{
				this.oldDirection = MOVE_DIRECTION.LEFT;
				this.newDirection = MOVE_DIRECTION.DOWN;
			}
			else
			{
				if (this.jobQueue.get(newPoint).getClass() != Mushroom.class)
					this.spawnPoint = newPoint;
			}
		}

		if (this.newDirection == MOVE_DIRECTION.RIGHT)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x
					+ Level.GAP, this.spawnPoint.y);
			if (this.jobQueue.get(newPoint) != null &&
					this.jobQueue.get(newPoint).getClass() != CentipedeComponent.class)
			{
				if (this.jobQueue.get(newPoint).getClass() == Mushroom.class)
				{
					this.oldDirection = MOVE_DIRECTION.RIGHT;
					this.newDirection = MOVE_DIRECTION.DOWN;
				} 
				else if (this.jobQueue.get(newPoint).getClass() == Mario.class)
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
			
			if (newPoint.x > 845)
			{
				this.oldDirection = MOVE_DIRECTION.RIGHT;
				this.newDirection = MOVE_DIRECTION.DOWN;
			}
			else
			{
				if (this.jobQueue.get(newPoint).getClass() != Mushroom.class)
					this.spawnPoint = newPoint;
			}
		}

		if (this.newDirection == MOVE_DIRECTION.DOWN)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x,
					this.spawnPoint.y + Level.GAP);
			
			if (this.jobQueue.get(newPoint) != null &&
					this.jobQueue.get(newPoint).getClass() != CentipedeComponent.class)
			{
				if (this.jobQueue.get(newPoint).getClass() == Mushroom.class)
				{					
					if (this.oldDirection == MOVE_DIRECTION.LEFT)
						this.newDirection = MOVE_DIRECTION.RIGHT;
					else if (this.oldDirection == MOVE_DIRECTION.RIGHT)
						this.newDirection = MOVE_DIRECTION.LEFT;
					this.oldDirection = MOVE_DIRECTION.DOWN;
				} 
				else if (this.jobQueue.get(newPoint).getClass() == Mario.class)
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
			
			if (newPoint.y > 700)
			{
				this.newDirection = MOVE_DIRECTION.UP;
			} 
			else
			{
				if (this.oldDirection == MOVE_DIRECTION.RIGHT)
					this.newDirection = MOVE_DIRECTION.LEFT;
				else if (this.oldDirection == MOVE_DIRECTION.LEFT)
					this.newDirection = MOVE_DIRECTION.RIGHT;
				this.oldDirection = MOVE_DIRECTION.DOWN;
				
				if (this.jobQueue.get(newPoint).getClass() != Mushroom.class)
					this.spawnPoint = newPoint;
			}
		}

		if (this.newDirection == MOVE_DIRECTION.UP)
		{
			Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x,
					this.spawnPoint.y - Level.GAP);
			
			if (this.jobQueue.get(newPoint) != null &&
					this.jobQueue.get(newPoint).getClass() != CentipedeComponent.class)
			{
				if (this.jobQueue.get(newPoint).getClass() == Mushroom.class)
				{ 
					if (this.oldDirection == MOVE_DIRECTION.LEFT)
						this.newDirection = MOVE_DIRECTION.RIGHT;
					else if (this.oldDirection == MOVE_DIRECTION.RIGHT)
						this.newDirection = MOVE_DIRECTION.RIGHT;
					this.oldDirection = MOVE_DIRECTION.UP;
				} 
				else if (this.jobQueue.get(newPoint).getClass() == Mario.class)
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
		
			if (this.oldDirection == MOVE_DIRECTION.RIGHT)
				this.newDirection = MOVE_DIRECTION.LEFT;	
			else if (this.oldDirection == MOVE_DIRECTION.LEFT)
				this.newDirection = MOVE_DIRECTION.RIGHT;
			this.oldDirection = MOVE_DIRECTION.DOWN;
			
			if (this.jobQueue.get(newPoint).getClass() != Mushroom.class)
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
			this.newDirection  = MOVE_DIRECTION.STILL;
			this.oldDirection  = MOVE_DIRECTION.STILL;
			if (this.deathCount % 20 == 0)
			{
				this.world.removeCreature(this);
				this.jobQueue.put(this.spawnPoint, null); 
			}
		}
		else
		{
			if (this.count % this.Factor == 0)
			{
				moveToNewPoint();
				this.count = 0;
			}
		
			
			if (this.imgCount % this.imgFactor == 0)
			{
				this.imgCount = 0;
			}
				 
			if (this.isHead)
			{
				if (this.newDirection == MOVE_DIRECTION.LEFT)
				{
					this.image = this.headImageLeft;
				}
				
				if (this.newDirection == MOVE_DIRECTION.RIGHT)
				{
					this.image = this.headImageRight;
				}
				
				if (this.newDirection == MOVE_DIRECTION.UP)
				{
					this.image = this.headImageUp;
				}
				
				if (this.newDirection == MOVE_DIRECTION.DOWN)
				{
					this.image = this.headImageDown;
				}
			}
			else
			{
				this.image = this.bodyImage;
			}
			this.count++;
			this.imgCount++;
		}
	}

	@Override
	public void takeDamage()
	{
		if(this.hit_times == 0)
		{
			this.hit_times++;
			this.world.score += 2;
		}
		else if(this.hit_times == 1)
		{
			this.isDead = true;
			this.world.score += 5;
			this.world.removeCreature(this);
		}
	}

	@Override
	public void drawOn(Graphics2D g)
	{
	}

}
