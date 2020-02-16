package model;

public class User {
	
	private String name;
	private String lastName;
	private String numDoc;
	private String phone;
	private String address;
	private String typeDoc;
	
	private Turn turn;
	private String assisted;
	public static String AS="ASSITED";
	public static String NOAS="NOT ASSISTED";
	public static String NOTD="NOT DEFINEAD";
	
	public User(String ptypeDoc, String pnumDoc, String pname, String pLastName, String pPhone, String pAddress) {
		typeDoc=ptypeDoc;
		name=pname;
		lastName=pLastName;
		numDoc=pnumDoc;
		phone=pPhone;
		address=pAddress;
		assisted=NOTD;
	}
	
	public String getTypeDoc() {
		return typeDoc;
	}
	
	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getNumDoc() {
		return numDoc;
	}
	
	public String getTurn() {
		return turn.getLetter()+turn.getNumber();
	}
	
	public int getTurnNumber() {
		return Integer.parseInt(turn.getNumber());
	}
	
	public char getTurnLetter() {
		return turn.getLetter();
	}

	public void setTurn(char letter, String number) {
		if(Integer.parseInt(number)<9) {
			number="0"+number;
			turn= new Turn(letter, number);
		}
		else
		{
			turn= new Turn(letter, number);
		}
	}
	
	public void isAssisted(String option) {
		assisted=option;
	}
	public String wasAssisted() {
		return assisted;
	}
	

}
