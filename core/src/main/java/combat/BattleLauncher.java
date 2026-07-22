package combat;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;

import entities.CombatEntity;
import entities.Player;

// FIXME make it compatible to GUI or delete it entirely
// TODO add slight delay between each move
// the old class is kept for reference I'll delete when i feel like it
public class BattleLauncher {
	
	private static void battleValidate() {
		
	}


	public static void launchBattle(Player player, CombatEntity enemy, Label dialogLabel, Stage stage) {
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

		TextButton kick = new TextButton("Kick", Assets.skin);
		kick.addListener(new ClickListener() {
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		        player.kick(enemy);
		        enemy.takeTurn(player);
		    }
		});
		kick.setVisible(true);
		stage.addActor(kick);
	}
}
