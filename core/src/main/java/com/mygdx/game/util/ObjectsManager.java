package com.mygdx.game.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import com.mygdx.game.debug.Debug;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.GateKeeper;
import com.mygdx.game.entities.Ghost;
import com.mygdx.game.entities.Hero;
import com.mygdx.game.storyutil.StoryDisplay;
import com.mygdx.game.touchables.Touchable;
import com.mygdx.game.touchables.Wall;
import com.mygdx.game.world.Physics;

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

        stopPlayer = new Touchable(null, 1, 0, (new Rectangle(100, 100, 200, 800)));

        // TEMP remove ones you start using tiles or give it its own method
        wall.hitBox.set(-350, 50, 200, 700);

        touchables.add(wall);
        touchables.add(stopPlayer);
    }

    public void update(float delta) {
        Physics.applyPhysics(entities, delta, 50);
        for (Entity object : entities) {
            object.update();
        }

        for (Touchable touchable : touchables) {
            for (Entity entity : entities) {
                touchable.update(entity);

                if (touchable == stopPlayer && touchable.isEntityInside(hero)) {
                    stopPlayer.usages = stopPlayer.maxUsage;
                    hero.movementLocked = true;
                    storyDisplay.setStoryActive(true);
                    gateKeeper.facingLeft = true;
                }
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
