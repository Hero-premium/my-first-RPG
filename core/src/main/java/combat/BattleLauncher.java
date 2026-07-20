package combat;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import entities.CombatEntity;

// FIXME make it compatible to GUI or delete it entirely
// the old class is kept for reference I'll delete when i feel like it
public class BattleLauncher {


	public static void launchBattle(CombatEntity player, CombatEntity enemy, Label dialogLabel, Stage stage) {
		/*	System.out.println("_______ battle starts! _______");
		System.out.println(player.name + " has " + player.getHp() + " hit points");
		System.out.println(enemy.name + " has " + enemy.getHp() + " hit points");
		battleloop: while (true) {

			if (enemy.getHp() <= 0) {
				player.gold += enemy.gold;
				System.out.println("You won!");
				System.out.println("you gained " + enemy.gold + " gold");
				System.out.println("to keep going press any button");

				break;
			}

			if (player.getHp() <= 0) {
				System.out.println("you lost!");
				System.out.println("to retry press 1 to quit press 2");

				int choice;
				choiceloop: while (true) {
					retryloop: while (true) {
						try {
							choice = 1;
							break retryloop;
						} catch (Exception e) {
							System.out.println("you had 1 and 2! use those and try again");

						}
					}

					switch (choice) {
					case 1:
						player.isDodging = false;
						player.resetHp();
						enemy.resetHp();
						enemy.isfocused = false;
						enemy.isDefending = false;
						break choiceloop;

					case 2:
						System.exit(0);
					default:
						System.out.println("you had 1 and 2! use those and try again");
					}
				}
			}
		}*/

		System.out.println("you're a cool guy");
	}
}
