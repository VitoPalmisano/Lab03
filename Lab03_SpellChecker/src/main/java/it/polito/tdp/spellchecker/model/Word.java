package it.polito.tdp.spellchecker.model;

public class Word {

	private String word;
	private boolean isRight;
	
	public Word(String word) {
		this.word = word;
		this.isRight = false;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setItIsRight() {
		this.isRight = true;
	}

	public String getWord() {
		return word;
	}
}
