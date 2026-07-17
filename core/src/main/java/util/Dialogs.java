package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import entities.Entity;

public class Dialogs {
	int index = 0;

	static String[] getStory(Entity pl) {
		return new String[] {
				"gatekeeper: welcome to this dungeon; but coming here was a big mistake! as there's an angry dragon ready to hunt whoever enters.",
				"player: yes! I know, but I want the gold the dragon is hiding beneath his thrown.",
				"gatekeeper: what do I call you, hunter?",
				"gatekeeper: but " + pl.name + ", that is such a dangerous mission, how are you planning on doing it?",
				pl.name + ": of course I didn't come unprepared, I do have some moves that will help me defeat this evil monster",
				"gatekeeper: and what are they...?",
				pl.name + ": dash! an awesome ability that would help me dodge the dragon attacks, sometimes I don't move fast enough though",
				"swordSlash: im not very good at using my sword, but the sword helps me attack vital organs, but most of the time i can't",
				"kick: one of my best moves that I mastered, it's still based on my human abilities but it's very consistent",
				"gatekeeper: oh you shouldn't have told me that, as I'll be your first foe, I'll defend my lord with my life!" };
	}

	boolean proceed(Entity pl) {
		if (getStory(pl).length > index)
			return Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
		else
			return false;
	}

	static void display(String text, Label dialogLabel) {
		dialogLabel.setText(text);
		if (text.equals("gatekeeper: what do I call you, hunter?")) {
			
		}
	}

	public void lunchStory(Entity pl, Label dialogLabel) {
		if (proceed(pl)) {
			display(getStory(pl)[index], dialogLabel);
			index++;
		}

	}
}
