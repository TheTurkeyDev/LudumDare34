package turkey.ld34.screen;

import turkey.ld34.graphics.Draw2D;
import turkey.ld34.gui.GuiButton;
import turkey.ld34.gui.GuiComponent;

import com.badlogic.gdx.graphics.Texture;

public class MainScreen extends Screen
{
	private Texture background = new Texture("MainScreen.png");
	private Texture buttonS = new Texture("buttonStart.png");
	private Texture buttonH = new Texture("buttonHelp.png");
	public MainScreen()
	{
		super("Main Screen");
		super.addGuiComponent(new GuiButton(0, 100, 75, 200, 75, "", buttonS));
		super.addGuiComponent(new GuiButton(1, 500, 75, 200, 75, "", buttonH));
	}
	
	public void render()
	{
		Draw2D.drawTextured(0, 0, background);
		super.render();
	}
	
	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
		{
			ScreenManager.INSTANCE.setCurrentScreen("Game Screen");
		}
		if(guic.getId() == 1)
		{
			ScreenManager.INSTANCE.setCurrentScreen("Help Screen");
		}
	}

}
