import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import menus.LoginMenu;

/**
 * Classe d'initialisation du programme console TimeLog.
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class TimeLogRunner {
	
	/**
	 * Lance le programme de gestion de projet TimeLog
	 */
	public static void main(String[] args) {
		initializeRepository();
		LoginMenu.mainMenu();
	}
	
	private static void initializeRepository() {
		String destinationPath = System.getProperty("user.home").concat(System.getProperty("file.separator")).concat(".timeLog").concat(System.getProperty("file.separator"));
		
	    if (!Files.exists(Paths.get(destinationPath))) {
			try {
				Files.createDirectories(Paths.get(destinationPath));
				
				String[] fileNames = {"admins.json", "configs.json", "employees.json", "projects.json", "worklogs.json"};
				
				for (String fileName : fileNames) {
					InputStream is = TimeLogRunner.class.getClassLoader().getResourceAsStream(fileName);
					File targetFile = new File(destinationPath.concat(fileName));
					FileUtils.copyInputStreamToFile(is, targetFile);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
