package repositories;

import java.time.LocalDate;
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
	private String sin;
	private LocalDate startDate;
	private LocalDate endDate;
	private ArrayList<EmployeeRate> rates = new ArrayList<EmployeeRate>();
	
	/**
	 * Construit l'objet de type Employee.
	 * 
	 * @param username Le nom d'usager de l'employé.
	 * @param id Le numéro d'identification de l'employé.
	 * @param sin Le numéro d'assurance sociale de l'employé.
	 * @param startDate La date d'embauche de l'employé.
	 * @param endDate La date de terminaison de l'employé.
	 * @param rates L'historique des taux horaires des l'employé.
	 */
	public Employee(String username, String id, String sin, LocalDate startDate, LocalDate endDate, ArrayList<EmployeeRate> rates) {
		this.username = username;
		this.id = id;
		this.sin = sin;
		this.startDate = startDate;
		this.endDate = endDate;
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
	 * Retourne le numéro d'assurance sociale de l'employé.
	 * 
	 * @return sin Le numéro d'assurance sociale de l'employé.
	 */	
	public String getSin() {
		return sin;
	}
	
	/**
	 * Retourne la date d'embauche de l'employé.
	 * 
	 * @return startDate La date d'embauche de l'employé.
	 */	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	/**
	 * Retourne la date de terminaison de l'employé.
	 * 
	 * @return endDate La date de terminaison de l'employé.
	 */	
	public LocalDate getEndDate() {
		return endDate;
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
