package io.github.hero_premium.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import io.github.hero_premium.Assets;
import io.github.hero_premium.entities.CombatEntity;
import io.github.hero_premium.storyutil.TextManager;
import io.github.hero_premium.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

// TODO add slight delay between each move

public final class BattleManager {

    private static final Logger LOGGER = new Logger(BattleManager.class.getName(), Util.LOGGING_LEVEL);

    private final CombatEntity[] fighters = new CombatEntity[2];
    private final Stage stage;
    private TextButton[] gameOverButtons, combatButtons;
    private Label playerHp, enemyHp;

    /**
     * construct a new BattleManager object.
     *
     * @param stage the stage this object will use to build the gui on.
     * @throws NullPointerException if the passed stage was null.
     */
    public BattleManager(Stage stage) {
        this.stage = Objects.requireNonNull(stage, "stage cannot be null");
    }

    @NotNull
    private String buildHpText(@NotNull CombatEntity entity) {
        return entity.name + " Health: " + entity.health.getHp();
    }

    private void endBattle() {
        setButtonsVisibility(combatButtons, false);

        playerHp.setVisible(false);
        enemyHp.setVisible(false);
        LOGGER.info("Battle is over");
    }

    private void buildAllButtons(CombatEntity entity) {
        Table buttonsTable = new Table();
        buttonsTable.setName("buttonsTable");
        buttonsTable.setFillParent(true);
        buttonsTable.bottom();

        String[] gameOverButtonNames = {TextManager.getText(1), TextManager.getText(2)};

        Runnable[] gameOverActions = { //
            this::retry, //
            () -> Gdx.app.exit()};

        combatButtons = generateButtons(buttonsTable, entity);
        gameOverButtons = generateButtons(buttonsTable, gameOverButtonNames, gameOverActions);

        setButtonsVisibility(gameOverButtons, false);

        stage.addActor(buttonsTable);
    }

    @NotNull
    private TextButton[] generateButtons(Table buttonsTable, @NotNull CombatEntity entity) {
        Array<CombatEntity.CombatMovesManager.Move> moves = entity.movesManager.getMoves();
        TextButton[] buttons = new TextButton[moves.size];

        for (int i = 0; i < moves.size; i++) {
            Consumer<CombatEntity> moveAction = moves.get(i).move();
            Runnable action = () -> {
                moveAction.accept(BattleUtils.other(entity, fighters));
                handleBattleState(BattleUtils.validateBattle(entity, fighters), entity);
            };
            buttons[i] = generateButton(buttonsTable, moves.get(i).name(), action);
        }
        return buttons;
    }

    @NotNull
    private TextButton[] generateButtons(Table buttonsTable, @NotNull String[] buttonNames, @NotNull Runnable[] actions) {
        if (buttonNames.length != actions.length)
            throw new IllegalArgumentException("the amount of names passed does not match the amount of actions passed with");
        TextButton[] buttons = new TextButton[actions.length];
        for (int i = 0; i < actions.length; i++) {
            buttons[i] = generateButton(buttonsTable, buttonNames[i], actions[i]);
        }
        return buttons;
    }

    @NotNull
    private TextButton generateButton(@NotNull Table buttonsTable, String name, Runnable action) {
        TextButton button = new TextButton(name, Assets.skin);
        button.addListener(createListener(action));
        buttonsTable.add(button).row();
        return button;
    }

    @NotNull
    private static ChangeListener createListener(Runnable action) {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                action.run();
            }
        };
    }

    private void generateLabels() {
        Table labelsTable = new Table();
        labelsTable.setFillParent(true);
        labelsTable.bottom().right();

        playerHp = new Label(buildHpText(fighters[0]), Assets.skin);
        enemyHp = new Label(buildHpText(fighters[1]), Assets.skin);

        labelsTable.add(playerHp).pad(20).row();
        labelsTable.add(enemyHp);
        stage.addActor(labelsTable);
    }

    private void generateUI(CombatEntity entity) {
        buildAllButtons(entity);
        generateLabels();
        LOGGER.info("GUI generated successfully");
    }

    private void handleBattleState(@NotNull BattleUtils.BattleState state, CombatEntity entity) {
        CombatEntity otherEntity = BattleUtils.other(entity, fighters);
        switch (state) {
            case WON -> {
                endBattle();
                entity.movement.movementLocked = false;
                otherEntity.moveGold(otherEntity.getGold(), entity);
            }
            case LOST -> {
                endBattle();
                setButtonsVisibility(gameOverButtons, true);
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
     * @param fighter1 one of the fighters.
     * @param fighter2  one of the fighters.
     * @throws NullPointerException if one of the fighters was null.
     */
    public void launchBattle(CombatEntity fighter1, CombatEntity fighter2) {
        fighters[0] = Objects.requireNonNull(fighter1, "fighter1 cannot be null");
        fighters[1] = Objects.requireNonNull(fighter2, "fighter2 cannot be null");
        if (fighters[0].equals(fighters[1]))
            throw new IllegalArgumentException("2 fighters cannot be the same");
        LOGGER.info("_______ battle starts! _______");
        for (CombatEntity fighter : fighters) {
            LOGGER.info(fighter.name + " has " + fighter.health.getHp() + " hit points");
        }

        if (!BattleUtils.anyFighterIsPlayable(fighters)) {
            new AiBattleManager().launchBattle(fighters[0], fighters[1]);
            return;
        }

        if (stage.getRoot().findActor("buttonsTable") == null) {
            if (fighters[0].isPlayable) generateUI(fighters[0]);
            else generateUI(fighters[1]);
        }
    }

    private void retry() {
        BattleUtils.resetFightersStates(fighters);

        setButtonsVisibility(combatButtons, true);
        setButtonsVisibility(gameOverButtons, false);
        updateHpLabels();

        playerHp.setVisible(true);
        enemyHp.setVisible(true);

        launchBattle(fighters[0], fighters[1]);
    }

    private void setButtonsVisibility(TextButton[] buttons, boolean state) {
        for (TextButton button : buttons) {
            button.setDisabled(!state);
            button.setVisible(state);
        }
    }

    private void updateHpLabels() {
        playerHp.setText(buildHpText(fighters[0]));
        enemyHp.setText(buildHpText(fighters[1]));
    }
}
