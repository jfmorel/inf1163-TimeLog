package repositories;

/**
 * Classe représentant un administrateur.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class Admin {
	private String username;
	private String id;
	
	/**
	 * Construit l'objet de type Admin.
	 * 
	 * @param username Le nom d'usager de l'administateur.
	 * @param id Le numéro d'identification de l'administateur.
	 */
	public Admin(String username, String id) {
		this.username = username;
		this.id = id;
	}
	
	/**
	 * Retourne le nom d'usager de l'administateur.
	 * 
	 * @return username Le nom d'usager de l'administateur.
	 */	
	public String getUsername() {
		return username;
	}
	
	/**
	 * Assigne le nom d'usager de l'administateur.
	 * 
	 * @param username Le nom d'usager de l'administateur.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Retourne le numéro d'identification de l'administateur.
	 * 
	 * @return username Le numéro d'identification de l'administateur.
	 */	
	public String getId() {
		return id;
	}
	
	/**
	 * Assigne le numéro d'identification de l'administateur.
	 * 
	 * @param id Le numéro d'identification de l'administateur.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/*
	 * Permet à l'administrateur de modifier le nom d'utilisateur d'un employé
	 * 
	 * @param username : le nouveau nom d'utilisateur | e : Employé en question
	 * */
	
	public void modifyEmployeeUsername(String username, Employee e) {
		e.setUsername(username);
	}
	/*
	 * Permet à l'administrateur de modifier le ID d'un utilisateur d'un employé
	 * 
	 * @param username : le nouveau id de l'utilisateur | e : Employé en question
	 * */
	
	public void modifyEmployeeID(String id, Employee e) {
		e.setLoginId(id);
	}
}
