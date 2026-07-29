package storyUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

public class Dialogue {

	private Dialogue() {}
	
	private static Map<Integer, DialogNode> story = new HashMap<>();
	// TODO make this take the language the player selected
	private static Locale locale = Locale.of("ar");

	private static I18NBundle bundle = I18NBundle.createBundle(
		    Gdx.files.internal("translation/translation"), locale
		);
	
	static {
		generateStory();
	}
	
	private static void generateStory() {
		story.put(1, (new DialogNode("gatekeeper.askName")));
		
	}
/**
 *  this is to decode the translations, see {@link Dialogue#generateStory()} for all IDs
 *  
 * @param id the ID of the line you want to show
 * @throws IllegalArgumentException if the ID you passed doesn't exist
 * @return the line you called
 */
	public static String getText(int id) {
		DialogNode node = story.get(id);
		if (node == null) throw new IllegalArgumentException("the id " + id + " doesn't exist");
	
		return bundle.get(node.text);
	}
	
}
