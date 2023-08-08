package menus;

import java.util.ArrayList;

import helpers.Console;
import repositories.ConfigRepository;
import repositories.Employee;
import repositories.EmployeeRepository;
import repositories.Project;
import repositories.ProjectRepository;

public class EmployeeManagementMenu {
	private static Employee currentEmployee;
	
	/**
	 * Affiche la liste des employés disponibles pour gestion
	 */
	public static void employeeListMenu() {
		int selectedOption;
		
	    System.out.println("| Choisir un employé à gérer |");
	    
	    ArrayList<Employee> employees = EmployeeRepository.getInstance().getAll();
	    
	    for (int i = 0; i < employees.size(); i++) {
	    	System.out.printf("%d. %s%n", i+1, employees.get(i).getUsername());
	    }
	    int lastIndex =  employees.size() + 1;
	    System.out.printf("%d. Retour en arrière%n", lastIndex);

	    selectedOption = Console.inInt("Option:");
	    
	    if (selectedOption > lastIndex) {
	    	employeeListMenu();
	    } else if (selectedOption == lastIndex) {
	    	currentEmployee = null;
	    	AdminMenu.mainMenu();
	    } else {
	    	currentEmployee = employees.get(selectedOption - 1);
	    	employeeActionMenu();
	    }
	}
	
	/**
	 * Affiche le menu de gestion d'un employé
	 */
	private static void employeeActionMenu() {
		int selectedOption;
	    System.out.println("| Choisir une action |");
	    System.out.println("1. Modifier le nom d'usager");
	    System.out.println("2. Modifier l'ID");
	    System.out.println("3. Assigner à un projet");
	    System.out.println("4. Retour en arrière");

	    selectedOption = Console.inInt("Action:");

	    switch (selectedOption) {
		    case 1:
		    	requestUsername();
		    	break;
		    case 2:
		    	requestID();
		    	break;
		    case 3:
		    	requestProject();
		    	break;
		    case 4:
		    	currentEmployee = null;
		    	employeeListMenu();
		    	break;
	    }
	    
	    employeeActionMenu();
	}
	
	/**
	 * Demande à la console la valeur du nouveau nom d'utilisateur de l'employé
	 * 
	 */
	public static void requestUsername() {
		System.out.println("| Veuillez entrer la nouvelle valeur du nom d'utilisateur de l'employé |");

		String username = Console.inString("Nom d'utilisateur:");
		currentEmployee.setUsername(username);
		EmployeeRepository.getInstance().writeDataSource();
		
		System.out.println();
		System.out.println("***Nom d'utilisateur modifié avec succès***");
		System.out.println();
	}
	
	/**
	 * Demande à la console la valeur du nouvel ID de l'employé
	 * 
	 */
	public static void requestID() {
		System.out.println("| Veuillez entrer la nouvelle valeur de l'ID de l'employé |");

		String id = Console.inString("ID:");
		currentEmployee.setLoginId(id);
		EmployeeRepository.getInstance().writeDataSource();
		
		System.out.println();
		System.out.println("***ID modifié avec succès***");
		System.out.println();
	}
	
	/**
	 * Demande à la console le projet auquel assigné l'employé
	 * 
	 */
	public static void requestProject() {
		System.out.println("| Veuillez choisir le projet |");
		
		ArrayList<Project> projects = ProjectRepository.getInstance().getAll();
	    
	    for (int i = 0; i < projects.size(); i++) {
	    	System.out.printf("%d. %s%n", i+1, projects.get(i).getName());
	    }
	    int lastIndex =  projects.size() + 1;
	    System.out.printf("%d. Retour en arrière%n", lastIndex);
	    
	    int selectedOption = Console.inInt("Projet:");

		ArrayList<Project> employeeProjects = ProjectRepository.getInstance().getAllEmployeeProjects(currentEmployee);
		long npe = ConfigRepository.getInstance().getConfig().getNPE();

		if (employeeProjects.size() < npe) {
			Project project = projects.get(selectedOption - 1);
			project.addEmployee(currentEmployee);
			
			ProjectRepository.getInstance().writeDataSource();
			
			System.out.println();
			System.out.println("***Employé assigné avec succès***");
			System.out.println();
		} else {
			System.out.println();
			System.out.println("***L'employé n'a pas pu être assigné. Violation du NPE.***");
			System.out.println();
		}
	}
}
