package turkey.ld34.game.stations;

import turkey.ld34.actions.ActionCollection;
import turkey.ld34.actions.ActionShip;
import turkey.ld34.entities.Entity;
import turkey.ld34.entities.Worker;
import turkey.ld34.game.GameManager;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.screen.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class PackagingStation extends StationBase
{
	private int screensStored = 0;
	private int casesStored = 0;
	private int electronicsStored = 0;
	private int screenUsedPer = 1;
	private int casesUsedPer = 1;
	private int electronicsUsedPer = 1;

	private int processTimeLeft = -1;
	private int processTime = 600;
	private int productsStored = 0;

	public PackagingStation(Vector2 location)
	{
		super(location, 64, 64, "stations/packaging.png", "Packaging");
		super.cost = 10;
		super.powerNeeded = 3;
	}

	public void render(float xoff, float yoff)
	{
		super.render(xoff, yoff);
	}

	public void renderGuiInfo(Screen screen)
	{
		Draw2D.drawRect(100, 100, 600, 400, Color.LIGHT_GRAY, true);
		Draw2D.drawString(125, 450, "Screens Stored (" + this.screenUsedPer + " per): ", 3, Color.WHITE);
		Draw2D.drawString(125, 400, "Cases Stored (" + this.casesUsedPer + " per): ", 3, Color.WHITE);
		Draw2D.drawString(125, 350, "Electronics Stored (" + this.electronicsUsedPer + " per): ", 3, Color.WHITE);
		Draw2D.drawString(125, 300, "Packages Stored: ", 3, Color.WHITE);
		Draw2D.drawString(125, 250, "Time Per Package: ", 3, Color.WHITE);
		Draw2D.drawString(125, 200, "Time Left: ", 3, Color.WHITE);

		String plasticS = "" + this.screensStored;
		String metalS = "" + this.casesStored;
		String circuitryS = "" + this.electronicsStored;
		String electronicsS = "" + this.productsStored;
		String timePerS = "" + this.processTime;
		String timeLeftS = "" + this.processTimeLeft;
		Draw2D.drawString(690 - (plasticS.length() * 20), 450, plasticS, 3, Color.WHITE);
		Draw2D.drawString(690 - (metalS.length() * 20), 400, metalS, 3, Color.WHITE);
		Draw2D.drawString(690 - (circuitryS.length() * 20), 350, circuitryS, 3, Color.WHITE);
		Draw2D.drawString(690 - (electronicsS.length() * 20), 300, electronicsS, 3, Color.WHITE);
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
			this.productsStored++;
		}

		if(this.processTimeLeft <= 0 && this.screensStored >= this.screenUsedPer && this.casesStored >= this.casesUsedPer && this.electronicsStored >= this.electronicsUsedPer)
		{
			this.processTimeLeft = this.processTime;
			this.screensStored -= this.screenUsedPer;
			this.casesStored -= this.casesUsedPer;
			this.electronicsStored -= this.electronicsUsedPer;
		}
	}

	public void onVisitByWorker(Entity entity)
	{
		if(!this.isBuilt)
			return;
		
		if(entity.getAction() != null && entity.getAction() instanceof ActionCollection)
		{
			this.screensStored += ((Worker) entity).takeResource("Screens", this.screenUsedPer);
			this.casesStored += ((Worker) entity).takeResource("Cases", this.casesUsedPer);
			this.electronicsStored += ((Worker) entity).takeResource("Electronics", this.electronicsUsedPer);
		}
		else if(entity.getAction() != null && entity.getAction() instanceof ActionShip)
		{
			if(this.productsStored > 0)
			{
				this.productsStored -= 1;
				((Worker) entity).giveResource("Packages", 1);
			}
		}

	}

	public int getProductsStored()
	{
		return this.productsStored;
	}
}