package turkey.ld34.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

public class ScreenManager
{
	public static final ScreenManager INSTANCE = new ScreenManager();
	
	private List<Screen> screens  = new ArrayList<Screen>();
	private Screen currentScreen;
	
	public void addScreen(Screen screen)
	{
		screens.add(screen);
	}
	
	public void setCurrentScreen(Screen screen)
	{
		this.currentScreen = screen;
		Gdx.input.setInputProcessor(currentScreen);
	}
	
	public void setCurrentScreen(String name)
	{
		for(Screen screen: this.screens)
			if(screen.getScreenName().equalsIgnoreCase(name))
				this.currentScreen = screen;
		Gdx.input.setInputProcessor(currentScreen);
	}
	
	public void updateScreen()
	{
		currentScreen.update();
	}
	
	public void renderScreen()
	{
		currentScreen.render();
	}
	
}
