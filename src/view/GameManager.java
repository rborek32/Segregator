package view;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.DIFFICULTY;
import model.InfoLabel;

/**
 * GUI of the game
 * @author Rafa³
 */
public class GameManager {

	/**
	 * AnchorPane of the game
	 */
	private AnchorPane gamePane;
	/**
	 * Scene of the game
	 */
	private Scene gameScene;
	/**
	 * Stage of the game
	 */
	private Stage gameStage;

	/**
	 * Stage of the menu
	 */
	private Stage menuStage;
	
	/**
	 * Game animation timer
	 */
	private AnimationTimer gameTimer;
	/**
	 * Random number generator
	 */
	Random randomPositionGenerator;
	/**
	 * Number of the trash from 0 to 15
	 */
	private int randomTrash;
	/**
	 * User score
	 */
	private static int score;
	
	private final static int trash_RADIUS = 20;
	private final static int bin_RADIUS = 40;

	/**
	 * Imageview meant to change colors
	 */
	ImageView bin;
	Image greenBin = new Image("file:src/resources/greenTrash.png");
	Image blueBin = new Image("file:src/resources/blueTrash.png");
	Image yellowBin = new Image("file:src/resources/yellowTrash.png");

	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	/**
	 * ImageView array of all trash
	 */
	private ImageView[] allTrash;

	private boolean isBlue = false;
	private boolean isGreen = true;
	private boolean isYellow = false;

	/**
	 * ImageView Array of life
	 */
	private ImageView[] life;
	/**
	 * Current life 
	 */
	private int lifeCount;
	/**
	 * Int to count life
	 */
	int reverseCounter;
	private InfoLabel pointsLabel;
	private DIFFICULTY chosenDiff;

	public GameManager() {
		initializeStage();
		createKeysListeners();
		randomPositionGenerator = new Random();
	

	}

	private void initializeStage() {	
		score = 0;
		reverseCounter = 0;
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, 800, 600);
		gameStage = new Stage();
		gameStage.setScene(gameScene);

	}

	/**
	 * Method creating game stage and hiding menu stage
	 * @param menuStage takes current Stage
	 * @param chosenDiff takes current Difficulty
	 */
	public void createNewGame(Stage menuStage, DIFFICULTY chosenDiff) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		this.chosenDiff = chosenDiff;
		addIcon();
		addElementsToArray();
		createBin();
		createInstructionPanel();
		createBackground();
		createlife();
		createGameElements();
		createGameLoop(chosenDiff);
		gameStage.show();
	}

	/**
	 * Method starting ActionTimer
	 * @param chosenDiff
	 */
	private void createGameLoop(DIFFICULTY chosenDiff) {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				moveGameElements(chosenDiff);
				moveBin();
				trashCollide();
			}
		};
		gameTimer.start();
	}

	/**
	 * Method creating starting game elements
	 */
	private void createGameElements() {
		pointsLabel = new InfoLabel("POINTS:" + score, 250, 50);
		pointsLabel.setLayoutX(10);
		pointsLabel.setLayoutY(10);
		gamePane.getChildren().add(pointsLabel);

		randomTrash = ThreadLocalRandom.current().nextInt(0, 15);
		setNewElementPosition(allTrash[randomTrash]);
		gamePane.getChildren().add(allTrash[randomTrash]);

	}

	/**
	 * Creates life 
	 */
	private void createlife() {
		lifeCount = 2;
		life = new ImageView[3];

		for (int i = 0; i < life.length; i++) {
			life[i] = new ImageView("file:src/resources/blue_boxCheckmark.png");
			life[i].setLayoutX(480 + (i * 50));
			life[i].setLayoutY(25);
			gamePane.getChildren().add(life[i]);

		}
	}

	/**
	 * Method reducing life
	 */
	private void reduceLife() {
		
		gamePane.getChildren().remove(life[2-lifeCount]);
		//reverseCounter++;
		lifeCount--;
		if (lifeCount < 0) {
			gameTimer.stop();
			Summary summ = new Summary();
			
			summ.createSummary(gameStage, score, chosenDiff);
		}
	}

	/**
	 * Method assigning a falling speed depending on the difficulty level 
	 * @param chosenDiff
	 */
	private void moveGameElements(DIFFICULTY chosenDiff) {

		if (chosenDiff == DIFFICULTY.EASY) {
			allTrash[randomTrash].setLayoutY(allTrash[randomTrash].getLayoutY() + 3);
			allTrash[randomTrash].setRotate(allTrash[randomTrash].getRotate() + 2);
		} else if (chosenDiff == DIFFICULTY.MEDIUM) {
			allTrash[randomTrash].setLayoutY(allTrash[randomTrash].getLayoutY() + 4.5);
			allTrash[randomTrash].setRotate(allTrash[randomTrash].getRotate() + 3);
		} else if (chosenDiff == DIFFICULTY.HARD) {
			allTrash[randomTrash].setLayoutY(allTrash[randomTrash].getLayoutY() + 6);
			allTrash[randomTrash].setRotate(allTrash[randomTrash].getRotate() + 2);
		}

	}

	/**
	 * Create default bin (green)
	 */
	private void createBin() {
		bin = new ImageView("file:src/resources/greenTrash.png");
		bin.setLayoutX(200);
		bin.setLayoutY(450);
		bin.setFitHeight(150);
		bin.setFitWidth(125);
		
		gamePane.getChildren().add(bin);
	}

	private void createInstructionPanel() {
		ImageView panelInfo = new ImageView("file:src/resources/nak³adka.png");
		panelInfo.setLayoutX(650);
		panelInfo.setFitHeight(600);
		panelInfo.setFitWidth(150);
		gamePane.getChildren().add(panelInfo);
	}

	/**
	 * Loading ImageViews of trash 
	 */
	private void addElementsToArray() {
		allTrash = new ImageView[15];
		for (int i = 0; i < 15; i++) {
			if (i <= 5)
				allTrash[i] = new ImageView("file:src/resources/blueTrash" + Integer.toString(i) + ".png");
			else if (i <= 10 && i >= 5)
				allTrash[i] = new ImageView("file:src/resources/yellowTrash" + Integer.toString(i - 5) + ".png");
			else if (i <= 15 && i >= 10)
				allTrash[i] = new ImageView("file:src/resources/greenTrash" + Integer.toString(i - 10) + ".png");
		}
	}

	private void createKeysListeners() {

		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.Q) {
					bin.setImage(greenBin);
					isBlue = false;
					isGreen = true;
					isYellow = false;
				} else if (event.getCode() == KeyCode.W) {
					bin.setImage(blueBin);
					isBlue = true;
					isGreen = false;
					isYellow = false;
				} else if (event.getCode() == KeyCode.E) {
					bin.setImage(yellowBin);
					isBlue = false;
					isGreen = false;
					isYellow = true;

				} else if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				} else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				}
			}
		});

		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				} else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
				}
			}
		});
	}

	private void moveBin() {

		if (isLeftKeyPressed && !isRightKeyPressed) {
			if (bin.getLayoutX() > -20) {
				bin.setLayoutX(bin.getLayoutX() - 5);
			}
		}
		if (isRightKeyPressed && !isLeftKeyPressed) {
			if (bin.getLayoutX() < 450) {
				bin.setLayoutX(bin.getLayoutX() + 5);
			}
		}
	}

	/**
	 * Method checking if trash collide with the bin or falls of the window
	 * 
	 */
	private void trashCollide() {

		String fileName = allTrash[randomTrash].getImage().getUrl();

		if ((bin_RADIUS + trash_RADIUS > calculateDistance(bin.getLayoutX() + 50,
				allTrash[randomTrash].getLayoutX() + 15, bin.getLayoutY() + 35,
				allTrash[randomTrash].getLayoutY() + 15)) && isYellow == true && fileName.contains("yellow")) {
			gamePane.getChildren().remove(allTrash[randomTrash]);
			score = score + 2;
			createGameElements();
		} else if ((bin_RADIUS + trash_RADIUS > calculateDistance(bin.getLayoutX() + 50,
				allTrash[randomTrash].getLayoutX() + 15, bin.getLayoutY() + 35,
				allTrash[randomTrash].getLayoutY() + 15)) && isBlue == true && fileName.contains("blue")) {
			gamePane.getChildren().remove(allTrash[randomTrash]);
			score =score + 4;
			createGameElements();
		} else if ((bin_RADIUS + trash_RADIUS > calculateDistance(bin.getLayoutX() + 50,
				allTrash[randomTrash].getLayoutX() + 15, bin.getLayoutY() + 35,
				allTrash[randomTrash].getLayoutY() + 15)) && isGreen == true && fileName.contains("green")) {
			gamePane.getChildren().remove(allTrash[randomTrash]);
			score =score + 3;
			createGameElements();
		} else if (allTrash[randomTrash].getLayoutY() > 500) {
			gamePane.getChildren().remove(allTrash[randomTrash]);
			createGameElements();
			reduceLife();
		}
	}

	/**
	 * Generate random position for new trash
	 * LayoutX = 100 || 250 || 400
	 * @param image
	 */
	private void setNewElementPosition(ImageView image) {

		int randPos = ThreadLocalRandom.current().nextInt(0, 3);
		if (randPos == 0)
			image.setLayoutX(100);
		else if (randPos == 1)
			image.setLayoutX(250);
		else
			image.setLayoutX(400);
		image.setLayoutY(-30);
	}

	private void createBackground() {
		Image backgroundImage = new Image("file:src/resources/plainbg.jpg", 800, 600, false, false);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(background));
	}

	/**
	 * Method to make collision more accurate
	 * @param x2
	 * @param y1
	 * @param y2
	 * @return
	 */
	private double calculateDistance(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	private void addIcon() {
		Image icon = new Image("file:src/resources/bin.png");
		gameStage.getIcons().add(icon);
		gameStage.setTitle("Segregator");
		//gameStage.show();
	}
}
