package repositories;

import java.util.ArrayList;

/**
 * Classe représentant un employé.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class Employee {
	private String username;
	private String id;
	private ArrayList<EmployeeRate> rates = new ArrayList<EmployeeRate>();
	
	/**
	 * Construit l'objet de type Employee.
	 * 
	 * @param username Le nom d'usager de l'employé.
	 * @param id Le numéro d'identification de l'employé.
	 * @param rates L'historique des taux horaires des l'employé.
	 */
	public Employee(String username, String id, ArrayList<EmployeeRate> rates) {
		this.username = username;
		this.id = id;
		this.rates = rates;
	}
	
	/**
	 * Retourne le nom d'usager de l'employé.
	 * 
	 * @return username Le nom d'usager de l'employé.
	 */	
	public String getUsername() {
		return username;
	}
	
	/**
	 * Assigne le nom d'usager de l'employé.
	 * 
	 * @param username Le nom d'usager de l'employé.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Retourne le numéro d'identification de l'employé.
	 * 
	 * @return id Le numéro d'identification de l'employé.
	 */	
	public String getId() {
		return id;
	}
	
	/**
	 * Assigne le numéro d'identification de l'employé.
	 * 
	 * @param id Le numéro d'identification de l'employé.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Retourne l'historique des taux horaires de l'employé.
	 * 
	 * @return rates L'historique des taux horaires de l'employé.
	 */	
	public ArrayList<EmployeeRate> getRates() {
		return rates;
	}
}
