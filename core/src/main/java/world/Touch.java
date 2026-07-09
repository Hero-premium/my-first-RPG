package world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Textures;

import entities.Entity;
import entities.Player;

public class Touch extends Entity{
    
	public Touch() {
	super( 1, "Hero", false, false, true,  new Vector2 (400,50), 0, new Vector2 (0,0), new Rectangle (0f,0f,50f,60f), Textures.closedSpringTrap) ;
	}
	 //   gold,name                                     speed      position at start        velocity             hitbox            
		boolean playerisinside = false;
		
		 boolean isEntityInside(Entity entity) {
			return  (entity.hitBox.overlaps(hitBox));
		}
		
		public void springTrap(Entity entity) {
			 if (gold < 0) return; 
			 
			 if (isEntityInside(entity)) {
				 gold--;
				 entity.velocity.y += 100;
				 texture = Textures.openedSpringTrap;
			 }
			
		}
		public void stopPLayerMovment(Player entity) {
			if (isEntityInside(entity)) {
				entity.isAllowedToMove = false;
				isAlive = false;
			}
		}
}
