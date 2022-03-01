package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

/**
 * Class responsible for SubScene appearance
 * @author Rafa³
 *
 */
public class SubScenes extends SubScene{
	
	private final static String BACKGROUND_IMAGE = "file:src/resources/rounded.png";
	
	private  boolean isHidden = true;
	
	/**
	 * Constructor includes information about size,layout and background of the subscene
	 */
	public SubScenes() {
		super(new AnchorPane(), 600, 600);
		prefWidth(600);
		prefHeight(400);
		
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 450, 350, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(image));
		
		setLayoutX(1025);
		setLayoutY(150);
	}
	
	/**
	 * Subscenes are created outside of the window. 
	 * In case to make subscene appear it is transitioned.
	 */
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.5));
		transition.setNode(this);
		
		if(isHidden) {
			transition.setToX(-700);
			isHidden = false;			
		} 
		else {
			transition.setToX(0);
			isHidden = true;
		}
		transition.play();
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}
}