package storyutil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Assets;
import util.Util;

import java.util.Objects;

// TODO make the GUI the way you imagined it
public class StoryDisplay {

    /**
     * The amount of buttons this game supports, the project is built around this
     * number, so I ensure no unexpected {@link ArrayIndexOutOfBoundsException} can
     * happen because of exceeding this number
     */
    public final static int BUTTONS_COUNT = 3;

    private final static int STORY_START = 6;
    private final TextButton[] buttons = new TextButton[BUTTONS_COUNT];
    private int index;
    private Label dialogLabel;
    private TextField takeInput;
    private Integer[] nextNodes;

    private boolean firstTime = true;
    private boolean storyActive = false;

    /**
     * Creates a StoryDisplay and builds its dialogue UI on the given stage.
     *
     * @param stage the stage the StoryDisplay GUI is going to be built on
     * @throws NullPointerException if stage was null
     */
    public StoryDisplay(Stage stage) {
        this(stage, STORY_START);
    }

    /**
     * Creates a StoryDisplay and builds its dialogue UI on the given stage.
     *
     * @param stage the stage the StoryDisplay GUI is going to be built on
     * @param index set the index from outside, if that index don't exist it will
     *              throw {@link IllegalStateException} check {@link TextManager}
     *              for all valid indexes
     * @throws NullPointerException if stage was null
     *
     */
    public StoryDisplay(Stage stage, int index) {
        Objects.requireNonNull(stage, "stage cannot be null");
        Table dialogsGUI = generateTable(stage);

        buildButtons(dialogsGUI);
        buildDialogWidgets(stage, dialogsGUI);

        this.index = index;
    }

    private Table generateTable(Stage stage) {
        Table dialogsGUI = new Table();
        dialogsGUI.setFillParent(true);
        dialogsGUI.bottom();
        stage.addActor(dialogsGUI);
        return dialogsGUI;
    }

    private void buildButtons(Table dialogsGUI) {

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new TextButton("", Assets.skin);
            buttons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (event.getListenerActor().getUserObject() != null) {
                        index = (Integer) event.getListenerActor().getUserObject();
                        displayOptions();
                    }
                }
            });
            buttons[i].setVisible(false);
            dialogsGUI.add(buttons[i]).row();
        }
    }

    private void buildDialogWidgets(Stage stage, Table dialogsGUI) {
        dialogLabel = new Label("", Assets.skin);
        dialogLabel.setWrap(true);

        takeInput = new TextField("", Assets.skin);
        takeInput.setVisible(false);
        takeInput.setPosition(250, 100, 10);

        dialogsGUI.add(dialogLabel).width(700).pad(10).row();
        stage.addActor(takeInput);
    }

    private void clearButtons() {
        for (TextButton button : buttons) {
            button.setVisible(false);
            button.setText("");
            button.setUserObject(null);
        }
    }

    private void displayOptions() {
        clearButtons();
        runNodesAction();
        if (updateNextNodes())
            return;

        // because the first node is always the other person's line this always happens
        dialogLabel.setText(TextManager.getText(nextNodes[0]));

        if (nextNodes.length == 1) {
            index = nextNodes[0];
            return;
        }
        fillButtons();
    }

    private void fillButtons() {
        for (int i = 1; i < nextNodes.length; i++) {
            buttons[i - 1].setText(TextManager.getText(nextNodes[i]));
            buttons[i - 1].setUserObject(nextNodes[i]);
            buttons[i - 1].setDisabled(false);
            buttons[i - 1].setVisible(true);
        }
    }

    private void runNodesAction() {
        Runnable action = TextManager.getAction(index);
        if (action != null)
            action.run();
    }

    /**
     * Meant to be called every frame from the render loop.
     * <p>
     * Nothing will happen if {@code storyActive} = false, use
     * {@link #setStoryActive} to make that boolean true.
     * <p>
     * On the first call, dialogue starts automatically and may immediately advance
     * if the current node has no branching choice ({@code nextNodes.length == 1}).
     * <p>
     * On subsequent calls, if the current node has no branching choice, the player
     * can press Enter to advance.
     * <p>
     * Otherwise, the player advances by clicking one of the displayed choice
     * buttons.
     */
    public void runStory() {
        if (storyActive && firstTime
            || (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && nextNodes != null && nextNodes.length == 1)) {
            firstTime = false;
            displayOptions();
        }
    }

    private boolean updateNextNodes() {
        nextNodes = TextManager.getNextNodes(index);
        if (nextNodes == null) {
            dialogLabel.setText("");
            Util.log("end of nodes reached");
            return true;
        }
        return false;
    }

    public boolean isStoryActive() {
        return storyActive;
    }

    public void setStoryActive(boolean storyOn) {
        this.storyActive = storyOn;
    }
}
