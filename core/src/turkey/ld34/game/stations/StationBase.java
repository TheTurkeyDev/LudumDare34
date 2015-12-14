package turkey.ld34.game.stations;

import turkey.ld34.entities.Entity;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.screen.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class StationBase extends Entity
{
	public int cost = 0;
	private String stationName = "";
	protected int buildDuration = 1800;
	protected int buildTimeLeft = 1800;
	protected boolean hasPower = false;
	protected boolean isBuilt = false;
	protected boolean isBuidling = false;
	
	protected int powerNeeded = 0;

	public StationBase(Vector2 location, int width, int height, String texturePath, String name)
	{
		super(texturePath);
		super.width = width;
		super.height = height;
		super.position = location;
		this.stationName = name;
	}

	public void update()
	{
		if(this.isBuidling && !this.isBuilt)
		{
			if(this.buildTimeLeft == 0)
			{
				this.isBuilt = true;
				this.isBuidling = false;
				return;
			}
			this.buildTimeLeft--;
		}
	}

	public void render(float xoff, float yoff)
	{
		if(this.isSelected)
			Draw2D.drawRect(position.x - 3 + xoff, position.y - 3 + yoff, width + 6, height + 6, Color.YELLOW, true);
		super.render(xoff, yoff);
	}

	public void renderGuiInfo(Screen screen)
	{
		Draw2D.drawString(400 - (this.stationName.length() * 10), 550, stationName, 3, Color.WHITE);
		Draw2D.drawString(125, 500, "Is Running: ", 3, Color.WHITE);
		String isBuiltS = "" + (this.isBuilt && (this.hasPower || this.powerNeeded == 0));
		if(!this.isBuilt)
			isBuiltS += " (" + (this.buildTimeLeft / 60) + " sec)";
		if(!this.hasPower && this.powerNeeded != 0)
			isBuiltS += " (No Power)";
		Draw2D.drawString(690 - (isBuiltS.length() * 19), 500, isBuiltS, 3, Color.WHITE);
	}

	public void startBuilding()
	{
		this.isBuidling = true;
	}

	public void stopBuilding()
	{
		this.isBuidling = false;
	}

	public void onVisitByWorker(Entity entity)
	{

	}

	public boolean isBuilt()
	{
		return this.isBuilt;
	}

	public boolean isBeingBuilt()
	{
		return this.isBuidling;
	}

	public void setBuilt()
	{
		this.isBuilt = true;
	}
}