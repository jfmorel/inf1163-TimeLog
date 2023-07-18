import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe d'initialisation des données du programme console TimeLog.
 * Les méthodes de cette classes sont très spécifiques à l'initialisation des données tel que
 * demandé dans les spécification du projet.
 * @author Généviève Abikou
 * @author William McAllister
 * @author Jean-Francois Morel
 * @version 1.0
 */
public class TimeLogInitializer {
	/**
	 * Lance l'initialisation des données du programme console TimeLog.
	 * 
	 */
	public static void initData() {
		initializeRepository();
	}
	
	/**
	 * Détermine le numéro de semaine d'une date.
	 * 
	 * @param date La date pour laquelle déterminer le numéro de semaine.
	 * @return Le numéro de semaine.
	 */
	private static int getWeekNumber(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }
	
	/**
	 * Détermine la date de la combinaison année, numéro de semaine et jour de la semaine.
	 * 
	 * @param year L'année.
	 * @param weekNumber Le numéro de semaine.
	 * @param dayOfWeek Le jour de la semaine.
	 * @return La date calculée.
	 */
	private static LocalDate getDayOfWeek(int year, int weekNumber, DayOfWeek dayOfWeek) {
        try {
            LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
            LocalDate dayOfFirstWeek = firstDayOfYear.with(TemporalAdjusters.previousOrSame(dayOfWeek));

            if (weekNumber == 1) {
                return dayOfFirstWeek;
            } else {
                return dayOfFirstWeek.plusWeeks(weekNumber);
            }
        } catch (Exception e) {
            return null;
        }
    }
	
	/**
	 * Popule le fichier worklogs.json avec les entrées de temps tel que stipulé dans les
	 * spécifications du projet.
	 * La qualité du code de cette méthode laisse à désirer. Le revoir à une date ultérieure.
	 */
	@SuppressWarnings("unchecked")
	private static void populateWorklogs() {
        String filePrefix = System.getProperty("user.home").concat(System.getProperty("file.separator")).concat(".timeLog").concat(System.getProperty("file.separator")).concat(System.getProperty("file.separator"));
		DayOfWeek[] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
		ArrayList<Long> startTimestamps = new ArrayList<Long>();
		LocalDate currentDate = LocalDate.now();

        // Get the week number using ISO-8601 standard (Monday as the first day of the week)
        int weekNumber = getWeekNumber(currentDate);
        int[] weekNumbers = new int[2];
        if (weekNumber % 2 == 0) {
        	weekNumbers[0] = weekNumber - 3;
        	weekNumbers[1] = weekNumber - 2;
        } else {
        	weekNumbers[0] = weekNumber - 2;
        	weekNumbers[1] = weekNumber - 1;
        }
        
        for (int week : weekNumbers) {
        	for (DayOfWeek dayOfWeek : daysOfWeek ) {
        		long timestamp = getDayOfWeek(2023, week, dayOfWeek).atStartOfDay(ZoneOffset.UTC).toInstant().getEpochSecond();
        		startTimestamps.add(timestamp);
        	}
        }
        
        Integer worklogId = 1;
        String[] activities = {"design1", "design2", "implementation", "test", "deploiement"};
        JSONArray worklogs = new JSONArray();
        String[] projectIdsForEmployee1 = {"1", "2"};
        String[] projectIdsForEmployee2 = {"2", "3"};
        String[] projectIdsForEmployee3 = {"1", "3"};
        
        // Employe1
        for (long startTimestamp : startTimestamps) {
        	long currentTimestamp = startTimestamp;
        	for (String projectId : projectIdsForEmployee1) {
        		for (String activity : activities) {
        			JSONObject worklogObject = new JSONObject();
        			worklogObject.put("id", worklogId.toString());
        			worklogObject.put("projectId", projectId);
        			worklogObject.put("employeeId", "1");
        			worklogObject.put("activity", activity);
        			worklogObject.put("start", currentTimestamp);
        			worklogObject.put("end", currentTimestamp += (3600 + (360 * 1)));
        			
        			worklogs.add(worklogObject);
        			
        			worklogId += 1;
                }
            }
        }
        
        // Employe2
        for (long startTimestamp : startTimestamps) {
        	long currentTimestamp = startTimestamp;
        	for (String projectId : projectIdsForEmployee2) {
        		for (String activity : activities) {
        			JSONObject worklogObject = new JSONObject();
        			worklogObject.put("id", worklogId.toString());
        			worklogObject.put("projectId", projectId);
        			worklogObject.put("employeeId", "2");
        			worklogObject.put("activity", activity);
        			worklogObject.put("start", currentTimestamp);
        			worklogObject.put("end", currentTimestamp += (3600 + (360 * 2)));
        			
        			worklogs.add(worklogObject);
        			
        			worklogId += 1;
                }
            }
        }
        
        // Employe3
        for (long startTimestamp : startTimestamps) {
        	long currentTimestamp = startTimestamp;
        	for (String projectId : projectIdsForEmployee3) {
        		for (String activity : activities) {
        			JSONObject worklogObject = new JSONObject();
        			worklogObject.put("id", worklogId.toString());
        			worklogObject.put("projectId", projectId);
        			worklogObject.put("employeeId", "3");
        			worklogObject.put("activity", activity);
        			worklogObject.put("start", currentTimestamp);
        			worklogObject.put("end", currentTimestamp += (3600 + (360 * 3)));
        			
        			worklogs.add(worklogObject);
        			
        			worklogId += 1;
                }
            }
        }
        
        
        try (FileWriter file = new FileWriter(filePrefix.concat("worklogs.json"))) {
            file.write(worklogs.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Copie les fichiers se trouvant dans le dossier de ressources du programme dans le dossier
	 * personel de l'utilisateur connecté au compte du système d'exploitation.
	 */
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
			
			populateWorklogs();
		}
	}
}
