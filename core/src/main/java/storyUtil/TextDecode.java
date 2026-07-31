package storyUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

public class TextDecode {

	private TextDecode() {}
	
	private static Map<Integer, DialogNode> story = new HashMap<>();
	// TODO make this take the language the player selected
	private static Locale locale = Locale.of("en");

	private static I18NBundle bundle = I18NBundle.createBundle(
		    Gdx.files.internal("translation/translation"), locale
		);
	
	static {
		generateStory();
	}
	
	private static void generateStory() {
		story.put(1, (new DialogNode("retryButton")));
		story.put(2, (new DialogNode("quitButton")));
		story.put(3, (new DialogNode("hero.hey", new Integer[] {4})));
		story.put(4, (new DialogNode("hero.askWhoYouAre", new Integer[] {4,5,6,7})));
		story.put(5, (new DialogNode("player.imFriend")));
		story.put(6, (new DialogNode("player.imEnemy")));
		story.put(7, (new DialogNode("player.imhi")));
		
	}
/**
 *  this is to decode the translations, see {@link Dialogue#generateStory()} for all IDs
 *  
 * @param id the ID of the line you want to show
 * @throws IllegalArgumentException if the ID you passed doesn't exist
 * @return String - the line you called
 */
	public static String getText(int id) {
		DialogNode node = story.get(id);
		if (node == null) throw new IllegalArgumentException("the id " + id + " doesn't exist");
		
		// TEMP when testing re-enable the line below
//	    Util.log("returned " + node.text + " here");
		return bundle.get(node.text);
	}
	
	/**
	 *  this is to get each nodes next node ID, see {@link Dialogue#generateStory()} for all IDs
	 *  
	 * @param id the ID of the line you want to see the next line of
	 * @throws IllegalArgumentException if the ID you passed doesn't exist
	 * @return int - the ID of the line that links for the line you called
	 */
	public static Integer[] getNextNodes(int id) {
		DialogNode node = story.get(id);
		if (node == null) throw new IllegalArgumentException("the id " + id + " doesn't exist");
		
		// TEMP when testing re-enable the line below
//	    Util.log("returned " + node.nextNode + " here");
		return node.nextNodes;
	}
	
}