import java.awt.geom.Point2D.Double;
import java.util.HashMap;

public abstract class Enemy extends Creature
{
	public Enemy(Environment world, HashMap<Double, Creature> occupationMap)
	{
		super(world, occupationMap);
	}
}
