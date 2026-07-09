package world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import entities.Entity;
import entities.Player;

public class Touch extends Entity{
    
	public Touch() {
	super( 10, "Hero", false, false, true,  new Vector2 (400,50), 0, new Vector2 (0,0), new Rectangle (0f,0f,50f,60f)) ;
	}
	 //   gold,name                                     speed      position at start        velocity             hitbox            
		boolean playerisinside = false;
		
		 boolean isEntityInside(Entity entity) {
			return  (entity.hitBox.overlaps(hitBox));
		}
		
		public void springTrap(Entity entity) {
			 if (!isEntityInside(entity)) {
				 playerisinside = false;
			 }
			 if (playerisinside) {
				 return; }
			 
			 if (isEntityInside(entity)) {
				 playerisinside = true;
				 entity.velocity.y += 1000;
				  
			 }
			
		}
		public void stopPLayerMovment(Player entity) {
			if (isEntityInside(entity)) {
				entity.isAllowedToMove = false;
				isAlive = false;
			}
		}
}
