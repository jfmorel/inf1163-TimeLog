package menus;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Scanner;

import helpers.Console;
import repositories.Employee;
import repositories.EmployeeRepository;
import repositories.Report;
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
 
	/*
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
			System.out.println("***Informations de connexion erronées***");
			System.out.println();
			
			return false;
		}
	}
	
	/**
	 * Affiche le menu principal du rôle Employé
	 */
	public static void mainMenu() {
		//int selectedOption;
	    System.out.println("| Choisir une action |");
	    System.out.println("1. Signaler le début d'une activité");
	    System.out.println("2. Signaler la fin d'une activité");
	    System.out.println("3. Générer un rapport d'heures travaillées");
	    System.out.println("4. Déconnexion");
	    
	    
	   int selectedOption = Console.inInt("Action:");
	    

	    // Switch construct
	    switch (selectedOption) {
		    case 1:
		    	if (EmployeeRepository.getInstance().canStartActivity(currentEmployee)) {
		    		ActivityManagementMenu.projectListMenu(currentEmployee);
		    	} else {
		    		System.out.println();
					System.out.println("***Une activité est déjà en cours. Impossible d'en démarrer une nouvelle***");
					System.out.println();
		    	}
		    	break;
		    case 2:
		    	if (EmployeeRepository.getInstance().canEndActivity(currentEmployee)) {
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
		    	Scanner scanner = new Scanner(System.in);

		       
		        System.out.print("Entrer l'année: ");
		        String année = scanner.nextLine();

		        System.out.print("Entrer le mois: ");
		        String mois = scanner.nextLine();
		        
		        System.out.print("Entrer le jour: ");
		        String jour = scanner.nextLine();
		      

		        try {
		            // Parse the date and time inputs into LocalDateTime objects
		            LocalDate date = LocalDate.of(Integer.parseInt(année),Integer.parseInt(mois),Integer.parseInt(jour));

		            // Convert LocalDateTime to Instant with ZoneOffset.UTC
		            Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
		            Report r = new Report();
			        r.rapportEmployee(instant, currentEmployee);
		        } catch (DateTimeException e) {
		            System.out.println("Invalid date or time format. Please use the correct format.");
		        }
		       
		    	break;
		    case 4:
		    	currentEmployee = null;
		    	LoginMenu.mainMenu();
		    	break;
	    }
	    
	    mainMenu();
	}
	
}
