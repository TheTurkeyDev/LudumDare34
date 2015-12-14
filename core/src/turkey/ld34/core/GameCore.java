package turkey.ld34.core;

import turkey.ld34.game.GameManager;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.screen.GameScreen;
import turkey.ld34.screen.HelpScreen;
import turkey.ld34.screen.MainScreen;
import turkey.ld34.screen.ScreenManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GameCore extends ApplicationAdapter
{

	@Override
	public void create()
	{
		new Draw2D();
		
		GameManager.gameManagerInit();
		
		ScreenManager.INSTANCE.addScreen(new MainScreen());
		ScreenManager.INSTANCE.addScreen(new HelpScreen());
		ScreenManager.INSTANCE.addScreen(new GameScreen());
		
		ScreenManager.INSTANCE.setCurrentScreen("Main Screen");
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Draw2D.updateCamera();
		ScreenManager.INSTANCE.updateScreen();
		ScreenManager.INSTANCE.renderScreen();
	}
}
