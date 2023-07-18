package repositories;

import org.json.simple.JSONObject;

/**
 * Classe représentant un dépôt de données de type Config.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class ConfigRepository extends Repository {
	private static ConfigRepository INSTANCE;
	private String fileName = super.filePrefix.concat("configs.json");
	private Config config;
    
    private ConfigRepository() {
    	readDataSource();
    }
    
    public static ConfigRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigRepository();
        }
        
        return INSTANCE;
    }
    
    public Config getConfig() {
    	return config;
    }
    
    private void readDataSource() {         
        JSONObject configObject = (JSONObject) super.readDataSource(fileName);
        config = new Config((long) configObject.get("npe"));
    }

	@SuppressWarnings("unchecked")
	public void writeDataSource() {
		JSONObject configs = new JSONObject();
		configs.put("npe", config.getNPE());
		
		super.writeDataSource(fileName, configs);
	}
}
