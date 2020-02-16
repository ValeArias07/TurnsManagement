package ui;

import java.util.Scanner;

import model.Admin;
import model.TypeDocuments;
import CustomExceptions.DocumentExistException;
import CustomExceptions.NoTurnYetException;
import CustomExceptions.NotFoundException;
import CustomExceptions.ObligatoryFieldsException;

public class Main {
	
	public Scanner lectorL;
	public	Scanner lectorN;
	public Admin admin;
	
	public Main() {
		lectorN= new Scanner(System.in);
		lectorL= new Scanner(System.in);
		admin= new Admin();
	}
	
	public static void main (String args[]) {
		Main main= new Main();
		main.menu();
	}
	
	public void menu() {
		System.out.println("Welcome the the Turn Management. Enjoy the program");
		boolean exit=false;
			while(!exit) {
				System.out.println("Please, choose an option:"
						+ "\n 1. Add user"
						+ "\n 2. Regist Turn"
						+ "\n 3. Atent Turn"
						+ "\n 4. Exit");
			int choice=lectorN.nextInt();
			switch(choice) {
				case(1):
					addUser();
					break;
				case(2):
					registUser();
					break;
				case(3):
					attentTurn();
					break;
				case(4):
					exit=true;
				default:
					System.out.println("Write a correct option.");
				}
			}
		}
	public void addUser() {
		boolean sucess=false;
		while(!sucess) {
		String typeDoc=defineTypeDoc();
		
		System.out.println("Write the person's complete name");
		String names=lectorL.nextLine();
		
		System.out.println("Write the person's last name");
		String lastNames=lectorL.nextLine();
		
		System.out.println("Write the person's document number");
		String numDoc=lectorL.nextLine();
		
		System.out.println("Write the person's phone");
		String phone=lectorL.nextLine();
		
		System.out.println("Write the person's address");
		String address=lectorL.nextLine();


		try {
			if(numDoc.equalsIgnoreCase(" ") || names.equalsIgnoreCase(" ") || lastNames.equalsIgnoreCase(" ")) {
				throw new ObligatoryFieldsException();	
			}
			else {
				admin.addUser(typeDoc, numDoc, names, lastNames, phone, address);
				sucess=true;
			}
		}
		catch(ObligatoryFieldsException o) {
			System.out.println(o.getMessage());
			}
		catch(DocumentExistException d) {
			System.out.println(d.getMessage());
			}
		}
	}

	
	public void registUser (){
		System.out.println("Please, write the person´s number of Document to attend the turn");
		String numDoc=lectorL.nextLine();
		try {
			System.out.println(admin.registTurn(numDoc));
		}
		catch(NotFoundException o) {
			System.out.println(o.getMessage());
		}	

	}
	
	public void attentTurn() {
		try {
		System.out.println("ACTUAL TURN " + admin.showTurn() );
		System.out.println("What you want to do with the turn? Choose an option"
				+ "\n 1. Attended"
				+ "\n 2. User is not there");
		int option=lectorN.nextInt();
		admin.attentTurn(option);
		}
		catch(NoTurnYetException nt) {
			System.out.println(nt.getMessage());
		}
	}
	//////COMPLETEMENT///////
	public String defineTypeDoc() {
		boolean correctOption=false;
		int option=0;
		String typeDoc="";
		while(!correctOption) {
		System.out.println("Select the type of Document"
				+ "\n1."+ TypeDocuments.ACARD.getType()
				+ "\n2."+ TypeDocuments.ICARD.getType()
				+ "\n3."+ TypeDocuments.CIVILREGIST.getType()
				+ "\n4."+ TypeDocuments.PASSPORT.getType()
				+ "\n5."+ TypeDocuments.FOREIGNCARD.getType());
		option=lectorN.nextInt();
		if(option==1 || option==2 || option==3 || option==4 || option==5)
			correctOption=true;
		}
	
		switch(option){
			case(1):
				typeDoc=(TypeDocuments.ACARD.getType());
				break;
			case(2):
				typeDoc=(TypeDocuments.ICARD.getType());
				break;
			case(3):
				typeDoc=(TypeDocuments.CIVILREGIST.getType());
				break;
			case(4):
				typeDoc=(TypeDocuments.PASSPORT.getType());
				break;
			case(5):
				typeDoc=(TypeDocuments.FOREIGNCARD.getType());
			break;
		}
	return typeDoc;	
	}
}
