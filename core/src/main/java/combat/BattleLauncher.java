package combat;

import java.util.List;

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
import storyutil.TextDecode;
import util.Util;

// TODO add slight delay between each move
public class BattleLauncher {

	enum BattleState {
		GOING, WON, LOST
	}

	private static TextButton[] gameOverButtons = new TextButton[2];

	private static TextButton[] combatButtons = new TextButton[3];

	private static Label playerHp;
	private static Label enemyHp;

	private static Hero player;
	private static CombatEntity enemy;
	private static Stage stage;
	private static boolean generated = false;

	private static String buildHpText(CombatEntity entity) {
		return entity.name + " Health :" + entity.getHp();
	}

	private static void endBattle() {
		setCombatButtonsVisibility(false);

		playerHp.setVisible(false);
		enemyHp.setVisible(false);

	}

	private static void generateButtons() {
		Table buttonsTable = new Table();
		buttonsTable.setFillParent(true);
		buttonsTable.bottom();

		String[] combatButtonNames = { TextDecode.getText(3), TextDecode.getText(4), TextDecode.getText(5) };
		List<Runnable> playerMoves = List.of( //
				() -> player.kick(enemy), //
				() -> player.swordSlash(enemy), //
				() -> player.dodge(enemy)); //

		if (combatButtons.length != combatButtonNames.length) {
			throw new IllegalStateException("the amount of combatButtonNames we have \"" + combatButtonNames.length
					+ "\" does not match the amount of buttons you're trying to generate" + combatButtons.length);
		}
		for (int i = 0; i < combatButtons.length; i++) {
			combatButtons[i] = new TextButton(combatButtonNames[i], Assets.skin);
			combatButtons[i].setUserObject(playerMoves.get(i));
			combatButtons[i].addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Runnable action = (Runnable) event.getListenerActor().getUserObject();
					action.run();
					handleBattleState(validateBattle());
				}
			});

			buttonsTable.add(combatButtons[i]).pad(30);
		}

		String[] gameOverButtonNames = { TextDecode.getText(1), TextDecode.getText(2) };
		List<Runnable> gameOverActions = List.of( //
				() -> retry(), //
				() -> Gdx.app.exit()); //

		if (gameOverButtons.length != gameOverButtonNames.length) {
			throw new IllegalStateException("the amount of combatButtonNames we have \"" + gameOverButtonNames.length
					+ "\" does not match the amount of buttons you're trying to generate" + gameOverButtons.length);
		}
		for (int i = 0; i < gameOverButtons.length; i++) {
			gameOverButtons[i] = new TextButton(gameOverButtonNames[i], Assets.skin);
			gameOverButtons[i].setUserObject(gameOverActions.get(i));
			gameOverButtons[i].addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Runnable action = (Runnable) event.getListenerActor().getUserObject();
					action.run();
				}
			});

			buttonsTable.add(gameOverButtons[i]).pad(30);
		}
		setGameOverButtonsVisibility(false);

		stage.addActor(buttonsTable);

	}

	private static void generateLabels() {

		Table labelsTable = new Table();
		labelsTable.setFillParent(true);
		labelsTable.bottom().right();

		playerHp = new Label(buildHpText(player), Assets.skin);
		enemyHp = new Label(buildHpText(enemy), Assets.skin);

		labelsTable.add(playerHp).pad(20).right();
		labelsTable.add(enemyHp);
		stage.addActor(labelsTable);
	}

	private static void generateUI() {
		generateButtons();
		generateLabels();
		Util.log("GUI generated successfully");
	}

	private static void handleBattleState(BattleState state) {
		switch (state) {
		case WON -> {
			updateLabels();
			endBattle();
			player.setMovementLocked(false);
		}
		case LOST -> {
			endBattle();
			setGameOverButtonsVisibility(true);
			updateLabels();
		}
		case GOING -> {
			enemy.takeTurn(player);
			validateBattle();
			updateLabels();
		}
		default -> throw new IllegalStateException("The returned enum \"" + state + "\" can't be handled here");

		}
	}

	public static void launchBattle(Hero p, CombatEntity e, Stage gameStage) {
		player = p;
		enemy = e;
		stage = gameStage;

		if (!generated) {
			generateUI();
			generated = true;
		}

		Util.log("_______ battle starts! _______");
		Util.log(player.name + " has " + player.getHp() + " hit points");
		Util.log(enemy.name + " has " + enemy.getHp() + " hit points");

	}

	private static void retry() {
		player.isDodging = false;
		player.resetHp();
		enemy.resetHp();
		enemy.isFocused = false;
		enemy.isDefending = false;

		setCombatButtonsVisibility(true);
		setGameOverButtonsVisibility(false);

		playerHp.setVisible(true);
		enemyHp.setVisible(true);

		launchBattle(player, enemy, stage);
	}

	private static void setCombatButtonsVisibility(boolean state) {
		for (TextButton combatButton : combatButtons) {
			combatButton.setDisabled(!state);
			combatButton.setVisible(state);
		}
	}

	private static void setGameOverButtonsVisibility(boolean state) {
		for (TextButton gameOverButton : gameOverButtons) {
			gameOverButton.setDisabled(!state);
			gameOverButton.setVisible(state);
		}
	}

	private static void updateLabels() {
		playerHp.setText(buildHpText(player));
		enemyHp.setText(buildHpText(enemy));
	}

	private static BattleState validateBattle() {
		if (enemy.getHp() <= 0) {
			Util.log("the player won");
			enemy.moveGold(enemy.gold(), player);
			return BattleState.WON;

		}
		if (player.getHp() <= 0) {
			Util.log("the player lost");
			return BattleState.LOST;
		}
		return BattleState.GOING;
	}

	private BattleLauncher() {
		throw new AssertionError("No combat.BattleLauncher instance for you!");
	}

}
