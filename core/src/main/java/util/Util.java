package util;

import com.badlogic.gdx.Gdx;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public final class Util {

    public static final Random RANDOM = new Random();
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static boolean isPressed = false;

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
        String time = LocalDateTime.now().format(FORMAT);
        System.out.println("[" + time + "] " + obj);
    }

    public static void logWarn(Object obj) {
        log("\u001B[33m" + "WARNING - " + obj + "\u001B[0m");
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
