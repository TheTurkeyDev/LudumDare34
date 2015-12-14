package turkey.ld34.entities;

import java.util.ArrayList;
import java.util.List;

import turkey.ld34.graphics.Draw2D;
import turkey.ld34.gui.GuiButton;
import turkey.ld34.gui.GuiComponent;
import turkey.ld34.screen.Screen;
import turkey.ld34.util.CustomEntry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Worker extends Entity
{
	private List<CustomEntry<String, Integer>> carrying = new ArrayList<CustomEntry<String, Integer>>();

	private List<GuiComponent> components = new ArrayList<GuiComponent>();

	public Worker(String texturePath)
	{
		super(texturePath);
		components.add(new GuiButton(0, 100, 434, 64, 64, "", new Texture("icons/coalIcon.png")).setHoverText("Coal -> Generator"));
		components.add(new GuiButton(1, 100, 364, 64, 64, "", new Texture("icons/trainingIcon.png")).setHoverText("Train New Employees"));
		components.add(new GuiButton(2, 100, 294, 64, 64, "", new Texture("icons/constructionIcon.png")).setHoverText("Build Stuff"));
		components.add(new GuiButton(3, 100, 224, 64, 64, "", new Texture("icons/rawMaterialsIcon.png")).setHoverText("Sort Raw Materials"));
		components.add(new GuiButton(4, 100, 154, 64, 64, "", new Texture("icons/collectionIcon.png")).setHoverText("Collect Processed Parts"));
		components.add(new GuiButton(5, 100, 84, 64, 64, "", new Texture("icons/distobutionIcon.png")).setHoverText("Package -> Shipping"));

		carrying.add(new CustomEntry<String, Integer>("Coal", 0));
		carrying.add(new CustomEntry<String, Integer>("Raw Plastic", 0));
		carrying.add(new CustomEntry<String, Integer>("Raw Glass", 0));
		carrying.add(new CustomEntry<String, Integer>("Raw Circuitry", 0));
		carrying.add(new CustomEntry<String, Integer>("Raw Metal", 0));
		carrying.add(new CustomEntry<String, Integer>("Screens", 0));
		carrying.add(new CustomEntry<String, Integer>("Electronics", 0));
		carrying.add(new CustomEntry<String, Integer>("Cases", 0));
		carrying.add(new CustomEntry<String, Integer>("Packages", 0));
	}

	public void giveResource(String res, int amount)
	{
		CustomEntry<String, Integer> resource = this.getResourceFromName(res);
		if(resource != null)
			resource.setValue(resource.getValue() + amount);
	}

	public int takeResource(String res, int amountMax)
	{
		int toReturn = 0;
		CustomEntry<String, Integer> resource = this.getResourceFromName(res);
		if(resource != null)
		{
			toReturn = resource.getValue() < amountMax ? resource.getValue() : amountMax;
			resource.setValue(resource.getValue() - (resource.getValue() < amountMax ? resource.getValue() : amountMax));
		}
		return toReturn;
	}

	public void render()
	{
		if(this.isSelected)
			Draw2D.drawRect(position.x - 3, position.y - 3, width + 6, height + 6, Color.YELLOW, true);
		Draw2D.drawTextured(this.position.x, this.position.y, texture, (float) Math.toDegrees(rotation) - 90);
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
		super.renderGuiInfo(screen);
		String action = "Current Action";
		Draw2D.drawString(397 - (action.length() * 5), 500, action, 2, Color.WHITE);
		Draw2D.drawRect(397, 402, 70, 70, Color.WHITE, true);
		if(action1 != null)
			Draw2D.drawTextured(400, 405, action1.getTexture());
		
		Draw2D.drawString(450, 400, "Carrying:", 2f, Color.WHITE);

		for(int i = 0; i < this.carrying.size(); i++)
		{
			CustomEntry<String, Integer> item = carrying.get(i);
			Draw2D.drawString(300, 370 - (30 * i), item.getKey(), 2f, Color.WHITE);
			Draw2D.drawString(675 - (("" + item.getValue()).length() * 5), 370 - (30 * i), "" + item.getValue(), 2f, Color.WHITE);
		}
	}

	public CustomEntry<String, Integer> getResourceFromName(String name)
	{
		for(CustomEntry<String, Integer> entry : this.carrying)
			if(entry.getKey().equalsIgnoreCase(name))
				return entry;
		return null;
	}
}