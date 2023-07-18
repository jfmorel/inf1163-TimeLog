package repositories;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe représentant un dépôt de données de type Employee.
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
    
    @SuppressWarnings("unchecked")
    private void readDataSource() {         
        JSONArray employeeList = (JSONArray) super.readDataSource(fileName);
        
        // Itération dans le tableau d'employés
        employeeList.forEach(employee -> addEmployeeToRepository((JSONObject) employee));
    }
    
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
    
    public boolean isValid(String username, String id) {
    	Predicate<Employee> filter = employee -> username.equals(employee.getUsername()) && id.equals(employee.getId());
    	if (employees.stream().anyMatch(filter)) {
    		return true;
    	} else {
    		return false;
    	}
    }

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
