package menus;

import java.util.ArrayList;
import java.util.Arrays;

import helpers.Console;
import repositories.AdminRepository;
import repositories.Config;
import repositories.ConfigRepository;

/**
 * Classe de construction du menu administrateur.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class AdminMenu {
	enum ProjectOperation {
		EDIT,
		DELETE
	};
	
	/**
	 * Demande à la console le nom d'usager et le ID de l'administrateur
	 * 
	 * @return booléen indiquant si l'administrateur s'est connecté avec succès ou non
	 */
	public static boolean validateLogin() {		
		System.out.println("| Veuillez vous identifier |");
		
		String username = Console.inString("Nom d'usager:");
		String ID = Console.inString("ID:");
		
		if (AdminRepository.getInstance().isValid(username, ID)) {
			return true;
		} else {
			System.out.println();
			System.out.println("***Informations de connexion érronées***");
			System.out.println();
			
			return false;
		}
	}
	
	/**
	 * Demande à la console la valeur du NPE
	 * 
	 */
	public static void requestNPE() {
		System.out.println("| Veuillez entrer la nouvelle valeur du NPE |");

		int npe = Console.inInt("NPE:");
		Config currentConfig = ConfigRepository.getInstance().getConfig();
		currentConfig.setNPE(npe);
		ConfigRepository.getInstance().writeDataSource();
		
		System.out.println();
		System.out.println("***NPE modifié avec succès***");
		System.out.println();
	}
	
	/**
	 * Affiche le menu principal du rôle Administrateur
	 */
	public static void mainMenu() {
		int selectedOption;
	    System.out.println("| Choisir une action |");
	    System.out.println("1. Modifier NPE");
	    System.out.println("2. Modifier mon ID");
	    System.out.println("3. Gérer les projets");
	    System.out.println("4. Déconnexion");

	    selectedOption = Console.inInt("Action:");

	    switch (selectedOption) {
		    case 1:
		    	requestNPE();
		    	break;
		    case 2:
		    	System.out.println("Option 2 sélectionnée");
		    	break;
		    case 3:
		    	projectsMenu();
		    	break;
		    case 4:
		    	LoginMenu.mainMenu();
		    	break;
	    }
	    
	    mainMenu();
	}
	
	/**
	 * Affiche le menu de gestion de projet du rôle Administrateur
	 */
	private static void projectsMenu() {
		int selectedOption;
	    System.out.println("| Choisir une action |");
	    System.out.println("1. Ajouter un projet");
	    System.out.println("2. Modifier un projet");
	    System.out.println("3. Supprimer un projet");
	    System.out.println("4. Retour en arrière");

	    selectedOption = Console.inInt("Action:");

	    switch (selectedOption) {
		    case 1:
		    	System.out.println("Option 1 sélectionnée");
		    	break;
		    case 2:
		    	projectListMenu(ProjectOperation.EDIT);
		    	break;
		    case 3:
		    	projectListMenu(ProjectOperation.DELETE);
		    	break;
		    case 4:
		    	mainMenu();
		    	break;
	    }
	    
	    projectsMenu();
	}
	
	/**
	 * Affiche la liste des projet disponibles pour modification ou suppression
	 */
	private static void projectListMenu(ProjectOperation op) {
		int selectedOption;
		String opString = "";
		switch (op) {
			case EDIT:
				opString = "modifier";
				break;
			case DELETE:
				opString = "supprimer";
				break;
		}
		
	    System.out.printf("| Choisir un projet à %s |%n", opString);
	    
	    // TODO: Aller chercher la liste de projet à partir du/des fichiers JSON contenant la liste de projets
	    ArrayList<String> projects = new ArrayList<String>(Arrays.asList("projet1", "projet2"));
	    
	    for (int i = 0; i < projects.size(); i++) {
	    	System.out.printf("%d. %s%n", i+1, projects.get(i));
	    }
	    int lastIndex =  projects.size() + 1;
	    System.out.printf("%d. Retour en arrière%n", lastIndex);

	    selectedOption = Console.inInt("Action:");
	    
	    if (selectedOption == lastIndex) {
	    	projectsMenu();
	    } else {
	    	switch (op) {
	    		case EDIT:
	    			System.out.println("Afficher liste d'option de modification d'un projet");
	    			break;
	    		case DELETE:
	    			System.out.println("Lancer action delete");
	    			break;
	    	}
	    }
	    
	    projectListMenu(op);
	}
}
