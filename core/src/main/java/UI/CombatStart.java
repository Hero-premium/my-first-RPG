/*
 * package UI;
 * 
 * import java.util.Random; import Entities.Entity; import Entities.Player;
 * import javafx.geometry.Pos; import javafx.scene.Scene; import
 * javafx.scene.control.Button; import javafx.scene.control.Label; import
 * javafx.scene.layout.BorderPane; import javafx.scene.layout.HBox; import
 * javafx.scene.layout.VBox;
 * 
 * 
 * public class CombatStart { public Label playerHp; public static void
 * updateHp(Label playerHp, Player player, Label enemyHp, Entity enemy) {
 * playerHp.setText(player.name + " hp:" + String.valueOf(player.hp));
 * enemyHp.setText(enemy.name + " hp:" +String.valueOf(enemy.hp)); }
 * 
 * public static void updateVc(Label text, String talker) {
 * text.setText(talker); }
 * 
 * private Scene scene;
 * 
 * public CombatStart(Player player, Entity enemy,Random rand) {
 * 
 * this.playerHp = new Label(player.name + " hp:" + String.valueOf(player.hp));
 * Label enemyHp = new Label(enemy.name + " hp:" + String.valueOf(enemy.hp));
 * Label yapp = new Label();
 * 
 * 
 * Button Kick = new Button("Kick"); Kick.setOnAction(e -> { player.kick(rand,
 * enemy, yapp); enemy.taketurn(player, rand, yapp); updateHp(playerHp,player,
 * enemyHp, enemy); });
 * 
 * Button SwordSlash = new Button("SwordSlash"); SwordSlash.setOnAction(e -> {
 * player.swordSlash(rand, enemy, yapp); enemy.taketurn(player, rand, yapp);
 * updateHp(playerHp, player, enemyHp, enemy); });
 * 
 * Button Dash = new Button("Dash"); Dash.setOnAction(e -> { player.dash(rand,
 * enemy, yapp); enemy.taketurn(player, rand, yapp); updateHp(playerHp, player,
 * enemyHp, enemy); });
 * 
 * 
 * HBox root = new HBox(10); root.setAlignment(Pos.BOTTOM_LEFT);
 * root.getChildren().addAll(Kick,SwordSlash,Dash);
 * 
 * VBox HpDisplay = new VBox(20); HpDisplay.setAlignment(Pos.BOTTOM_RIGHT);
 * HpDisplay.getChildren().addAll(playerHp, enemyHp);
 * 
 * BorderPane mainLayout = new BorderPane(); mainLayout.setLeft(root);
 * mainLayout.setRight(HpDisplay); mainLayout.setCenter(yapp);
 * 
 * scene = new Scene(mainLayout, 600, 400); } public Scene getScene() { return
 * scene;
 * 
 * } }
 */