package helpers;

import java.io.IOException;

/**
 * Classe de méthodes auxiliaire pour manipulation des entrées console.
 * Inspiré de la classe Keyin: http://www.java2s.com/Tutorials/Java/IO_How_to/Console/Create_a_Console_menu.htm
 * 
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class Console {

	/**
	 * Imprime à la console un message.
	 * 
	 * @param message Le message à afficher à la console.
	 */
	private static void printPrompt(String message) {
		System.out.print(message + " ");
		System.out.flush();
	}

	/**
	 * Vide le "input stream" pour s'assurer qu'il soit vide
	 */
	private static void inputFlush() {
		try {
			while ((System.in.available()) != 0) {
				System.in.read();
			}
		} catch (java.io.IOException e) {
			System.out.println("Erreur à l'entrée");
		}
	}

	/**
	 * Traite l'entrée à la console.
	 * 
	 * @return la chaine de caractère lue par l'entrée console
	 */
	private static String readInput() {
		int aChar;
		String s = "";
		boolean finished = false;

		while (!finished) {
			try {
				aChar = System.in.read();
				if (aChar < 0 || (char) aChar == '\n') {
					finished = true;
				} else if ((char) aChar != '\r') {
					s = s + (char) aChar;
				}
			}

			catch (IOException e) {
				System.out.println("Erreur à l'entrée");
				finished = true;
			}
		}

		return s;
	}

	/**
	 * Demande et valide une entrée de type chaine de caractère
	 * 
	 * @param message
	 * @return la chaine de caractère entrée
	 */
	public static String inString(String message) {
		while (true) {
			inputFlush();
			printPrompt(message);

			return readInput().trim();
		}
	}

	/**
	 * Demande et valide une entrée de type entier
	 * 
	 * @param message Le message à afficher à la console
	 * @return l'entier entré
	 */
	public static int inInt(String message) {
		while (true) {
			inputFlush();
			printPrompt(message);
			try {
				return Integer.valueOf(readInput().trim()).intValue();
			} catch (NumberFormatException e) {
				System.out.println("Entrée invalide. Un nombre entier est attendu.");
			}
		}
	}

}
