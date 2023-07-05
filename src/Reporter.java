import com.console.Menu;
import com.console.MenuItem;
import com.repository.ProjectRepository;

public class Reporter {
	public void generateAllProjectsReport(ProjectRepository projectRepository) {
		TimeLog tl = TimeLog.getInstance();
		System.out.println(tl.getInfo());
		Menu menu = new Menu();
		menu.setTitle("*** Pour quel projet voulez-vous générer le rapport? ***");
		for (String project : projectRepository.getAll()) {
			menu.addItem(new MenuItem(project));
		}
		menu.addItem(new MenuItem("Tous les projets"));
		menu.execute();
	}
}
