package UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartScreen implements Screen {

    private Stage stage;
    private BitmapFont buttonFont;
    private Texture texUp;
    private Texture texDown;
    private MainGame mainGame;
    private TextButton playButton;
    private SpriteBatch batch;
    private Texture backGround;
    private OrthographicCamera camera;
    private FitViewport viewport ;
    
    public StartScreen() {
    	
    	   camera = new OrthographicCamera();
  		 viewport = new FitViewport(800, 600, camera);

        stage = new Stage(new ScreenViewport());
        mainGame = new MainGame();
        buttonFont = new BitmapFont();
        batch = new SpriteBatch();
        backGround = new Texture("Gemini_Generated_Image_5e1rrh5e1rrh5e1r.png");
        
        // Generate button textures safely in RAM
        Pixmap pixmap = new Pixmap(200, 60, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#2c3e50")); 
        pixmap.fill();
        texUp = new Texture(pixmap);
        
        pixmap.setColor(Color.valueOf("#34495e")); 
        pixmap.fill();
        texDown = new Texture(pixmap);
        pixmap.dispose(); 

        TextureRegionDrawable drawableUp = new TextureRegionDrawable(texUp);
        TextureRegionDrawable drawableDown = new TextureRegionDrawable(texDown);

        TextButton.TextButtonStyle manualStyle = new TextButton.TextButtonStyle();
        manualStyle.up = drawableUp;       
        manualStyle.down = drawableDown;   
        manualStyle.font = buttonFont;     
        manualStyle.fontColor = Color.WHITE;

        playButton = new TextButton("PLAY GAME", manualStyle);
        playButton.setSize(200, 60);
        playButton.setPosition(200, 200);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(mainGame);

            } 
        });
        
        stage.addActor(playButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
    }

    @Override
    public void render(float delta) {
    	
    	ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
        batch.begin();
        batch.draw(backGround,0,0, viewport.getScreenWidth(), viewport.getScreenHeight());
        batch.end();
        
        
       
        stage.act(delta);
        stage.draw();

    }

    @Override 
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    
    @Override 
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
    @Override 
    public void dispose() {
    	batch.dispose();
        stage.dispose();
        buttonFont.dispose();
        texUp.dispose();
        texDown.dispose();
    }
}