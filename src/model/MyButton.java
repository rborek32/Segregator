package model;

 import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.text.Font;

public class MyButton extends Button {

	
	private final String FONT_PATH = "src/resources/bublefat.ttf";
	private final String BUTTON_PRESSED_STYLE = "-fx-background-image: url('file:src/resources/green_button04.png')";
	private final String BUTTON_FREE_STYLE = "-fx-background-image: url('file:src/resources/green_button03.png')";
	

	public MyButton (String text) {
		setText(text);
		setButtonFont();
		setPrefWidth(190);
		setPrefHeight(45);
		setStyle(BUTTON_FREE_STYLE);
		initializeButtonListeners();
	}

	private void setButtonFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), 25));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 25));
			System.out.println("Font not found or could not be loaded. Using default \"Verdana\"");
		}

	}

	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED_STYLE);
		setPrefHeight(45);
		setLayoutY(getLayoutY() + 4);

	}

	private void setButtonReleasedStyle() {
		setStyle(BUTTON_FREE_STYLE);
		setPrefHeight(45);
		setLayoutY(getLayoutY() - 4);
	}

	private void initializeButtonListeners() {
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedStyle();
				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedStyle();
				}
			}

		});
		setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
			}
		});
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setEffect(null);
			}
		});
	}
}