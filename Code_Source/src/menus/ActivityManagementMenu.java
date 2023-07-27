package menus;

import java.time.Instant;
import java.util.ArrayList;

import helpers.Console;
import repositories.Activity;
import repositories.Employee;
import repositories.Project;
import repositories.ProjectRepository;
import repositories.Worklog;
import repositories.WorklogRepository;

public class ActivityManagementMenu {
	private static Project currentProject;
	private static Employee currentEmployee;
	/**
	 * Affiche la liste des projet disponibles
	 */
	public static void projectListMenu(Employee employee) {
		currentEmployee = employee;
		int selectedOption;
		
	    System.out.println("| Choisir un projet |");
	    
	    ArrayList<Project> projects = ProjectRepository.getInstance().getAllEmployeeProjects(currentEmployee);
	    
	    for (int i = 0; i < projects.size(); i++) {
	    	System.out.printf("%d. %s%n", i+1, projects.get(i).getName());
	    }
	    int lastIndex =  projects.size() + 1;
	    System.out.printf("%d. Retour en arrière%n", lastIndex);

	    selectedOption = Console.inInt("Option:");
	    
	    if (selectedOption > lastIndex) {
	    	projectListMenu(currentEmployee);
	    } else if (selectedOption == lastIndex) {
	    	currentProject = null;
	    	EmployeeMenu.mainMenu();
	    } else {
	    	currentProject = projects.get(selectedOption - 1);
	    	requestActivity();
	    }
	}
	
	
	/**
	 * Demande à la console l'activité à démarrer
	 * 
	 */
	public static void requestActivity() {
		int selectedOption;
		System.out.println("| Veuillez choisir l'activité à démarrer |");
		
		ArrayList<Activity> activities = currentProject.getActivities();
	    
	    for (int i = 0; i < activities.size(); i++) {
	    	System.out.printf("%d. %s%n", i+1, activities.get(i).getName());
	    }
	    int lastIndex =  activities.size() + 1;
	    System.out.printf("%d. Retour en arrière%n", lastIndex);

	    selectedOption = Console.inInt("Option:");
	    
	    if (selectedOption > lastIndex) {
	    	requestActivity();
	    } else if (selectedOption == lastIndex) {
	    	projectListMenu(currentEmployee);
	    } else {
	    	String id = WorklogRepository.getInstance().getNextId();
	    	Activity activity = currentProject.getActivities().get(selectedOption - 1);
	    	long start = Instant.now().toEpochMilli() / 1000;
	    	WorklogRepository.getInstance().insert(new Worklog(id, activity, currentEmployee, currentProject, start, 0));
	    	WorklogRepository.getInstance().writeDataSource();
	    	
	    	System.out.println();
			System.out.println("***Activité démarrée avec succès***");
			System.out.println();
			
			EmployeeMenu.mainMenu();
	    }
	}
}
