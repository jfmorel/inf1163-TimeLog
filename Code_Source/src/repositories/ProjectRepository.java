package repositories;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe singleton représentant un dépôt de données de type Project.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class ProjectRepository extends Repository {
	private static ProjectRepository INSTANCE;
	private String fileName = super.filePrefix.concat("projects.json");
    private ArrayList<Project> projects = new ArrayList<Project>();
    
    private ProjectRepository() {
    	readDataSource();
    }
    
    public static ProjectRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProjectRepository();
        }
        
        return INSTANCE;
    }
    /*
     * Retourne la liste d'activité pour un projet p
     * 
     * @param p Objet Projet
     * */
    public ArrayList<Activity> getProjectActivities(Project p){
    	ArrayList<Activity> list = new ArrayList<>();
    	for(Project project : projects) {
    		if(p.equals(project));
    		list = project.getActivities();
    		break;
    	}
    	
		return list;
    	
    }
    
    /**
	 * Retourne l'objet Project qui possède l'identifiant passé en paramètre.
	 * 
	 * @param ID L'identifiant du projet.
	 */
    public Project getById(String id)  {         
    	Predicate<Project> filter = project -> id.equals(project.getId());
    	
    	return projects.stream().filter(filter).findFirst().orElse(null);
    }
    
    /**
	 * Retourne tous les projets présentement dans le dépôt de données.
	 * 
	 */
    public ArrayList<Project> getAll()  {         
    	return projects;
    }
    
    /**
	 * Retourne tous les projets assignés à un employé se trouvant dans le dépôt de données.
	 * 
	 * @param employee L'employé auquel est assigné le projet
	 */
    public ArrayList<Project> getAllEmployeeProjects(Employee employee)  {         
    	Predicate<Project> filter = project -> project.getAssignedEmployees().contains(employee);
    	
    	return (ArrayList<Project>) projects.stream().filter(filter).collect(Collectors.toList());
    }
    
    /**
	 * Ajoute l'objet JSON représentant un project au dépôt de données.
	 * 
	 * @param project L'objet JSON représentant un projet.
	 */
    @SuppressWarnings("unchecked")
	private void addProjectToRepository(JSONObject project)  {         
        String name = (String) project.get("name");             
        String id = (String) project.get("id");
        JSONArray activitiesList = (JSONArray) project.get("activities");
        ArrayList<Activity> activities = new ArrayList<Activity>();
        JSONArray employeesList = (JSONArray) project.get("employees");
        ArrayList<Employee> employees = new ArrayList<Employee>();
        
        // Itération dans le tableau des activités du projet
        activitiesList.forEach(activity -> addActivityToProject((JSONObject) activity, activities));
        
        // Itération dans le tableau des ID d'employés assignés au projet
        employeesList.forEach(employeeId -> addEmployeeToProject((String)employeeId, employees));
        

        projects.add(new Project(name, id, activities, employees));
    }
    
    private void addActivityToProject(JSONObject activity, ArrayList<Activity> activities)  {
    	String name = (String) activity.get("name");
        long budget = (long) activity.get("budget");
        
        activities.add(new Activity(name, budget));
    }
    
    private void addEmployeeToProject(String employeeId, ArrayList<Employee> employees)  {        
        employees.add(EmployeeRepository.getInstance().getById(employeeId));
    }
    
    
    /**
	 * Lit le contenu du fichier JSON contenant la liste des projets et construit les objets.
	 * 
	 */
    @SuppressWarnings("unchecked")
    private void readDataSource() {         
        JSONArray projectList = (JSONArray) super.readDataSource(fileName);
        
        // Itération dans le tableau de projets
        projectList.forEach(project -> addProjectToRepository((JSONObject) project));
    }

    /**
	 * Écrit le contenu des objets du dépot de données dans le fichier JSON associé pour persistence.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void writeDataSource() {
		JSONArray projectList = new JSONArray();
		for (Project project : projects) {
			JSONArray activities = new JSONArray();
			JSONArray employeeIds = new JSONArray();
			for (Activity projectActivity : project.getActivities()) {
				JSONObject activity = new JSONObject();
				activity.put("name", projectActivity.getName());
				activity.put("budget", projectActivity.getBudget());
				activities.add(activity);
			}
			
			for (Employee projectEmployee : project.getAssignedEmployees()) {
				employeeIds.add(projectEmployee.getId());
			}

			JSONObject projectObject = new JSONObject();
			projectObject.put("name", project.getName());
			projectObject.put("id", project.getId());
			projectObject.put("activities", activities);
			projectObject.put("employees", employeeIds);
			
			projectList.add(projectObject);
		}
		
		super.writeDataSource(fileName, projectList);
	}
}
