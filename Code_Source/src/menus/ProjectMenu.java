package menus;

import java.util.ArrayList;
import java.util.Arrays;

import helpers.Console;

public class ProjectMenu {
	/**
	 * Affiche la liste des projet disponibles pour modification ou suppression
	 */
	public static void projectListMenu() {
		int selectedOption;
		
	    System.out.println("| Choisir un projet à gérer |");
	    
	    // TODO: Aller chercher la liste de projet à partir du/des fichiers JSON contenant la liste de projets
	    ArrayList<String> projects = new ArrayList<String>(Arrays.asList("projet1", "projet2"));
	    
	    for (int i = 0; i < projects.size(); i++) {
	    	System.out.printf("%d. %s%n", i+1, projects.get(i));
	    }
	    int lastIndex =  projects.size() + 1;
	    System.out.printf("%d. Retour en arrière%n", lastIndex);

	    selectedOption = Console.inInt("Projet:");
	    
	    if (selectedOption == lastIndex) {
	    	AdminMenu.mainMenu();
	    } else {
	    	projectActionMenu();
	    }
	    
	    projectListMenu();
	}
	
	/**
	 * Affiche le menu de gestion d'un projet
	 */
	private static void projectActionMenu() {
		int selectedOption;
	    System.out.println("| Choisir une action |");
	    System.out.println("1. Modifier le projet");
	    System.out.println("2. Supprimer le projet");
	    System.out.println("3. Retour en arrière");

	    selectedOption = Console.inInt("Action:");

	    switch (selectedOption) {
		    case 1:
		    	System.out.println("Option 1 sélectionnée");
		    	break;
		    case 2:
		    	System.out.println("Option 2 sélectionnée");
		    	break;
		    case 3:
		    	projectListMenu();
		    	break;
	    }
	    
	    projectActionMenu();
	}
}
