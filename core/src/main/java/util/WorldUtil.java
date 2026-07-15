package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import entities.CombatEntity;

public class WorldUtil {

	static int index = 0;
    static boolean isHere = true;
    // FIXME out of array if the player keepes pressing after the array is over
	public static void startDialog(CombatEntity E1, CombatEntity E2) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && isHere) {
		System.out.println(E1.dialog[index]);
		isHere = false;
	} else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !isHere ) {
			System.out.println(E2.dialog[index]);
			index++;
			isHere = true;
		}
	}
}
