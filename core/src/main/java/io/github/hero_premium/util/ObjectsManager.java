package io.github.hero_premium.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import io.github.hero_premium.debug.Debug;
import io.github.hero_premium.entities.Entity;
import io.github.hero_premium.entities.GateKeeper;
import io.github.hero_premium.entities.Ghost;
import io.github.hero_premium.entities.Hero;
import io.github.hero_premium.storyutil.StoryDisplay;
import io.github.hero_premium.touchables.Touchable;
import io.github.hero_premium.touchables.Wall;
import io.github.hero_premium.world.Physics;

public class ObjectsManager {

    public Hero hero;
    public GateKeeper gateKeeper;
    private Ghost ghost;
    private Wall wall;
    private Touchable stopPlayer;

    private Array<Entity> entities;
    private Array<Touchable> touchables;

    private StoryDisplay storyDisplay;

    public ObjectsManager createMainGameObjects(Stage stage) {
        storyDisplay = new StoryDisplay(stage);
        generateEntities();
        generateTouchables();
        return this;
    }

    private void generateEntities() {
        entities = new Array<>();

        hero = new Hero();
        ghost = new Ghost(hero);
        gateKeeper = new GateKeeper();

        entities.add(ghost);
        entities.add(hero);
        entities.add(gateKeeper);
    }

    private void generateTouchables() {
        touchables = new Array<>();

        wall = new Wall();

        stopPlayer = new Touchable(null, 1, 0, (new Rectangle(100, 100, 200, 800))) {
            @Override
            public void update(Entity player) {
                if (isEntityInside(hero)) {
                    usages = maxUsage;
                    hero.movement.movementLocked = true;
                    storyDisplay.setStoryActive(true);
                    gateKeeper.movement.facingLeft = true;
                }
            }
        };

        // TEMP remove ones you start using tiles or give it its own method
        wall.hitBox.set(-350, 50, 200, 700);

        touchables.add(wall);
        touchables.add(stopPlayer);
    }

    public void update(float delta) {
        // i figured a null check is cheaper than an isEmpty but that's just a guess
        for (Entity entity : entities) {
            Physics.applyPhysics(entity.movement, delta, 50);
        }

        for (Entity object : entities) {
            object.update();
        }

        for (Touchable touchable : touchables) {
            for (Entity entity : entities) {
                touchable.update(entity);
            }

        }

        storyDisplay.runStory();

        for (int i = 0; i < touchables.size; i++) {
            Touchable touchable = touchables.get(i);
            if (touchable.usages >= touchable.maxUsage) {
                touchables.removeIndex(i);
                i--;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (Entity draw : entities) {
            draw.draw(batch);
        }
        for (Touchable drawable : touchables) {
            drawable.draw(batch);
        }
    }

    public void showHitboxes(OrthographicCamera camera, Debug debug) {
        debug.showHitboxes(camera, entities, touchables);
    }
}
