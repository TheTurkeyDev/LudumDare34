package turkey.ld34.game.stations;

import java.util.ArrayList;
import java.util.List;

import turkey.ld34.entities.Entity;
import turkey.ld34.entities.Worker;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.gui.GuiButton;
import turkey.ld34.gui.GuiComponent;
import turkey.ld34.screen.GameScreen;
import turkey.ld34.screen.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class RawMaterials extends StationBase
{
	private List<GuiComponent> components = new ArrayList<GuiComponent>();
	
	private Texture plasticIcon = new Texture("icons/plasticIcon.png");
	private Texture glassIcon = new Texture("icons/glassIcon.png");
	private Texture circuitryIcon = new Texture("icons/circuitryIcon.png");
	private Texture metalIcon = new Texture("icons/metalIcon.png");

	private int plasticStored = 100;
	private int plasticCost = 15;
	private int glassStored = 100;
	private int glassCost = 30;
	private int circuitryStored = 100;
	private int circuitryCost = 50;
	private int metalStored = 100;
	private int metalCost = 20;

	public RawMaterials(Vector2 location)
	{
		super(location, 64, 64, "stations/rawMaterials.png", "Raw Materials");
		components.add(new GuiButton(0, 500, 364, 200, 75, "+100/ $" + this.plasticCost, new Texture("button.png")));
		components.add(new GuiButton(1, 500, 279, 200, 75, "+100/ $" + this.glassCost, new Texture("button.png")));
		components.add(new GuiButton(2, 500, 194, 200, 75, "+100/ $" + this.circuitryCost, new Texture("button.png")));
		components.add(new GuiButton(3, 500, 109, 200, 75, "+100/ $" + this.metalCost, new Texture("button.png")));
		super.cost = 10;
	}

	public void onVisitByWorker(Entity entity)
	{
		if(this.isBuilt)
		{
			int amount = this.plasticStored >= 3 ? 3 : this.plasticStored;
			((Worker) entity).giveResource("Raw Plastic", amount);
			this.plasticStored -= amount;

			amount = this.glassStored >= 3 ? 3 : this.glassStored;
			((Worker) entity).giveResource("Raw Glass", amount);
			this.glassStored -= amount;

			amount = this.circuitryStored >= 3 ? 3 : this.circuitryStored;
			((Worker) entity).giveResource("Raw Circuitry", amount);
			this.circuitryStored -= amount;

			amount = this.metalStored >= 3 ? 3 : this.metalStored;
			((Worker) entity).giveResource("Raw Metal", amount);
			this.metalStored -= amount;
		}
	}

	public void render(float xoff, float yoff)
	{
		super.render(xoff, yoff);
	}

	public void loadGui(Screen screen)
	{
		for(GuiComponent guic : this.components)
			screen.addGuiComponent(guic);
	}

	public void unloadGui(Screen screen)
	{
		for(GuiComponent guic : this.components)
			screen.removeGuiComponent(guic);
	}

	public void renderGuiInfo(Screen screen)
	{
		Draw2D.drawRect(100, 90, 600, 410, Color.LIGHT_GRAY, true);
		Draw2D.drawTextured(100, 364, this.plasticIcon);
		Draw2D.drawString(100, 374, "" + this.plasticStored, 2, Color.LIGHT_GRAY);
		Draw2D.drawTextured(100, 279, this.glassIcon);
		Draw2D.drawString(100, 289, "" + this.glassStored, 2, Color.CYAN);
		Draw2D.drawTextured(100, 194, this.circuitryIcon);
		Draw2D.drawString(100, 204, "" + this.circuitryStored, 2, Color.RED);
		Draw2D.drawTextured(100, 109, this.metalIcon);
		Draw2D.drawString(100, 119, "" + this.metalStored, 2, Color.DARK_GRAY);
		super.renderGuiInfo(screen);
	}

	public void stockMaterial(int id)
	{
		if(id == 0)
		{
			if(GameScreen.money >= this.plasticCost)
			{
				this.plasticStored += 100;
				GameScreen.money -= this.plasticCost;
			}
		}
		if(id == 1)
		{
			if(GameScreen.money >= this.glassCost)
			{
				this.glassStored += 100;
				GameScreen.money -= this.glassCost;
			}
		}
		if(id == 2)
		{
			if(GameScreen.money >= this.circuitryCost)
			{
				this.circuitryStored += 100;
				GameScreen.money -= this.circuitryCost;
			}
		}
		if(id == 3)
		{
			if(GameScreen.money >= this.metalCost)
			{
				this.metalStored += 100;
				GameScreen.money -= this.metalCost;
			}
		}
	}
}