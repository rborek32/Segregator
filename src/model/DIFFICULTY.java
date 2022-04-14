package model;

public enum DIFFICULTY {

	EASY("file:src/resources/easy.png"), 
	MEDIUM("file:src/resources/medium.png"),
	HARD("file:src/resources/hard.png");


	String urlDifficutly;

	private DIFFICULTY(String urlDifficutly) {
		this.urlDifficutly = urlDifficutly;
	}
	
	public String getUrlDifficutly() {
		return this.urlDifficutly;
	}	
}
