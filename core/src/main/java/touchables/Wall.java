package touchables;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Textures;

import entities.Entity;

public class Wall extends Touchable {

	public Wall() {
		super(Textures.PlaceHolder, Integer.MAX_VALUE, 0, (new Rectangle(0, 0, 50, 50)));
	}

	public void wall(Entity entity) {
		if (isEntityInside(entity)) {
			entity.hitBox.x = hitBox.x - entity.hitBox.width;

		}
	}

	@Override
	public void update(Entity entity) {
		wall(entity);
	}
}
