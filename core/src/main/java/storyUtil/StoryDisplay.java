package storyUtil;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.Assets;

public class StoryDisplay {
	
	private Label dialogLabel;
	private TextField takeInput;

	private void buildDialogUI(Stage stage) {
		
		dialogLabel = new Label("", Assets.skin);
		dialogLabel.setVisible(true);
		dialogLabel.setWrap(true);

		takeInput = new TextField("", Assets.skin);
		takeInput.setVisible(false);
		takeInput.setPosition(250, 100, 10);
		Table dialog = new Table();

		dialog.setFillParent(true);
		dialog.bottom();
		dialog.add(dialogLabel).width(700).pad(10);

		stage.addActor(dialog);
		stage.addActor(takeInput);
	}
	public void launchStory(Stage stage) {
		if (dialogLabel == null) buildDialogUI(stage);
		
		// TODO complete this to make it actually display
	}
}
