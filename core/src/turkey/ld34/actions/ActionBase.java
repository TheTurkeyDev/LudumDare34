package turkey.ld34.actions;

import turkey.ld34.entities.Entity;

import com.badlogic.gdx.graphics.Texture;

public abstract class ActionBase
{
	protected Entity entity;
	
	protected int setUpStage = 0;
	protected String SetUpDisplayInfo = "";
	
	protected Texture texture;
	
	public ActionBase(Entity entity)
	{
		this.entity = entity;
	}
	
	public abstract void updateAction();
	
	public void stopAction()
	{
		
	}
	
	public abstract String getActionName();
	
	public Texture getTexture()
	{
		return this.texture;
	}
	
	public abstract boolean wasClickDuringSetup(Entity ent);
	
	public String getDisplayInfo()
	{
		return this.SetUpDisplayInfo;
	}
}
