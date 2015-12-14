package turkey.ld34.screen;

import turkey.ld34.graphics.Draw2D;
import turkey.ld34.gui.GuiButton;
import turkey.ld34.gui.GuiComponent;

import com.badlogic.gdx.graphics.Texture;

public class HelpScreen extends Screen
{
	private Texture background = new Texture("helpBackground.png");
	private Texture buttonS = new Texture("buttonStart.png");
	public HelpScreen()
	{
		super("Help Screen");
		super.addGuiComponent(new GuiButton(0, 300, -20, 200, 75, "", buttonS));
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
	}

}
