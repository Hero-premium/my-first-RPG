package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Intefer;

public class Traps extends Entity implements Intefer {
    
	public Traps() {
	super( 10, "Hero", false, false, new Texture ("hollowKnight.png"),  new Vector2 (400,50), 128f, new Vector2 (0,0), new Rectangle (0f,0f,50f,60f)) ;
	}
	 //   gold,name,                                                          texture         position at start    speed     velocity             hitbox            
		boolean playerisinside = false;
		
		@Override
		public void interact(Entity entity) {
			 if (!entity.hitBox.overlaps(hitBox)) {
				 playerisinside = false;
			 }
			 if (playerisinside) {
				 return; }
			 
			 if (entity.hitBox.overlaps(hitBox)) {
				 playerisinside = true;
				 // hp -= 1;
				 entity.velocity.y += 1000;
				 return; 
			 }
			
		}

}
