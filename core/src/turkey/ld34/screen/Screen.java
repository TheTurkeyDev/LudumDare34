package turkey.ld34.screen;

import java.util.ArrayList;
import java.util.List;

import turkey.ld34.gui.GuiComponent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class Screen implements InputProcessor
{
	private String name;

	protected List<GuiComponent> components = new ArrayList<GuiComponent>();

	public Screen(String name)
	{
		this.name = name;
	}

	public void update()
	{

	}

	public void render()
	{
		for(GuiComponent guic : components)
			guic.render();
	}

	public String getScreenName()
	{
		return this.name;
	}

	public void addGuiComponent(GuiComponent guic)
	{
		components.add(guic);
	}

	public void removeGuiComponent(GuiComponent guic)
	{
		components.remove(guic);
	}

	public void clearCompnents()
	{
		this.components.clear();
	}

	public void onComponentClicked(GuiComponent guic)
	{

	}

	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		screenY = Gdx.graphics.getHeight() - screenY;
		for(GuiComponent guic : components)
		{
			if((guic.getX() <= screenX && guic.getX() + guic.getWidth() >= screenX) && (guic.getY() <= screenY && guic.getY() + guic.getHeight() >= screenY))
			{
				if(!guic.isVisible())
					continue;
				this.onComponentClicked(guic);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}
