package turkey.ld34.game.stations;

import turkey.ld34.entities.Entity;
import turkey.ld34.entities.Worker;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.screen.GameScreen;
import turkey.ld34.screen.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ShippingStation extends StationBase
{
	private int productsSold = 0;
	private int pricePerProduct = 20;

	public ShippingStation(Vector2 location)
	{
		super(location, 64, 64, "stations/distobution.png", "Shipping");
		super.cost = 10;
	}

	public void render(float xoff, float yoff)
	{
		super.render(xoff, yoff);
	}

	public void renderGuiInfo(Screen screen)
	{
		Draw2D.drawRect(100, 100, 600, 400, Color.LIGHT_GRAY, true);
		Draw2D.drawString(125, 450, "Products Sold: ", 3, Color.WHITE);
		Draw2D.drawString(125, 400, "Price Per Product: ", 3, Color.WHITE);

		String soldS = "" + this.productsSold;
		String piceS = "" + this.pricePerProduct;
		Draw2D.drawString(690 - (soldS.length() * 20), 450, soldS, 3, Color.WHITE);
		Draw2D.drawString(690 - (piceS.length() * 20), 400, piceS, 3, Color.WHITE);
		super.renderGuiInfo(screen);
	}

	public void update()
	{
		super.update();
		if(!this.isBuilt)
			return;
	}

	public void onVisitByWorker(Entity entity)
	{
		if(!this.isBuilt)
			return;

		if(((Worker) entity).takeResource("Packages", 1) == 1)
		{
			this.productsSold++;
			GameScreen.money += this.pricePerProduct;
		}

	}
}