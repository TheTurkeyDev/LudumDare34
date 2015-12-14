package turkey.ld34.game.stations;

import turkey.ld34.entities.Entity;
import turkey.ld34.entities.Worker;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.screen.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Generator extends StationBase
{
	private int coalStored = 0;
	private int coalburnLeft = 0;
	private int coalburnTime = 600;
	public int energyPerTick = 10;
	private boolean isBurning = false;

	public Generator(Vector2 position)
	{
		super(position, 64, 64, "stations/generator.png", "Generator");
		super.cost = 500;
	}

	public void render(float xoff, float yoff)
	{
		super.render(xoff, yoff);
	}

	public void renderGuiInfo(Screen screen)
	{
		Draw2D.drawRect(100, 100, 600, 400, Color.LIGHT_GRAY, true);
		Draw2D.drawString(125, 450, "Coal Stored: ", 3, Color.WHITE);
		Draw2D.drawString(125, 400, "Energy Provided: ", 3, Color.WHITE);
		String energyS = "" + this.energyPerTick;
		String coalS = "" + this.coalStored;
		Draw2D.drawString(690 - (coalS.length() * 20), 450, "" + coalStored, 3, Color.WHITE);
		Draw2D.drawString(690 - (energyS.length() * 20), 400, "" + this.energyPerTick, 3, Color.WHITE);
		super.renderGuiInfo(screen);
	}

	public void update()
	{
		super.update();
		if(!this.isBuilt)
			return;
		if(this.coalburnLeft != 0)
		{
			this.coalburnLeft--;
			isBurning = true;
		}

		if(this.coalburnLeft == 0 && this.coalStored > 0)
		{
			this.coalburnLeft = this.coalburnTime;
			this.coalStored--;
			isBurning = true;
		}

		if(this.coalStored == 0 && this.coalburnLeft == 0)
			isBurning = false;
	}

	public void onVisitByWorker(Entity entity)
	{
		if(!this.isBuilt)
			return;
		coalStored += ((Worker) entity).takeResource("Coal", 10);
	}

	public boolean isBurning()
	{
		return this.isBurning;
	}
}