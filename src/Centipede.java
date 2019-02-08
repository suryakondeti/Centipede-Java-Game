import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Centipede extends Enemy
{
	protected Environment world;
	protected int centipedeLenth = 10;
	protected ArrayList<CentipedeComponent> bodyParts = new ArrayList<>();
	protected MOVE_DIRECTION direction = MOVE_DIRECTION.LEFT;

	
	public Centipede(Environment world, HashMap<Point2D.Double, Creature> jobQueue, double xPos, double yPos)
	{
		super(world, jobQueue);
		this.world = world;
		this.jobQueue = jobQueue;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		

		for (int i = 0; i < this.centipedeLenth; i++)
		{
			CentipedeComponent centipedePart = new CentipedeComponent(this.world, this.jobQueue, xPos + i*44, yPos, this.direction, i);
			if (i == 0)
				centipedePart.isHead = true;
			else
				centipedePart.isHead = false;
			this.bodyParts.add(centipedePart);
		}
	}
	
	@Override
	public void timePassed()
	{	
		int count = 0;
		for (int i = 0; i < this.centipedeLenth; i++)
		{
			if (count == this.centipedeLenth - 1)
			{
				this.world.score += 600;
				this.world.victory();
					
			}
			
			if (this.bodyParts.get(i).isDead && (i != this.centipedeLenth - 1))
			{
				this.bodyParts.get(i + 1).isHead = true;
				count++;
			}
		}
	}


	@Override
	public void moveToNewPoint()
	{
		// Nothing here.
	}

	@Override
	public void drawOn(Graphics2D g)
	{
		// Nothing here.	
	}

	@Override
	public void takeDamage()
	{
		// Nothing here.
	}

}
