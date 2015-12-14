package turkey.ld34.actions;

import turkey.ld34.entities.Entity;
import turkey.ld34.game.stations.StationBase;

import com.badlogic.gdx.graphics.Texture;

public class ActionConstruction extends ActionBase
{
	private StationBase building;
	private boolean isBuilding = true;

	public ActionConstruction(Entity entity)
	{
		super(entity);
		this.SetUpDisplayInfo = "Cost";
		super.texture = new Texture("icons/constructionIcon.png");
	}

	@Override
	public void updateAction()
	{
		if(entity.getDestination() == null || !entity.getDestination().equals(building.getPosition()))
			entity.setDesination(building.getPosition());
		else if(entity.isAtDestination() && !building.isBeingBuilt())
			building.startBuilding();

		if(building.isBuilt())
			this.isBuilding = false;
	}

	public void stopAction()
	{
		this.building.stopBuilding();
	}

	@Override
	public String getActionName()
	{
		return "Construction";
	}
	
	@Override
	public boolean wasClickDuringSetup(Entity ent)
	{
		return true;
	}

	public void setBuilding(StationBase building)
	{
		if(building == null)
			return;
		this.building = building;
		this.SetUpDisplayInfo = "Cost $" + building.cost;
	}

	public boolean isBuilding()
	{
		return isBuilding;
	}
}
