package repositories;

import java.util.ArrayList;

/**
 * Classe représentant un projet.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class Project {
	private String name;
	private String id;
	private ArrayList<Activity> activities = new ArrayList<Activity>();
	private ArrayList<Employee> assignedEmployees = new ArrayList<Employee>();
	
	/**
	 * Construit l'objet de type Project.
	 * 
	 * @param name Le nom du projet.
	 * @param id Le numéro d'identification du projet.
	 * @param activities Les activités du projet.
	 * @param employees Les employés assignés au projet
	 */
	public Project(String name, String id, ArrayList<Activity> activities, ArrayList<Employee> employees) {
		this.name = name;
		this.id = id;
		this.activities = activities;
		this.assignedEmployees = employees;
	}
	
	/**
	 * Retourne le nom du projet.
	 * 
	 * @return name Le nom du projet.
	 */	
	public String getName() {
		return name;
	}
	
	/**
	 * Assigne le nom du projet.
	 * 
	 * @param name Le nom du projet.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Retourne le numéro d'identification du projet.
	 * 
	 * @return username Le numéro d'identification du projet.
	 */	
	public String getId() {
		return id;
	}
	
	/**
	 * Assigne le numéro d'identification du projet.
	 * 
	 * @param id Le numéro d'identification du projet.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Retourne les activités du projet.
	 * 
	 * @return activities Les activités du projet.
	 */	
	public ArrayList<Activity> getActivities() {
		return activities;
	}
	
	/**
	 * Retourne les employés assignés au projet.
	 * 
	 * @return assignedEmployees Les employés assignés au projet.
	 */	
	public ArrayList<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}
}
