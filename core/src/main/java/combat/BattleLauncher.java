package combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;

import entities.CombatEntity;
import entities.Hero;
import storyUtil.TextDecode;
import util.Util;

// TODO add slight delay between each move
public class BattleLauncher {

	private static TextButton kick;
	private static TextButton swordSlash;
	private static TextButton dodge;
	private static TextButton retry;
	private static TextButton quit;
	
	private static Label playerHp;
	private static Label enemyHp;

	private static Hero player;
	private static CombatEntity enemy;
	private static Stage stage;

	private static void retry() {
		player.isDodging = false;
		player.resetHp();
		enemy.resetHp();
		enemy.isFocused = false;
		enemy.isDefending = false;

		kick.setDisabled(false);
		kick.setVisible(true);

		swordSlash.setDisabled(false);
		swordSlash.setVisible(true);

		dodge.setDisabled(false);
		dodge.setVisible(true);
		
		playerHp.setVisible(true);
		enemyHp.setVisible(true);

		retry.setVisible(false);
		quit.setVisible(false);
		retry.setDisabled(true);
		quit.setDisabled(true);

		launchBattle(player, enemy, stage);
	}
	
	private static String buildHpText(CombatEntity entity) {
		return entity.name + " Health :" + entity.getHp();
	}

	private static void generateButtons() {
		Table buttonsTable = new Table();
		buttonsTable.setFillParent(true);
		buttonsTable.bottom();
		
	//	buttonsTable.setVisible(false);
	//	buttonsTable.setDebug(true);
		
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

		retry = new TextButton(TextDecode.getText(1), Assets.skin);
		retry.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				retry();
			}
		});
		quit = new TextButton(TextDecode.getText(2), Assets.skin);
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

		if (buttonsTable.getChildren().isEmpty()) {
			buttonsTable.add(kick).pad(30);
			buttonsTable.add(swordSlash).pad(30);
			buttonsTable.add(dodge).pad(30);
			buttonsTable.add(retry).pad(30);
			buttonsTable.add(quit).pad(30);
			stage.addActor(buttonsTable);
		}
		
		
	}
	
	
	private static void generateLabels() {
		
		Table labelsTable = new Table();
		labelsTable.setFillParent(true);
		labelsTable.bottom().right();
	//	labelsTable.setDebug(true);
		
		playerHp = new Label(buildHpText(player), Assets.skin);
	    enemyHp = new Label(buildHpText(enemy), Assets.skin);
		
		labelsTable.add(playerHp).pad(20).right();
		labelsTable.add(enemyHp);
		stage.addActor(labelsTable);
	}

	public static void launchBattle(Hero p, CombatEntity e, Stage gameStage) {
		player = p;
		enemy = e;
	    stage = gameStage;

		if (dodge == null) {
			generateButtons();
			Util.log("buttons generated");
		}
		if (playerHp == null) {
			generateLabels();
			Util.log("labels generated");
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
			Util.log("the player won");
			enemy.moveGold(enemy.gold, player);
			return BattleState.WON;

		}
		if (player.getHp() <= 0) {
			Util.log("the player lost");
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
		
		playerHp.setVisible(false);
		enemyHp.setVisible(false);

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
			
			playerHp.setText(buildHpText(player));
			enemyHp.setText(buildHpText(enemy));
		}
		default -> {
			throw new IllegalStateException("The returned enum \"" + state + "\" can't be handled here");
		}

		}
	}

}
