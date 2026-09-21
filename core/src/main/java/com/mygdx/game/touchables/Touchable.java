package com.mygdx.game.touchables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import com.mygdx.game.entities.Entity;

public class Touchable {

    public final int maxUsage;
    public final Texture texture;
    public int usages;
    public final Rectangle hitBox;
    public boolean entityInside = false;

    public Touchable(Texture texture, int maxUsage, int useages, Rectangle hitBox) {

        this.texture = texture;
        this.maxUsage = maxUsage;
        this.usages = useages;
        this.hitBox = hitBox;
    }

    public boolean isEntityInside(Entity entity) {
        return (entity.hitBox.overlaps(hitBox));
    }

    // to be overridden
    public void update(Entity entity) {
    }

    public void draw(SpriteBatch batch) {
        if (texture != null)
            batch.draw(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }
}
