package touchables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import entities.Entity;

public class Touchable {

	public Texture texture;
	public final int maxUsage;
	public int useages;
	public Rectangle hitBox;
	public boolean EntityInside = false;

	public Touchable(Texture texture, int maxUsage, int useages, Rectangle hitBox) {

		this.texture = texture;
		this.maxUsage = maxUsage;
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
