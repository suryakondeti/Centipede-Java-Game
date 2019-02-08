import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;


public class Environment extends JFrame implements RefreshRate
{

	private static final long serialVersionUID = 1L;
	protected static final long UPDATE_INTERVAL = 10;
	protected List<Creature> creatures = new ArrayList<>();
	protected List<Creature> creaturesToRemove = new ArrayList<>();
	protected List<Creature> creaturesToAdd = new ArrayList<>();
	protected boolean reloader = false;
	protected boolean defeated = false;
	protected boolean victorious = false;
	protected int score = 0;
	protected int lives = 3;
	public Environment(){}

	public synchronized void addCreature(Creature creature) 
	{
		this.creaturesToAdd.add(creature);
	}

	public synchronized void removeCreature(Creature creature)
	{
		this.creaturesToRemove.add(creature);
	}
	
	public List<Creature> getDrawableParts()
	{
		return this.creatures;
	}

	public void clear()
	{
		this.creaturesToAdd.clear();
		this.creaturesToRemove.clear();
		this.creatures.clear();
	}
	
	public void defeat()
	{
		clear();
		this.defeated = true;
	}
	
	public void victory()
	{
		clear();
		this.victorious = true;
	}
	@Override
	public void timePassed()
	{
		this.creatures.removeAll(this.creaturesToRemove);
		this.creatures.addAll(this.creaturesToAdd);
		this.creaturesToRemove.clear();
		this.creaturesToAdd.clear();		
	}
}
