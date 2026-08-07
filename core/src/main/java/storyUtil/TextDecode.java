package storyUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import util.Util;

public class TextDecode {

	private TextDecode() {
	}

	private static int internalID = 1;

	private static Map<Integer, DialogNode> story = new HashMap<>();
	// TODO make this take the language the player selected
	private static Locale locale = Locale.of("en");

	private static I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("translation/translation"), locale);

	static {
		generateStory();
	}

	private static void addToStory(Integer id, String line, Integer[] nodesTree) {
		if (story.containsKey(id))
			throw new IllegalStateException("the id :" + id + " already exists");
		if (nodesTree != null && nodesTree.length - 1 > StoryDisplay.BUTTONS_COUNT)
			throw new IllegalArgumentException("the amount of nodes passed " + nodesTree.length
					+ " are larger than the amounts of buttons we have");
		if (id == null || line == null)
			throw new IllegalArgumentException("the line or id are not applicant for being a null");
		if (id != internalID)
			Util.log("WARNING - the ID passed " + id + " does not match the internal ID " + internalID
					+ " make sure you did not make a mistake");
		story.put(id, (new DialogNode(line, nodesTree)));
		internalID++;
	}

	private static void generateStory() {
		addToStory(1, "retryButton", null);
		addToStory(2, "quitButton", null);
		addToStory(3, "hero.hey", new Integer[] { 4 });
		addToStory(4, "hero.askWhoYouAre", new Integer[] { 1, 4, 3, 7 });
		addToStory(5, "optionsNull", null);
		addToStory(6, "options0", new Integer[] {});
		addToStory(7, "options1", new Integer[] { 8 });
		addToStory(8, "options2", new Integer[] { 9, 10 });
		addToStory(9, "options3", new Integer[] { 6, 5, 8 });
		addToStory(10, "options4", new Integer[] { 1, 2, 3, 7 });
	}

	/**
	 * this is to decode the translations, see {@link TextDecode#generateStory()}
	 * for all IDs
	 * 
	 * @param id the ID of the line you want to show
	 * @throws IllegalArgumentException if the ID you passed doesn't exist
	 * @return String - the line you called
	 */
	public static String getText(Integer id) {
		DialogNode node = story.get(id);
		if (node == null)
			throw new IllegalArgumentException("the id " + id + " doesn't exist");

		return bundle.get(node.text);
	}

	/**
	 * this is to get each nodes next node ID, see
	 * {@link TextDecode#generateStory()} for all IDs
	 * 
	 * @param id the ID of the line you want to see the next line of
	 * @throws IllegalArgumentException if the ID you passed doesn't exist
	 * @return Integer[] - the ID of the line that links for the lines you called
	 */
	public static Integer[] getNextNodes(Integer id) {
		DialogNode node = story.get(id);
		if (node == null)
			throw new IllegalArgumentException("the id " + id + " doesn't exist");

		if (node.nextNodes != null)
			return node.nextNodes.clone();
		return null;
	}

}