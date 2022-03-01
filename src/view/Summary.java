package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DIFFICULTY;
import model.InfoLabel;
import model.MyButton;
import model.SubScenes;

/**
 * Class creating Summary Scene
 * @author Rafa³
 */
public class Summary extends Menu {

	private AnchorPane summaryPane;
	private Scene summaryScene;
	private Stage summaryStage;

	private Stage gameStage;
	private SubScenes summSubScene;

	private int points;
	DIFFICULTY chosenDiff;
	
	private final String FONT_PATH = "src/resources/bublefat.ttf";

	/**
	 * Summary constructor that initializes stage
	 */
	public Summary() {
		initializeStage();
	}

	private void initializeStage() {
		summaryPane = new AnchorPane();
		summaryScene = new Scene(summaryPane, 800, 600);
		summaryStage = new Stage();
		summaryStage.setScene(summaryScene);
	}

	
	/**
	 * Method hides gameStage and displays the summary stage
	 * @param gameStage previous Stage
	 * @param points Points collected during the game
	 */
	public void createSummary(Stage gameStage, int points, DIFFICULTY chosenDiff) {
		this.gameStage = gameStage;
		this.gameStage.hide();
		summaryStage.show();
		this.points = points;
		this.chosenDiff = chosenDiff;
		addIcon();
		createBackground();
		createSummSubScene();
	}

	/**
	 * Summary SubScene 
	 */
	private void createSummSubScene() {
		summSubScene = new SubScenes();
		summaryPane.getChildren().add(summSubScene);

		summSubScene.setLayoutX(200);
		summSubScene.setLayoutY(100);

		InfoLabel heading = new InfoLabel("\t  Score",250,50);

		heading.setLayoutX(100);
		heading.setLayoutY(20);

		
		
		Label info = new Label("Congratulations!!");
		info.setLayoutX(95);
		info.setLayoutY(125);
		try {
			info.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 32));
		} catch (FileNotFoundException e) {
			info.setFont(new Font("Georgia", 24));
			System.out.println("Font not found or could not be loaded. Using default \"Verdana\"");
		}
		
		Text score = new Text();
		score.setText("Your score is: " + Integer.toString(points));
		score.setFont(Font.font ("Verdana", 25));
		score.setLayoutX(120);
		score.setLayoutY(200);
		
		summSubScene.getPane().getChildren().addAll(heading, info, score);
		createPlayAgainButton();
		createMenuButton();
		createExitButton();
	}
	
	/**
	 * Button returning user to menu class 
	 */
	private void createMenuButton() {
		MyButton menuButton = new MyButton("Menu");
		menuButton.setLayoutX(300);
		menuButton.setLayoutY(500);

		menuButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				summaryStage.hide();
				getMainStage().show();
			}
		});
		summaryPane.getChildren().add(menuButton);
	}

	/**
	 * Button that allows to play again
	 */
	
	private void createPlayAgainButton() {
		MyButton playAgainButton = new MyButton("Play Again");
		playAgainButton.setLayoutX(100);
		playAgainButton.setLayoutY(500);

		playAgainButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				GameManager gameManager = new GameManager();
				gameManager.createNewGame(summaryStage, chosenDiff);

			}
		});
		summaryPane.getChildren().add(playAgainButton);
	}
	

	/**
	 * Button finishing program
	 */
	private void createExitButton() {

		MyButton exitButton = new MyButton("Exit");
		exitButton.setLayoutX(500);
		exitButton.setLayoutY(500);

		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				summaryStage.close();
			}
		});
		summaryPane.getChildren().add(exitButton);
	}

	private void addIcon() {
		Image icon = new Image("file:src/resources/bin.png");
		summaryStage.getIcons().add(icon);
		summaryStage.setTitle("Segregator");
		//summaryStage.show();
	}

	private void createBackground() {
		Image backgroundImage = new Image("file:src/resources/plainbg.jpg", 800, 800, false, false);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		summaryPane.setBackground(new Background(background));
	}
}
