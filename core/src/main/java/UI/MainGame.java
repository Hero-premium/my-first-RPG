package UI;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Textures;

import debug.Debug;
import entities.Entity;
import entities.GateKeeper;
import entities.Player;
import touchables.Touchable;
import touchables.Wall;
import util.Dialogs;
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
    Dialogs story;
    Stage stage;
    Skin skin;
    Label dialogLabel;

	public MainGame() {
		player = new Player();
		wall = new Wall();
		stopPlayer = new Touchable(null, 1, 0, (new Rectangle(100, 100, 200, 700)));
		gatekeeper = new GateKeeper();
        story = new Dialogs();

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
        wall.texture = null;
      
	}

	@Override
	public void show() {
		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 600, camera);
		
		skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
		stage = new Stage(viewport);

		dialogLabel = new Label("", skin);
		dialogLabel.setVisible(true);
		dialogLabel.setWrap(true);
		
		Table table = new Table();
		table.setFillParent(true); // table fills the whole stage/screen
		table.bottom(); 
		table.add(dialogLabel).width(700).pad(10);
		stage.addActor(table);
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
			player.setMovmentLocked(true);
			story.lunchStory(player, dialogLabel);
		}

		touchables.removeIf(object -> object.useages >= object.MAX_USAGE);


		camera.position.set(player.hitBox.x + 350, 300, 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		batch.draw(Textures.backGround, player.hitBox.x - 50, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		for (Entity object : objects)
		batch.draw(
				object.texture,
				object.hitBox.x,
				object.hitBox.y,
				64f,
				64f,
				0,
				0,
				object.texture.getWidth(),
				object.texture.getHeight(),
				!object.facingLeft,
				false
		);
		for (Touchable object : touchables) {
			if (object.texture != null) 
			batch.draw(object.texture, object.hitBox.x, object.hitBox.y, object.hitBox.width, object.hitBox.height);
		}

		batch.end();
		
		stage.act(delta);
		stage.draw();

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
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
	
	@Override
	public void dispose() {
		skin.dispose();
		stage.dispose();
		batch.dispose();
		debug.dispose();
		Textures.dispose();
	}
}
