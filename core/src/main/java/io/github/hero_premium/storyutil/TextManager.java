package io.github.hero_premium.storyutil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class TextManager {
    private static final Logger LOGGER = new Logger(TextManager.class.getName(), Util.LOGGING_LEVEL);
    private static final Map<Integer, DialogNode> story = new HashMap<>();
    // TODO make this take the language the player selected
    // FIXME translation to Arabic doesn't work because libGDX apparently doesn't support displaying it :/
    private static final Locale locale = Locale.of("en");
    private static final I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("translation/translation"), locale);
    private static int internalID = 1;

    static {
        generateStory();
    }

    private TextManager() {
        throw new AssertionError("No storyutil.TextManager instance for you!");
    }

    private static void addNewNode(int id, String line, int[] nodesTree, Runnable action) {
        Objects.requireNonNull(line, "the line is not applicant for being a null");
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
        if (id != internalID) {
            LOGGER.info("the ID passed " + id + " does not match the internal ID " + internalID
                + " make sure you did not make a mistake");
        }
        story.put(id, (new DialogNode(line, nodesTree, action)));
        internalID++;
    }

    private static void addNewNode(int id, String line) {
        addNewNode(id, line, null, null);
    }

    @SuppressWarnings("unused")
    private static void addNewNode(int id, String line, Runnable action) {
        addNewNode(id, line, null, action);
    }

    private static void addNewNode(int id, String line, int[] nodes) {
        addNewNode(id, line, nodes, null);
    }

    private static void generateStory() {
        addNewNode(1, "retryButton");
        addNewNode(2, "quitButton");
        addNewNode(3, "kick");
        addNewNode(4, "swordSlash");
        addNewNode(5, "dodge");
        addNewNode(6, "", new int[]{7});
        addNewNode(7, "gatekeeper.welcome", new int[]{8});
        addNewNode(8, "player.motivation", new int[]{9});
        addNewNode(9, "gatekeeper.ask_name", new int[]{10});
        addNewNode(10, "gatekeeper.warn_mission", new int[]{11});
        addNewNode(11, "player.explain_moves", new int[]{12});
        addNewNode(12, "gatekeeper.askAbilities", new int[]{13});
        addNewNode(13, "player.dash", new int[]{14});
        addNewNode(14, "player.swordSlash", new int[]{15});
        addNewNode(15, "player.kick", new int[]{16});
        addNewNode(16, "gatekeeper.challenge");
    }

    /**
     * this is to get each nodes next node ID, see
     * {@link TextManager#generateStory()} for all IDs
     *
     * @param id the ID of the line you want to see the next line of
     * @return Integer[] the ID of the line that links for the lines you called -
     * or {@code null} if there's no next node
     * @throws IllegalArgumentException if the ID you passed doesn't exist
     */
    public static int[] getNextNodes(int id) {
        return resolveNode(id).getNextNodes();
    }


    private static @NotNull DialogNode resolveNode(int id) {
        DialogNode node = story.get(id);
        if (node == null) {
            throw new NoSuchElementException("the id " + id + " doesn't exist");
        }
        return node;
    }

    /**
     * this is to decode the translations, see {@link TextManager#generateStory()}
     * for all IDs
     *
     * @param id the ID of the line you want to show
     * @return String - the line you called
     * @throws IllegalArgumentException if the ID you passed doesn't exist
     */
    public static String getText(int id) {
        return bundle.get(resolveNode(id).text);
    }

    /**
     * to get the action of the id you're trying to get
     *
     * @param id the ID of the line you want to get the action of
     * @return <span style="color:#589DF6">{@code Optional<Runnable>}</span>} the action you called
     * <p>
     * or null - if the action being called hasn't been set yet
     * @throws IllegalArgumentException if the ID you passed doesn't exist
     */
    public static Optional<Runnable> getAction(int id) {
        return resolveNode(id).getAction();
    }

    /**
     * to give a node an action
     * <p>
     * note that you can't change the action once set
     *
     * @param id     the ID of the line you want to set the action of
     * @param action the new action
     * @throws NoSuchElementException if the ID you passed doesn't exist
     * @throws IllegalStateException    if you called this on a node that has been
     *                                  already set
     */
    public static void setAction(int id, Runnable action) {
        resolveNode(id).setAction(action);
    }
}
