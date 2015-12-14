package turkey.ld34.game.stations;

import turkey.ld34.actions.ActionCollection;
import turkey.ld34.actions.ActionSorting;
import turkey.ld34.entities.Entity;
import turkey.ld34.entities.Worker;
import turkey.ld34.game.GameManager;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.screen.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ScreenProcessing extends StationBase
{
	private int plasticStored = 0;
	private int glassStored = 0;
	private int circuitryStored = 0;
	private int plasticUsedPer = 1;
	private int glassUsedPer = 3;
	private int circuitryUsedPer = 1;

	private int processTimeLeft = -1;
	private int processTime = 600;
	private int screensStored = 0;

	public ScreenProcessing(Vector2 location)
	{
		super(location, 64, 64, "stations/screenProcessing.png", "Screen Processing");
		super.cost = 10;
		super.powerNeeded = 2;
	}

	public void render(float xoff, float yoff)
	{
		super.render(xoff, yoff);
	}

	public void renderGuiInfo(Screen screen)
	{
		Draw2D.drawRect(100, 100, 600, 400, Color.LIGHT_GRAY, true);
		Draw2D.drawString(125, 450, "Plastic Stored (" + this.plasticUsedPer + " per): ", 3, Color.WHITE);
		Draw2D.drawString(125, 400, "Glass Stored (" + this.glassUsedPer + " per): ", 3, Color.WHITE);
		Draw2D.drawString(125, 350, "Circuitry Stored (" + this.circuitryUsedPer + " per): ", 3, Color.WHITE);
		Draw2D.drawString(125, 300, "Screens Stored: ", 3, Color.WHITE);
		Draw2D.drawString(125, 250, "Time Per Screen: ", 3, Color.WHITE);
		Draw2D.drawString(125, 200, "Time Left: ", 3, Color.WHITE);

		String plasticS = "" + this.plasticStored;
		String glassS = "" + this.glassStored;
		String circuitryS = "" + this.circuitryStored;
		String screensS = "" + this.screensStored;
		String timePerS = "" + this.processTime;
		String timeLeftS = "" + this.processTimeLeft;
		Draw2D.drawString(690 - (plasticS.length() * 20), 450, plasticS, 3, Color.WHITE);
		Draw2D.drawString(690 - (glassS.length() * 20), 400, glassS, 3, Color.WHITE);
		Draw2D.drawString(690 - (circuitryS.length() * 20), 350, circuitryS, 3, Color.WHITE);
		Draw2D.drawString(690 - (screensS.length() * 20), 300, screensS, 3, Color.WHITE);
		Draw2D.drawString(690 - (timePerS.length() * 20), 250, timePerS, 3, Color.WHITE);
		Draw2D.drawString(690 - (timeLeftS.length() * 20), 200, timeLeftS, 3, Color.WHITE);
		super.renderGuiInfo(screen);
	}

	public void update()
	{
		super.update();
		if(!this.isBuilt)
			return;

		if(!(GameManager.powerNeededPerTick + this.powerNeeded <= GameManager.powerPerTick))
		{
			this.hasPower = false;
			return;
		}

		this.hasPower = true;
		GameManager.powerNeededPerTick += this.powerNeeded;

		if(this.processTimeLeft >= 0)
		{
			this.processTimeLeft--;
		}
		
		if(this.processTimeLeft == 0)
		{
			this.screensStored++;
		}

		if(this.processTimeLeft <= 0 && this.plasticStored >= this.plasticUsedPer && this.glassStored >= this.glassUsedPer && this.circuitryStored >= this.circuitryUsedPer)
		{
			this.processTimeLeft = this.processTime;
			this.plasticStored -= this.plasticUsedPer;
			this.glassStored -= this.glassUsedPer;
			this.circuitryStored -= this.circuitryUsedPer;
		}
	}

	public void onVisitByWorker(Entity entity)
	{
		if(!this.isBuilt)
			return;

		if(entity.getAction() != null && entity.getAction() instanceof ActionSorting)
		{
			plasticStored += ((Worker) entity).takeResource("Raw Plastic", this.plasticUsedPer);
			glassStored += ((Worker) entity).takeResource("Raw Glass", this.glassUsedPer);
			circuitryStored += ((Worker) entity).takeResource("Raw Circuitry", this.circuitryUsedPer);
		}
		else if(entity.getAction() != null && entity.getAction() instanceof ActionCollection)
		{
			if(this.screensStored > 0)
			{
				this.screensStored--;
				((Worker) entity).giveResource("Screens", 1);
			}
		}
	}
}