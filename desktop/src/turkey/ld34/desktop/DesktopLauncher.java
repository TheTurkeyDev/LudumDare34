package turkey.ld34.desktop;

import turkey.ld34.core.GameCore;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LudumDare 34";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new GameCore(), config);
	}
}
