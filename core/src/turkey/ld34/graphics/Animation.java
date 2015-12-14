package turkey.ld34.graphics;

import com.badlogic.gdx.graphics.Texture;

public class Animation
{
	public float x;
	public float y;
	private int stage = 0;
	private int totalStages;
	private int stageDelay;
	private int stageDelayTick;

	private boolean loops;

	private Texture[] textures;

	private boolean running = true;

	public Animation(float x, float y, int stages, int delay, boolean loops, Texture... textures)
	{
		this.x = x;
		this.y = y;
		
		this.totalStages = stages;
		this.stageDelay = delay;

		this.loops = loops;

		this.textures = textures;
	}

	public void update()
	{
		this.stageDelayTick++;
		if(this.stageDelayTick > this.stageDelay)
		{
			this.stage++;
			if(this.stage >= this.totalStages)
			{
				if(this.loops)
				{
					this.stage = 0;
					this.stageDelayTick = 0;
				}
				else
				{
					this.stage--;
					this.running = false;
				}
			}
		}
	}

	public void render()
	{
		Draw2D.drawTextured(x, y, this.textures[this.stage]);
	}

	public boolean isRunning()
	{
		return this.running;
	}
}
