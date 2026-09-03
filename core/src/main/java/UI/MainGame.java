package UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Assets;

import combat.BattleManager;
import debug.Debug;
import storyutil.TextManager;
import util.ObjectsManager;

public class MainGame implements Screen {

    // private final float floorLevel = 50;
    private SpriteBatch batch;
    private Debug debug;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private ObjectsManager objects;
    private Stage stage;
    private World world;

    @SuppressWarnings("unused")
    private Game game;

    public MainGame(Game game) {
        this.game = game;

        debug = new Debug();
        batch = new SpriteBatch();

        TextManager.setAction(16, () -> new BattleManager(stage).launchBattle(objects.hero, objects.gateKeeper));
        world = new World(new Vector2(9.11f, 0), true);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);

        stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);
        objects = new ObjectsManager().createMainGameObjects(stage);

        Assets.mainMenu.setLooping(true);
        Assets.mainMenu.setVolume(0.1f);
        Assets.mainMenu.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        objects.update(delta);
        world.step(delta, 6,2);

        camera.position.set(objects.hero.hitBox.x + 350, 300, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Assets.backGround, objects.hero.hitBox.x - 50, 0, viewport.getWorldWidth(),
            viewport.getWorldHeight());

        objects.draw(batch);

        // TEMP for me to debug remove when you send to someone
        debug.showInformations(batch, objects.hero, viewport, camera);

        batch.end();
        objects.showHitboxes(camera, debug);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height);
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
    public void resume() {
        Assets.mainMenu.play();
    }

    @Override
    public void dispose() {
        world.dispose();
        stage.dispose();
        batch.dispose();
        debug.dispose();
        Assets.dispose();
    }

}
