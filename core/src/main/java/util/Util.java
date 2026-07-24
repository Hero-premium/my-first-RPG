package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
// I'm planning on putting more utility things in this class
public class Util {

	public final static Random rand = new Random();
	
	private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void log(String message) {
        String time = LocalDateTime.now().format(FORMAT);
        System.out.println("[" + time + "] " + message);
    }

}
