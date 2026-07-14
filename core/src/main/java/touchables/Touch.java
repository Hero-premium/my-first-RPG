/*
 * package touchables;
 * 
 * import com.badlogic.gdx.graphics.Texture; import
 * com.badlogic.gdx.math.Rectangle; import com.mygdx.game.Textures;
 * 
 * import entities.Entity; import entities.Player;
 * 
 * public class Touch extends Touchable {
 * 
 * 
 * 
 * public void springTrap(Entity entity) { if (useages >= MAX_USAGE) return;
 * 
 * if (isEntityInside(entity)) { useages++; entity.velocity.y += 100; texture =
 * Textures.openedSpringTrap; }
 * 
 * }
 * 
 * public boolean stopPlayerMovement(Player entity) { if
 * (!isEntityInside(entity)) { return false; } entity.isAllowedToMove = false;
 * 
 * return true; }
 * 
 * //TODO implement a wall that acts like a wall from all sides and remove this
 * 
 * 
 * }
 * 
 * }
 */