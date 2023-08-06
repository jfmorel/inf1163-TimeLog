import org.junit.After;
import org.junit.Before;


import menus.AdminMenu;
import menus.LoginMenu;
import repositories.Admin;
import repositories.Config;
import repositories.ConfigRepository;
import repositories.Employee;
import repositories.EmployeeRate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
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
	 * */
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
	 * Modification d'un projet
	 * */
	@org.junit.Test
	public void test_ModifierProjet() {
		
	}
	

    	
}