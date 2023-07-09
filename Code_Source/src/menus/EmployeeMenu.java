package menus;

import helpers.Console;

/**
 * Classe de construction du menu employé.
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class EmployeeMenu {
	/**
	 * Demande à la console le nom d'usager et le ID à de l'employé
	 * 
	 * @return booléen indiquant si l'employé s'est connecté avec succès ou non
	 */
	public static boolean validateLogin() {
		String username;
		int ID;
		
		System.out.println("| Veuillez vous identifier |");
		
		username = Console.inString("Nom d'usager:");
		ID = Console.inInt("ID:");
		
		// TODO: Valider les informations de connexion en lisant le contenu du fichier JSON approprié
		if (username.equals("employe1") && ID == 1) {
			return true;
		}
		
		System.out.println();
		System.out.println("***Informations de connexion érronées***");
		System.out.println();
		return false;
	}
	
	/**
	 * Affiche le menu principal du rôle Employé
	 */
	public static void mainMenu() {
		int selectedOption;
	    System.out.println("| Choisir une action |");
	    System.out.println("1. Signaler le début d'une activité");
	    System.out.println("2. Signaler la fin d'une activité");
	    System.out.println("3. Générer un rapport d'heures travaillées");
	    System.out.println("4. Déconnexion");

	    selectedOption = Console.inInt("Action:");

	    // Switch construct
	    switch (selectedOption) {
		    case 1:
		    	System.out.println("Option 1 sélectionnée");
		    	break;
		    case 2:
		    	System.out.println("Option 2 sélectionnée");
		    	break;
		    case 3:
		    	System.out.println("Option 3 sélectionnée");
		    	break;
		    case 4:
		    	LoginMenu.mainMenu();
		    	break;
	    }
	    
	    mainMenu();
	}
}
