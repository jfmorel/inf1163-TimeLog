package menus;

import java.util.ArrayList;
import java.util.Arrays;

import helpers.Console;
import repositories.Admin;
import repositories.AdminRepository;
import repositories.Config;
import repositories.ConfigRepository;

/**
 * Classe de construction du menu administrateur.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class AdminMenu {
	static String currentAdminId;
	enum ProjectOperation {
		EDIT,
		DELETE
	};
	
	/**
	 * Demande à la console le nom d'usager et le ID de l'administrateur
	 * 
	 * @return booléen indiquant si l'administrateur s'est connecté avec succès ou non
	 */
	public static boolean validateLogin() {		
		System.out.println("| Veuillez vous identifier |");
		
		String username = Console.inString("Nom d'usager:");
		String ID = Console.inString("ID:");
		
		if (AdminRepository.getInstance().isValid(username, ID)) {
			currentAdminId = ID;
			return true;
		} else {
			System.out.println();
			System.out.println("***Informations de connexion érronées***");
			System.out.println();
			
			return false;
		}
	}
	
	/**
	 * Demande à la console la valeur du NPE
	 * 
	 */
	public static void requestNPE() {
		System.out.println("| Veuillez entrer la nouvelle valeur du NPE |");

		int npe = Console.inInt("NPE:");
		Config currentConfig = ConfigRepository.getInstance().getConfig();
		currentConfig.setNPE(npe);
		ConfigRepository.getInstance().writeDataSource();
		
		System.out.println();
		System.out.println("***NPE modifié avec succès***");
		System.out.println();
	}
	
	/**
	 * Demande à la console la valeur du nouvel ID de l'administateur
	 * 
	 */
	public static void requestID() {
		System.out.println("| Veuillez entrer la nouvelle valeur de votre ID |");

		String id = Console.inString("ID:");
		Admin admin = AdminRepository.getInstance().getAdminById(currentAdminId);
		currentAdminId = id;
		admin.setId(id);
		AdminRepository.getInstance().writeDataSource();
		
		System.out.println();
		System.out.println("***ID modifié avec succès***");
		System.out.println();
	}
	
	/**
	 * Affiche le menu principal du rôle Administrateur
	 */
	public static void mainMenu() {
		int selectedOption;
	    System.out.println("| Choisir une action |");
	    System.out.println("1. Modifier NPE");
	    System.out.println("2. Modifier mon ID");
	    System.out.println("3. Gérer un projet existant");
	    System.out.println("4. Ajouter un nouveau projet");
	    System.out.println("5. Déconnexion");

	    selectedOption = Console.inInt("Action:");

	    switch (selectedOption) {
		    case 1:
		    	requestNPE();
		    	break;
		    case 2:
		    	requestID();
		    	break;
		    case 3:
		    	ProjectMenu.projectListMenu();
		    	break;
		    case 4:
		    	System.out.println("Option 4 sélectionnée");
		    	break;
		    case 5:
		    	LoginMenu.mainMenu();
		    	break;
	    }
	    
	    mainMenu();
	}
}
