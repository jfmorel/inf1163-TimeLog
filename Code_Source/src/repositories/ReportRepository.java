package repositories;


import java.util.ArrayList;
import java.util.HashMap;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe singleton représentant un dépôt de données de type Report.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */

public class ReportRepository extends Repository {
	private static ReportRepository INSTANCE;
	private String fileName = super.filePrefix.concat("rapport.json");
    private ArrayList<Report> rapports = new ArrayList<Report>();
    
    
    private ReportRepository() {
    	readDataSource();
    }
    public static ReportRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReportRepository();
        }
        
        return INSTANCE;
    }
    public void addRapportoRepository(JSONObject rapport) {
    	JSONObject logs = (JSONObject) rapport.get("logs");	
    	HashMap<String, Double> r = new HashMap<String, Double>();
    	for(Object s : logs.keySet()) {
    		r.put((String)s,(Double) logs.get(s));
    	}
    	rapports.add(new Report());   
    }
    /**
  	 * Lit le contenu du fichier JSON contenant la liste des employés et construit les objets.
  	 * 
  	 */
      @SuppressWarnings("unchecked")
      private void readDataSource() {         
          JSONArray rapportList = (JSONArray) super.readDataSource(fileName);
          
          // Itération dans le tableau de rapport
          rapportList.forEach(rapport -> addRapportoRepository((JSONObject) rapport));
      }
      
      @SuppressWarnings("unchecked")
      public void writeDataSource() {
    	  JSONArray rapportList = new JSONArray();
    	  for(Report r : rapports) {
    		  JSONObject logs = new JSONObject();
    		  for(String id : r.getLog().keySet()) {
    			  JSONObject log = new JSONObject();
    			  if(r.getType().equals("employee")) {
    				  log.put("activity", id);
    				  log.put("paie", r.getLog().get(id));
    			  }
    			  else if(r.getType().equals("project")) {
    				  log.put("activity", id);
    				  log.put("progress", r.getLog().get(id));
    			  }
    			  else if(r.getType().equals("global")) {
    				  log.put("project", id);
    				  log.put("progress", r.getLog().get(id));
    			  }
    			  logs.put("log",logs);
    		  }
    		  JSONObject rapportObject = new JSONObject();
    		  rapportObject.put("id", r.getId());
    		  rapportObject.put("logs", logs);
    	  }
    	  super.writeDataSource(fileName, rapportList); 	  
      }

}