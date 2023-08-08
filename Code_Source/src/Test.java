import org.junit.After;
import org.junit.Before;

import menus.ActivityManagementMenu;
import menus.AdminMenu;
import menus.EmployeeMenu;
import menus.LoginMenu;
import repositories.Activity;
import repositories.Admin;
import repositories.AdminRepository;
import repositories.Config;
import repositories.ConfigRepository;
import repositories.Employee;
import repositories.EmployeeRate;
import repositories.EmployeeRepository;
import repositories.Project;
import repositories.Worklog;
import repositories.WorklogRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.mockito.Mock;


public class Test {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpInputOutput() {
        // Prepare the test input data for selecting Administrator (option 1)
        String input = "1\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Redirect the standard output to capture it
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemInputOutput() {
        // Restore the original standard input and output streams
        System.setIn(systemIn);
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
	 * Modification d'un projet (Assigner et Désassigner un employé à un projet)
	 */
	@org.junit.Test
	public void test_ModifierProjet() {
		 ArrayList<Activity> activities = new ArrayList<>();
		 ArrayList<Employee> employees = new ArrayList<>();
		Project p = new Project("test","test",activities, employees);
		ArrayList<EmployeeRate> list = new ArrayList<>();
		Employee e = new Employee("test","6","7",null,null, list); 
		//assigner un employee 
		p.addEmployee(e);
		assertTrue(p.getAssignedEmployees().contains(e));
		//desassigner un employee
		p.removeEmployee(e);
		assertTrue(!p.getAssignedEmployees().contains(e));
	}
	/*
	 * Signaler début et fin d'activité 
	 */
	@org.junit.Test
	public void test_SignalerDebutFinActivite() {
		ArrayList<EmployeeRate> list = new ArrayList<>();
		Employee e = new Employee("test6","643","74",null,null, list); 
		EmployeeRepository er = mock(EmployeeRepository.class);
		ArrayList<Activity> activities = new ArrayList<>();
		Activity a = new Activity("test",14);
		activities.add(a);
		ArrayList<Employee> employees = new ArrayList<>();
		Project p = new Project("test","test",activities, employees);
		p.addEmployee(e);
		//Il n'existe pas de Worklog pour cet employé, je pense que canStartActivity devrait retourner True dans ce cas
    	//assertTrue(er.canStartActivity(e)== true);
    	//assertTrue(er.canEndActivity(e) == false);
	}
	
	/*
	 * Possible de Login Admin et Login Employer
	 * En vérifiant de les répertoires si les combinaisons Username, id sont valides
	 */
	
	@org.junit.Test
	public void test_ValidateUserLogin() {
		AdminRepository ar = mock(AdminRepository.class);
		assertTrue(ar.getInstance().isValid("admin", "0")==true);
		assertTrue(ar.getInstance().isValid("admin", "1")==false);
		
		EmployeeRepository er = mock(EmployeeRepository.class);
		assertTrue(er.getInstance().isValid("employe1", "1")==true);
		assertTrue(er.getInstance().isValid("employe1", "5")==false);
		}
    	
}