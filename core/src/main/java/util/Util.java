package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public final class Util {

    public static final Random RANDOM = new Random();
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static boolean isPressed = false;
    private static final Logger logger = new Logger("", Logger.DEBUG);

    private Util() {
        throw new AssertionError("No util.Util instance for you!");
    }

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

    public static void log(Object obj) {
        logger.info(logFormat(obj));
    }

    private static String logFormat(Object obj) {
        return "[" + LocalDateTime.now().format(FORMAT) + "] " + obj;
    }

    public static void logWarn(Object obj) {
        logger.error(logFormat("WARNING - " + obj));
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
