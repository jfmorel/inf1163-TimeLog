package repositories;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONAware;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Repository {
	protected String filePrefix = System.getProperty("user.home").concat(System.getProperty("file.separator")).concat(".timeLog").concat(System.getProperty("file.separator")).concat("json").concat(System.getProperty("file.separator"));
	protected Object readDataSource(String fileName) {
		Object jsonObj = new Object();
		// Instancie un objet de type JSON parser
        JSONParser jsonParser = new JSONParser();
		
        try (FileReader reader = new FileReader(fileName)) {
            // Lecture du fichier JSON
            jsonObj = jsonParser.parse(reader); 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonObj;
	}
	
	protected void writeDataSource(String fileName, JSONAware content) {
		try (FileWriter file = new FileWriter(fileName)) {
            file.write(content.toJSONString()); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
