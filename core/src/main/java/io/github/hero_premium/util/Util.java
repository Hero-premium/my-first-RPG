package io.github.hero_premium.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Random;

public final class Util {
    public static final int LOGGING_LEVEL = Logger.DEBUG;
    public static final Random RANDOM = new Random();
    private static final ObjectMap<Integer, Boolean> keyPresses = new ObjectMap<>();
    private static final Logger LOGGER = new Logger(Util.class.getName(), LOGGING_LEVEL);

    private Util() {
        throw new AssertionError("No util.Util instance for you!");
    }

    public static boolean isKeyJustReleased(int key) {
        if (keyPresses.get(key) == null) {
            keyPresses.put(key, false);
            return false;
        }
        if (Gdx.input.isKeyPressed(key)) {
            keyPresses.put(key, true);
        } else if (keyPresses.get(key)) {
            keyPresses.put(key, false);
            LOGGER.debug("Release detected - true returned");
            return true;
        }
        return false;
    }

    public static int requireNonNegative(int number) {
        return requireNonNegative(number, "this number cannot be negative");
    }

    public static int requireNonNegative(int number, String msg) {
        if (number < 0)
            throw new IllegalArgumentException(msg);
        return number;
    }

    public static float requireNonNegative(float number) {
        return requireNonNegative(number, "this number cannot be negative");
    }

    public static float requireNonNegative(float number, String msg) {
        if (number < 0)
            throw new IllegalArgumentException(msg);
        return number;
    }
}
