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

	public final static int buttonsCount = 3;

	private int index = 3; // <- not a magic number - this is where the story starts

	private Label dialogLabel;
	private TextField takeInput;

	private TextButton[] buttons = new TextButton[buttonsCount];

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
					index = (Integer) event.getListenerActor().getUserObject();
					displayOptions();
				}
			});

			dialogsGUI.add(buttons[i]).row();

		}

		dialogsGUI.add(dialogLabel).width(700).pad(10).row();

		stage.addActor(dialogsGUI);
		stage.addActor(takeInput);
		stage.setDebugAll(true);
	}

	private int useages = 0;

	public void launchStory(Stage stage) {
		if (dialogLabel == null)
			buildDialogUI(stage);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && useages < 2) {
			useages++;
			dialogLabel.setText(TextDecode.getText(index));
			displayOptions();
		}
	}

	private boolean checkArray(int nextNodesLength) {
		if (TextDecode.getNextNodes(index) == null) {
			Util.log("end of nodes reached");

			return true;
		}
		if (nextNodesLength == 0) {
			Util.log("WARNING - empty array here on ID " + index);

			return true;
		}
		return false;
	}

	private void displayOptions() {
		int loops = 0;
		int nextNodesLength = TextDecode.getNextNodes(index).length;

		if (checkArray(nextNodesLength))
			return;

		// because the first node is always the other person's line this always happens
		dialogLabel.setText(TextDecode.getText(TextDecode.getNextNodes(index)[0]));
		if (nextNodesLength == 1) {
			index = TextDecode.getNextNodes(index)[0];

			return;
		}

		for (int i = 1; i < nextNodesLength; i++) {
			buttons[i - 1].setText(TextDecode.getText(TextDecode.getNextNodes(index)[i]));
			buttons[i - 1].setUserObject(TextDecode.getNextNodes(index)[i]);
			loops++;
		}
		for (int i = buttons.length - 1; i >= loops; i--) {
			buttons[i].setText("");
			buttons[i].setUserObject(null);
		}
	}
}
