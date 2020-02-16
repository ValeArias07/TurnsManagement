package model;

import java.util.ArrayList;

import CustomExceptions.DocumentExistException;
import CustomExceptions.NoTurnYetException;
import CustomExceptions.NotFoundException;
import CustomExceptions.ObligatoryFieldsException;


public class Admin {
	
	private ArrayList <User> usersData;
	private ArrayList <User> usersWithTurns;
	
	public Admin() {
		usersData=new ArrayList<User>();
		usersWithTurns= new ArrayList<User>();
	}
	
//////////////////////////////////////////////////////////Principal Methods//////////////////////////////////////////////////////////
	/**This method add an User in the ArrayList User of the class
	 * <b>post<b/>:The ArrayList has to be initialized and the user has not be in the ArrayList before
	 * <b>pre<b/>:The ArrayList will have an  new User 
	 * @param typeDoc is the String that represent the user's type of the document. It just can be the constants of the Class TypeDocuments
	 * @param numDoc is the String that represent the number of the User's Document numDoc!=0 and numDoc!=" "
	 * @param name is the String that represents the user's name name!=" " 
	 * @param lastName is the String that represents the user's last name lastName!=" "
	 * @param phone is the String that represents the user's phone number phone!=0
	 * @param adress is the String that represent the user's address
	 */
	public void addUser(String typeDoc, String numDoc, String name, String lastName, String phone,String address)  throws DocumentExistException, ObligatoryFieldsException {
			searchUserRepeated(numDoc);
				User u=new User(typeDoc, numDoc, name, lastName, phone, address);
				usersData.add(u);
	}
	
	/**
	 * This method assign a turn for a User in the Arraylist 
	 * <b>pre<b/>: The user has to be in the ArrayList and it doesn't have a turn before
	 * <b>post<b/>: The user will have assigned a consecutive turn
	 * @param numDoc is the String that represent the User's number of Document numDoc!=0 and numDoc!=" "
	 * @return turn is the String that represent the User's turn that was assigned 
	 * @throws NotFoundException it is throwing when the user is not registered in the ArrayList
	 */
	public String registTurn(String numDoc) throws NotFoundException{
	
	int numUbication=0; 
	char letter;
	String turn="";
	boolean foundDoc=false;
	
	if(oneTurnOnce(numDoc).equalsIgnoreCase("none")) {
	for(int i=0;i<usersData.size() && foundDoc==false; i++) {
		if(usersData.get(i).getNumDoc().equalsIgnoreCase(numDoc)) {
			foundDoc=true;
			if(usersWithTurns.size()>0) {
				
				letter=usersWithTurns.get(usersWithTurns.size()-1).getTurnLetter();
				numUbication=(usersWithTurns.get(usersWithTurns.size()-1).getTurnNumber())+1;
				
					if(numUbication>99) {
						numUbication=0;
						turn=addTurn(searchLetter(letter), numUbication, i);
						}
						else {
							turn=addTurn(letter, numUbication,i);
							numUbication++;
							}
					}
					else {
						usersWithTurns.add(usersData.get(i));
						usersWithTurns.get(i).setTurn('A', "0");
						turn="A00";
					}
				}
			}
	if(foundDoc==false)
		throw new NotFoundException(numDoc);
	}
	else {
		turn="Sorry, the user have an active turn yet and it is " + oneTurnOnce(numDoc);
	}
		return turn;
	}
	
	/**
	 * This method attend a turn in order to the first created until the last one 
	 * @param option it represent the two possible option, option==ATTENTED or option==NOT ATTENDED
	 * @throws NoTurnYetException it is throw when there is not more turns to attend
	 */
	public void attentTurn(int option) throws NoTurnYetException {
	String turn="";
	try {
		 turn=showTurn();
	}
	finally {
		for(int i=0; i<usersWithTurns.size(); i++) {
			if(usersWithTurns.get(i).getTurn().equalsIgnoreCase(turn)) {
				if(option==1) 
					usersWithTurns.get(i).isAssisted(User.AS);
				else 
					usersWithTurns.get(i).isAssisted(User.NOAS);
			}
		}
	}
}
	
//////////////////////////////////////////////////////////Auxiliary Methods//////////////////////////////////////////////////////////
	/**
	 * This auxiliary method assign the turn using the letter of the /anterior/ turn and a number superior of the last 
	 * @param letter is the char that represent the order of the turn. 
	 * @param numUbication is the integer that represent the order of the turn numUbication<100 and numUbication>0
	 * @param positionUser is the position where the user is in the ArrayList of usersData positionUser<usersData.size()
	 * @return turn is the turn assigned to the User
	 */
	public String addTurn(char letter, int numUbication, int positionUser) {
		usersWithTurns.add(usersData.get(positionUser));
		usersWithTurns.get(positionUser).setTurn(letter,String.valueOf(numUbication));
		String turn=(numUbication>9)?(letter+String.valueOf(numUbication)):(letter+"0"+String.valueOf(numUbication));
		return turn;
	}
	
	/*
	 * 
	 */
	public String oneTurnOnce(String numDoc) {
		boolean found=false;
		String turn="none";
		if(usersWithTurns.size()>0) {
		for(int i=0; i<usersWithTurns.size() && found==false;i++) {
			if(numDoc.equals(usersWithTurns.get(i).getNumDoc())) {
				if(usersWithTurns.get(i).wasAssisted().equals(User.NOTD))
				turn=usersWithTurns.get(i).getTurn();
				}
			}
		}
		return turn;
	}
	
	/**
	 * This auxiliar method search a letter in the Array Letters to assign it in the user's turn
	 * @param letter it could be the letter of the last turn created 
	 * @return letter it represent the letter that will have the new turn
	 */
	public char searchLetter(char letter) {
	char[] letters= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	boolean found=false;
		for(int j=0;j<letters.length && found==false ;j++) {
			if(letter==letters[j]) {
				if(j==letters.length-1) {
					letter=letters[0];
					found=true;
				}
					else
					{
					letter=letters[j+1];
					found=true;	
					}	
			}
		}
		return letter;
	}
	
	/**
	 * This auxiliary method have the function of search in the ArrayList usersData if the user's document number is not repeated
	 * @param numDoc it represent the document number of the new User
	 * @throws DocumentExistException it is throw if some user in the ArrayList have the same number of the users new
	 */
	
	public void searchUserRepeated(String numDoc) throws DocumentExistException {
		for(int i=0;i<usersData.size(); i++) {
			if(usersData.get(i).getNumDoc().equalsIgnoreCase(numDoc)) {
				throw new DocumentExistException(numDoc);
			}
		}
	}
	
/**
 * This auxiliary method have the function of show the turn to attend in a ascendent order
 * @return turn represent the turn to attend
 * @throws NoTurnYetException it is throw if there is not turn to attend
 */
	public String showTurn () throws NoTurnYetException{
		boolean found=false;
		String turn="";
			
			for(int i=0; i<usersWithTurns.size() && found==false;i++) {
				if(usersWithTurns.get(i).wasAssisted().equalsIgnoreCase(User.NOTD)) {
					turn=usersWithTurns.get(i).getTurn();
					found=true;
				}
			}
			if(found==false)
				throw new NoTurnYetException();
		return turn;
	}
}
