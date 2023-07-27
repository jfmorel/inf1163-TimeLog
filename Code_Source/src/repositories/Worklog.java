package repositories;

import java.time.Instant;

/**
 * Classe représentant une entrée de temps de travail.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class Worklog {
	private String id;
	private Activity activity;
	private Employee employee;
	private Project project;
	private Instant start;
	private Instant end;
	
	/**
	 * Construit l'objet de type Worklog.
	 * 
	 * @param id Le numéro d'identification de l'entrée de temps de travail.
	 * @param activity L'activité associée à l'entrée de temps de travail.
	 * @param employee L'employé associé à l'entrée de temps de travail.
	 * @param project Le projet associé à l'entrée de temps de travail.
	 * @param start La date et l'heure du début de l'entrée de temps de travail en timestamp UNIX.
	 * @param end La date et l'heure de la fin de l'entrée de temps de travail en timestamp UNIX.
	 */
	public Worklog(String id, Activity activity, Employee employee, Project project, long start, long end) {
		this.id = id;
		this.activity = activity;
		this.employee = employee;
		this.project = project;
		this.start = Instant.ofEpochSecond(start);
		this.end = Instant.ofEpochSecond(end);
	}
	
	/**
	 * Retourne le numéro d'identification de l'entrée de temps de travail.
	 * 
	 * @return id Le numéro d'identification de l'entrée de temps de travail.
	 */	
	public String getId() {
		return id;
	}
	
	/**
	 * Retourne le projet associé à l'entrée de temps de travail.
	 * 
	 * @return project Le projet associé à l'entrée de temps de travail.
	 */	
	public Project getProject() {
		return project;
	}
	
	/**
	 * Retourne l'employé associé à l'entrée de temps de travail.
	 * 
	 * @return employee L'employé associé à l'entrée de temps de travail.
	 */	
	public Employee getEmployee() {
		return employee;
	}
	
	/**
	 * Retourne l'activité associée à l'entrée de temps de travail.
	 * 
	 * @return employee L'activité associée à l'entrée de temps de travail.
	 */	
	public Activity getActivity() {
		return activity;
	}
	
	/**
	 * Retourne la date et l'heure du début de l'entrée de temps de travail.
	 * 
	 * @return start La date et l'heure du début de l'entrée de temps de travail.
	 */	
	public Instant getStart() {
		return start;
	}
	
	/**
	 * Retourne la date et l'heure de fin de l'entrée de temps de travail.
	 * 
	 * @return end La date et l'heure de fin de l'entrée de temps de travail.
	 */	
	public Instant getEnd() {
		return end;
	}
	
	/**
	 * Termine une entrée de temps de travail.
	 * 
	 */
	public void stop() {
		end = Instant.now();
	}
}
