package repositories;

/**
 * Classe représentant les configurations globales de TimeLog.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class Config {
	private long npe;
	
	/**
	 * Construit l'objet de type Config.
	 * 
	 * @param npe Le nombre maximal de projet pouvant être assigné à un employé.
	 */
	public Config(long npe) {
		this.npe = npe;
	}
	
	/**
	 * Retourne le NPE.
	 * 
	 * @return npe Le nombre maximal de projet pouvant être assigné à un employé.
	 */	
	public long getNPE() {
		return npe;
	}
	
	/**
	 * Assigne le NPE.
	 * 
	 * @param npe Le nombre maximal de projet pouvant être assigné à un employé.
	 */
	public void setNPE(long npe) {
		this.npe = npe;
	}
}
