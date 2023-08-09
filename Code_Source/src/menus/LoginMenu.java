package menus;

import helpers.Console;

/**
 * Classe de construction du menu de connexion.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class LoginMenu {
	private static int selectedOption;
	/**
	 * Affiche le menu principal de connexion
	 */
	public static void mainMenu() {
		
	    System.out.println("| Connexion en tant que |");
	    System.out.println("1. Administrateur");
	    System.out.println("2. Employé");

	    selectedOption = Console.inInt("Rôle:");
	    switch (selectedOption) {
		    case 1:
		    	boolean validAdminLogin = AdminMenu.validateLogin();
		    	if (validAdminLogin) {
		    		AdminMenu.mainMenu();
		    	}
		    	break;
		    case 2:
		    	boolean validUserLogin = EmployeeMenu.validateLogin();
		    	if (validUserLogin) {
		    		EmployeeMenu.mainMenu();
		    	}
		    case 3: {
		    	System.out.println("error");
		    }
		    	break;
	    }
	    
	    mainMenu();
	}
}
