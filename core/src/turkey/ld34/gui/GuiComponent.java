package turkey.ld34.gui;

public class GuiComponent
{
	private int id;
	
	protected float x;
	protected float y;
	
	protected float width;
	protected float height;
	
	protected String displayText = "";
	protected String hoverText = "";
	
	protected boolean visible = true;
	
	public GuiComponent(int id, float x, float y, float width, float height)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render()
	{
		
	}
	
	public void setDisplayText(String text)
	{
		this.displayText = text;
	}
	public String getDisplayText()
	{
		return this.displayText;
	}
	
	public GuiComponent setHoverText(String text)
	{
		this.hoverText = text;
		return this;
	}
	public String getHoverText()
	{
		return this.hoverText;
	}
	
	public float getX()
	{
		return this.x;
	}
	public float getY()
	{
		return this.y;
	}
	
	public float getWidth()
	{
		return this.width;
	}
	public float getHeight()
	{
		return this.height;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setVisible(boolean toggle)
	{
		this.visible = toggle;
	}
	
	public boolean isVisible()
	{
		return this.visible;
	}
}