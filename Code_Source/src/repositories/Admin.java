package repositories;

public class Admin {
	private String username;
	private long id;
	
	/**
	 * Construit l'objet de type Admin.
	 * 
	 * @param username Le nom d'usager de l'administateur.
	 * @param id Le numéro d'identification de l'administateur.
	 */
	public Admin(String username, long id) {
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
	 * Retourne le numéro d'identification de l'administateur.
	 * 
	 * @return username Le numéro d'identification de l'administateur.
	 */	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
}
