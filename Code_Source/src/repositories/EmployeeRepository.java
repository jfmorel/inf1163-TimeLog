package repositories;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe singleton représentant un dépôt de données de type Employee.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class EmployeeRepository extends Repository {
	private static EmployeeRepository INSTANCE;
	private String fileName = super.filePrefix.concat("employees.json");
    private ArrayList<Employee> employees = new ArrayList<Employee>();
    
    private EmployeeRepository() {
    	readDataSource();
    }
    
    public static EmployeeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmployeeRepository();
        }
        
        return INSTANCE;
    }
    
    /**
	 * Retourne l'objet Employee qui possède l'identifiant passé en paramètre.
	 * 
	 * @param ID L'identifiant de l'employé.
	 */
    public Employee getEmployeeById(String id)  {         
    	Predicate<Employee> filter = employee -> id.equals(employee.getId());
    	
    	return employees.stream().filter(filter).findFirst().orElse(null);
    }
    
    /**
	 * Ajoute l'objet JSON représentant un employé au dépôt de données.
	 * 
	 * @param employee L'objet JSON représentant un employé.
	 */
    @SuppressWarnings("unchecked")
	private void addEmployeeToRepository(JSONObject employee)  {         
        String username = (String) employee.get("username");             
        String id = (String) employee.get("id");
        JSONArray employeeRatesList = (JSONArray) employee.get("rates");;
        ArrayList<EmployeeRate> rates = new ArrayList<EmployeeRate>();
        
        // Itération dans le tableau des taux horaire de l'employé
        employeeRatesList.forEach(employeeRate -> addEmployeeRateToEmployee((JSONObject) employeeRate, rates));
        

        employees.add(new Employee(username, id, rates));
    }
    
    private void addEmployeeRateToEmployee(JSONObject employeeRate, ArrayList<EmployeeRate> rates)  {
    	LocalDate date = Instant.ofEpochSecond((long) employeeRate.get("date")).atOffset(ZoneOffset.UTC).toLocalDate();
        double rate = (double) employeeRate.get("rate");
        
        rates.add(new EmployeeRate(date, rate));
    }
    
    /**
	 * Vérifie si la combinaison username et id se trouve dans le dépot de données.
	 * 
	 * @param username Le nom d'usager de l'employé.
	 * @param id Le numéro d'identification de l'employé.
	 * @return booléen indiquant si la combinaison username et id se trouve dans le dépot de données.
	 */
    public boolean isValid(String username, String id) {
    	Predicate<Employee> filter = employee -> username.equals(employee.getUsername()) && id.equals(employee.getId());
    	if (employees.stream().anyMatch(filter)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
	 * Lit le contenu du fichier JSON contenant la liste des employés et construit les objets.
	 * 
	 */
    @SuppressWarnings("unchecked")
    private void readDataSource() {         
        JSONArray employeeList = (JSONArray) super.readDataSource(fileName);
        
        // Itération dans le tableau d'employés
        employeeList.forEach(employee -> addEmployeeToRepository((JSONObject) employee));
    }

    /**
	 * Écrit le contenu des objets du dépot de données dans le fichier JSON associé pour persistence.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void writeDataSource() {
		JSONArray employeeList = new JSONArray();
		for (Employee employee : employees) {
			JSONArray employeeRates = new JSONArray();
			for (EmployeeRate employeeRate : employee.getRates()) {
				JSONObject rate = new JSONObject();
				rate.put("date", employeeRate.getDate());
				rate.put("rate", employeeRate.getRate());
				employeeRates.add(rate);
			}

			JSONObject employeeObject = new JSONObject();
			employeeObject.put("username", employee.getUsername());
			employeeObject.put("id", employee.getId());
			employeeObject.put("rates", employeeRates);
			
			employeeList.add(employeeObject);
		}
		
		super.writeDataSource(fileName, employeeList);
	}
}
