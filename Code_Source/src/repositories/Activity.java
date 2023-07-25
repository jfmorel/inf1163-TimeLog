package repositories;

/**
 * Classe représentant une activité de projet.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class Activity {
	private String name;
	private long budget;
	
	/**
	 * Construit l'objet de type Activity.
	 * 
	 * @param name Le nom de l'activité.
	 * @param budget Le budget en heure de l'activité.
	 */
	public Activity(String name, long budget) {
		this.name = name;
		this.budget = budget;
	}
	
	/**
	 * Retourne le nom de l'activité.
	 * 
	 * @return name Le nom de l'activité.
	 */	
	public String getName() {
		return name;
	}
	
	/**
	 * Assigne le nom de l'activité.
	 * 
	 * @param name Le nom de l'activité.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Retourne le budget en heure de l'activité.
	 * 
	 * @return budget Le budget en heure de l'activité.
	 */	
	public long getBudget() {
		return budget;
	}
	
	/**
	 * Assigne le budget en heure de l'activité.
	 * 
	 * @param budget Le budget en heure de l'activité.
	 */
	public void setBudget(long budget) {
		this.budget = budget;
	}
}
