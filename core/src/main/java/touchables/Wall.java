package touchables;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Textures;

import entities.Entity;

public class Wall extends Touchable {

	public Wall() {
		super(Textures.PlaceHolder, Integer.MAX_VALUE, 0, (new Rectangle(0, 0, 50, 50)));
	}

	public void wall(Entity entity) {
		if (!isEntityInside(entity)) return;

		Rectangle e = entity.hitBox;

		float overlapLeft   = (e.x + e.width) - hitBox.x;
		float overlapRight  = (hitBox.x + hitBox.width) - e.x;
		float overlapBottom = (e.y + e.height) - hitBox.y;
		float overlapTop    = (hitBox.y + hitBox.height) - e.y;

		float minOverlapX = Math.min(overlapLeft, overlapRight);
		float minOverlapY = Math.min(overlapBottom, overlapTop);

		if (minOverlapX < minOverlapY) {
			// push out horizontally
			e.x += (overlapLeft < overlapRight) ? -minOverlapX : minOverlapX;
		} else {
			// push out vertically
			e.y += (overlapBottom < overlapTop) ? -minOverlapY : minOverlapY;
		}
	}

	@Override
	public void update(Entity entity) {
		wall(entity);
	}
}
