package menus;

import java.util.ArrayList;

import helpers.Console;
import repositories.Activity;
import repositories.Project;
import repositories.ProjectRepository;

public class ProjectManagementMenu {
	private static Project currentProject;
	private static Activity currentActivity;
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
		    	modifyProject();
		    	break;
		    case 2:
		    	ArrayList<Project> projects = ProjectRepository.getInstance().getAll();
		    	projects.remove(currentProject);
		    	ProjectRepository.getInstance().writeDataSource();
		    	
				System.out.println();
				System.out.println("***Le projet (" + currentProject.getName()+") a été supprimé.");
				System.out.println();
				
				AdminMenu.mainMenu();
		    	break;
		    case 3:
		    	currentProject = null;
		    	projectListMenu();
		    	break;
	    }
	    
	    projectActionMenu();
	}
	
	private static void modifyProject() {
		ArrayList<Activity> list = ProjectRepository.getInstance().getProjectActivities(currentProject);
		int selectedOption;
		
	    System.out.println("| Choisir une activitée |");
	    for (int i = 0; i < list.size(); i++) {
	    	System.out.printf("%d. %s%n", i+1, list.get(i).getName());
	    }
	    int lastIndex =  list.size() + 1;
	    System.out.printf("%d. Retour en arrière%n", lastIndex);

	    selectedOption = Console.inInt("Option:");
	    
	    if (selectedOption > lastIndex) {
	    	modifyProject();
	    } else if (selectedOption == lastIndex) {
	    	currentProject = null;
	    	AdminMenu.mainMenu();
	    } else {
	    	currentActivity = list.get(selectedOption - 1);
	    	 System.out.println("| Choisir une activitée |");
	    	 System.out.println("1: Modifier le titre");
	    	 System.out.println("2: Modifier le budget");
	    	 selectedOption = Console.inInt("Option: ");
	    	 

	 	    switch (selectedOption) {
	 		    case 1:
	 		    	String titre = Console.inString("Nouveau titre: ");
	 		    	currentActivity.setName(titre);
	 		    	break;
	 		    case 2:
	 		    	long budget = (long)Console.inInt("Nouveau budget: ");
	 		    	currentActivity.setBudget(budget);
	 		    	break;
	 	    }
	 	    ProjectRepository.getInstance().writeDataSource();
	 	    System.out.println();
	 	    System.out.println("***Modification effectuée***");
	 	    System.out.println();
	 	   projectActionMenu();
	    	
	    }
	}
	
}
