package storyUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;

import util.Util;

// TODO make the GUI the way you imagined it	
public class StoryDisplay {

	public StoryDisplay(Stage stage) {
		buildDialogUI(stage);
	}

	public final static int BUTTONS_COUNT = 3;

	private int index = 3; // <- not a magic number - this is where the story starts

	private Label dialogLabel;
	private TextField takeInput;

	private TextButton[] buttons = new TextButton[BUTTONS_COUNT];

	private Integer[] nextNodes;

	private boolean updateData() {
		nextNodes = TextDecode.getNextNodes(index);
		if (nextNodes == null) {
			Util.log("end of nodes reached");
			return true;
		}
		return false;
	}

	private void buildDialogUI(Stage stage) {

		Table dialogsGUI = new Table();

		dialogsGUI.setFillParent(true);
		dialogsGUI.bottom();

		dialogLabel = new Label("", Assets.skin);
		dialogLabel.setWrap(true);

		takeInput = new TextField("", Assets.skin);
		takeInput.setVisible(false);
		takeInput.setPosition(250, 100, 10);

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new TextButton("", Assets.skin);
			buttons[i].addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (event.getListenerActor().getUserObject() != null) {
						index = (Integer) event.getListenerActor().getUserObject();
						displayOptions();
					}
				}
			});

			dialogsGUI.add(buttons[i]).row();

		}

		dialogsGUI.add(dialogLabel).width(700).pad(10).row();

		stage.addActor(dialogsGUI);
		stage.addActor(takeInput);
		stage.setDebugAll(true);
	}

	private boolean ran = false;

	public void launchStory() {
		if (!ran || (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && nextNodes != null && nextNodes.length == 1)) {
			ran = true;
			displayOptions();
		}
	}
	
	private void clearButtons() {
		for (int i = 0 ; i < buttons.length ; i++) {
			buttons[i].setDisabled(true);
			buttons[i].setVisible(false);
			buttons[i].setText("");
			buttons[i].setUserObject(null);
		}
	}

	private void displayOptions() {
		clearButtons();
		if (updateData())
			return;

		// because the first node is always the other person's line this always happens
		dialogLabel.setText(TextDecode.getText(nextNodes[0]));
		if (nextNodes.length == 1) {
			index = nextNodes[0];
			

			return;
		}

		for (int i = 1; i < nextNodes.length; i++) {
			buttons[i - 1].setText(TextDecode.getText(nextNodes[i]));
			buttons[i - 1].setUserObject(nextNodes[i]);
			buttons[i - 1].setDisabled(false);
			buttons[i - 1].setVisible(true);
		}
	}
}
