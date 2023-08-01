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

public class Rapport {
	private ArrayList<Worklog> worklogs;
	private WorklogRepository worklogRepo;
	private String id;
	private ProjectRepository projectRepository;
	private HashMap<String, Double> log;
	private String type;

	
	public Rapport(String id, HashMap<String, Double> logs) {
		/**
		 * À chaque rapport, il faut générer un nouveau worklogs afin de s'assurer que nous avons l'information la plus récente
		 */
		worklogs = new ArrayList<>();
		worklogs = worklogRepo.getAll();
		projectRepository = ProjectRepository.getInstance();
		this.id = id;
		this.log = logs;
		
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
	 * entre le moment choisi et le présent (currentTime - time).  
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
		log =  paieActivity;
		type ="employee";
	}
	
	/*
	 * Pour un projet (p), crée un hashmap qui contient le pourcentage de progrès fait pour chaque activitée du projet p 
	 */
	public void rapportProject(Project p) {
		 ArrayList<Activity> projectActivity = p.getActivities() ;
		 worklogs = parseWorklog(p.getId());
		 HashMap<String, Double> projectProgress = new HashMap<String, Double>();
		 for(Activity a : projectActivity) {
			 projectProgress.put(a.getName(), getProjectProgress(worklogs,a));
		 }
		 log = projectProgress;
		 type ="project";
		}
		
	/*
	 * crée un hashmap qui contient le pourcentage de progrès fait pour chaque projet  
	 * */	
	public void rapportGlobal() {
		 ArrayList<Project> projects = projectRepository.getAll();
		 HashMap<String, Double> globalProgress = new HashMap<String, Double>();
		 for(Project p : projects) {
			 globalProgress.put(p.getName(),getGlobalProgress(parseWorklog(p.getId()),p.getActivities()) );
		 }
		 log = globalProgress;
		 type = "global";
		
	}
	   /*
		 * 	Retourne une liste des Worklog d'un employée qui sont après la date | heure choisi
		 */
		private ArrayList<Worklog> parseWorklog(Employee employee, Instant time){
			for(Worklog log : worklogs) {
				if(log.getEmployee()!=employee || log.getStart().isBefore(time)) {
					worklogs.remove(log);
				}
			}
			return worklogs;
		}
		/*
		 *  Retourne une liste des Worklog d'un projet en fonction de son id
		 */
		private ArrayList<Worklog> parseWorklog(String id){
			for(Worklog log : worklogs) {
				if(!(log.getProject().getId().equals(id))) {
					worklogs.remove(log);
				}
			}
			return worklogs;
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
	 * Retourne le pourcentage d'avancement pour chaque projet, en comparant la liste d'activité d'un projet au worklog 
	 * */
	private double getGlobalProgress(ArrayList<Worklog> log, ArrayList<Activity> list) {
		double progress = 0;
		for(Activity a : list) {
			for(Worklog l : log) {
				//Considère le cas ou une activité est terminé
				if(l.getActivity()==a && l.getEnd().toEpochMilli()!=0) {
					progress +=1;
					break;
				}
				//Considère le cas ou une activité est en cours
				else if(l.getActivity()==a && l.getEnd().toEpochMilli()==0 && l.getStart().toEpochMilli()!=0) {
					progress += Duration.between(Instant.now(),l.getStart()).toHours()/a.getBudget();
				}
				//Dans le cas ou une activité n'est pas commencé on n'ajoute rien à la variable progress
			}
		}
		
		return (progress / list.size())*100;
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

}
