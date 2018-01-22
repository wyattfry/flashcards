package flashcards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FlashCards {
	public static void main(String[] args) {
		
		boolean interactive = true;
		
		// implement command line options here
		// -a 'question' 'answer' (add card)
		// -p (practice all cards)
		// -v (view all cards, edit, delete)
		// -V (view all cards then exit)
		// -f /path/to/card/database.bin
		String cardDbFileName = "cards.dat";
		if (args.length == 2) {
			if (args[0].equals("-f")) {
				cardDbFileName = args[1];
				if (!(new File(cardDbFileName).exists())) {
					System.out.println(cardDbFileName + " not found. Using cards.dat");
					cardDbFileName = "cards.dat";
				}
			}
		}
		// Interactive Mode
		
		if (interactive) {System.out.println("~~ Flash Cards ~~\n");}
		
		// Initial Setup
		Scanner console = new Scanner(System.in);
		ArrayList<Card> workingCardDb = new ArrayList<>();
		workingCardDb.addAll(readCardsFromDatabase(cardDbFileName));
		
		if (interactive) {System.out.printf("- %d card(s) loaded from %s.\n\n", workingCardDb.size(), cardDbFileName);}
		
		
		// Menu
		int menuNumberSelection = -1;
		
		while (menuNumberSelection != 5) {
			while (menuNumberSelection == -1) {
				menuNumberSelection = getMenuSelection(console);
				System.out.println();
			}
			if (menuNumberSelection == 5) {
				break;
			}
			
			switch (menuNumberSelection) {
			case 1:
				workingCardDb.add(addNewCardToList(console));
				break;
			case 2:
				System.out.println("2. practice cards from a category -- not yet implemented");
				break;
			case 3:
				practiceCards(workingCardDb, console);
				break;
			case 4:
				viewAllCards(workingCardDb, console);
				break;
			}
			menuNumberSelection = -1;
		}
		console.close();
		writeCardsToDatabase( cardDbFileName, workingCardDb );
	} // main
	
	public static int getMenuSelection(Scanner console) {
		System.out.println("~~~ Main Menu ~~~");
		String menuOptions = 
				" 1. add card to database\n" +
				" 2. practice cards from a category - not implemented\n" +
				" 3. practice all cards\n" +
				" 4. view all cards, edit, delete - part implemented\n" +
				" 5. save and quit\n\n" +
				"Your selection: ";
		System.out.print(menuOptions);
		int menuNumberSelection = 0;
		try {
			menuNumberSelection = Integer.parseInt(console.nextLine());
			// console.close();
		} catch (NumberFormatException e) {
			System.out.println("Invalid selection");
			return -1;
		}
		if (menuNumberSelection < 1 || menuNumberSelection > 5) {
			System.out.println("Invalid selection");
			return -1;
		}
		return menuNumberSelection;
	}
	
	public static void practiceCards(ArrayList<Card> cards, Scanner console) {
		// implement ability to indicate right/wrong, priority
		String selection = "";
		int cardIndex = -1;
		System.out.println("~~ Practice Mode ~~\nPress enter to advance.\n" +
				"To mark a card as 'hard', enter 'H',\n" +
				"To mark a card as 'easy', enter 'E',\n" + 
				"or 'M' to return to the menu.\n");
		
		while (!selection.toLowerCase().startsWith("m")) {
			
			cardIndex = new Random().nextInt(cards.size());
			// if cards[idx].isHard
			
			// else if countHardCards(cards) === 0
			if (cards.get(cardIndex).isHard() || countHardCards(cards) == 0) {
				System.out.print(cards.get(cardIndex).isHard() ? "(Hard) " : "(Easy) ");
				System.out.print("Q: " + cards.get(cardIndex).getQuestion());
				console.nextLine();
				System.out.println("       A: " + cards.get(cardIndex).getAnswer());
				System.out.print("[enter], h, e, m: ");
				selection = console.nextLine();
				System.out.print("- - - - - - - - - -\n");
				if (selection.toLowerCase().startsWith("e")) {
					cards.get(cardIndex).setHard(false);
					System.out.println("Card marked easy.");
				}
				if (selection.toLowerCase().startsWith("h")) {
					cards.get(cardIndex).setHard(true);
					System.out.println("Card marked hard.");
				}
				if (selection.toLowerCase().startsWith("m")) {
					break;
				}	
			}
		}
	}
	
	public static int countHardCards(ArrayList<Card> cards) {
		int count = 0;
		for (Card c : cards) {
			count += c.isHard() ? 1 : 0;
		}
		return count;
	}
	
	public static Card addNewCardToList(Scanner console) {
		System.out.print("Q: ");
		String question = console.nextLine();
		System.out.print("A: ");
		String answer = console.nextLine();
		System.out.println();
		Card c = new Card(question, answer);
		return c;
	}
	
	public static void addCard(ArrayList<Card> cards, Category cat) {
		
	}
	
	public static void viewAllCards(ArrayList<Card> cards, Scanner console) {
		System.out.println("~~~ View all cards ~~~");
		for (int i = 0; i < cards.size(); i++) {
			System.out.println("Card #" + i + 
					"\n - Question: " + cards.get(i).getQuestion() + 
					"\n - Answer:   " + cards.get(i).getAnswer());
		}
		System.out.print("- - - - -\n'D' to delete, 'E' to edit, [enter] to return to menu: ");
		String selection = console.nextLine();
		
		// Delete Card
		if (selection.toLowerCase().startsWith("d")) {
			System.out.print("*** Delete ***\nEnter card number to delete: ");
			selection = console.nextLine();
			int cardIndexToDel = Integer.parseInt(selection);
			if (cardIndexToDel >= 0 && cardIndexToDel < cards.size()) {
				cards.remove(cardIndexToDel);
				System.out.println("Card deleted.\n");
				viewAllCards(cards, console);
			} else {
				System.out.println("Not a valid card number.");
			}
		}
		
		// Edit Card
		if (selection.toLowerCase().startsWith("e")) {
			System.out.println("--not yet implemented--");
		}
		System.out.println();
	}
	
	public static void writeCardsToDatabase(String fileName, ArrayList<Card> cards) {
		Card[] cardsToWrite = new Card[cards.size()];
		cards.toArray(cardsToWrite);
		ObjectOutputStream objOut = null;
		try {
			objOut = new ObjectOutputStream(new FileOutputStream(fileName));
			objOut.writeObject(cardsToWrite);
			System.out.println("Wrote to " + fileName);
			objOut.close();
		} catch (FileNotFoundException e) {
			System.out.println("Output: file could not be read.");
		} catch (IOException e) {
			System.out.println("Output exception.");
		}
	}
	
	public static ArrayList<Card> readCardsFromDatabase(String fileName) {
		File cardsDatabase = new File(fileName);
		ArrayList<Card> cardList = new ArrayList<>();
		
		if (cardsDatabase.exists()) {
			ObjectInputStream objIn = null;
			Card[] cardsToRead;
			try {
				objIn = new ObjectInputStream(new FileInputStream(cardsDatabase));
				cardsToRead = (Card[]) objIn.readObject();
				objIn.close();
				for (int i = 0; i < cardsToRead.length; i++) {
					cardList.add(cardsToRead[i]);
				}
			} catch (FileNotFoundException e) {
				System.out.println("File could not be read.");
			} catch (IOException e) {
				System.out.println("Input exception");
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found exception");
			}
		} else {
			System.out.println(fileName + " not found.");
		}
		return cardList;
	}
}
