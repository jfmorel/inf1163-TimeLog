package repositories;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe singleton représentant un dépôt de données de type Admin.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class AdminRepository extends Repository {
	private static AdminRepository INSTANCE;
	private String fileName = super.filePrefix.concat("admins.json");
    private ArrayList<Admin> admins = new ArrayList<Admin>();
    
    private AdminRepository() {
    	readDataSource();
    }
    
    public static AdminRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdminRepository();
        }
        
        return INSTANCE;
    }
    
    /**
	 * Retourne l'objet Admin qui possède l'identifiant passé en paramètre.
	 * 
	 * @param ID L'identifiant de l'administrateur.
	 */
    public Admin getAdminById(String id)  {         
    	Predicate<Admin> filter = admin -> id.equals(admin.getId());
    	
    	return admins.stream().filter(filter).findFirst().orElse(null);
    }
    
    /**
	 * Ajoute l'objet JSON représentant un administrateur au dépôt de données.
	 * 
	 * @param admin L'objet JSON représentant un administrateur.
	 */
    private void addAdminToRepository(JSONObject admin)  {         
        String username = (String) admin.get("username");             
        String id = (String) admin.get("id");
        admins.add(new Admin(username, id));
    }
    
    /**
	 * Vérifie si la combinaison username et id se trouve dans le dépot de données.
	 * 
	 * @param username Le nom d'usager de l'administrateur.
	 * @param id Le numéro d'identification de l'administrateur.
	 * @return booléen indiquant si la combinaison username et id se trouve dans le dépot de données.
	 */
    public boolean isValid(String username, String id) {
    	Predicate<Admin> filter = admin -> username.equals(admin.getUsername()) && id.equals(admin.getId());
    	if (admins.stream().anyMatch(filter)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
	 * Lit le contenu du fichier JSON contenant la liste des administrateurs et construit les objets.
	 * 
	 */
    @SuppressWarnings("unchecked")
    private void readDataSource() {         
        JSONArray adminList = (JSONArray) super.readDataSource(fileName);
        
        // Itération dans le tableau d'administateurs
        adminList.forEach(admin -> addAdminToRepository((JSONObject) admin));
    }

    /**
	 * Écrit le contenu des objets du dépot de données dans le fichier JSON associé pour persistence.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void writeDataSource() {
		JSONArray adminList = new JSONArray();
		for (Admin admin : admins) {
			JSONObject adminObject = new JSONObject();
			adminObject.put("username", admin.getUsername());
			adminObject.put("id", admin.getId());
			
			adminList.add(adminObject);
		}
		
		super.writeDataSource(fileName, adminList);
	}
}
