package UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Physics;

import debug.Debug;
import entities.Player;
import entities.Traps;


public class MainGame implements Screen {
	
	
	SpriteBatch batch;
	Texture backGround;
	Player player;
	Debug debug;
	BitmapFont debugfont;
	Physics physics;
	float floorLevel; 
	FitViewport viewport;
	OrthographicCamera camera;
	ShapeRenderer shapeRenderer;
	Traps trap;
	
	public MainGame(){
		player = new Player();
		trap = new Traps();
		debug = new Debug();
	    physics = new  Physics();
		batch = new SpriteBatch();
	    backGround = new Texture("main_game_bg.jpeg");
		debugfont = new BitmapFont();
		floorLevel = 50;
		shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void show () {
	     camera = new OrthographicCamera();
		 viewport = new FitViewport(800, 600, camera);
	}

	@Override
	public void render (float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		player.syncHitBox(player.hitBox);;
		trap.syncHitBox(trap.hitBox);
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		player.move(deltaTime, physics);
		physics.gravity(player,deltaTime,floorLevel);
		
		physics.airRis(player, deltaTime);
		trap.interact(player);
		
		batch.begin();
		
		batch.draw(backGround, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		batch.draw(
			    player.texture,                        // 1. The texture
			    player.position.x,           // 2. Target X on screen
			    player.position.y,           // 3. Target Y on screen
			    64f,                        // 4. Width to draw on screen (adjust as needed)
			    64f,                        // 5. Height to draw on screen (adjust as needed)
			    0,                          // 6. Source X start (left side of texture)
			    0,                          // 7. Source Y start (top side of texture)
			    player.texture.getWidth(),             // 8. Source Width (use full texture width)
			    player.texture.getHeight(),            // 9. Source Height (use full texture height)
			    !player.facingLeft,        // 10. Flip horizontally? (True if NOT facing right)
			    false                       // 11. Flip vertically? (False, keep right-side up)
			);
		 
		batch.end();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
   	    	debug.isdebug = !debug.isdebug;
   	   }
		debug.showDebug(batch, player, debugfont, viewport, shapeRenderer, trap, camera);
		
	
	}
		

		
	
	@Override
	public void resize (int width, int height) {
		viewport.update(width, height,true);
	}
	@Override
	public void pause () {
		
	}
	@Override
	public void resume () {
		
	}
	@Override
	public void hide () {
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		debugfont.dispose();
		backGround.dispose();
		shapeRenderer.dispose();
		player.texture.dispose();
		trap.texture.dispose();
	}
}
