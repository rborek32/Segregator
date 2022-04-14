package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;


public class InfoLabel extends Label{

	private final String FONT_PATH = "src/resources/kenvector_future.ttf";
	private final static String BACKGROUND_IMAGE = "file:src/resources/blue_button13.png";

	public InfoLabel(String text, int width, int height) { // 250 , 50
		setPrefWidth(width);
		setPrefHeight(height);
		BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, width, height, false, false), 
						BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(backgroundImage));
		setAlignment(Pos.CENTER_LEFT);
		setPadding(new Insets(10, 10, 10, 10));
		setLabelFont();
		setText(text);
	}
	
	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 24));
		} catch (FileNotFoundException e) {
			System.out.println("Font file not found. Using default font \"Verdana\"");
			setFont(Font.font("Verdana", 34));
		}
		
	}
}
