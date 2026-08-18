package UI;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Assets;

import combat.BattleManager;
import debug.Debug;
import entities.Entity;
import storyutil.StoryDisplay;
import touchables.Touchable;
import util.Objects;
import world.Physics;

public class MainGame implements Screen {

	private final float floorLevel = 50;
	private SpriteBatch batch;
	private Debug debug;
	private FitViewport viewport;
	private OrthographicCamera camera;
	private List<Entity> entities;
	private List<Touchable> touchables;
	private Stage stage;
	private StoryDisplay storyDisplay;

	boolean battleOn = false;
	boolean storyOn = true;

	public MainGame() {

		entities = Objects.generateEntities();
		touchables = Objects.generateTouchables();

		debug = new Debug();
		batch = new SpriteBatch();
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		debug.dispose();
		Assets.dispose();
	}

	@Override
	public void hide() {
		Assets.mainMenu.stop();
	}

	@Override
	public void pause() {
		Assets.mainMenu.pause();
	}

	@Override
	public void render(float delta) {

		ScreenUtils.clear(0, 0, 0, 1);

		Objects.hero.move(delta);
		Objects.ghost.move(delta, Objects.hero);

		Physics.applyPhysics(entities, delta, floorLevel);

		entities.removeIf(object -> !object.isAlive);

		for (Touchable touchable : touchables) {
			for (Entity entity : entities) {
				touchable.update(entity);
			}
			if (touchable == Objects.stopPlayer) {
				if (touchable.isEntityInside(Objects.hero)) {
					Objects.stopPlayer.useages = Objects.stopPlayer.maxUsage;
					Objects.hero.setMovementLocked(true);
					storyOn = true;
					Objects.gateKeeper.facingLeft = true;
				}
			}
		}

		if (storyOn) {
			storyDisplay.launchStory();
		}
		if (battleOn) {
			BattleManager.launchBattle(Objects.hero, Objects.gateKeeper, stage);
			battleOn = false;
		}

		touchables.removeIf(object -> object.useages >= object.maxUsage);

		camera.position.set(Objects.hero.hitBox.x + 350, 300, 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		batch.draw(Assets.backGround, Objects.hero.hitBox.x - 50, 0, viewport.getWorldWidth(),
				viewport.getWorldHeight());

		for (Entity object : entities) {
			if (object.texture != null) {
				object.draw(batch);
			}
		}

		for (Touchable object : touchables) {
			if (object.texture != null) {
				batch.draw(object.texture, object.hitBox.x, object.hitBox.y, object.hitBox.width, object.hitBox.height);
			}
		}

		// TEMP for me to debug remove when you send to someone
		debug.showInformations(batch, Objects.hero, viewport, camera);

		batch.end();

		debug.showHitboxes(camera, entities, touchables);

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		stage.getViewport().update(width, height);
	}

	@Override
	public void resume() {
		Assets.mainMenu.play();
	}

	@Override
	public void show() {
		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 600, camera);

		stage = new Stage(new FitViewport(800, 600));
		storyDisplay = new StoryDisplay(stage);
		Gdx.input.setInputProcessor(stage);

		Assets.mainMenu.setLooping(true);
		Assets.mainMenu.setVolume(0.1f);
		Assets.mainMenu.play();
	}
}
