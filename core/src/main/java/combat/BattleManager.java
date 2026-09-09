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
    private Label playerHp, enemyHp;
    private final Stage stage;

    public BattleManager(Stage stage) {
        this.stage = Objects.requireNonNull(stage, "stage cannot be null");
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
        CombatEntity otherEntity = BattleUtils.other(entity, fighter);
        switch (state) {
            case WON -> {
                endBattle();
                entity.movementLocked = false;
                otherEntity.moveGold(otherEntity.getGold(), entity);
            }
            case LOST -> {
                endBattle();
                setGameOverButtonsVisibility(true);
            }
            case GOING -> {
                otherEntity.takeTurn(entity);

                if (entity.health.getHp() <= 0) handleBattleState(BattleUtils.BattleState.LOST, entity);
            }
            default -> throw new AssertionError("The returned enum \"" + state + "\" was unexpected");
        }
        updateHpLabels();
    }

    /**
     * Launches a battle between two combat entities.
     *
     * @param player one of the fighters.
     * @param enemy  one of the fighters.
     * @throws NullPointerException if one of the fighters was null.
     */
    public void launchBattle(CombatEntity player, CombatEntity enemy) {
        fighter[0] = Objects.requireNonNull(player, "hero cannot be null");
        fighter[1] = Objects.requireNonNull(enemy, "enemy cannot be null");

        Util.log("_______ battle starts! _______");
        for (CombatEntity fighter : fighter) {
            Util.log(fighter.name + " has " + fighter.health.getHp() + " hit points");
        }

        if (!AnyFighterIsPlayable()) {
            new AiBattleManager().launchBattle(fighter[0], fighter[1]);
            return;
        }

        if (stage.getRoot().findActor("buttonsTable") == null) {
            if (fighter[0].isPlayable) generateUI(fighter[0]);
            else generateUI(fighter[1]);
        }
    }

    private boolean AnyFighterIsPlayable() {
        for (CombatEntity fighter : fighter) {
            if (fighter.isPlayable) return true;
        }
        return false;
    }

    private void retry() {
        for (CombatEntity fighter : fighter) {
            fighter.statusEffectsManager.resetBattleStates();
            fighter.health.resetHp();
        }

        setCombatButtonsVisibility(true);
        setGameOverButtonsVisibility(false);
        updateHpLabels();

        playerHp.setVisible(true);
        enemyHp.setVisible(true);

        launchBattle(fighter[0], fighter[1]);
    }

    private void setCombatButtonsVisibility(boolean state) {
        setButtonsVisibility(combatButtons, state);
    }

    private void setButtonsVisibility(TextButton[] buttons, boolean state) {
        for (TextButton button : buttons) {
            button.setDisabled(!state);
            button.setVisible(state);
        }
    }

    private void setGameOverButtonsVisibility(boolean state) {
        setButtonsVisibility(gameOverButtons, state);
    }

    private void updateHpLabels() {
        playerHp.setText(buildHpText(fighter[0]));
        enemyHp.setText(buildHpText(fighter[1]));
    }
}
