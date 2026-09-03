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
    private Label playerHp;
    private Label enemyHp;
    private CombatEntity player;
    private CombatEntity enemy;
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
        return entity.name + " Health: " + entity.getHp();
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
                    action.accept(enemy);
                    handleBattleState(validateBattle());
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
                    handleBattleState(validateBattle());
                }
            });
            buttonsTable.add(buttons[i]).row();
        }
    }

    private void generateLabels() {

        Table labelsTable = new Table();
        labelsTable.setFillParent(true);
        labelsTable.bottom().right();

        playerHp = new Label(buildHpText(player), Assets.skin);
        enemyHp = new Label(buildHpText(enemy), Assets.skin);

        labelsTable.add(playerHp).pad(20).row();
        labelsTable.add(enemyHp);
        stage.addActor(labelsTable);
    }

    private void generateUI(CombatEntity entity) {
        buildAllButtons(entity);
        generateLabels();
        Util.log("GUI generated successfully");
    }

    private void handleBattleState(BattleState state) {
        switch (state) {
            case WON -> {
                endBattle();
                player.movementLocked = false;
                enemy.moveGold(enemy.getGold(), player);
            }
            case LOST -> {
                endBattle();
                setGameOverButtonsVisibility(true);
            }
            case GOING -> {
                enemy.takeTurn(player);

                /*
                 * the line below triggers when the player losses, in using validateBattle
                 * knowing it will return lost just so I can get it's side effects (the prints)
                 */
                if (player.getHp() <= 0) handleBattleState(validateBattle());
            }
            default -> throw new AssertionError("The returned enum \"" + state + "\" was unexpected");
        }
        updateLabels();
    }

    /**
     *
     * @param player
     * @param enemy
     */
    public void launchBattle(CombatEntity player, CombatEntity enemy) {
        this.player = Objects.requireNonNull(player, "hero cannot be null");
        this.enemy = Objects.requireNonNull(enemy, "enemy cannot be null");

        Util.log("_______ battle starts! _______");
        Util.log(this.player.name + " has " + this.player.getHp() + " hit points");
        Util.log(this.enemy.name + " has " + this.enemy.getHp() + " hit points");


        if (!this.player.isGUIBased && !this.enemy.isGUIBased) {
            new AiBattleManager().launchBattle(this.player, this.enemy);
            return;
        }

        Objects.requireNonNull(stage, "attempted launching a GUI based fight without passing a stage");
        /*
         * to avoid recreating the combat GUI on the same stage and to make sure they
         * get generated when they're needed
         */
        if (stage.getRoot().findActor("buttonsTable") == null) {
            if (this.player.isGUIBased) generateUI(this.player);
            else generateUI(this.enemy);
        }
    }

    private void retry() {
        player.resetBattleStates();
        enemy.resetBattleStates();
        player.resetHp();
        enemy.resetHp();

        setCombatButtonsVisibility(true);
        setGameOverButtonsVisibility(false);
        updateLabels();

        playerHp.setVisible(true);
        enemyHp.setVisible(true);

        launchBattle(player, enemy);
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
        playerHp.setText(buildHpText(player));
        enemyHp.setText(buildHpText(enemy));
    }

    private BattleState validateBattle() {
        if (enemy.getHp() <= 0) {
            Util.log("the player won");
            return BattleState.WON;
        }
        if (player.getHp() <= 0) {
            Util.log("the player lost");
            return BattleState.LOST;
        }
        return BattleState.GOING;
    }

    private enum BattleState {
        GOING, WON, LOST
    }

}
