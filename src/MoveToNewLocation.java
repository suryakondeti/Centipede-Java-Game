
import java.awt.geom.Point2D;

public interface MoveToNewLocation {
	
	public enum MOVE_DIRECTION
	{
		UP, DOWN, LEFT, RIGHT, STILL
	}
	
	void moveToNewPoint();

	Point2D.Double getSpawnPoint();

}