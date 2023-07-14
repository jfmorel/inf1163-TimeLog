package repositories;

import java.time.LocalDate;

/**
 * Classe représentant un taux horaire d'employé.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class EmployeeRate {
	LocalDate date;
	double rate;
	
	/**
	 * Construit l'objet de type EmployeeRate.
	 * 
	 * @param date La date à partir de laquelle le taux horaire prend effet.
	 * @param rate Le taux horaire.
	 */
	public EmployeeRate(LocalDate date, double rate) {
		this.date = date;
		this.rate = rate;
	}
	
	/**
	 * Retourne la date à partir de laquelle le taux horaire prend effet.
	 * 
	 * @return date La date à partir de laquelle le taux horaire prend effet.
	 */	
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Retourne le taux horaire.
	 * 
	 * @return rate Le taux horaire.
	 */	
	public double getRate() {
		return rate;
	}
}
