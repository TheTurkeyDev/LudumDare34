package turkey.ld34.actions;

import turkey.ld34.entities.Entity;
import turkey.ld34.game.stations.CaseProcessing;
import turkey.ld34.game.stations.ElectronicsProcessing;
import turkey.ld34.game.stations.PackagingStation;
import turkey.ld34.game.stations.ScreenProcessing;

import com.badlogic.gdx.graphics.Texture;

public class ActionCollection extends ActionBase
{
	private PackagingStation dumpLocation;
	private ScreenProcessing screenDumpLocation;
	private CaseProcessing caseDumpLocation;
	private ElectronicsProcessing electronicsDumpLocation;
	private boolean goingToPack = true;
	private int goingToStationNum = 0;

	public ActionCollection(Entity entity)
	{
		super(entity);
		this.SetUpDisplayInfo = "Select the Packaging Station";
		super.texture = new Texture("icons/rawMaterialsIcon.png");
	}

	@Override
	public void updateAction()
	{
		if(this.dumpLocation == null || this.screenDumpLocation == null || this.caseDumpLocation == null || this.electronicsDumpLocation == null)
			return;

		if(super.entity.getDestination() == null)
		{
			super.entity.setDesination(dumpLocation.getPosition());
		}
		else if(super.entity.isAtDestination())
		{
			if(goingToPack)
			{
				super.entity.setDesination(dumpLocation.getPosition());
				dumpLocation.onVisitByWorker(entity);
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
			}
			if(goingToStationNum == 3 || goingToPack)
			{
				goingToPack = !goingToPack;
				goingToStationNum = 0;
			}
		}
	}

	public void setDumpLocation(PackagingStation dumpLocation)
	{
		this.dumpLocation = dumpLocation;
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
			if(ent instanceof PackagingStation)
			{
				this.dumpLocation = (PackagingStation) ent;
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