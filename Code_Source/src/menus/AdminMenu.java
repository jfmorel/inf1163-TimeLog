package menus;

import java.util.ArrayList;

import helpers.Console;
import repositories.Activity;
import repositories.Admin;
import repositories.AdminRepository;
import repositories.Config;
import repositories.ConfigRepository;
import repositories.Employee;
import repositories.Project;
import repositories.ProjectRepository;

/**
 * Classe de construction du menu administrateur.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class AdminMenu {
	private static Admin currentAdmin;
	
	/**
	 * Demande à la console le nom d'usager et le ID de l'administrateur
	 * 
	 * @return booléen indiquant si l'administrateur s'est connecté avec succès ou non
	 */
	public static boolean validateLogin() {		
		System.out.println("| Veuillez vous identifier |");
		
		String username = Console.inString("Nom d'usager:");
		String id = Console.inString("ID:");
		
		if (AdminRepository.getInstance().isValid(username, id)) {
			currentAdmin = AdminRepository.getInstance().getById(id);
			return true;
		} else {
			System.out.println();
			System.out.println("***Informations de connexion erronées***");
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
		
	
	}
	
	/**
	 * Demande à la console la valeur du nouvel ID de l'administateur
	 * 
	 */
	public static void requestID() {
		System.out.println("| Veuillez entrer la nouvelle valeur de votre ID |");

		String id = Console.inString("ID:");
		currentAdmin.setId(id);
		AdminRepository.getInstance().writeDataSource();
		
		System.out.println();
		System.out.println("***ID modifié avec succès***");
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
	    System.out.println("3. Générer un rapport");
	    System.out.println("4. Gérer un employé");
	    System.out.println("5. Gérer un projet existant");
	    System.out.println("6. Créer un nouveau projet");
	    System.out.println("7. Déconnexion");

	    selectedOption = Console.inInt("Action:");

	    switch (selectedOption) {
		    case 1:
		    	requestNPE();
		    	System.out.println();
				System.out.println("***NPE modifié avec succès***");
				System.out.println();
		    	break;
		    case 2:
		    	requestID();
		    	break;
		    case 3:
		    	GenererRapportMenu.optionMenu();
		    	break;
		    case 4: 
		    	EmployeeManagementMenu.employeeListMenu();
		    	break;
		    case 5:
		    	ProjectManagementMenu.projectListMenu();
		    	break;
		    case 6:
		    	String titre = Console.inString("Entrer le titre du projet: ");
		    	ArrayList<Project> projects = ProjectRepository.getInstance().getAll();
		    	String id = Integer.toString(Integer.parseInt(projects.get(projects.size()-1).getId())+1);
		    	ArrayList<Activity> activities = new ArrayList<Activity>();
		    	ArrayList<Employee> assignedEmployees = new ArrayList<Employee>();
		    	Project newProjet = new Project(titre, id, activities, assignedEmployees);
		    	projects.add(newProjet);
		    	ProjectRepository.getInstance().writeDataSource();
		    	System.out.println();
		    	System.out.println("***Le projet "+ titre+" a été créé avec succès***");
		    	System.out.println();
		    	break;
		    case 7:
		    	currentAdmin = null;
		    	LoginMenu.mainMenu();
		    	break;
	    }
	    
	    mainMenu();
	}
}
