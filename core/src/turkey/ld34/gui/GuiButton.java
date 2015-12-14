package turkey.ld34.gui;

import turkey.ld34.graphics.Draw2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class GuiButton extends GuiComponent
{
	private Texture texture;

	public GuiButton(int id, float x, float y, float width, float height, String text, Texture texture)
	{
		super(id, x, y, width, height);
		this.texture = texture;
		this.displayText = text;
	}

	public void render()
	{
		if(!visible)
			return;
		Draw2D.drawTextured(x, y, texture);
		Draw2D.drawString(x, y + (this.height - 5), this.displayText, 3, Color.WHITE);
	}
	
	public Texture getTexture()
	{
		return this.texture;
	}
}
