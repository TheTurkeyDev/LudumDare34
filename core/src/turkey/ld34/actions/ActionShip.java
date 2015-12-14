package turkey.ld34.actions;

import turkey.ld34.entities.Entity;
import turkey.ld34.game.stations.PackagingStation;
import turkey.ld34.game.stations.ShippingStation;

import com.badlogic.gdx.graphics.Texture;

public class ActionShip extends ActionBase
{
	private PackagingStation gatherLocation;
	private ShippingStation dumpLocation;
	private boolean goingToPackaging = true;

	public ActionShip(Entity entity)
	{
		super(entity);
		this.SetUpDisplayInfo = "Select the Packaging Station";
		super.texture = new Texture("icons/distobutionIcon.png");
	}

	@Override
	public void updateAction()
	{
		if(this.dumpLocation == null || this.gatherLocation == null)
			return;

		if(super.entity.getDestination() == null)
		{
			super.entity.setDesination(gatherLocation.getPosition());
		}
		else if(super.entity.isAtDestination())
		{
			if(goingToPackaging)
			{
				super.entity.setDesination(dumpLocation.getPosition());
				gatherLocation.onVisitByWorker(entity);
			}
			else
			{
				super.entity.setDesination(gatherLocation.getPosition());
				dumpLocation.onVisitByWorker(entity);
			}
			goingToPackaging = !goingToPackaging;
		}
	}

	public void setGatherLocation(PackagingStation gatherLocation)
	{
		this.gatherLocation = gatherLocation;
	}

	public void setDumpLocation(ShippingStation dumpLocation)
	{
		this.dumpLocation = dumpLocation;
	}

	@Override
	public String getActionName()
	{
		return "Transport to Shipping";
	}

	@Override
	public boolean wasClickDuringSetup(Entity ent)
	{
		if(super.setUpStage == 0)
		{
			if(ent instanceof PackagingStation)
			{
				this.gatherLocation = (PackagingStation) ent;
				this.setUpStage++;
				this.SetUpDisplayInfo = "Select the Shipping Station";
				return false;
			}
		}
		else if(super.setUpStage == 1)
		{
			if(ent instanceof ShippingStation)
			{
				this.dumpLocation = (ShippingStation) ent;
				this.setUpStage = 0;
				this.SetUpDisplayInfo = "Select the Packaging Station";
				return true;
			}
		}
		return false;
	}
}
