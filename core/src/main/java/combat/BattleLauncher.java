package combat;

import com.badlogic.gdx.Gdx;
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

	private static void validateBattle(Player player, CombatEntity enemy) {
		if (enemy.getHp() <= 0) {
			player.gold += enemy.gold;
			System.out.println("You won!");
			System.out.println("you gained " + enemy.gold + " gold");
			System.out.println("to keep going press any button");
			
		} else if (player.getHp() <= 0) {
			System.out.println("you lost!");
			System.out.println("to retry press 1 to quit press 2");

			TextButton retry = new TextButton("retry", Assets.skin);
			retry.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					player.isDodging = false;
					player.resetHp();
					enemy.resetHp();
					enemy.isfocused = false;
					enemy.isDefending = false;
				}
			});
			TextButton quit = new TextButton("quit", Assets.skin);
			quit.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Gdx.app.exit();
				}

			});
		} else {
			enemy.takeTurn(player);
		}
	}

	public static void launchBattle(Player player, CombatEntity enemy, Label dialogLabel, Stage stage) {
		/*
		 * System.out.println("_______ battle starts! _______");
		 * System.out.println(player.name + " has " + player.getHp() + " hit points");
		 * System.out.println(enemy.name + " has " + enemy.getHp() + " hit points");
		 * 
		 */

		TextButton kick = new TextButton("Kick", Assets.skin);
		kick.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.kick(enemy);
				validateBattle(player, enemy);
			}
		});
		kick.setVisible(true);
		stage.addActor(kick);
	}
}
