package view;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DIFFICULTY;
import model.InfoLabel;
import model.MyButton;
import model.SubScenes;
import model.ChooseDiff;

/**
 * GUI menu class
 * @author Rafa³
 *
 */
public class Menu {

	
	/**
	 * Size parameters of menu class
	 */
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	private final static int MENU_BUTTON_START_X = 100;
	private final static int MENU_BUTTON_START_Y = 150;

	/**
	 * Pane,scene instances
	 */
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;

	
	/**
	 * File paths for menu elements
	 */
	Image greenBin = new Image("file:src/resources/greenTrash.png", 70, 70, true, false);
	Image blueBin = new Image("file:src/resources/blueTrash.png", 70, 70, true, false);
	Image yellowBin = new Image("file:src/resources/yellowTrash.png", 70, 70, true, false);
	private final String FONT_PATH = "src/resources/bublefat.ttf";

	
	/**
	 * Scenes that transition after clicking required button
	 */
	private SubScenes infoSubScene;
	private SubScenes controlSubScene;
	private SubScenes difficultySubScene;
	private SubScenes sceneToHide;

	private DIFFICULTY chosenDiff;

	List<MyButton> menuButtons;
	List<ChooseDiff> diffList;

	
	/**
	 * Creates new form gui.Menu
	 */
	public Menu() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createSubScenes();
		createButtons();
		createBackground();
		createTitle();
		addIcon();
	}

	/**
	 * Method checking if the scene is displayed, if not then it transitions subscene
	 * @param subScene
	 */
	private void showSubScene(SubScenes subScene) {

		if (sceneToHide != null) {
			sceneToHide.moveSubScene();
		}

		subScene.moveSubScene();
		sceneToHide = subScene;
	}

	/**
	 * Method gathering all button creation methods
	 */
	public void createButtons() {
		createStartButton();
		createControlButton();
		createInfoButton();
		createExitButton();
	}

	/**
	 * Start button
	 */
	public void createStartButton() {
		MyButton startButton = new MyButton("Play");
		addMenuButtons(startButton);

		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(difficultySubScene);
			}
		});
	}
	
	/**
	 * Button displaying control instruction
	 */
	public void createControlButton() {
		MyButton scoreButton = new MyButton("Control info");
		addMenuButtons(scoreButton);

		scoreButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(controlSubScene);
			}
		});
	}

	
	/**
	 * Button displaying guide
	 */
	private void createInfoButton() {

		MyButton infoButton = new MyButton("Guide");
		addMenuButtons(infoButton);

		infoButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(infoSubScene);

			}
		});
	}
	
	/**
	 * Exit button
	 */
	private void createExitButton() {
		MyButton exitButton = new MyButton("Exit");
		addMenuButtons(exitButton);

		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();

			}
		});
	}

	
	/**
	 * Creating subscenes
	 */
	private void createSubScenes() {
		createControlSubScene();
		createInfoSubScene();
		createDifficultySubScene();
	}

	/**
	 * Creating difficulty subscene
	 */
	private void createDifficultySubScene() {
		difficultySubScene = new SubScenes();
		mainPane.getChildren().add(difficultySubScene);

		InfoLabel chooseDifficulty = new InfoLabel("  Choose difficulty:", 250, 50);
		chooseDifficulty.setLayoutX(100);
		chooseDifficulty.setLayoutY(25);
		try {
			chooseDifficulty.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 25));
		} catch (FileNotFoundException e) {
			chooseDifficulty.setFont(Font.font("Verdana", 25));
			System.out.println("Font not found or could not be loaded. Using default \"Verdana\"");
		}

		difficultySubScene.getPane().getChildren().add(chooseDifficulty);
		difficultySubScene.getPane().getChildren().add(createDifficulties());
		difficultySubScene.getPane().getChildren().add(createDiffStartButton());

	}

	/**
	 * Component used to define game difficulty
	 * @return
	 */
	private HBox createDifficulties() {
		HBox box = new HBox();
		box.setSpacing(20);
		diffList = new ArrayList<>();
		for (DIFFICULTY diff : DIFFICULTY.values()) {
			ChooseDiff pickDiff = new ChooseDiff(diff);
			diffList.add(pickDiff);
			box.getChildren().add(pickDiff);
			pickDiff.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					for (ChooseDiff diff : diffList) {
						diff.setChosenCircle(false);
					}
					pickDiff.setChosenCircle(true);
					chosenDiff = pickDiff.getDiff();

				}
			});
		}

		box.setLayoutX(75);
		box.setLayoutY(100);
		return box;
	}

	
	/**
	 * Start game button of Diff subscene
	 * @return
	 */
	private MyButton createDiffStartButton() {
		MyButton startButton = new MyButton("Start");

		startButton.setLayoutX(135);
		startButton.setLayoutY(250);

		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (getChosenDiff() != null) {
					GameManager gameManager = new GameManager();
					gameManager.createNewGame(mainStage, getChosenDiff());
				}

			}
		});

		return startButton;
	}

	
	/**
	 * Control Subscene displays goal of the game and instruction how to play
	 */
	private void createControlSubScene() {
		controlSubScene = new SubScenes();
		mainPane.getChildren().add(controlSubScene);

		InfoLabel heading = new InfoLabel("	 Control", 250, 50);

		heading.setLayoutX(100);
		heading.setLayoutY(20);

		Label info = new Label("Your goal is to collect garbage \nusing the appropriate container.\n"
				+ "Move using arrows and change \ncolours with QWE buttons!");
		info.setLayoutX(50);
		info.setLayoutY(80);
		info.setFont(new Font("Georgia", 24));
		controlSubScene.getPane().getChildren().addAll(heading, info);

		ImageView panelInfo = new ImageView("file:src/resources/nak³adkaPoziomo.png");
		panelInfo.setFitHeight(100);
		panelInfo.setFitWidth(350);
		panelInfo.setLayoutY(220);
		panelInfo.setLayoutX(50);
		controlSubScene.getPane().getChildren().add(panelInfo);
	}

	/**
	 * Info SubScene contains the basic rules of segregation 
	 */
	private void createInfoSubScene() {
		infoSubScene = new SubScenes();
		mainPane.getChildren().add(infoSubScene);

		InfoLabel info = new InfoLabel("	   Guide", 250, 50);
		info.setLayoutX(100);
		info.setLayoutY(20);
		GridPane guideGrid = new GridPane();
		guideGrid.setLayoutX(40);
		guideGrid.setLayoutY(80);
		guideGrid.setHgap(20);
		guideGrid.setVgap(10);

		ImageView blue = new ImageView(blueBin);
		ImageView green = new ImageView(greenBin);
		ImageView yellow = new ImageView(yellowBin);

		Label blueInfo = new Label("Paper \nclean paper, cardboard, packaging, \r\n"
				+ "newspapers, magazines and leaflets,\r\n" + "cardboard, notebooks, office paper ");
		Label greenInfo = new Label("Glass \nglass packaging only,\r\n" + "including empty bottles, jars,\r\n"
				+ "cosmetics packaging, empty");
		Label yellowInfo = new Label("Metal and plastic\nempty squashed plastic bottles, bottle\r\n"
				+ "caps and jar lids, plastic packaging,\r\n" + "bags, cartons, beverage and cans");

		blueInfo.setFont(new Font("Georgia", 16));
		greenInfo.setFont(new Font("Georgia", 16));
		yellowInfo.setFont(new Font("Georgia", 16));

		guideGrid.add(blue, 0, 0);
		guideGrid.add(blueInfo, 1, 0);
		guideGrid.add(green, 0, 1);
		guideGrid.add(greenInfo, 1, 1);
		guideGrid.add(yellow, 0, 2);
		guideGrid.add(yellowInfo, 1, 2);
		infoSubScene.getPane().getChildren().addAll(info, guideGrid);
	}

	/**
	 * Method used to add buttons to the pane
	 * @param button
	 */
	private void addMenuButtons(MyButton button) {
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}

	/**
	 * Method to create title
	 */
	private void createTitle() {
		Text t = new Text(300, 100, "Segregator");
		try {
			t.setFont(Font.loadFont(new FileInputStream(new File("src/resources/bublefat.ttf")), 50));
		} catch (FileNotFoundException e) {
			System.out.println("Font not found or could not be loaded. Using default \"Verdana\"");
		}

		t.setFill(Color.BLACK);
		mainPane.getChildren().add(t);
	}

	/**
	 * Method to create background
	 */
	private void createBackground() {
		Image backgroundImage = new Image("file:src/resources/paper.jpg", 800, 600, false, false);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
	
	private void addIcon() {
		Image icon = new Image("file:src/resources/bin.png");
		mainStage.getIcons().add(icon);
		mainStage.setTitle("Segregator");
	}

	/**
	 * @return main stage
	 */
	public Stage getMainStage() {
		return mainStage;
	}

	/**
	 * Method used to pass chosen Difficulty
	 * @return returns chosenDiff
	 */
	public DIFFICULTY getChosenDiff() {
		return chosenDiff;
	}

}
