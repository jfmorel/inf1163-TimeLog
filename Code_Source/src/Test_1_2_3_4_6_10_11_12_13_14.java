import org.junit.After;
import org.junit.Before;

import repositories.Admin;
import repositories.AdminRepository;
import repositories.Config;
import repositories.Employee;
import repositories.EmployeeRate;
import repositories.EmployeeRepository;
import repositories.Project;
import repositories.ProjectRepository;
import repositories.Report;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class Test_1_2_3_4_6_10_11_12_13_14 {
	private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpOutputCapture() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemOutput() {
        System.setOut(systemOut);
    }

    /*
     * modification du NPE
     * */
	@org.junit.Test
    public void test_ModifierNPE() {
       Config c = mock(Config.class);
       c.setNPE(0);
        assertEquals(c.getNPE(), 0);
    }
	
	/*
	 * Modification du Username et ID d'un employee par l'admin
	 */
	@org.junit.Test
	public void test_ModifierEmployee() {
		ArrayList<EmployeeRate> list = new ArrayList<>();
		Employee e = new Employee("test","6","7",null,null, list);
		Admin a = new Admin("Admin", "admin");
		a.modifyEmployeeUsername("modify", e);
		String testUsername = "modify";
		assertTrue(e.getUsername().equals(testUsername));
		String  testID = "3";
		a.modifyEmployeeID(testID,e);
		assertTrue(e.getLoginId().equals(testID));
	}
	
	/*
	 * Modification d'un projet (modification du titre)
	 */
	@org.junit.Test
	public void test_ModifierProjet() {
		String titre = "projet1";
		ArrayList<Project> p1 = ProjectRepository.getInstance().getAll();
		for(Project pr : p1) {
			if(pr.getName().equals(titre)) {
				pr.setName("modifier");	
				break;
			}
		}
		ProjectRepository.getInstance().writeDataSource();
		ArrayList<Project> p2 = ProjectRepository.getInstance().getAll();
		for(Project pr : p2) {
			if(pr.getName().equals("modifier")) {
				assertTrue(pr.getName().equals("modifier"));
				pr.setName(titre);
				break;
			}
		}
		
		ProjectRepository.getInstance().writeDataSource();
	
	}
	/*
	 * Signaler début et fin d'activité 
	 *
	 *	En vérifiant que chaques employés (à initialization) peuvent commencer une activité
	 */
	@org.junit.Test
	public void test_SignalerDebutFinActivite() {
		ArrayList<Employee> employees = EmployeeRepository.getInstance().getAll();
		for(Employee e : employees) {
			assertTrue(EmployeeRepository.getInstance().canStartActivity(e)== true);
		}
    	
	}
	
	/*
	 * Possible de Login Admin et Login Employer
	 * En vérifiant de les répertoires si les combinaisons Username, id sont valides
	 */
	
	@SuppressWarnings("static-access")
	@org.junit.Test
	public void test_ValidateUserLogin() {
		AdminRepository ar = mock(AdminRepository.class);
		assertTrue(ar.getInstance().isValid("admin", "0")==true);
		assertTrue(ar.getInstance().isValid("admin", "1")==false);
		
		EmployeeRepository er = mock(EmployeeRepository.class);
		assertTrue(er.getInstance().isValid("employe1", "1")==true);
		assertTrue(er.getInstance().isValid("employe1", "5")==false);
		}
	@org.junit.Test
	public void test_GenererRapportGlobal() {
		Report r = new Report();
		r.rapportGlobal();
		 	System.out.flush();
		 	String capturedOutput = testOut.toString();
	        String expectedOutput = "Rapport Global"; 
	        String[] lines = capturedOutput.split("\r?\n");
	        String firstLine = lines[0];
	        assertEquals(expectedOutput, firstLine);
	}
	@org.junit.Test
	public void test_GenererRapportProjet() {
		ArrayList<Project> p = ProjectRepository.getInstance().getAll();
		Project p1 = p.get(0);;
		Report r = new Report();
		r.rapportProject(p1);
		 	System.out.flush();
		 	String capturedOutput = testOut.toString();
	        String expectedOutput = "Rapport de projet"; 
	        String[] lines = capturedOutput.split("\r?\n");
	        String firstLine = lines[1];
	        assertEquals(expectedOutput, firstLine);
	}
	@org.junit.Test
	public void test_GenererRapportEmployee() {
		ArrayList<Employee> e = EmployeeRepository.getInstance().getAll();
		Employee e1 = e.get(0);
		Report r = new Report();
		r.rapportEmployee(Instant.now(),e1);
		 	System.out.flush();
		 	String capturedOutput = testOut.toString();
	        String expectedOutput = "Rapport de l'Employé: " + e1.getUsername(); 
	        String[] lines = capturedOutput.split("\r?\n");
	        String firstLine = lines[2];
	        assertEquals(expectedOutput, firstLine);
	}
    	
}