import com.console.Menu;
import com.console.MenuItem;
import com.repository.ProjectRepository;

public class AdminMenu {
	ProjectRepository projectRepository = new ProjectRepository();
	Reporter reporter = new Reporter();
	
	public void init() {
		this.mainMenu();
	}
	
	private void mainMenu() {
		Menu menu = new Menu();
		menu.setTitle("*** Menu Administrateur ***");
		menu.addItem(new MenuItem("Option A", () -> this.subMenuA() ));
		menu.addItem(new MenuItem("Générer un rapport", () -> reporter.generateAllProjectsReport(projectRepository)));
		menu.execute();
	}


	public void subMenuA() {
		Menu menu = new Menu();
		menu.setTitle("*** Sub Menu A ***");
		menu.addItem(new MenuItem("Option Aa", () -> System.out.println("breg")));
		menu.addItem(new MenuItem("Option Ab"));
		menu.execute();
	}
}
