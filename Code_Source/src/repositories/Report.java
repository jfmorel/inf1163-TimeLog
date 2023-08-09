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
		
		/*
		 * Par défaut, le rapportEmployé va considérer les heures travaillé à partir de la dernière semaine impaire
		 * */
		time = checkTime(time);
		
		
		
		ArrayList<Worklog> employeeWorklog = parseWorklogEmployee(employee, time);
		 System.out.println("******************************************************************************");
		 System.out.println();
		 System.out.println("Rapport de l'Employé: " + employee.getUsername());
		 ArrayList<String> seen = new ArrayList<String>();
		 for(Worklog w : employeeWorklog) {
			 long currentWorklogtime = 0;
			 String currentWorklog = w.getProject().getName();
			 if(!seen.contains(currentWorklog)) {
				 for(Worklog w2 : employeeWorklog) {
					 if(w2.getProject().getName()==currentWorklog && w2.getId()!=w.getId()) {
						 currentWorklogtime += Duration.between(w.getStart(), w.getEnd()).toHours();
					 }
				 }
				System.out.println("Projet: " + w.getProject().getName() );
				System.out.println("	Heures travaillées: " + currentWorklogtime);
				System.out.println("	Salaire: " + (int)currentWorklogtime * getActivityPay(employee,w)+ "$ CAD");
			 }
			 seen.add(currentWorklog);
			 
			 
			
		  }
		 System.out.println();
		 System.out.println("******************************************************************************");
		 
		
		 }
	
	public void talonDePaie(Instant time, Employee employee) {
		
			
			/*
			 * Par défaut, le rapportEmployé va considérer les heures travaillé à partir de la dernière semaine impaire
			 * */
			time = checkTime(time);
			
			
			
			ArrayList<Worklog> employeeWorklog = parseWorklogEmployee(employee, time);
			 if(employeeWorklog.size()==0) {
				 System.out.println("******************************************");
				 System.out.println("******************************************");
				 System.out.println("******** Aucun travail à été fait ********");
				 System.out.println("******************************************");
				 System.out.println("******************************************");
			 }
			 else {
			 System.out.println("Rapport de l'Employé: " + employee.getUsername());
			 double total = 0;
			 for(Worklog w : employeeWorklog) {
				
				 total +=(int)Duration.between(w.getStart(), w.getEnd()).toHours() * getActivityPay(employee,w);
 
		}
			 System.out.println("******************************************************************************");
			 System.out.println();
			 System.out.println("Talon de Paie de: " + employee.getUsername());
			 System.out.println("Pour la période de " + LocalDate.ofInstant(time,ZoneId.systemDefault()) +" à " + LocalDate.now());
			 System.out.println();
			 System.out.println("Salaire brut: " + total +"$ CAD");
			 System.out.println("Salaire net: " + total*0.6 +"$ CAD");
			 System.out.println();
			 System.out.println("******************************************************************************");
			 }
	}
	/*
	 * Pour un projet (p), crée un hashmap qui contient le pourcentage de progrès fait pour chaque activitée du projet p 
	 * et imprime le rapport dans la console
	 */
	public void rapportProject(Project p) {
		
		 System.out.println("******************************************************************************");
		 System.out.println("Rapport de projet");
		 System.out.println("	Projet: " + p.getName());
		 long [] progress = getProjectHours(parseWorklog(p.getId()),p.getActivities());
		 System.out.println("	Total des heures travaillées: " + progress[0]);
		 if(progress[0]==0) {
			 System.out.println("	Pourcentage d'avancement du projet: 0%");
		 }
		 else {
			 System.out.println("	Pourcentage d'avancement du projet: " + (progress[0]/progress[1])*100+"%");
		 }
		 System.out.println("******************************************************************************");
		 System.out.println();
	}
		
	/*
	 *  imprime le rapport global dans la console
	 * */	
	public void rapportGlobal() {
		 ArrayList<Project> projects = ProjectRepository.getInstance().getAll();
		 if(projects.size()==0) {
			 System.out.println("******************************************");
			 System.out.println("******************************************");
			 System.out.println("******** Aucun travail à été fait ********");
			 System.out.println("******************************************");
			 System.out.println("******************************************");
		 }
		 else {
			 
		
		 System.out.println("Rapport Global");
		 for(Project p : projects) {
			 System.out.println("******************************************************************************");
			 System.out.println("	Projet: " + p.getName());
			 long[] progress = getProjectHours(parseWorklog(p.getId()),p.getActivities());
			 
			 System.out.println("	Total des heures travaillées: " + progress[0]);
			 if(progress[0]==0) {
				 System.out.println("	Pourcentage d'avancement du projet: 0%");
			 }
			 else {
				 System.out.println("	Pourcentage d'avancement du projet: " + (progress[0]/progress[1])*100+"%");
			 }
			 System.out.println("******************************************************************************");
			 System.out.println();
			 
		 }
		 }
		
		
	}
	   /*
		 * 	Retourne une liste des Worklog d'un employée qui sont après la date | heure choisi
		 */
		private ArrayList<Worklog> parseWorklogEmployee(Employee employee, Instant time){
			ArrayList<Worklog> logs = new ArrayList<Worklog>();
			for(Worklog log : worklogs) {
				if((log.getEmployee().getId().equals(employee.getId()) && log.getStart().isAfter(time))) {
					logs.add(log);
				}
			}
			return logs;
		}
		/*
		 *  Retourne une liste des Worklog d'un projet en fonction de son id
		 */
		private ArrayList<Worklog> parseWorklog(String id){
			ArrayList<Worklog> worklog = WorklogRepository.getInstance().getAll();
			ArrayList<Worklog> logs = new ArrayList<Worklog>();
			for(Worklog log : worklog) {
				if(log.getProject().getId().equals(id)) {
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
		return rate * Duration.between(log.getStart(),log.getEnd()).toHours();
	}
	
	/*
	 *	Log dans la console les heures travaillées et le pourcentage d'avancement pour chaque activité d'un projet en comparant la liste d'activité d'un projet au worklog 
	 * */
	private long[] getProjectHours(ArrayList<Worklog> log, ArrayList<Activity> list) {
		long [] total = new long[2];
		total[0] = 0;
		total[1] = 0;
		
		for(Activity a : list) {
			boolean isStarted = false;
			System.out.println("		Activité: "+a.getName());
			for(Worklog l : log) {
				//Considère le cas ou une activité est terminé
				if(l.getActivity()==a && l.getEnd().toEpochMilli()!=0) {
					total[0] += a.getBudget();
					System.out.println("			Heures Travaillées: " +a.getBudget() );
					System.out.println("			Pourcentage d'avancement: 100%");
					isStarted = true;
					break;
				}
				//Considère le cas ou une activité est en cours
				else if(l.getActivity()==a && l.getEnd().toEpochMilli()==0 && l.getStart().toEpochMilli()!=0) {
					total[0] += Duration.between(Instant.now(),l.getStart()).toHours();
					System.out.println("			Heures Travaillées: " +Duration.between(Instant.now(),l.getStart()).toHours());
					System.out.println("			Pourcentage d'avancement: " + (Long)Duration.between(Instant.now(),l.getStart()).toHours()/a.getBudget()+"%");
					isStarted = true;
					break;
				}
			}
			if(isStarted==false) {
				System.out.println("			Heures Travaillées: 0%" );
				System.out.println("			Pourcentage d'avancement: 0%");
			}
		
			total[1] += a.getBudget();
		}		
		return total;
	}
	
	

}
