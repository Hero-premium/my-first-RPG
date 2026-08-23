package storyutil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import util.Util;

public final class TextManager {

	private static int internalID = 1;

	private static Map<Integer, DialogNode> story = new HashMap<>();

	// TODO make this take the language the player selected
	// FIXME translation to Arabic doesn't work because libGDX apparently doesn't support displaying it :/
	private static Locale locale = Locale.of("en");
	private static I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("translation/translation"), locale);

	static {
		generateStory();
	}

	private static void addNewNode(Integer id, String line, Integer[] nodesTree, Runnable action) {
		if (id == null || line == null) {
			throw new NullPointerException("the line or id are not applicant for being a null");
		}
		if (story.containsKey(id)) {
			throw new IllegalArgumentException("the id :" + id + " already exists");
		}
		if (nodesTree != null) {
			if (nodesTree.length == 0) {
				throw new IllegalArgumentException("empty array on ID " + id);
			}
			if (nodesTree.length - 1 > StoryDisplay.BUTTONS_COUNT) {
				throw new IllegalArgumentException("the amount of nodes passed " + nodesTree.length
						+ " are larger than the amounts of buttons we have");
			}
		}
		if (!id.equals(internalID)) {
			Util.logWarn("the ID passed " + id + " does not match the internal ID " + internalID
					+ " make sure you did not make a mistake");
		}
		story.put(id, (new DialogNode(line, nodesTree, action)));
		internalID++;
	}

	private static void addNewNode(Integer id, String line) {
		addNewNode(id, line, null, null);
	}

	@SuppressWarnings("unused")
	private static void addNewNode(Integer id, String line, Runnable action) {
		addNewNode(id, line, null, action);
	}

	private static void addNewNode(Integer id, String line, Integer[] nodes) {
		addNewNode(id, line, nodes, null);
	}

	private static void generateStory() {
		addNewNode(1, "retryButton");
		addNewNode(2, "quitButton");
		addNewNode(3, "kick");
		addNewNode(4, "swordSlash");
		addNewNode(5, "dodge");
		addNewNode(6, "", new Integer[] { 7 });
		addNewNode(7, "gatekeeper.welcome", new Integer[] { 8 });
		addNewNode(8, "player.motivation", new Integer[] { 9 });
		addNewNode(9, "gatekeeper.ask_name", new Integer[] { 10 });
		addNewNode(10, "gatekeeper.warn_mission", new Integer[] { 11 });
		addNewNode(11, "player.explain_moves", new Integer[] { 12 });
		addNewNode(12, "gatekeeper.askAbilities", new Integer[] { 13 });
		addNewNode(13, "player.dash", new Integer[] { 14 });
		addNewNode(14, "player.swordSlash", new Integer[] { 15 });
		addNewNode(15, "player.kick", new Integer[] { 16 });
		addNewNode(16, "gatekeeper.challenge");
	}

	/**
	 * this is to get each nodes next node ID, see
	 * {@link TextManager#generateStory()} for all IDs
	 *
	 * @param id the ID of the line you want to see the next line of
	 * @throws IllegalArgumentException if the ID you passed doesn't exist
	 * @throws NullPointerException     if a null was passed
	 * @return Integer[] - the ID of the line that links for the lines you called -
	 *         or null - if there's no next node
	 */
	public static Integer[] getNextNodes(Integer id) {
		return resolveNode(id).getNextNodes();
	}

	private static DialogNode resolveNode(Integer id) {
		Objects.requireNonNull(id, "id cannot be null");
		DialogNode node = story.get(id);
		if (node == null) {
			throw new IllegalArgumentException("the id " + id + " doesn't exist");
		}
		return node;
	}

	/**
	 * this is to decode the translations, see {@link TextManager#generateStory()}
	 * for all IDs
	 *
	 * @param id the ID of the line you want to show
	 * @throws IllegalArgumentException if the ID you passed doesn't exist
	 * @throws NullPointerException     if a null was passed
	 * @return String - the line you called
	 */
	public static String getText(Integer id) {
		return bundle.get(resolveNode(id).text);
	}

	/**
	 * to get the action of the id you're trying to get
	 * 
	 * @param id the ID of the line you want to get the action of
	 * @throws IllegalArgumentException if the ID you passed doesn't exist
	 * @throws NullPointerException     if a null was passed
	 * @return Runnable - the action you called
	 *         <p>
	 *         or null - if the action being called hasn't been set yet
	 */
	public static Runnable getAction(Integer id) {
		return resolveNode(id).getAction();
	}

	/**
	 * to give a node an action
	 * <p>
	 * note that you can't change the action once set
	 * 
	 * @param id     the ID of the line you want to set the action of
	 * @param action the new action
	 * @throws IllegalArgumentException if the ID you passed doesn't exist
	 * @throws NullPointerException     if a null was passed
	 * @throws IllegalStateException    if you called this on a node that has been
	 *                                  already set
	 */
	public static void setAction(Integer id, Runnable action) {
		resolveNode(id).setAction(action);
	}

	private TextManager() {
		throw new AssertionError("No storyutil.TextManager instance for you!");
	}

}