package menus;

import helpers.Console;
import repositories.Employee;
import repositories.EmployeeRepository;
import repositories.Worklog;
import repositories.WorklogRepository;

/**
 * Classe de construction du menu employé.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class EmployeeMenu {
	private static Employee currentEmployee;
	/**
	 * Demande à la console le nom d'usager et le ID de l'employé
	 * 
	 * @return booléen indiquant si l'employé s'est connecté avec succès ou non
	 */
	public static boolean validateLogin() {		
		System.out.println("| Veuillez vous identifier |");
		
		String username = Console.inString("Nom d'usager:");
		String id = Console.inString("ID:");
		
		if (EmployeeRepository.getInstance().isValid(username, id)) {
			currentEmployee = EmployeeRepository.getInstance().getById(id);
			return true;
		} else {
			System.out.println();
			System.out.println("***Informations de connexion érronées***");
			System.out.println();
			
			return false;
		}
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
		    	if (EmployeeRepository.getInstance().canStart(currentEmployee)) {
		    		ActivityManagementMenu.projectListMenu(currentEmployee);
		    	} else {
		    		System.out.println();
					System.out.println("***Une activité est déjà en cours. Impossible d'en démarrer une nouvelle***");
					System.out.println();
		    	}
		    	break;
		    case 2:
		    	if (EmployeeRepository.getInstance().canEnd(currentEmployee)) {
		    		Worklog worklog = WorklogRepository.getInstance().getOpenWorklog(currentEmployee);
		    		worklog.stop();
		    		WorklogRepository.getInstance().writeDataSource();
		    		
		    		System.out.println();
					System.out.println("***Activité terminé avec succès***");
					System.out.println();
		    	} else {
		    		System.out.println();
					System.out.println("***Aucune activité en cours à terminer***");
					System.out.println();
		    	}
		    	break;
		    case 3:
		    	System.out.println("Option 3 sélectionnée");
		    	break;
		    case 4:
		    	currentEmployee = null;
		    	LoginMenu.mainMenu();
		    	break;
	    }
	    
	    mainMenu();
	}
}
