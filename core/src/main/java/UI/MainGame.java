package UI;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Textures;

import debug.Debug;
import entities.Entity;
import entities.Player;
import world.Physics;
import world.Touch;


public class MainGame implements Screen {
	
	
	SpriteBatch batch;
	Player player;
	Debug debug;
	Physics physics;
	float floorLevel; 
	FitViewport viewport;
	OrthographicCamera camera;
	Touch springTrap;
	Touch stopPlayer;
	List<Entity> objects;
	
	public MainGame(){
		player = new Player();
		springTrap = new Touch();
		stopPlayer = new Touch();
		stopPlayer.position.add(50,100);
		debug = new Debug();
	    physics = new Physics();
		batch = new SpriteBatch();
		floorLevel = 50;
		
		
		objects = new ArrayList<Entity>();
		objects.add(player);
		objects.add(springTrap);
		objects.add(stopPlayer);
		
	}
	
	@Override
	public void show () {
	     camera = new OrthographicCamera();
		 viewport = new FitViewport(800, 600, camera);
	}

	@Override
	public void render (float delta) {
		
		ScreenUtils.clear(0, 0, 0, 1);
		
		
		final float deltaTime = Gdx.graphics.getDeltaTime();
		
		player.move(deltaTime, physics);
		
		
		for (Entity object : objects) {
			
			object.syncHitBox(object.hitBox);
			physics.airRis(object, deltaTime);
			physics.gravity(object, deltaTime, floorLevel);
			
			
		}
		
		objects.removeIf(object -> !object.isAlive);
		
		springTrap.springTrap(player);
		stopPlayer.stopPLayerMovment(player);
		camera.position.set(player.position.x + 350, 300 ,0);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		
		batch.begin();
		
		batch.draw(Textures.backGround, player.position.x - 50 ,0, viewport.getWorldWidth(), viewport.getWorldHeight());
		batch.draw(
			    Textures.player,             // 1. The texture
			    player.position.x,          // 2. Target X on screen
			    player.position.y,          // 3. Target Y on screen
			    64f,                        // 4. Width to draw on screen (adjust as needed)
			    64f,                        // 5. Height to draw on screen (adjust as needed)
			    0,                          // 6. Source X start (left side of texture)
			    0,                          // 7. Source Y start (top side of texture)
			    Textures.player.getWidth(),             // 8. Source Width (use full texture width)
			    Textures.player.getHeight(),            // 9. Source Height (use full texture height)
			    !player.facingLeft,         // 10. Flip horizontally? (True if NOT facing right)
			    false                       // 11. Flip vertically? (False, keep right-side up)
			);
		batch.draw(
			    springTrap.texture,             // 1. The texture
			    springTrap.position.x,          // 2. Target X on screen
			    springTrap.position.y,          // 3. Target Y on screen
			    64f,                        // 4. Width to draw on screen (adjust as needed)
			    64f                        // 5. Height to draw on screen (adjust as needed)
			    );
		batch.end();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
   	    	debug.isdebug = !debug.isdebug;
   	    
   	   }
		debug.showDebug(batch, player, viewport, objects, camera);
		
	
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
		Textures.backGround.dispose();
		Textures.player.dispose();
		debug.dispose();
		//trap.texture.dispose();
	}
}
