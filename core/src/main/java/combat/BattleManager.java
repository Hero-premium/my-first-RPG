package combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Assets;
import entities.CombatEntity;
import storyutil.TextManager;
import util.Util;

import java.util.Objects;
import java.util.function.Consumer;

// TODO add slight delay between each move
public final class BattleManager {

    private final TextButton[] gameOverButtons = new TextButton[2];
    private final TextButton[] combatButtons = new TextButton[3];
    private final CombatEntity[] fighter = new CombatEntity[2];
    private Label playerHp;
    private Label enemyHp;
    private Stage stage;

    public BattleManager(Stage stage) {
        this.stage = Objects.requireNonNull(stage, "stage cannot be null");
    }

    /**
     * meant to be used only when EvE will fight this instance
     */
    public BattleManager() {
    }

    private String buildHpText(CombatEntity entity) {
        return entity.name + " Health: " + entity.health.getHp();
    }

    private void endBattle() {
        setCombatButtonsVisibility(false);

        playerHp.setVisible(false);
        enemyHp.setVisible(false);
        Util.log("Battle is over");

    }

    private void buildAllButtons(CombatEntity entity) {
        Table buttonsTable = new Table();
        buttonsTable.setName("buttonsTable");
        buttonsTable.setFillParent(true);
        buttonsTable.bottom();

        String[] gameOverButtonNames = {TextManager.getText(1), TextManager.getText(2)};

        Runnable[] gameOverActions = { //
            this::retry, //
            () -> Gdx.app.exit()}; //

        generateButton(buttonsTable, combatButtons, entity);
        generateButton(buttonsTable, gameOverButtonNames, gameOverActions, gameOverButtons);

        setGameOverButtonsVisibility(false);

        stage.addActor(buttonsTable);

    }

    private void generateButton(Table buttonsTable, TextButton[] buttons, CombatEntity entity) {
        Array<CombatEntity.CombatMovesManager.Move> moves = entity.movesManager.getMoves();
        for (int i = 0; i < moves.size; i++) {
            Consumer<CombatEntity> action = moves.get(i).move();
            buttons[i] = new TextButton(moves.get(i).name(), Assets.skin);
            buttons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    action.accept(BattleUtils.other(entity, fighter));
                    handleBattleState(BattleUtils.validateBattle(entity, fighter), entity);
                }
            });
            buttonsTable.add(buttons[i]).row();
        }
    }

    private void generateButton(Table buttonsTable, String[] buttonNames, Runnable[] actions, TextButton[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            Runnable action = actions[i];
            buttons[i] = new TextButton(buttonNames[i], Assets.skin);
            buttons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    action.run();
                }
            });
            buttonsTable.add(buttons[i]).row();
        }
    }

    private void generateLabels() {

        Table labelsTable = new Table();
        labelsTable.setFillParent(true);
        labelsTable.bottom().right();

        playerHp = new Label(buildHpText(fighter[0]), Assets.skin);
        enemyHp = new Label(buildHpText(fighter[1]), Assets.skin);

        labelsTable.add(playerHp).pad(20).row();
        labelsTable.add(enemyHp);
        stage.addActor(labelsTable);
    }

    private void generateUI(CombatEntity entity) {
        buildAllButtons(entity);
        generateLabels();
        Util.log("GUI generated successfully");
    }

    private void handleBattleState(BattleUtils.BattleState state, CombatEntity entity) {
        switch (state) {
            case WON -> {
                endBattle();
                entity.movementLocked = false;
                BattleUtils.other(entity, fighter).moveGold(BattleUtils.other(entity, fighter).getGold(), entity);
            }
            case LOST -> {
                endBattle();
                setGameOverButtonsVisibility(true);
            }
            case GOING -> {
                BattleUtils.other(entity, fighter).takeTurn(entity);

                /*
                 * the line below triggers when the player losses, in using validateBattle
                 * knowing it will return lost just so I can get it's side effects (the prints)
                 */
                if (entity.health.getHp() <= 0) handleBattleState(BattleUtils.validateBattle(entity, fighter), entity);
            }
            default -> throw new AssertionError("The returned enum \"" + state + "\" was unexpected");
        }
        updateLabels();
    }

    /**
     * Launches a battle between two combat entities.
     *
     * @param player one of the fighters.
     * @param enemy  one of the fighters.
     * @throws IllegalStateException if one of the fighters has {@code isGUIBased == true} and you didn't pass a stage.
     * @throws NullPointerException  if one of the fighters was null.
     */
    public void launchBattle(CombatEntity player, CombatEntity enemy) {
        fighter[0] = Objects.requireNonNull(player, "hero cannot be null");
        fighter[1] = Objects.requireNonNull(enemy, "enemy cannot be null");

        Util.log("_______ battle starts! _______");
        Util.log(fighter[0].name + " has " + fighter[0].health.getHp() + " hit points");
        Util.log(fighter[1].name + " has " + fighter[1].health.getHp() + " hit points");

        if (!fighter[0].isGUIBased && !fighter[1].isGUIBased) {
            new AiBattleManager().launchBattle(fighter[0], fighter[1]);
            return;
        }

        if (stage == null)
            throw new IllegalStateException("attempted launching a GUI based fight without passing a stage");

        if (stage.getRoot().findActor("buttonsTable") == null) {
            if (fighter[0].isGUIBased) generateUI(fighter[0]);
            else generateUI(fighter[1]);
        }
    }

    private void retry() {
        fighter[0].stats.resetBattleStates();
        fighter[1].stats.resetBattleStates();
        fighter[0].health.resetHp();
        fighter[1].health.resetHp();

        setCombatButtonsVisibility(true);
        setGameOverButtonsVisibility(false);
        updateLabels();

        playerHp.setVisible(true);
        enemyHp.setVisible(true);

        launchBattle(fighter[0], fighter[1]);
    }

    private void setCombatButtonsVisibility(boolean state) {
        for (TextButton combatButton : combatButtons) {
            combatButton.setDisabled(!state);
            combatButton.setVisible(state);
        }
    }

    private void setGameOverButtonsVisibility(boolean state) {
        for (TextButton gameOverButton : gameOverButtons) {
            gameOverButton.setDisabled(!state);
            gameOverButton.setVisible(state);
        }
    }

    private void updateLabels() {
        playerHp.setText(buildHpText(fighter[0]));
        enemyHp.setText(buildHpText(fighter[1]));
    }
}
