package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import combat.BattleLauncher;
import entities.CombatEntity;
import entities.Entity;
import entities.Player;

public class Dialogs {
	private int index = 0;
	private boolean here = false;
	private String[] story;
	
	public Dialogs(Entity player) {
		story = updateStory(player);
	}
	
	String[] updateStory(Entity player) {
		return new String[] {
				"gatekeeper: welcome to this dungeon; but coming here was a big mistake! as there's an angry dragon ready to hunt whoever enters.",
				"player: yes! I know, but I want the gold the dragon is hiding beneath his thrown.",
				"gatekeeper: what do I call you, hunter?",
				"gatekeeper: but " + player.name + ", that is such a dangerous mission, how are you planning on doing it?",
				player.name + ": of course I didn't come unprepared, I do have some moves that will help me defeat this evil monster",
				"gatekeeper: and what are they...?",
				player.name + ": dash! an awesome ability that would help me dodge the dragon attacks, sometimes I don't move fast enough though",
				"swordSlash: im not very good at using my sword, but the sword helps me attack vital organs, but most of the time i can't",
				"kick: one of my best moves that I mastered, it's still based on my human abilities but it's very consistent",
				"gatekeeper: oh you shouldn't have told me that, as I'll be your first foe, I'll defend my lord with my life!" };
	}

	
	
	void checkInput(Label dialogLabel, TextField takeInput, Player player, CombatEntity enemy, Stage stage) {
		if (story[index].equals("gatekeeper: what do I call you, hunter?")) {
			here = true;
			takeInput.setVisible(true);
		}
		if (story[index].equals("gatekeeper: oh you shouldn't have told me that, as I'll be your first foe, I'll defend my lord with my life!")) {
			BattleLauncher.launchBattle(player, enemy, dialogLabel, stage);
		
	}
	}

	boolean proceed() {
		if (story.length > index) {
			return Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
		} else {
			return false;
		}
	}


	public void lunchStory(Player player, CombatEntity enemy, Label dialogLabel, TextField takeInput, Stage stage) {

		if (proceed()) {
			if (!takeInput.getText().equals("")) {
				
				player.name = takeInput.getText();
				takeInput.setVisible(false);
				story = updateStory(player);
			}
			if (player.name.equals("") && here) {
				dialogLabel.setText("please enter a name");
				takeInput.setVisible(true);
				return;
			}
			dialogLabel.setText(story[index]);
			checkInput(dialogLabel, takeInput, player, enemy, stage);
			index++;
		}

	}
}
