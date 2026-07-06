package debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entities.Entity;
import entities.Traps;

public class Debug {
    public boolean isdebug = true; 
	public void showDebug(SpriteBatch batch,Entity player, BitmapFont debugfont,FitViewport viewport,ShapeRenderer shapeRenderer,Traps trap,OrthographicCamera camera) {
		if (isdebug) {
		batch.begin();
		debugfont.draw(batch, "Player x " + player.position.x + " / Player y " + player.position.y , 20f, viewport.getWorldHeight() - 20f);
		debugfont.draw(batch, "FPS " + Gdx.graphics.getFramesPerSecond(), 20f, viewport.getWorldHeight() - 35f);
		debugfont.draw(batch, "Player velocityX " + player.velocity.x + " / Player velocityY " + player.velocity.y , 20f, viewport.getWorldHeight() - 50f);
        batch.end();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(
		    player.position.x,
		    player.position.y,
		    player.hitBox.width,
		    player.hitBox.height
		);
		
		shapeRenderer.rect(
			    trap.position.x,
			    trap.position.y,
			    trap.hitBox.width,
			    trap.hitBox.height
			);
  
		shapeRenderer.end();
	}
	}
}
