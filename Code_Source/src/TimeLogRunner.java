import menus.LoginMenu;

/**
 * Classe d'initialisation du programme console TimeLog.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class TimeLogRunner {
	
	/**
	 * Lance le programme de gestion de projet TimeLog
	 */
	public static void main(String[] args) {
		TimeLogInitializer.initData();
		LoginMenu.mainMenu();
	}
}
