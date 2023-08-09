package menus;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;

import helpers.Console;
import repositories.Employee;
import repositories.EmployeeRepository;
import repositories.Project;
import repositories.ProjectRepository;
import repositories.Report;

class GenererRapportMenu{
	private static Project currentProject;
	private static Employee currentEmployee;
	
	private static void rapportProject(){
		Report r = new Report();
		int selectedOption;
		
	    System.out.println("| Choisir un projet |");
	    
	    ArrayList<Project> projects = ProjectRepository.getInstance().getAll();
	    
	    for (int i = 0; i < projects.size(); i++) {
	    	System.out.printf("%d. %s%n", i+1, projects.get(i).getName());
	    }
	    selectedOption = Console.inInt("Action:");
	    currentProject = projects.get(selectedOption);
    	r.rapportProject(currentProject);
	}
	
	private static void rapportGlobal() {
		
		Report p = new Report();
    	p.rapportGlobal();
	}
	
	private static void rapportEmployee() {
		int selectedOption;
		 System.out.println("| Choisir un employé |");
		    
		    ArrayList<Employee> employee = EmployeeRepository.getInstance().getAll();
		    
		    for (int i = 0; i < employee.size(); i++) {
		    	System.out.printf("%d. %s%n", i+1, employee.get(i).getUsername());
		    }
		    selectedOption = Console.inInt("Action:");
		    currentEmployee = employee.get(selectedOption - 1);
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
            System.out.println("Date invalide");
        }
		
	}
	
	public static void optionMenu() {
		int selectedOption;
	    System.out.println("| Choisir le type de rapport |");
	    System.out.println("1. Global");
	    System.out.println("2. Projet");
	    System.out.println("3. Employé");
	    System.out.println("4. Retour en arrière");
	    selectedOption = Console.inInt("Action:");

	    switch (selectedOption) {
		    case 1:
		    	rapportGlobal();
		    	break;
		    case 2:
		    	rapportProject();
		    	break;
		    case 3:
		    	rapportEmployee();
		    	break;
	}
	AdminMenu.mainMenu();
	}
}