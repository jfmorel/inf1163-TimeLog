package repositories;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe représentant un dépôt de données de type Admin.
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
    
    @SuppressWarnings("unchecked")
    private void readDataSource() {         
        JSONArray adminList = (JSONArray) super.readDataSource(fileName);
        
        // Itération dans le tableau d'administateurs
        adminList.forEach(admin -> addAdminToRepository((JSONObject) admin));
    }
    
    private void addAdminToRepository(JSONObject admin)  {         
        String username = (String) admin.get("username");             
        String id = (String) admin.get("id");
        admins.add(new Admin(username, id));
    }
    
    public boolean isValid(String username, String id) {
    	Predicate<Admin> filter = admin -> username.equals(admin.getUsername()) && id.equals(admin.getId());
    	if (admins.stream().anyMatch(filter)) {
    		return true;
    	} else {
    		return false;
    	}
    }

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
