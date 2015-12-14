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

public class CaseProcessing extends StationBase
{
	private int plasticStored = 0;
	private int metalStored = 0;
	private int plasticUsedPer = 3;
	private int metalUsedPer = 1;

	private int processTimeLeft = -1;
	private int processTime = 600;
	private int casesStored = 0;

	public CaseProcessing(Vector2 location)
	{
		super(location, 64, 64, "stations/caseProcessing.png", "Case Processing");
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
		Draw2D.drawString(125, 400, "Metal Stored (" + this.metalUsedPer + " per): ", 3, Color.WHITE);
		Draw2D.drawString(125, 350, "Cases Stored: ", 3, Color.WHITE);
		Draw2D.drawString(125, 300, "Time Per Screen: ", 3, Color.WHITE);
		Draw2D.drawString(125, 250, "Time Left: ", 3, Color.WHITE);

		String plasticS = "" + this.plasticStored;
		String metalS = "" + this.metalStored;
		String casesS = "" + this.casesStored;
		String timePerS = "" + this.processTime;
		String timeLeftS = "" + this.processTimeLeft;
		Draw2D.drawString(690 - (plasticS.length() * 20), 450, plasticS, 3, Color.WHITE);
		Draw2D.drawString(690 - (metalS.length() * 20), 400, metalS, 3, Color.WHITE);
		Draw2D.drawString(690 - (casesS.length() * 20), 350, casesS, 3, Color.WHITE);
		Draw2D.drawString(690 - (timePerS.length() * 20), 300, timePerS, 3, Color.WHITE);
		Draw2D.drawString(690 - (timeLeftS.length() * 20), 250, timeLeftS, 3, Color.WHITE);
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
			this.casesStored++;
		}

		if(this.processTimeLeft <= 0 && this.plasticStored >= this.plasticUsedPer && this.metalStored >= this.metalUsedPer)
		{
			this.processTimeLeft = this.processTime;
			this.plasticStored -= this.plasticUsedPer;
			this.metalStored -= this.metalUsedPer;
		}
	}

	public void onVisitByWorker(Entity entity)
	{
		if(!this.isBuilt)
			return;
		if(entity.getAction() != null && entity.getAction() instanceof ActionSorting)
		{
			plasticStored += ((Worker) entity).takeResource("Raw Plastic", this.plasticUsedPer);
			metalStored += ((Worker) entity).takeResource("Raw Metal", this.metalUsedPer);
		}
		else if(entity.getAction() != null && entity.getAction() instanceof ActionCollection)
		{
			if(this.casesStored > 0)
			{
				this.casesStored--;
				((Worker) entity).giveResource("Cases", 1);
			}
		}

	}

	public int getCasesStored()
	{
		return this.casesStored;
	}

}