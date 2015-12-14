package turkey.ld34.screen;

import turkey.ld34.actions.ActionBase;
import turkey.ld34.actions.ActionCollection;
import turkey.ld34.actions.ActionConstruction;
import turkey.ld34.actions.ActionGatherCoal;
import turkey.ld34.actions.ActionShip;
import turkey.ld34.actions.ActionSorting;
import turkey.ld34.actions.ActionTrainEmployees;
import turkey.ld34.entities.Entity;
import turkey.ld34.entities.Worker;
import turkey.ld34.game.GameManager;
import turkey.ld34.game.stations.CaseProcessing;
import turkey.ld34.game.stations.CoalDump;
import turkey.ld34.game.stations.ElectronicsProcessing;
import turkey.ld34.game.stations.Generator;
import turkey.ld34.game.stations.PackagingStation;
import turkey.ld34.game.stations.RawMaterials;
import turkey.ld34.game.stations.ScreenProcessing;
import turkey.ld34.game.stations.ShippingStation;
import turkey.ld34.game.stations.StationBase;
import turkey.ld34.game.stations.TrainingStation;
import turkey.ld34.graphics.Draw2D;
import turkey.ld34.gui.GuiButton;
import turkey.ld34.gui.GuiComponent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends Screen
{
	public static int money = 300;

	private Texture floorTexture = new Texture("factoryFloor.png");

	private Entity selectedEntity = null;
	private Entity focusedEntity = null;

	private ActionBase settingUp = null;
	private boolean isSettingUpAction = false;
	private ActionConstruction building = null;
	private StationBase toBuild = null;
	private int toBuildPos = 0;
	private boolean isbuilding = false;

	private Vector2 hoverLoc = new Vector2(0, 0);
	private String hoverText = "";

	private Vector2 tempOffset = new Vector2(0, 0);
	private Vector2 startLocation = new Vector2(0, 0);
	private boolean isDragging = false;

	public GameScreen()
	{
		super("Game Screen");
		CoalDump coal = new CoalDump(new Vector2(100, 100));
		coal.setBuilt();
		Generator generator = new Generator(new Vector2(300, 400));
		generator.setBuilt();
		TrainingStation training = new TrainingStation(new Vector2(100, 500));
		training.setBuilt();
		RawMaterials mats = new RawMaterials(new Vector2(600, 400));
		mats.setBuilt();
		ScreenProcessing sp = new ScreenProcessing(new Vector2(500, 300));
		sp.setBuilt();
		CaseProcessing cp = new CaseProcessing(new Vector2(600, 300));
		cp.setBuilt();
		ElectronicsProcessing ep = new ElectronicsProcessing(new Vector2(700, 300));
		ep.setBuilt();
		PackagingStation pack = new PackagingStation(new Vector2(600, 200));
		pack.setBuilt();
		ShippingStation ship = new ShippingStation(new Vector2(600, 100));
		ship.setBuilt();
		GameManager.entities.add(coal);
		GameManager.entities.add(generator);
		GameManager.entities.add(training);
		GameManager.entities.add(mats);
		GameManager.entities.add(sp);
		GameManager.entities.add(cp);
		GameManager.entities.add(ep);
		GameManager.entities.add(pack);
		GameManager.entities.add(ship);
		Worker w = new Worker("workers/coalGatherer.png");
		Worker w1 = new Worker("workers/coalGatherer.png");
		Worker w2 = new Worker("workers/coalGatherer.png");
		Worker w3 = new Worker("workers/coalGatherer.png");
		ActionGatherCoal action = new ActionGatherCoal(w);
		action.setDumpLocation(generator);
		action.setGatherLocation(coal);
		w.setAction1(action);
		GameManager.entities.add(w);
		GameManager.entities.add(w1);
		GameManager.entities.add(w2);
		GameManager.entities.add(w3);
	}

	public void update()
	{
		GameManager.update();
		this.inputUpdate();
		// if(this.focusedEntity != null)
		// this.focusedEntity = this.selectedEntity;
	}

	public void render()
	{
		Draw2D.drawTextured(0, 0, floorTexture);
		GameManager.render(this.tempOffset);
		Draw2D.drawRect(600, 550, 200, 50, Color.RED, true);
		Draw2D.drawString(725 - (("Money: $" + money).length() * 5), 600, "Money: $" + money, 1.25f, Color.GRAY);
		Draw2D.drawString(725 - (("Power: " + GameManager.powerNeededPerTick + "/" + GameManager.powerPerTick).length() * 5), 580, "Power: " + GameManager.powerNeededPerTick + "/" + GameManager.powerPerTick, 1.25f, Color.GRAY);
		if(this.focusedEntity != null && !this.isSettingUpAction && !this.isbuilding)
			this.focusedEntity.renderGuiInfo(this);
		else if(this.isSettingUpAction)
			Draw2D.drawString(400 - (this.settingUp.getDisplayInfo().length() * 5), 600, this.settingUp.getDisplayInfo(), 2, Color.GREEN);
		else if(this.isbuilding && this.building != null)
			Draw2D.drawString(400 - (this.building.getDisplayInfo().length() * 5), 600, this.building.getDisplayInfo(), 2, Color.GREEN);
		if(this.isbuilding && this.toBuild != null && this.selectedEntity == null)
			Draw2D.drawTextured(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), this.toBuild.getTexture());

		super.render();

		Draw2D.drawString(this.hoverLoc.x, this.hoverLoc.y, this.hoverText, 2, Color.GRAY);
	}

	public void inputUpdate()
	{
		if(this.focusedEntity == null || this.isSettingUpAction || this.isbuilding)
		{
			if(selectedEntity != null)
				selectedEntity.setSelected(false);

			Vector2 mouse = new Vector2(Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()));
			this.selectedEntity = GameManager.getEntityAt(mouse);

			if(selectedEntity != null)
				selectedEntity.setSelected(true);
		}
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if((guic instanceof GuiButton))
		{
			if(this.focusedEntity instanceof Worker)
			{
				if(guic.getId() == 0)
					settingUp = new ActionGatherCoal(this.focusedEntity);
				else if(guic.getId() == 1)
					settingUp = new ActionTrainEmployees(this.focusedEntity);
				else if(guic.getId() == 2)
					building = new ActionConstruction(this.focusedEntity);
				else if(guic.getId() == 3)
					settingUp = new ActionSorting(this.focusedEntity);
				else if(guic.getId() == 4)
					settingUp = new ActionCollection(this.focusedEntity);
				else if(guic.getId() == 5)
					settingUp = new ActionShip(this.focusedEntity);
			}
			else if(this.focusedEntity instanceof RawMaterials)
			{
				((RawMaterials) focusedEntity).stockMaterial(guic.getId());
				return;
			}
		}

		if(this.settingUp != null)
		{
			this.stopActionBuild();
			this.loadActionSetup();
		}
		if(this.building != null)
		{
			this.loadActionBuild();
		}
	}

	private void stopActionBuild()
	{
		this.building = null;
		this.isbuilding = false;
		this.toBuild = null;
		this.toBuildPos = 0;
		this.focusedEntity.setAction1(null);
	}

	private void loadActionSetup()
	{
		this.isSettingUpAction = true;
		this.isbuilding = false;
		this.focusedEntity.unloadGui(this);
	}

	private void loadActionBuild()
	{
		this.isSettingUpAction = false;
		this.isbuilding = true;
		this.focusedEntity.unloadGui(this);
		this.toBuild = this.getStationBuildFromInt(this.toBuildPos);
		this.building.setBuilding(this.toBuild);

	}

	@Override
	public boolean keyTyped(char character)
	{
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE) && this.isSettingUpAction)
		{
			this.isSettingUpAction = false;
			this.settingUp = null;
			this.focusedEntity.loadGui(this);
			if(selectedEntity != null)
				selectedEntity.setSelected(false);
			this.selectedEntity = null;
		}
		else if(Gdx.input.isKeyJustPressed(Keys.ESCAPE) && this.isbuilding)
		{
			this.isbuilding = false;
			this.toBuild = null;
			this.toBuildPos = 0;
			this.focusedEntity.setAction1(null);
			this.focusedEntity.loadGui(this);
			if(selectedEntity != null)
				selectedEntity.setSelected(false);
			this.selectedEntity = null;
		}
		else if(Gdx.input.isKeyJustPressed(Keys.ESCAPE) && this.focusedEntity != null)
		{
			this.focusedEntity.unloadGui(this);
			this.focusedEntity = null;
		}

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		startLocation = new Vector2(screenX, screenY);
		if(this.isDragging)
			return false;
		if(super.touchDown(screenX, screenY, pointer, button))
			return true;
		if(button == 0 && this.isSettingUpAction)
		{
			Entity ent = GameManager.getEntityAt(new Vector2(screenX, Gdx.graphics.getHeight() - screenY));
			if(ent != null)
			{
				if(this.settingUp.wasClickDuringSetup(ent))
				{
					this.focusedEntity.setAction1(this.settingUp);
					this.isSettingUpAction = false;
					this.settingUp = null;
					this.focusedEntity.loadGui(this);
					if(selectedEntity != null)
						selectedEntity.setSelected(false);
					this.selectedEntity = null;
				}
			}
		}
		else if(button == 0 && this.isbuilding)
		{
			if(this.selectedEntity == null)
			{
				if(money - this.toBuild.cost >= 0)
				{
					money -= this.toBuild.cost;
					this.toBuild.setPosition(new Vector2(Gdx.input.getX() - GameManager.offset.x, (Gdx.graphics.getHeight() - Gdx.input.getY()) - GameManager.offset.y));
					this.building.setBuilding(this.toBuild);
					GameManager.entities.add(this.toBuild);
					this.isbuilding = false;
					this.toBuild = null;
					this.toBuildPos = 0;
					this.focusedEntity.setAction1(this.building);
					this.focusedEntity.loadGui(this);
				}
			}
			else if(this.selectedEntity instanceof StationBase)
			{
				StationBase base = (StationBase) this.selectedEntity;
				if(!base.isBuilt())
				{
					this.building.setBuilding(base);
					this.isbuilding = false;
					this.toBuild = null;
					this.toBuildPos = 0;
					this.focusedEntity.setAction1(this.building);
					this.focusedEntity.loadGui(this);
				}
			}
		}
		else if(button == 0 && this.selectedEntity != null)
		{
			if(this.focusedEntity != null)
				this.focusedEntity.unloadGui(this);
			this.focusedEntity = this.selectedEntity;
			this.focusedEntity.loadGui(this);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		isDragging = true;

		tempOffset.x = screenX - this.startLocation.x;
		tempOffset.y = this.startLocation.y - screenY;

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		isDragging = false;
		GameManager.offset.x += tempOffset.x;
		GameManager.offset.y += tempOffset.y;
		tempOffset = new Vector2(0, 0);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		this.hoverText = "";
		screenY = Gdx.graphics.getHeight() - screenY;
		for(GuiComponent guic : super.components)
		{
			if((guic.getX() <= screenX && guic.getX() + guic.getWidth() >= screenX) && (guic.getY() <= screenY && guic.getY() + guic.getHeight() >= screenY))
			{
				if(!guic.isVisible())
					continue;
				this.hoverLoc = new Vector2(screenX, screenY);
				this.hoverText = guic.getHoverText();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		if(this.isbuilding)
		{
			this.toBuild = this.getStationBuildFromInt(this.toBuildPos += amount);
			this.building.setBuilding(this.toBuild);
		}
		return false;
	}

	public StationBase getStationBuildFromInt(int id)
	{
		id %= 9;
		id = Math.abs(id);
		if(id == 0)
			return new CoalDump(new Vector2(0, 0));
		else if(id == 1)
			return new Generator(new Vector2(0, 0));
		else if(id == 2)
			return new TrainingStation(new Vector2(0, 0));
		else if(id == 3)
			return new RawMaterials(new Vector2(0, 0));
		else if(id == 4)
			return new ScreenProcessing(new Vector2(0, 0));
		else if(id == 5)
			return new CaseProcessing(new Vector2(0, 0));
		else if(id == 6)
			return new ElectronicsProcessing(new Vector2(0, 0));
		else if(id == 7)
			return new PackagingStation(new Vector2(0, 0));
		else if(id == 8)
			return new ShippingStation(new Vector2(0, 0));
		return null;
	}
}