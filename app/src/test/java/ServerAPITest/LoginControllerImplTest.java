package ServerAPITest;

import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.LoginAPI;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.LoginControllerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;

import static org.junit.Assert.*;

/**
 * This class tests the login with valid Data and invalid Data
 * Created by Benjamin on 13.06.2017.
 */
public class LoginControllerImplTest {

    private LoginControllerImpl loginController;

    @Before
    public void setUp() throws Exception {

        loginController = new LoginControllerImpl();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRequestLogin()throws Exception{

        //Valid Data

        try {

            loginController.requestLogin("mustermann","1234");

        }catch (AssertionError e){

            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }


        //Invalid Data


        try {
            loginController.requestLogin("4567","1234");
        } catch (AssertionError e) {
            assertTrue(true);
        }catch (Exception e) {
            fail("Unexpected exception thrown... " + e.getMessage());
        }

        try {
            loginController.requestLogin("","");
        } catch (AssertionError e) {
            assertTrue(true);
        }catch (Exception e) {
            fail("Unexpected exception thrown... " + e.getMessage());
        }

        try {
            loginController.requestLogin("4567","");
        } catch (AssertionError e) {
            assertTrue(true);
        }catch (Exception e) {
            fail("Unexpected exception thrown... " + e.getMessage());
        }


    }

}