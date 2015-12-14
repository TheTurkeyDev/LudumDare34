package turkey.ld34.game.stations;

import turkey.ld34.entities.Entity;
import turkey.ld34.entities.Worker;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.screen.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class CoalDump extends StationBase
{
	public CoalDump(Vector2 location)
	{
		super(location, 64, 64, "stations/coalDump.png", "Coal Dump");
		super.cost = 500;
	}

	public void onVisitByWorker(Entity entity)
	{
		if(this.isBuilt)
			((Worker) entity).giveResource("Coal", 1);
	}

	public void render(float xoff, float yoff)
	{
		super.render(xoff, yoff);
	}

	public void renderGuiInfo(Screen screen)
	{
		Draw2D.drawRect(100, 100, 600, 400, Color.LIGHT_GRAY, true);
		super.renderGuiInfo(screen);
	}
}