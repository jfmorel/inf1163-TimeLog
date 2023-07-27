package menus;

import java.util.ArrayList;

import helpers.Console;
import repositories.Project;
import repositories.ProjectRepository;

public class ProjectManagementMenu {
	private static Project currentProject;
	/**
	 * Affiche la liste des projet disponibles pour modification ou suppression
	 */
	public static void projectListMenu() {
		int selectedOption;
		
	    System.out.println("| Choisir un projet |");
	    
	    ArrayList<Project> projects = ProjectRepository.getInstance().getAll();
	    
	    for (int i = 0; i < projects.size(); i++) {
	    	System.out.printf("%d. %s%n", i+1, projects.get(i).getName());
	    }
	    int lastIndex =  projects.size() + 1;
	    System.out.printf("%d. Retour en arrière%n", lastIndex);

	    selectedOption = Console.inInt("Option:");
	    
	    if (selectedOption > lastIndex) {
	    	projectListMenu();
	    } else if (selectedOption == lastIndex) {
	    	currentProject = null;
	    	AdminMenu.mainMenu();
	    } else {
	    	currentProject = projects.get(selectedOption - 1);
	    	projectActionMenu();
	    }
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
		    	currentProject = null;
		    	projectListMenu();
		    	break;
	    }
	    
	    projectActionMenu();
	}
}
