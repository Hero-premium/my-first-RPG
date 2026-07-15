package touchables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import entities.Entity;

public class Touchable {

	public Texture texture;
	public int MAX_USAGE;
	public int useages;
	public Rectangle hitBox;
	public boolean EntityInside = false;

	public Touchable(Texture texture, int MAX_USAGE, int useages, Rectangle hitBox) {

		this.texture = texture;
		this.MAX_USAGE = MAX_USAGE;
		this.useages = useages;
		this.hitBox = hitBox;
	}

	public boolean isEntityInside(Entity entity) {
		return (entity.hitBox.overlaps(hitBox));
	}

	// to be overridden
	public void update(Entity entity) {
	}

}
