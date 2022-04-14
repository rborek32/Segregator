package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ChooseDiff extends VBox{
	
	private ImageView circle;
	private ImageView diffImage;
	  

	private String notChosenCircle = "file:src/resources/grey_circle.png";
	private String ChosenCircle = "file:src/resources/blue_boxTick.png";

	private DIFFICULTY diff;
	
	private boolean isChosen;
	
	public ChooseDiff(DIFFICULTY diff) {
		circle = new ImageView(notChosenCircle);
		diffImage = new ImageView(diff.getUrlDifficutly());
		this.diff = diff;
		isChosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		circle.setFitHeight(40);
		circle.setFitWidth(40);
		diffImage.setInputMethodRequests(getInputMethodRequests());
		diffImage.setFitHeight(40);
		diffImage.setFitWidth(90);
		this.getChildren().add(circle);
		this.getChildren().add(diffImage);
	}
	
	public DIFFICULTY getDiff() {
		return diff;
	}
	
	public boolean getChosenCircle() {
		return isChosen;
	}
	
	public void setChosenCircle(boolean isChosen) {
		this.isChosen = isChosen;
		String imageToSet = this.isChosen ? ChosenCircle : notChosenCircle;
		circle.setImage(new Image(imageToSet));
	}
}
