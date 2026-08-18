package combat;

import java.util.List;
import java.util.Objects;

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
import storyutil.TextManager;
import util.Util;

// TODO add slight delay between each move
public final class BattleManager {

	private enum BattleState {
		GOING, WON, LOST
	}

	private static TextButton[] gameOverButtons = new TextButton[2];

	private static TextButton[] combatButtons = new TextButton[3];

	private static Label playerHp;
	private static Label enemyHp;

	private static Hero player;
	private static CombatEntity enemy;
	private static Stage stage;

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

		String[] combatButtonNames = { TextManager.getText(3), TextManager.getText(4), TextManager.getText(5) };
		String[] gameOverButtonNames = { TextManager.getText(1), TextManager.getText(2) };

		List<Runnable> playerMoves = List.of( //
				() -> {
					player.kick(enemy);
					handleBattleState(validateBattle());
				}, //
				() -> {
					player.swordSlash(enemy);
					handleBattleState(validateBattle());
				}, //
				() -> {
					player.dodge(enemy);
					handleBattleState(validateBattle());
				});
		List<Runnable> gameOverActions = List.of( //
				() -> retry(), //
				() -> Gdx.app.exit()); //

		generateButton(buttonsTable, combatButtonNames, playerMoves, combatButtons);
		generateButton(buttonsTable, gameOverButtonNames, gameOverActions, gameOverButtons);
		
		setGameOverButtonsVisibility(false);

		stage.addActor(buttonsTable);

	}

	private static void generateButton(Table buttonsTable, String[] buttonNames, List<Runnable> actions,
			TextButton[] buttons) {
		if (buttons.length != buttonNames.length) {
			throw new IllegalStateException("the amount of combatButtonNames we have \"" + buttonNames.length
					+ "\" does not match the amount of buttons you're trying to generate" + buttons.length);
		}
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new TextButton(buttonNames[i], Assets.skin);
			buttons[i].setUserObject(actions.get(i));
			buttons[i].addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Runnable action = (Runnable) event.getListenerActor().getUserObject();
					action.run();
				}
			});
			buttonsTable.add(buttons[i]).row();
		}
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
			endBattle();
			player.setMovementLocked(false);
		}
		case LOST -> {
			endBattle();
			setGameOverButtonsVisibility(true);
		}
		case GOING -> {
			enemy.takeTurn(player);
		}
		default -> throw new IllegalStateException("The returned enum \"" + state + "\" can't be handled here");
		}
		updateLabels();
	}

	public static void launchBattle(Hero p, CombatEntity e, Stage gameStage) {
		Objects.requireNonNull(p, "hero cannot be null");
		Objects.requireNonNull(e, "enemy cannot be null");
		Objects.requireNonNull(gameStage, "stage cannot be null");
				
		player = p;
		enemy = e;
		stage = gameStage;

		/*
		 * to avoid recreating the combat GUI on the same stage and to make sure they
		 * get generated when they're needed, gameOverButtons[1] is just a random pick
		 * because everything gets generated at once
		 */
		if (!stage.getActors().contains(gameOverButtons[1], true)) {
			generateUI();
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

	private BattleManager() {
		throw new AssertionError("No combat.BattleManager instance for you!");
	}

}
