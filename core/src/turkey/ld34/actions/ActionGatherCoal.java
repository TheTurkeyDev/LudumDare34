package turkey.ld34.actions;

import com.badlogic.gdx.graphics.Texture;

import turkey.ld34.entities.Entity;
import turkey.ld34.game.stations.CoalDump;
import turkey.ld34.game.stations.Generator;

public class ActionGatherCoal extends ActionBase
{
	private CoalDump gatherLocation;
	private Generator dumpLocation;
	private boolean goingToCoal = true;

	public ActionGatherCoal(Entity entity)
	{
		super(entity);
		this.SetUpDisplayInfo = "Select the Coal Dump";
		this.texture = new Texture("icons/coalIcon.png");
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
			if(goingToCoal)
			{
				super.entity.setDesination(dumpLocation.getPosition());
				gatherLocation.onVisitByWorker(entity);
			}
			else
			{
				super.entity.setDesination(gatherLocation.getPosition());
				dumpLocation.onVisitByWorker(entity);
			}
			goingToCoal = !goingToCoal;
		}
	}

	public void setGatherLocation(CoalDump gatherLocation)
	{
		this.gatherLocation = gatherLocation;
	}

	public void setDumpLocation(Generator dumpLocation)
	{
		this.dumpLocation = dumpLocation;
	}

	@Override
	public String getActionName()
	{
		return "Gatering Coal";
	}

	@Override
	public boolean wasClickDuringSetup(Entity ent)
	{
		if(super.setUpStage == 0)
		{
			if(ent instanceof CoalDump)
			{
				this.gatherLocation = (CoalDump) ent;
				this.setUpStage++;
				this.SetUpDisplayInfo = "Select the Generator";
				return false;
			}
		}
		else if(super.setUpStage == 1)
		{
			if(ent instanceof Generator)
			{
				this.dumpLocation = (Generator) ent;
				this.setUpStage = 0;
				this.SetUpDisplayInfo = "Select the Coal Dump";
				return true;
			}
		}
		return false;
	}
}