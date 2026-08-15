package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import com.badlogic.gdx.Gdx;


public class Util {
	private static boolean isPressed = false;

	public static final Random rand = new Random();

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");


	public static boolean isKeyJustReleased(int key) {
		if (Gdx.input.isKeyPressed(key)) {
			isPressed = true;

		} else if (isPressed) {

			isPressed = false;
			log("Release detected - true returned");
			return true;
		}
		return false;
	}

	public final static void log(Object obj) {
		String time = LocalDateTime.now().format(FORMAT);
		System.out.println("[" + time + "] " + obj);
	}

}
