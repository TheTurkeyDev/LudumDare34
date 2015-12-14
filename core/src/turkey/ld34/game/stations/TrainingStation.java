package turkey.ld34.game.stations;

import turkey.ld34.entities.Entity;
import turkey.ld34.entities.Worker;
import turkey.ld34.game.GameManager;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.screen.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class TrainingStation extends StationBase
{
	private int trainTimeLeft = -1;
	private int trainTimeNorm = 1200;
	private int trainTime = 1200;
	private int TrainersAssigned = 0;
	private int timeDuctPerTrainer = 60;

	public TrainingStation(Vector2 position)
	{
		super(position, 64, 64, "stations/trainingStation.png", "Training Station");
		super.cost = 100;
		super.powerNeeded = 1;
	}

	public void render(float xoff, float yoff)
	{
		super.render(xoff, yoff);
	}

	public void renderGuiInfo(Screen screen)
	{
		Draw2D.drawRect(100, 100, 600, 400, Color.LIGHT_GRAY, true);
		Draw2D.drawString(125, 450, "Time until next worker: ", 3, Color.WHITE);
		Draw2D.drawString(125, 400, "Time to train a worker: ", 3, Color.WHITE);
		Draw2D.drawString(125, 350, "Trainers Assiged: ", 3, Color.WHITE);
		String timeLeftS = "" + (int) (trainTimeLeft / 60);
		String timetoTrainS = "" + (int) (this.trainTime / 60);
		String trainersAssignedS = "" + this.TrainersAssigned;
		Draw2D.drawString(680 - (timeLeftS.length() * 20), 450, "" + (int) (trainTimeLeft / 60), 3, Color.WHITE);
		Draw2D.drawString(680 - (timetoTrainS.length() * 20), 400, "" + (int) (this.trainTime / 60), 3, Color.WHITE);
		Draw2D.drawString(680 - (trainersAssignedS.length() * 20), 350, "" + this.TrainersAssigned, 3, Color.WHITE);
		super.renderGuiInfo(screen);
	}

	public void update()
	{
		super.update();
		if(!this.isBuilt)
			return;

		if(!(GameManager.powerNeededPerTick + this.powerNeeded <= GameManager.powerPerTick))
		{
			this.hasPower = false;
			return;
		}

		this.hasPower = true;
		GameManager.powerNeededPerTick += this.powerNeeded;

		if(this.trainTimeLeft < -1 || this.TrainersAssigned == 0)
			return;
		if(this.trainTimeLeft == 0)
		{
			Worker w = new Worker("workers/coalGatherer.png");
			w.setPosition(this.getPosition().cpy());
			GameManager.entities.add(w);
			this.trainTimeLeft = this.trainTime;
			return;
		}
		this.trainTimeLeft--;
	}

	public void onVisitByWorker(Entity entity)
	{
		if(!this.isBuilt)
			return;
		this.TrainersAssigned++;
		if(TrainersAssigned > 10)
			this.trainTime = trainTimeNorm - (10 * this.timeDuctPerTrainer);
		else
			this.trainTime = trainTimeNorm - ((this.TrainersAssigned - 1) * this.timeDuctPerTrainer);

		if(this.trainTimeLeft < 0)
			this.trainTimeLeft = this.trainTime;
	}

	public void removeTrainer()
	{
		if(!this.isBuilt)
			return;

		this.TrainersAssigned--;
		if(this.TrainersAssigned > 0)
			this.trainTime = trainTimeNorm - ((this.TrainersAssigned - 1) * this.timeDuctPerTrainer);
		else
			this.trainTime = -1;

	}
}