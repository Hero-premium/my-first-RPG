package UI;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Textures;

import debug.Debug;
import entities.Entity;
import entities.GateKeeper;
import entities.Player;
import touchables.Touchable;
import touchables.Wall;
import util.WorldUtil;
import world.Physics;

public class MainGame implements Screen {

	SpriteBatch batch;
	Player player;
	GateKeeper gatekeeper;
	Debug debug;
	Physics physics;
	final static float floorLevel = 50;
	FitViewport viewport;
	OrthographicCamera camera;
	Wall wall;
	Touchable stopPlayer;
	List<Entity> objects;
	List<Touchable> touchables;

	public MainGame() {
		player = new Player();
		wall = new Wall();
		stopPlayer = new Touchable(Textures.PlaceHolder, 1, 0, (new Rectangle(100, 100, 200, 700)));
		gatekeeper = new GateKeeper();

		debug = new Debug();
		physics = new Physics();
		batch = new SpriteBatch();
		stopPlayer.hitBox.setSize(200, 700);
		wall.hitBox.set(-350, 50, 200, 700);

		objects = new ArrayList<Entity>();
		objects.add(player);
		objects.add(gatekeeper);

		touchables = new ArrayList<Touchable>();
		touchables.add(wall);
		touchables.add(stopPlayer);

	}

	@Override
	public void show() {
		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 600, camera);
	}

	@Override
	public void render(float delta) {

		ScreenUtils.clear(0, 0, 0, 1);

		player.move(delta);

		for (Entity object : objects) {

			physics.airRis(object, delta);
			physics.gravity(object, delta, floorLevel);
			object.velocityClamp();
		}

		objects.removeIf(object -> !object.isAlive);

		// FIXME add a way to loop through all entities
		for (Touchable object : touchables) {
			object.update(player);
		}

		if (stopPlayer.isEntityInside(player)) {
			player.setAllowedToMove(false);
		}

		touchables.removeIf(object -> object.useages >= object.MAX_USAGE);

		if (stopPlayer.isEntityInside(player)) {
			WorldUtil.startDialog(gatekeeper, player);
		}

		camera.position.set(player.hitBox.x + 350, 300, 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		batch.draw(Textures.backGround, player.hitBox.x - 50, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		batch.draw(player.texture, // 1. The texture
				player.hitBox.x, // 2. Target X on screen
				player.hitBox.y, // 3. Target Y on screen
				64f, // 4. Width to draw on screen (adjust as needed)
				64f, // 5. Height to draw on screen (adjust as needed)
				0, // 6. Source X start (left side of texture)
				0, // 7. Source Y start (top side of texture)
				player.texture.getWidth(), // 8. Source Width (use full texture width)
				player.texture.getHeight(), // 9. Source Height (use full texture height)
				!player.facingLeft, // 10. Flip horizontally? (True if NOT facing right)
				false // 11. Flip vertically? (False, keep right-side up)
		);
		for (Touchable object : touchables) {
			batch.draw(object.texture, object.hitBox.x, object.hitBox.y, object.hitBox.width, object.hitBox.height);
		}

		batch.end();

		// TEMP for me to debug remove when you send to someone
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			debug.isdebug = !debug.isdebug;

		}
		debug.showDebug(batch, player, viewport, objects, touchables, camera);

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		debug.dispose();
		Textures.dispose();
	}
}
