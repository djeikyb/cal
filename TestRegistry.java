package gps.tasks.task3663;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Driver;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class TestRegistry
{

/*  Pick a working directory
  private String path = "src/gps/tasks/task3663/";  // eclipse
  private String path = "";                         // make
*/
  private String path = "src/gps/tasks/task3663/";  // eclipse

//------------------------------------------------------------------------------
//  Setup
//------------------------------------------------------------------------------

  private IDatabaseTester dbtester;
  private Connection gconn = new GimmeConn().conn;
  private ModifyDb mod = new ModifyDb(gconn);
  IDatabaseConnection dbuconn;

  @Before
  public void setUp() throws Exception
  {

    // set up connection
    dbtester = new JdbcDatabaseTester("com.mysql.jdbc.Driver",
                                      "jdbc:mysql://localhost/cal",
                                      "cal",
                                      "cal");
    //initialise dataset
    IDataSet dataSet = getDataSet("dataset.xml");
    dbtester.setDataSet(dataSet);

    dbuconn = dbtester.getConnection();

    // call default setUpOperation
    DatabaseOperation.TRUNCATE_TABLE.execute(dbuconn, getDataSet("dataset.xml"));
    dbtester.onSetup();

    // reset registry aka database cache
    CalendarRegistry.eventBeans = null;
    CalendarRegistry.eventBeans = new ArrayList<Map<String, String>>();

    CalendarRegistry.guestBeans = null;
    CalendarRegistry.guestBeans = new ArrayList<Map<String, String>>();
  }

  @After
  public void tearDown() throws Exception
  {
    dbtester.onTearDown();
  }

  protected IDataSet getDataSet(String f) throws Exception
  {
    return
      new FlatXmlDataSetBuilder().build(
        new FileInputStream(path + f));
  }



//------------------------------------------------------------------------------
//  Tests
//------------------------------------------------------------------------------

  /**
   * Test side effect: modifies static variable eventBeans
   *
   * @throws SQLException
   */
  @Test
  public void test_addEventBeans() throws SQLException
  {
    // expected value of CalendarRegistry.eventBeans

    Map<String, String> ev0 = new HashMap<String, String>();
    Map<String, String> ev1 = new HashMap<String, String>();
    Map<String, String> ev2 = new HashMap<String, String>();
    List<Map<String, String>> expected = Arrays.asList(ev0, ev1, ev2);

    ev0.put("id",           "5");
    ev0.put("day",          "2012-05-05");
    ev0.put("timeStart",    "15:00");
    ev0.put("timeEnd",      "18:00");
    ev0.put("kind",         "other");
    ev0.put("description",  "cinco de mayo happy hour");
    ev0.put("guests",       "");

    ev1.put("id",           "1");
    ev1.put("day",          "");
    ev1.put("timeStart",    "");
    ev1.put("timeEnd",      "");
    ev1.put("kind",         "");
    ev1.put("description",  "");
    ev1.put("guests",       "");

    ev2.put("id",           "3");
    ev2.put("day",          "0000-00-00");
    ev2.put("timeStart",    "");
    ev2.put("timeEnd",      "");
    ev2.put("kind",         "other");
    ev2.put("description",  "beginning of common era");
    ev2.put("guests",       "");


    // use the function

    CalendarRegistry.addEventBeans(Arrays.asList(5,1,3));


    // capture side effect

    List<Map<String, String>> result = CalendarRegistry.eventBeans;


    // will it blend?

    assertTrue(expected.toString().equals(
               result.toString()));
  }


  /**
   * Tests side effect: modify static variable guestBeans
   *
   * @throws SQLException
   */
  @Test
  public void test_addGuestBeans_weirdName() throws SQLException
  {
    //  use the function

    CalendarRegistry.addGuestBeans(Arrays.asList(1));


    //  capture side effect

    List<Map<String, String>> result = CalendarRegistry.guestBeans;


    //  expected value of CalendarRegistry.guestBeans

    Map<String, String> guest0 = new HashMap<String, String>();
    List<Map<String, String>> expected = Arrays.asList(guest0);

    guest0.put("id",     "1");
    guest0.put("name",   " null 0m*[2~*[2~*[2~*l*OQ0's'S'F2498 7*[5~*[3~*[4~df asdf[sd fw erfj203409faf; dsf;lsk dfas;df j2-sa;fsdf s\"i$(*%$R*$#(*R $(*R@J#R*@R$# R( $#*) $ R@# R$($* R(i   \"\"\"j;sdf asdfasfsadfs m1f93mf 933f134- f4398 `9t834p14-r039qtp3pq38t1tp3tq3 pgjasd;lfksdj fas");
    guest0.put("email",  "foo@bar.biz");


    //  will it blend?

    assertTrue(expected.toString().equals(
               result.toString()));
  }


  /**
   * Tests side effect: modify static variable guestBeans
   *
   * @throws SQLException
   */
  @Test
  public void test_addGuestBeans() throws SQLException
  {
    /* use the function */

    CalendarRegistry.addGuestBeans(Arrays.asList(3,2,5));


    /* capture side effect */

    List<Map<String, String>> actual = CalendarRegistry.guestBeans;


    /* expected value of CalendarRegistry.GuestBeans */

    Map<String, String> guest0 = new HashMap<String, String>();
    Map<String, String> guest1 = new HashMap<String, String>();
    Map<String, String> guest2 = new HashMap<String, String>();
    List<Map<String, String>> expected = Arrays.asList(guest0, guest1, guest2);

    guest0.put("id",     "3");
    guest0.put("name",   "Dweezil");
    guest0.put("email",  "bugs@zappa.fam");

    guest1.put("id",     "2");
    guest1.put("name",   "Moon Unit");
    guest1.put("email",  "lazers@zappa.fam");

    guest2.put("id",     "5");
    guest2.put("name",   "Diva Thin Muffin Pigeen");
    guest2.put("email",  "pdgn@zappa.fam");


    /* will it blend? */

    assertTrue(String.format("expected: %s\nbut was: %s", expected, actual), expected.toString().equals(actual.toString()));
  }


  @Test
  public void test_refreshEventBeans() throws SQLException
  {
    // use the function

    CalendarRegistry.addEventBeans(Arrays.asList(5));

    ArrayList<ArrayList<String>> newRow = new ArrayList<ArrayList<String>>();


    CalendarRegistry.refreshEventBeans();


  }

}
