/*
 * package UI;
 * 
 * import Entities.Player; import javafx.geometry.Pos; import
 * javafx.scene.Scene; import javafx.scene.control.Button; import
 * javafx.scene.control.Label; import javafx.scene.control.TextField; import
 * javafx.scene.image.Image; import javafx.scene.image.ImageView; import
 * javafx.scene.layout.BorderPane; import javafx.scene.layout.Pane; import
 * javafx.scene.layout.VBox; import javafx.stage.Stage;
 * 
 * public class GameStart{
 * 
 * private Scene scene; int index = 0;
 * 
 * public static void takeInput(Player player, TextField takein, int index) { if
 * (index == 3) { takein.setVisible(true); } else if (index == 4) { player.name
 * = takein.getText(); takein.clear(); takein.setVisible(false); } else {
 * takein.setVisible(false); } }
 * 
 * public static void updatelabel(Label label, String text) {
 * label.setText(text); }
 * 
 * 
 * public GameStart(Player player, Stage primaryStage, CombatStart combatStart)
 * { Label talk = new Label();
 * 
 * TextField takein = new TextField(); takein.setVisible(false); Button Continue
 * = new Button("continue");
 * 
 * Continue.setOnAction(e -> { index =
 * Story.Diologs.launchStory(player,talk,index); takeInput(player,takein,index);
 * combatStart.playerHp.setText(player.name + " hp:" +
 * String.valueOf(player.hp));
 * 
 * if (index == 11) { primaryStage.setScene(combatStart.getScene()); } });
 * 
 * 
 * 
 * int playerX = 250; int playerY = 250;
 * 
 * Image knightImage = new Image(getClass().getResourceAsStream(
 * "/Assets/Gemini_Generated_Image_l0rghdl0rghdl0rg.png")); ImageView
 * playerAvater = new ImageView(knightImage);
 * 
 * playerAvater.setFitWidth(200); playerAvater.setFitHeight(300);
 * playerAvater.setPreserveRatio(true);
 * 
 * Pane playerPs = new Pane(playerAvater); playerPs.setLayoutX(playerX);
 * playerPs.setLayoutY(playerY);
 * 
 * VBox root = new VBox(10); root.setAlignment(Pos.CENTER);
 * root.getChildren().addAll(talk,takein,Continue);
 * 
 * 
 * BorderPane mainLayout = new BorderPane(); mainLayout.setLeft(root);
 * mainLayout.getChildren().add(playerPs);
 * 
 * scene = new Scene(mainLayout, 600, 400);
 * 
 * }
 * 
 * public Scene getScene() { return scene;
 * 
 * }
 * 
 * }
 */