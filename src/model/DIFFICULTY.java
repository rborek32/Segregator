package model;

/**
 * Enumeration declaration of DIFFICULTY
 * @author Rafa³
 * 
 */

public enum DIFFICULTY {

	/**
	 * Enums with paths to their button files
	 */
	EASY("file:src/resources/easy.png"), 
	MEDIUM("file:src/resources/medium.png"),
	HARD("file:src/resources/hard.png");

	
	/**
	 * Difficulty url
	 */
	String urlDifficutly;

	private DIFFICULTY(String urlDifficutly) {
		this.urlDifficutly = urlDifficutly;
	}
	
	/**
	 * @return url of chosen diff
	 */
	public String getUrlDifficutly() {
		return this.urlDifficutly;
	}	
}
