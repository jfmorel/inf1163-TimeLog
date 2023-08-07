package repositories;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Classe en charge de la génération des rapports
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */

public class Report {
	private ArrayList<Worklog> worklogs;
	private String id;
	private HashMap<String, Double> log;
	private String type;

	
	public Report() {
		/**
		 * À chaque rapport, il faut générer un nouveau worklogs afin de s'assurer que nous avons l'information la plus récente
		 */
		worklogs = WorklogRepository.getInstance().getAll();
		
		
	}
	public HashMap<String,Double> getLog(){
		return log;
	}
	public String getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	
	/**
	 * Pour un Employé (id) et un moment (date|heure), retourne le salaire brut de l'employé pour chaque Projets et Activitées complétés 
	 * entre le moment choisi et le présent (currentTime - time). Imprime le rapport dans la console
	 */
	public void rapportEmployee(Instant time, Employee employee) {
		HashMap<String, Double> paieActivity = new HashMap<String, Double>(); 
		/*
		 * Par défaut, le rapportEmployé va considérer les heures travaillé à partir de la dernière semaine impaire
		 * */
		time = checkTime(time);
		
		worklogs = parseWorklog(employee, time);
		for(Worklog log : worklogs) {
			double paie = getActivityPay(employee, log);
			paieActivity.put(log.getActivity().getName(), paie);
		}
	
		 System.out.println("Rapport de l'Employé: " + employee.getUsername());
		 double total = 0;
		 for(String log: paieActivity.keySet()) {
			System.out.println("	Activité:" + log);
			System.out.println("		Paie:" + paieActivity.get(log) );
			total += paieActivity.get(log);
		  }
		 System.out.println("Paie total: " + total +" $CAD.");
		
		 
	}
	
	/*
	 * Pour un projet (p), crée un hashmap qui contient le pourcentage de progrès fait pour chaque activitée du projet p 
	 * et imprime le rapport dans la console
	 */
	public void rapportProject(Project p) {
		 System.out.println("******************************************************************************");
		 System.out.println("Rapport de projet");
		 System.out.println("	Projet: " + p.getName());
		 ArrayList<Long> progress = getProjectHours(parseWorklog(p.getId()),p.getActivities());
		 System.out.println("	Total des heures travaillées: " + progress.get(0));
		 if(progress.get(0)==0) {
			 System.out.println("	Pourcentage d'avancement du projet: 0%");
		 }
		 else {
			 System.out.println("	Pourcentage d'avancement du projet: " + (progress.get(0)/progress.get(1))+"%");
		 }
		 System.out.println("******************************************************************************");
		 System.out.println();
	}
		
	/*
	 *  imprime le rapport global dans la console
	 * */	
	public void rapportGlobal() {
		 ArrayList<Project> projects = ProjectRepository.getInstance().getAll();
		 System.out.println("Rapport Global");
		 for(Project p : projects) {
			 System.out.println("******************************************************************************");
			 System.out.println("	Projet: " + p.getName());
			 ArrayList<Long> progress = getProjectHours(parseWorklog(p.getId()),p.getActivities());
			 System.out.println("	Total des heures travaillées: " + progress.get(0));
			 if(progress.get(0)==0) {
				 System.out.println("	Pourcentage d'avancement du projet: 0%");
			 }
			 else {
				 System.out.println("	Pourcentage d'avancement du projet: " + (progress.get(0)/progress.get(1))+"%");
			 }
			 System.out.println("******************************************************************************");
			 System.out.println();
			 
		 }
	
		
		
	}
	   /*
		 * 	Retourne une liste des Worklog d'un employée qui sont après la date | heure choisi
		 */
		private ArrayList<Worklog> parseWorklog(Employee employee, Instant time){
			ArrayList<Worklog> logs = new ArrayList<Worklog>();
			for(Worklog log : worklogs) {
				if(!(log.getEmployee()!=employee || log.getStart().isBefore(time))) {
					logs.add(log);
				}
			}
			return worklogs;
		}
		/*
		 *  Retourne une liste des Worklog d'un projet en fonction de son id
		 */
		private ArrayList<Worklog> parseWorklog(String id){
			ArrayList<Worklog> logs = new ArrayList<Worklog>();
			for(Worklog log : worklogs) {
				if(!(log.getProject().getId().equals(id))) {
					logs.add(log);
				}
			}
			return logs;
		}
		
	
	private int getWeekNumber(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }
	/*
	 * Vérifie si l'Admin a sélectionné une période d'évalution. Si non, la période sera à partir de la dernière semaine impair (par défaut)
	 * */
	private Instant checkTime(Instant time) {
		Instant res = Instant.now();
		if(time == null) {
			time = res;
			LocalDate date = LocalDate.now();
			if(getWeekNumber(date)%2==0) {
				time.minusSeconds(604800);
			}
		}
		return time;
	}
	
	private double getActivityPay(Employee id, Worklog log) {	
		/*
		1. Convertir l'instant (rate en LocalDate)
		2. Comparer cette date à l'historique de taux de l'employé
		3. le taux approprié sera le dernier taux qui à une date <= à la fin de l'activité
		4. retourne la durée de l'activité * le taux approprié
		
	 */
		double rate = 0;
		 LocalDate instantToLocalDate = log.getEnd().atZone(ZoneId.systemDefault()).toLocalDate();
		for(EmployeeRate r : id.getRates()) {
			if(r.getDate().isBefore(instantToLocalDate)) {
				rate = r.getRate();
			}
			else {
				break;
			}
		}
		
		//vérifie le cas ou une activité est en cours au moment de la génération du rapport 
		if(log.getEnd().toEpochMilli()==0) {
			return rate * Duration.between(Instant.now(), log.getStart()).toHours();
			
		}
		return rate * Duration.between(log.getEnd(), log.getStart()).toHours();
	}
	
	/*
	 *	Log dans la console les heures travaillées et le pourcentage d'avancement pour chaque activité d'un projet en comparant la liste d'activité d'un projet au worklog 
	 * */
	private ArrayList<Long> getProjectHours(ArrayList<Worklog> log, ArrayList<Activity> list) {
		ArrayList<Long> total = new ArrayList<Long>();
		Long setup = (long) 0;
		total.add(0,setup); //travaillé
		total.add(1,setup); //budget
		for(Activity a : list) {
			System.out.println("		Activité: "+a.getName());
			for(Worklog l : log) {
				//Considère le cas ou une activité est terminé
				if(l.getActivity()==a && l.getEnd().toEpochMilli()!=0) {
					total.add(0,total.get(0)+a.getBudget());
					System.out.println("			Heures Travaillées: " +a.getBudget() );
					System.out.println("			Pourcentage d'avancement: 100%");
					break;
				}
				//Considère le cas ou une activité est en cours
				else if(l.getActivity()==a && l.getEnd().toEpochMilli()==0 && l.getStart().toEpochMilli()!=0) {
					total.add(0,total.get(0) + Duration.between(Instant.now(),l.getStart()).toHours());
					System.out.println("			Heures Travaillées: " +Duration.between(Instant.now(),l.getStart()).toHours());
					System.out.println("			Pourcentage d'avancement: " + (Long)Duration.between(Instant.now(),l.getStart()).toHours()/a.getBudget()+"%");
					break;
				}
				
				
			}
			//Dans le cas ou une activité n'est pas commencé 
			System.out.println("			Heures Travaillées: 0");
			System.out.println("			Pourcentage d'avancement: 0%");
		}
		
		return total;
	}
	private double getProjectProgress(ArrayList<Worklog> log, Activity a) {
		double progress = 0;
		for(Worklog l : log) {
			if(l.getActivity() == a) {
				if(l.getStart().toEpochMilli()==0) {
					break;
				}
				if(l.getEnd().toEpochMilli()==0) {
					progress = Duration.between(Instant.now(),l.getStart()).toHours()/a.getBudget();
				}
				else {
					progress = 1;
				}
			}
		}
		return progress * 100;
	}
	private double heureTravaille(ArrayList<Project> projects, String id) {
		double total = 0;
	
		return total;
	}

}
