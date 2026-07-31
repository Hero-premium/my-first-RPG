package UI;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Assets;

import debug.Debug;
import entities.Entity;
import entities.GateKeeper;
import entities.Ghost;
import entities.Hero;
import touchables.Touchable;
import touchables.Wall;
import world.Physics;

public class MainGame implements Screen {

	private SpriteBatch batch;
	private Hero hero;
	private Ghost ghost;
	private GateKeeper gateKeeper;
	private Debug debug;
	private Physics physics;
	final static float FLOOR_LEVEL = 50;
	private FitViewport viewport;
	private OrthographicCamera camera;
	private Wall wall;
	private Touchable stopPlayer;
	private List<Entity> objects;
	private List<Touchable> touchables;
	private Stage stage;

	boolean storyOn = false;

	public MainGame() {
		hero = new Hero();
		ghost = new Ghost();
		wall = new Wall();
		stopPlayer = new Touchable(null, 1, 0, (new Rectangle(100, 100, 200, 700)));
		gateKeeper = new GateKeeper();

		debug = new Debug();
		physics = new Physics();
		batch = new SpriteBatch();
		stopPlayer.hitBox.setSize(200, 800);
		wall.hitBox.set(-350, 50, 200, 700);

		objects = new ArrayList<Entity>();
		objects.add(ghost);
		objects.add(hero);
		objects.add(gateKeeper);

		touchables = new ArrayList<Touchable>();
		touchables.add(wall);
		touchables.add(stopPlayer);

	}

	@Override
	public void show() {
		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 600, camera);

		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);

		Assets.mainMenu.setLooping(true);
		Assets.mainMenu.setVolume(0.1f);
		Assets.mainMenu.play();

	}

	@Override
	public void render(float delta) {

		ScreenUtils.clear(0, 0, 0, 1);

		hero.move(delta);
		ghost.move(delta, hero);

		for (Entity object : objects) {
			physics.airRis(object, delta);
			
			if (!(object instanceof Ghost)) {
				physics.gravity(object, delta, FLOOR_LEVEL);
				object.velocityClamp();
			}
		}

		objects.removeIf(object -> !object.isAlive);

		for (Touchable touchable : touchables) {
			for (Entity entity : objects) {
				touchable.update(entity);
			}
			if (touchable == stopPlayer)
				if (touchable.isEntityInside(hero)) {
					stopPlayer.useages = stopPlayer.maxUsage;
					hero.setMovementLocked(true);
					storyOn = true;
					gateKeeper.facingLeft = true;
				}
		}

		if (storyOn) {
			// story.lunchStory(player, gateKeeper, dialogLabel, takeInput, stage);
		}

		touchables.removeIf(object -> object.useages >= object.maxUsage);

		camera.position.set(hero.hitBox.x + 350, 300, 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		batch.draw(Assets.backGround, hero.hitBox.x - 50, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

		for (Entity object : objects) {
			if (object.texture != null)
				object.draw(batch);
		}

		for (Touchable object : touchables) {
			if (object.texture != null)
				batch.draw(object.texture, object.hitBox.x, object.hitBox.y, object.hitBox.width, object.hitBox.height);
		}

		batch.end();

		stage.act(delta);
		stage.draw();

		// TEMP for me to debug remove when you send to someone
		debug.showDebug(batch, hero, viewport, objects, touchables, camera);

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void pause() {
		Assets.mainMenu.pause();
	}

	@Override
	public void resume() {
		Assets.mainMenu.play();
	}

	@Override
	public void hide() {
		Assets.mainMenu.stop();
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		debug.dispose();
		Assets.dispose();
	}
}
