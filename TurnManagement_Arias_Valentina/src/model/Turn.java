package model;

public class Turn {
	private char letter;
	private String number;
	
	public Turn(char letter_, String number_) {
		letter=letter_;
		number=number_;
	}
	
	public char getLetter() {
		return letter;
	}
	public String getNumber() {
		return number;
	}
	
	
}
