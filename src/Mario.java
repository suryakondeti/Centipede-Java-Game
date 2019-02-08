
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Mario extends Creature implements MouseListener,MouseMotionListener,KeyListener
{
	protected MOVE_DIRECTION direction = MOVE_DIRECTION.STILL;
	protected Environment world;
	protected int imgCount = 0;
	protected int imgFactor = 24;
	protected int hit_times = 0;

	public Mario(Environment world, HashMap<Point2D.Double, Creature> jobQueue, double xPos, double yPos)
	{
		super(world, jobQueue);
		this.world = world;
		this.jobQueue = jobQueue;
		this.spawnPoint = new Point2D.Double(xPos, yPos);
		this.Factor = 20;
		
		try 
		{
		    this.image1 = ImageIO.read(new File("./img/mario.png"));
		} 
		catch (IOException e) 
		{
			throw new RuntimeException("Could not load image");
		}
		
		this.image = this.image1;
	}

	public void moveToNewPoint()
	{	
		if (this.spawnPoint.x - Level.GAP > -1)
			if (this.direction == MOVE_DIRECTION.LEFT)
			{
				Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x - Level.GAP, this.spawnPoint.y);
				if ((this.jobQueue.get(newPoint) == null) || (this.jobQueue.get(newPoint).getClass() == Mushroom.class))
				{
					this.jobQueue.put(this.spawnPoint, null);
					this.spawnPoint = newPoint;
					this.jobQueue.put(this.spawnPoint, this);
					
				}
				else if (this.jobQueue.get(newPoint) instanceof Enemy)
					this.killHero();
			}
		
		if (this.spawnPoint.x + Level.GAP < 855)
			if (this.direction == MOVE_DIRECTION.RIGHT)
			{
				Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x + Level.GAP, this.spawnPoint.y);
				if ((this.jobQueue.get(newPoint) == null) || (this.jobQueue.get(newPoint).getClass() == Mushroom.class))
				{
					this.jobQueue.put(this.spawnPoint, null);
					this.spawnPoint = newPoint;
					this.jobQueue.put(this.spawnPoint, this);
					
				}
				else if (this.jobQueue.get(newPoint) instanceof Enemy)
					this.killHero();
			}
		
		if (this.spawnPoint.y - Level.GAP > 0)
			if (this.direction == MOVE_DIRECTION.UP)
			{
				Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x, this.spawnPoint.y - Level.GAP);
				if ((this.jobQueue.get(newPoint) == null) || (this.jobQueue.get(newPoint).getClass() == Mushroom.class))
				{
					this.jobQueue.put(this.spawnPoint, null);
					this.spawnPoint = newPoint;
					this.jobQueue.put(this.spawnPoint, this);
				}
				else if (this.jobQueue.get(newPoint) instanceof Enemy)
					this.killHero();
			}
		
		if (this.spawnPoint.y + Level.GAP < 855)
			if (this.direction == MOVE_DIRECTION.DOWN)
			{
				Point2D.Double newPoint = new Point2D.Double(this.spawnPoint.x, this.spawnPoint.y + Level.GAP);
				if ((this.jobQueue.get(newPoint) == null) || (this.jobQueue.get(newPoint).getClass() == Mushroom.class))
				{
					this.jobQueue.put(this.spawnPoint, null);
					this.spawnPoint = newPoint;
					this.jobQueue.put(this.spawnPoint, this);
					
				}
				else if (this.jobQueue.get(newPoint) instanceof Enemy)
					this.killHero();
			}
	}

	@Override
	public void drawOn(Graphics2D g2)
	{
		g2.drawImage(this.getImage(), null, (int) this.getSpawnPoint().getX(),
				(int) this.getSpawnPoint().getY());
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch(KeyEvent.getKeyText(e.getKeyCode()))
		{
		case("Up"):
			this.direction = MOVE_DIRECTION.UP;
			break;
		case("Down"):
			this.direction = MOVE_DIRECTION.DOWN;
			break;
		case("Left"):
			this.direction = MOVE_DIRECTION.LEFT;
			break;
		case("Right"):
			this.direction = MOVE_DIRECTION.RIGHT;
			break;
		case("Space"):
			this.direction = MOVE_DIRECTION.STILL;
			Bullet bullet;
			try {
				bullet = new Bullet(this.world, this.jobQueue, this.spawnPoint);
				this.world.addCreature(bullet);
			} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;	
		default:
			break;
		}
		moveToNewPoint();	
	}
	
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void timePassed()
	{		
		if (this.jobQueue.get(this.spawnPoint) instanceof Enemy)
			this.killHero();
	}
	
	public void killHero()
	{
		this.world.defeat();
	}
	
	@Override
	public void takeDamage()
	{
		if (this.hit_times != this.world.lives)
		{
			this.hit_times++;
			System.out.println("Hit this time: ");
			System.out.print(this.hit_times);
			Mario respawn_mario = new Mario(this.world, this.jobQueue, 440,836);
			this.world.addCreature(respawn_mario);
		}
		else {
			this.isDead = true;
			this.world.score += 600;
			if (this.jobQueue.get(this.spawnPoint) == this)
				this.jobQueue.put(this.spawnPoint, null);	
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
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

	
}

