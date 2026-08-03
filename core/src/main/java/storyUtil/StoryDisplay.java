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

public class StoryDisplay {
	private int index = 3; // <- not a magic number - this is where the story starts

	private Label dialogLabel;
	private TextField takeInput;
	
	private TextButton option1;
	private TextButton option2;
	private TextButton option3;

	private void buildDialogUI(Stage stage) {

		dialogLabel = new Label("", Assets.skin);
		dialogLabel.setWrap(true);

		takeInput = new TextField("", Assets.skin);
		takeInput.setVisible(false);
		takeInput.setPosition(250, 100, 10);
		
		option1 = new TextButton("", Assets.skin);
		option1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});

		option2 = new TextButton("", Assets.skin);
		option2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});

		option3 = new TextButton("", Assets.skin);
		option3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});

		Table dialogsGUI = new Table();

		
		dialogsGUI.setFillParent(true);
		dialogsGUI.bottom();
		dialogsGUI.add(dialogLabel).width(700).pad(10).row();
		
		dialogsGUI.add(option1).row();
		dialogsGUI.add(option2).row();
		dialogsGUI.add(option3).row();

		stage.addActor(dialogsGUI);
		stage.addActor(takeInput);
		
		stage.setDebugAll(true);
	}
	
	

	public void launchStory(Stage stage) {
		if (dialogLabel == null)
			buildDialogUI(stage);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			dialogLabel.setText(TextDecode.getText(index));
			displayOptions();
		}
	}
	private void displayOptions() {
		
		if (TextDecode.getNextNodes(index) == null) {
			Util.log("end of nodes reached");
			
			return;
		}
		if (TextDecode.getNextNodes(index).length == 1) {
			index = TextDecode.getNextNodes(index)[0];
			return;
		}
		
		
		for (int i = 0; i < TextDecode.getNextNodes(index).length; i++) {
			switch(i) {
			case 0 -> {
				dialogLabel.setText(TextDecode.getText(TextDecode.getNextNodes(index)[i]));
			}
			case 1 -> {
				option1.setText(TextDecode.getText(TextDecode.getNextNodes(index)[i]));
			}
			case 2 -> {
				option2.setText(TextDecode.getText(TextDecode.getNextNodes(index)[i]));
			}
			case 3 -> {
				option3.setText(TextDecode.getText(TextDecode.getNextNodes(index)[i]));
			}
			default -> {
				throw new IllegalStateException("the switch went into a case it didn't expect - chances are you added a 4th node");
			}
			}
		}
	}
}
