package turkey.ld34.actions;

import turkey.ld34.entities.Entity;
import turkey.ld34.game.stations.TrainingStation;

import com.badlogic.gdx.graphics.Texture;

public class ActionTrainEmployees extends ActionBase
{
	private TrainingStation station;
	private boolean isEntityTraining = false;

	public ActionTrainEmployees(Entity entity)
	{
		super(entity);
		this.SetUpDisplayInfo = "Select the Training Station";
		super.texture = new Texture("icons/trainingIcon.png");
	}

	@Override
	public void updateAction()
	{
		if(entity.getDestination() == null || !entity.getDestination().equals(station.getPosition()))
			entity.setDesination(station.getPosition());
		else if(entity.isAtDestination() && !this.isEntityTraining)
		{
			station.onVisitByWorker(entity);
			this.isEntityTraining = true;
		}
			
	}
	
	public void stopAction()
	{
		station.removeTrainer();
	}

	@Override
	public String getActionName()
	{
		return "Training Employees";
	}

	@Override
	public boolean wasClickDuringSetup(Entity ent)
	{
		if(ent instanceof TrainingStation)
		{
			this.station = (TrainingStation) ent;
			ent.setDesination(station.getPosition());
			return true;
		}
		return false;
	}
}