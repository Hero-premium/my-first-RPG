package storyUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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

	private static void addNewNode(Integer id, String line, Integer[] nodesTree) {
		if (id == null || line == null) {
			throw new NullPointerException("the line or id are not applicant for being a null");
		}
		if (nodesTree != null) {
			if (nodesTree.length - 1 > StoryDisplay.BUTTONS_COUNT) {
				throw new IllegalArgumentException("the amount of nodes passed " + nodesTree.length
						+ " are larger than the amounts of buttons we have");
			}
			if (nodesTree.length == 0) {
				throw new IllegalArgumentException("empty array on ID " + id);
			}
		}
		if (story.containsKey(id)) {
			throw new IllegalArgumentException("the id :" + id + " already exists");
		}
		if (!id.equals(internalID)) {
			Util.log("WARNING - the ID passed " + id + " does not match the internal ID " + internalID
					+ " make sure you did not make a mistake");
		}
		story.put(id, (new DialogNode(line, nodesTree)));
		internalID++;
	}

	private static void generateStory() {
		addNewNode(1, "retryButton", null);
		addNewNode(2, "quitButton", null);
		addNewNode(3, "kick", null);
		addNewNode(4, "swordSlash", null);
		addNewNode(5, "dodge", null);

	}

	private static DialogNode getNode(Integer id) {
		Objects.requireNonNull(id, "id cannot be null");
		DialogNode node = story.get(id);
		if (node == null) {
			throw new IllegalArgumentException("the id " + id + " doesn't exist");
		}
		return node;
	}

	/**
	 * this is to decode the translations, see {@link TextDecode#generateStory()}
	 * for all IDs
	 *
	 * @param id the ID of the line you want to show
	 * @throws IllegalArgumentException if the ID you passed doesn't exist
	 * @throws NullPointerException     if a null was passed
	 * @return String - the line you called
	 */
	public static String getText(Integer id) {
		DialogNode node = getNode(id);
		return bundle.get(node.text);
	}

	/**
	 * this is to get each nodes next node ID, see
	 * {@link TextDecode#generateStory()} for all IDs
	 *
	 * @param id the ID of the line you want to see the next line of
	 * @throws IllegalArgumentException if the ID you passed doesn't exist
	 * @throws NullPointerException     if a null was passed
	 * @return Integer[] - the ID of the line that links for the lines you called or
	 *         null - if there's no next node
	 */
	public static Integer[] getNextNodes(Integer id) {
		DialogNode node = getNode(id);
		if (node.nextNodes != null) {
			return node.nextNodes.clone();
		}
		return null;
	}

}