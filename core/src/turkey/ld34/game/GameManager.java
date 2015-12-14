package turkey.ld34.game;

import java.util.ArrayList;
import java.util.List;

import turkey.ld34.entities.Entity;
import turkey.ld34.entities.Worker;
import turkey.ld34.game.stations.Generator;
import turkey.ld34.game.stations.StationBase;

import com.badlogic.gdx.math.Vector2;

public class GameManager
{
	public static List<Entity> entities = new ArrayList<Entity>();

	public static int powerPerTick = 0;
	public static int powerNeededPerTick = 0;

	public static Vector2 offset = new Vector2(0, 0);

	public static void gameManagerInit()
	{

	}

	public static void update()
	{
		powerPerTick = 0;
		powerNeededPerTick = 0;

		for(Entity ent : entities)
			if(ent instanceof Generator)
				powerPerTick += ((Generator) ent).isBurning() ? ((Generator) ent).energyPerTick : 0;

		for(int i = entities.size() - 1; i >= 0; i--)
		{
			Entity ent = entities.get(i);
			ent.update();
		}
	}

	public static void render(Vector2 tempOffset)
	{
		for(Entity ent : entities)
		{
			if(ent instanceof StationBase)
				ent.render(offset.x + tempOffset.x, offset.y + tempOffset.y);
		}
		for(Entity ent : entities)
			if(!(ent instanceof StationBase))
				ent.render(offset.x + tempOffset.x, offset.y + tempOffset.y);
	}

	public static Entity getEntityAt(Vector2 loc)
	{
		loc.sub(offset);
		Entity toReturn = null;
		for(Entity ent : entities)
		{
			if(ent.isLocationWithin(loc))
			{
				if(toReturn == null || ent instanceof Worker)
					toReturn = ent;
			}
		}
		return toReturn;
	}
}