package turkey.ld34.entities;

import turkey.ld34.actions.ActionBase;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.screen.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Entity
{
	protected float moveSpeed = 100;
	protected Vector2 position;
	protected Vector2 destination;
	protected boolean isAtDestination = false;
	protected int width = 32, height = 32;
	protected double rotation = 0;
	protected ActionBase action1;
	protected Texture texture;

	protected boolean isSelected = false;

	public Entity(String texturePath)
	{
		this.texture = new Texture(texturePath);
		this.position = new Vector2(0, 0);
	}

	public void update()
	{
		if(this.destination != null)
		{
			float xDiff = this.position.x - this.destination.x;
			float yDiff = this.position.y - this.destination.y;
			if(xDiff == 0 && yDiff == 0)
				xDiff = 1;
			float distance = this.moveSpeed * Gdx.graphics.getDeltaTime();
			rotation = Math.atan(yDiff / xDiff);
			float xinc = (float) (distance * Math.cos(rotation));
			float yinc = (float) (distance * Math.sin(rotation));
			if(xDiff >= 0)
			{
				xinc *= -1;
				yinc *= -1;
				rotation += Math.PI;
			}
			double dist = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			if(dist > distance)
			{
				this.position.x += xinc;
				this.position.y += yinc;
				this.isAtDestination = false;
			}
			else
			{
				this.position.x = this.destination.x;
				this.position.y = this.destination.y;
				this.isAtDestination = true;
			}
		}

		if(action1 != null)
			action1.updateAction();
	}

	public void render(float xoff, float yoff)
	{
		if(this.isSelected)
			Draw2D.drawRect(position.x - 3 + xoff, position.y - 3 + yoff, width + 6, height + 6, Color.YELLOW, true);
		Draw2D.drawTextured(this.position.x + xoff, this.position.y + yoff, texture, (float) Math.toDegrees(rotation));
	}

	public void unloadGui(Screen screen)
	{

	}

	public void loadGui(Screen screen)
	{

	}

	public void renderGuiInfo(Screen screen)
	{
		Draw2D.drawRect(100, 100, 600, 400, Color.LIGHT_GRAY, true);
	}

	public void setDesination(Vector2 dest)
	{
		this.destination = dest;
	}

	public Vector2 getDestination()
	{
		return this.destination;
	}

	public boolean isAtDestination()
	{
		return this.isAtDestination;
	}

	public Vector2 getPosition()
	{
		return this.position;
	}

	public void setPosition(Vector2 pos)
	{
		this.position = pos;
	}

	public void setAction1(ActionBase action1)
	{
		if(this.action1 != null)
			this.action1.stopAction();
		this.action1 = action1;
	}
	
	public ActionBase getAction()
	{
		return this.action1;
	}

	public boolean isLocationWithin(Vector2 loc)
	{
		if(this.position.x < loc.x && this.position.x + this.width > loc.x && this.position.y < loc.y && this.position.y + this.height > loc.y)
			return true;
		return false;
	}

	public void setSelected(boolean toggle)
	{
		this.isSelected = toggle;
	}
	
	public Texture getTexture()
	{
		return this.texture;
	}
}
