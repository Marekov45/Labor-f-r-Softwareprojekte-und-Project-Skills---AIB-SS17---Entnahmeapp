package ServerAPITest;

import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.PrimerImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the list requests.
 * Created by Benjamin on 16.06.2017.
 */
public class ListImplTest {

    private ListImpl listImpl;
    private PrimerImpl primerImpl;

    @Before
    public void setUp() throws Exception {

        listImpl = new ListImpl();
        primerImpl = new PrimerImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void requestList() throws Exception {
        //Valid listrequest

        //robotlist
        try {
            listImpl.requestList("mustermann","1234","S");
        }catch (AssertionError e){
            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }

        //manuallist
        try {
            listImpl.requestList("mustermann","1234","M");
        }catch (AssertionError e){
            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }

        //extralist
        try {
            listImpl.requestList("mustermann","1234","E");
        }catch (AssertionError e){
            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }

        //----------------------------------------------------------------------------------
        //Invalid listrequest

        try {
            listImpl.requestList("mustermann","1234","D");
        }catch (AssertionError e){
            assertTrue(true);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }



    }

    @Test
    public void requestAllLists() throws Exception {

        try {
            listImpl.requestAllLists("mustermann","1234");
        }catch (AssertionError e){
            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }

    }

    @Test
    public void requestAllGatheredPrimers() throws Exception {

        //without gathered Primer
        try {
            listImpl.requestAllGatheredPrimers("mustermann", "1234");
        }catch (AssertionError e){
            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }
        //-----------------------------------------------------------------
        //with gathered Primer
        try {
            primerImpl.takePrimer(0,"mustermann","1234");
            listImpl.requestAllGatheredPrimers("mustermann", "1234");
        }catch (AssertionError e){
            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }

    }

    @Test
    public void requestGatheredPrimers() throws Exception {
        try {
            primerImpl.takePrimer(0,"mustermann","1234");
            listImpl.requestGatheredPrimers("0","mustermann", "1234");
        }catch (AssertionError e){
            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }
    }

    @Test
    public void takePrimer() throws Exception {

        try {
            primerImpl.takePrimer(0,"mustermann","1234");
        }catch (AssertionError e){
            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }

    }

    @Test
    public void requestLastSangerList() throws Exception {

        try {
           listImpl.requestLastSangerList("mustermann","1234");
        }catch (AssertionError e){
            assertFalse(false);
        }catch (Exception e){
            fail("Unexpected exception thrown... " + e.getMessage());
        }

    }

}