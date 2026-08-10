package util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

import entities.Entity;
import entities.GateKeeper;
import entities.Ghost;
import entities.Hero;
import touchables.Touchable;
import touchables.Wall;

public class Objects {
	private static List<Entity> entities;

	public static Hero hero;
	public static Ghost ghost;
	public static GateKeeper gateKeeper;

	private static List<Touchable> touchables;

	public static Wall wall;
	public static Touchable stopPlayer;

	public static List<Entity> generateEntities() {
		entities = new ArrayList<>();

		hero = new Hero();
		ghost = new Ghost();
		gateKeeper = new GateKeeper();

		entities.add(ghost);
		entities.add(hero);
		entities.add(gateKeeper);

		return entities;
	}

	public static List<Touchable> generateTouchables() {
		touchables = new ArrayList<>();

		wall = new Wall();
		stopPlayer = new Touchable(null, 1, 0, (new Rectangle(100, 100, 200, 800)));

		// TEMP remove ones you start using tiles or give it it's own method
		wall.hitBox.set(-350, 50, 200, 700);

		touchables.add(wall);
		touchables.add(stopPlayer);

		return touchables;
	}

}
