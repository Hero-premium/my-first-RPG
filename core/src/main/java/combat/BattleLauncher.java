package combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;

import entities.CombatEntity;
import entities.Player;
import util.Util;

// FIXME make it compatible to GUI
// TODO add slight delay between each move
public class BattleLauncher {

	private static TextButton kick;
	private static TextButton swordSlash;
	private static TextButton dodge;
	private static TextButton retry;
	private static TextButton quit;

	private static Player player;
	private static CombatEntity enemy;
	private static Stage stage;

	private static void retry() {
		player.isDodging = false;
		player.resetHp();
		enemy.resetHp();
		enemy.isfocused = false;
		enemy.isDefending = false;

		kick.setDisabled(false);
		kick.setVisible(true);

		swordSlash.setDisabled(false);
		swordSlash.setVisible(true);

		dodge.setDisabled(false);
		dodge.setVisible(true);

		retry.setVisible(false);
		quit.setVisible(false);
		retry.setDisabled(true);
		quit.setDisabled(true);

		launchBattle(player, enemy, stage);
	}

	private static void generateButtons() {
		Table combat = new Table();
		combat.setFillParent(true); // table fills the whole stage/screen
		combat.bottom();
		
		
		kick = new TextButton("Kick", Assets.skin);
		kick.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.kick(enemy);
				handleBattleState(validateBattle());
			}
		});

		swordSlash = new TextButton("Sword slash", Assets.skin);
		swordSlash.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.swordSlash(enemy);
				handleBattleState(validateBattle());
			}
		});

		dodge = new TextButton("dodge", Assets.skin);
		dodge.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				player.dodge(enemy);
				handleBattleState(validateBattle());

			}
		});

		retry = new TextButton("retry", Assets.skin);
		retry.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				retry();
			}
		});
		quit = new TextButton("quit", Assets.skin);
		quit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}

		});
		retry.setVisible(false);
		quit.setVisible(false);
		retry.setDisabled(true);
		quit.setDisabled(true);

		if (combat.getChildren().isEmpty()) {
			combat.add(kick).pad(30);
			combat.add(swordSlash).pad(30);
			combat.add(dodge).pad(30);
			combat.add(retry).pad(30);
			combat.add(quit).pad(30);
			stage.addActor(combat);
		}
	}

	public static void launchBattle(Player p, CombatEntity e, Stage gameStage) {
		player = p;
		enemy = e;
	    stage = gameStage;

		if (dodge == null) {
			generateButtons();
		}

		Util.log("_______ battle starts! _______");
		Util.log(player.name + " has " + player.getHp() + " hit points");
		Util.log(enemy.name + " has " + enemy.getHp() + " hit points");

	}

	enum BattleState {
		GOING, WON, LOST
	};

	private static BattleState validateBattle() {
		if (enemy.getHp() <= 0) {
			Util.log("You won!");
			Util.log("you gained " + enemy.gold + " gold");
			enemy.moveGold(enemy.gold, player);
			return BattleState.WON;

		}
		if (player.getHp() <= 0) {
			Util.log("you lost!");
			handleBattleState(BattleState.LOST);
			return BattleState.LOST;
		}
		return BattleState.GOING;
	}

	private static void endBattle() {
		kick.setDisabled(true);
		kick.setVisible(false);

		swordSlash.setDisabled(true);
		swordSlash.setVisible(false);

		dodge.setDisabled(true);
		dodge.setVisible(false);

	}

	private static void handleBattleState(BattleState state) {
		switch (state) {
		case WON -> {
			endBattle();
			player.setMovementLocked(false);
		}
		case LOST -> {
			endBattle();
			retry.setVisible(true);
			quit.setVisible(true);
			retry.setDisabled(false);
			quit.setDisabled(false);

		}
		case GOING -> {
			enemy.takeTurn(player);
			validateBattle();
		}
		default -> {
			throw new IllegalStateException("The returned enum \"" + state + "\" can't be handled here");
		}

		}
	}

}
