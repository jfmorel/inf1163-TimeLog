package repositories;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe singleton représentant un dépôt de données de type Worklog.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class WorklogRepository extends Repository {
	private static WorklogRepository INSTANCE;
	private String fileName = super.filePrefix.concat("worklogs.json");
    private ArrayList<Worklog> worklogs = new ArrayList<Worklog>();
    
    private WorklogRepository() {
    	readDataSource();
    }
    
    public static WorklogRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WorklogRepository();
        }
        
        return INSTANCE;
    }
    
    /**
	 * Retourne l'objet Worklog qui possède l'identifiant passé en paramètre.
	 * 
	 * @param ID L'identifiant de l'entrée de temps de travail.
	 */
    public Worklog getById(String id)  {         
    	Predicate<Worklog> filter = worklog -> id.equals(worklog.getId());
    	
    	return worklogs.stream().filter(filter).findFirst().orElse(null);
    }
    
    /**
	 * Retourne l'objet Worklog en cours pour un employé.
	 * 
	 */
    public Worklog getOpenWorklog(Employee employee)  {
    	Predicate<Worklog> filter = worklog -> employee.equals(worklog.getEmployee()) && worklog.getEnd().toEpochMilli() == 0;
    	
    	return worklogs.stream().filter(filter).findFirst().orElse(null);
    }
    
    
    /**
	 * Retourne toutes les entrées de temps de travail présentement dans le dépôt de données.
	 * 
	 */
    public ArrayList<Worklog> getAll()  {
    	return worklogs;
    }
    
    /**
	 * Retourne le prochain ID pour indexer l'entrée de temps de travail.
	 * 
	 */
    public String getNextId()  {
    	Worklog lastEntry = worklogs.get(worklogs.size() - 1);
    	return Integer.toString(Integer.parseInt(lastEntry.getId()) + 1);
    }
    
    /**
	 * Ajoute une nouvelle entrée de temps de travail dans le dépôt de données.
	 * 
	 */
    public void insert(Worklog worklog)  {
    	worklogs.add(worklog);
    }
    
    /**
	 * Ajoute l'objet JSON représentant une entrée de temps de travail.
	 * 
	 * @param project L'objet JSON représentant une entrée de temps de travail.
	 */
	private void addWorklogToRepository(JSONObject worklog)  {
    	String activityName = (String) worklog.get("activity");
        String id = (String) worklog.get("id");
        Project project = ProjectRepository.getInstance().getById((String) worklog.get("projectId"));
    	Employee employee = EmployeeRepository.getInstance().getById((String) worklog.get("employeeId"));
    	long start = (long) worklog.get("start");
    	long end = (long) worklog.get("end");
    	
    	Predicate<Activity> filter = a -> activityName.equals(a.getName());
    	Activity activity = project.getActivities().stream().filter(filter).findFirst().orElse(null);
        

        worklogs.add(new Worklog(id, activity, employee, project, start, end));
    }    
    
    /**
	 * Lit le contenu du fichier JSON contenant la liste des entrées de temps de travail et construit les objets.
	 * 
	 */
    @SuppressWarnings("unchecked")
    private void readDataSource() {         
        JSONArray worklogList = (JSONArray) super.readDataSource(fileName);
        
        // Itération dans le tableau des entrées de temps de travail
        worklogList.forEach(worklog -> addWorklogToRepository((JSONObject) worklog));
    }

    /**
	 * Écrit le contenu des objets du dépot de données dans le fichier JSON associé pour persistence.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void writeDataSource() {
		JSONArray worklogList = new JSONArray();
		for (Worklog worklog : worklogs) {
			JSONObject worklogObject = new JSONObject();
			worklogObject.put("id", worklog.getId());
			worklogObject.put("projectId", worklog.getProject().getId());
			worklogObject.put("employeeId", worklog.getEmployee().getId());
			worklogObject.put("activity", worklog.getActivity().getName());
			worklogObject.put("start", worklog.getStart().toEpochMilli() / 1000);
			worklogObject.put("end", worklog.getEnd().toEpochMilli() / 1000);
			
			worklogList.add(worklogObject);
		}
		
		super.writeDataSource(fileName, worklogList);
	}
}
