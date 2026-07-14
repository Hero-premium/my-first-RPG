package world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Textures;

import entities.Entity;
import entities.Player;

public class Touch extends Entity {
    
	public Touch() {
	super( 1, "Hero", false, false, true, 0, new Vector2 (0,0), new Rectangle (400f,500f,50f,60f), Textures.closedSpringTrap) ;
	}
	 //   gold,name                    speed           velocity             hitbox                   
		boolean playerisinside = false;
		
		 boolean isEntityInside(Entity entity) {
			return (entity.hitBox.overlaps(hitBox));
		}
		
		public void springTrap(Entity entity) {
			 if (gold < 0) return; 
			 
			 if (isEntityInside(entity)) {
				 gold--;
				 entity.velocity.y += 100;
				 texture = Textures.openedSpringTrap;
			 }
			
		}
		public boolean stopPLayerMovment(Player entity) {
			if (isEntityInside(entity)) {
				entity.isAllowedToMove = false;
				isAlive = false;
				return true;
			} else {
			return false;
		}
		}
		
		public void wall(Entity entity) {
			if (isEntityInside(entity)) {
				entity.hitBox.x = hitBox.x - entity.hitBox.width;
				
			}
		}
		
}
