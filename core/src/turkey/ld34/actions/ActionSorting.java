package turkey.ld34.actions;

import turkey.ld34.entities.Entity;
import turkey.ld34.game.stations.CaseProcessing;
import turkey.ld34.game.stations.ElectronicsProcessing;
import turkey.ld34.game.stations.RawMaterials;
import turkey.ld34.game.stations.ScreenProcessing;

import com.badlogic.gdx.graphics.Texture;

public class ActionSorting extends ActionBase
{
	private RawMaterials gatherLocation;
	private ScreenProcessing screenDumpLocation;
	private CaseProcessing caseDumpLocation;
	private ElectronicsProcessing electronicsDumpLocation;
	private boolean goingToRaw = true;
	private int goingToStationNum = 0;

	public ActionSorting(Entity entity)
	{
		super(entity);
		this.SetUpDisplayInfo = "Select the Raw Materials";
		super.texture = new Texture("icons/rawMaterialsIcon.png");
	}

	@Override
	public void updateAction()
	{
		if(this.gatherLocation == null || this.screenDumpLocation == null || this.caseDumpLocation == null || this.electronicsDumpLocation == null)
			return;

		if(super.entity.getDestination() == null)
		{
			super.entity.setDesination(gatherLocation.getPosition());
		}
		else if(super.entity.isAtDestination())
		{
			if(goingToRaw)
			{
				super.entity.setDesination(gatherLocation.getPosition());
				gatherLocation.onVisitByWorker(entity);
			}
			else
			{
				if(goingToStationNum == 0)
				{
					super.entity.setDesination(screenDumpLocation.getPosition());
					screenDumpLocation.onVisitByWorker(entity);
				}
				else if(goingToStationNum == 1)
				{
					super.entity.setDesination(caseDumpLocation.getPosition());
					caseDumpLocation.onVisitByWorker(entity);
				}
				else if(goingToStationNum == 2)
				{
					super.entity.setDesination(electronicsDumpLocation.getPosition());
					electronicsDumpLocation.onVisitByWorker(entity);
				}
				goingToStationNum++;
				goingToStationNum %= 3;
			}
			goingToRaw = !goingToRaw;
		}
	}

	public void setGatherLocation(RawMaterials gatherLocation)
	{
		this.gatherLocation = gatherLocation;
	}

	public void setScreenProcessLocation(ScreenProcessing screenDumpLocation)
	{
		this.screenDumpLocation = screenDumpLocation;
	}

	public void setCaseProcessLocation(CaseProcessing caseDumpLocation)
	{
		this.caseDumpLocation = caseDumpLocation;
	}

	public void setElectronicsProcessLocation(ElectronicsProcessing electronicsDumpLocation)
	{
		this.electronicsDumpLocation = electronicsDumpLocation;
	}

	@Override
	public String getActionName()
	{
		return "Sorting Raw Materials";
	}

	@Override
	public boolean wasClickDuringSetup(Entity ent)
	{
		if(super.setUpStage == 0)
		{
			if(ent instanceof RawMaterials)
			{
				this.gatherLocation = (RawMaterials) ent;
				this.setUpStage++;
				this.SetUpDisplayInfo = "Select the Screen Processor";
				return false;
			}
		}
		else if(super.setUpStage == 1)
		{
			if(ent instanceof ScreenProcessing)
			{
				this.screenDumpLocation = (ScreenProcessing) ent;
				this.setUpStage++;
				this.SetUpDisplayInfo = "Select the Case Processing";
				return false;
			}
		}
		else if(super.setUpStage == 2)
		{
			if(ent instanceof CaseProcessing)
			{
				this.caseDumpLocation = (CaseProcessing) ent;
				this.setUpStage++;
				this.SetUpDisplayInfo = "Select the Electronics";
				return false;
			}
		}
		else if(super.setUpStage == 3)
		{
			if(ent instanceof ElectronicsProcessing)
			{
				this.electronicsDumpLocation = (ElectronicsProcessing) ent;
				this.setUpStage = 0;
				this.SetUpDisplayInfo = "Select the Raw Materials";
				return true;
			}
		}
		return false;
	}
}