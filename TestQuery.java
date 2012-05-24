package gps.tasks.task3663;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class TestQuery
{

/*  Pick a working directory
  private String path = "src/gps/tasks/task3663/";  // eclipse
  private String path = "";                         // cwd
*/
  private String path = "src/gps/tasks/task3663/";  // eclipse

//------------------------------------------------------------------------------
//  Setup
//------------------------------------------------------------------------------

  private IDatabaseTester dbtester;
  private Connection gconn = new GimmeConn().conn;
  private QueryDb q = new QueryDb(gconn);
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

  @Test
  public void test_getEventsThis_mayo() throws SQLException
  {
    List<Integer> actual = q.getEvents_this("2012-05-05");

    List<Integer> expected = Arrays.asList(5,6);

    assertThat(actual, is(expected));
  }

  @Test
  public void test_getEventsThese_twoDays() throws SQLException
  {
    // actual

    List<String> days = Arrays.asList("0000-00-00","2012-05-28");
    List<Integer> actual = q.getEvents_these(days);


    // expected

    List<Integer> expected = Arrays.asList(3,8);


    // test

    assertThat(actual, is(expected));
  }

  @Test
  public void test_getEventsThese_mayo() throws SQLException
  {
    // actual

    List<String> days = Arrays.asList("2012-05-05");
    List<Integer> actual = q.getEvents_these(days);


    // expected

    List<Integer> expected = Arrays.asList(5,6);


    // test

    assertThat(actual, is(expected));
  }

  @Test
  public void test_getEventsThese_noEvent() throws SQLException
  {
    // actual

    List<String> days = Arrays.asList("0000-00-01");
    List<Integer> actual = q.getEvents_these(days);


    // expected

    List<Integer> expected = Arrays.asList();


    // test

    assertThat(actual, is(expected));
  }

  @Test
  public void test_getDescriptions() throws SQLException
  {
    // actual

    List<Integer> eids = Arrays.asList(1, 2, 5, 8);
    List<String> actual = q.getDescriptions(eids);


    // expected

    List<String> expected = Arrays.asList(
      "",
      "all hallows eve, when ghouls moan and banshees scream and children become the monsters under their beds",
      "cinco de mayo happy hour",
      "memorial day");


    // test

    assertThat(actual, is(expected));
  }

  @Test
  public void test_getRows_single() throws SQLException
  {
    // actual

    Map<Integer, Map<String, String>> actual = q.getRows("events", Arrays.asList(5));


    // expected

    Map<Integer, Map<String, String>> expected = new HashMap<Integer, Map<String, String>>();

    Map<String, String> foo = new HashMap<String, String>();
      foo.put("id",           "5");
      foo.put("day",          "2012-05-05");
      foo.put("timeStart",    "15:00");
      foo.put("timeEnd",      "18:00");
      foo.put("kind",         "other");
      foo.put("description",  "cinco de mayo happy hour");
      foo.put("guests",       "");
    expected.put(5, foo);


    // test

    assertThat(actual, is(expected));
  }

  @Test
  public void test_getRows_multiple() throws SQLException
  {
    // actual

    Map<Integer, Map<String, String>> actual = q.getRows("events", Arrays.asList(5,1,3));


    // expected

    Map<Integer, Map<String, String>> expected = new HashMap<Integer, Map<String, String>>();

    Map<String, String> foo = new HashMap<String, String>();
    Map<String, String> foobar = new HashMap<String, String>();
    Map<String, String> bar = new HashMap<String, String>();

    foo.put("id",           "5");
    foo.put("day",          "2012-05-05");
    foo.put("timeStart",    "15:00");
    foo.put("timeEnd",      "18:00");
    foo.put("kind",         "other");
    foo.put("description",  "cinco de mayo happy hour");
    foo.put("guests",       "");
    expected.put(5, foo);

    bar.put("id",           "1");
    bar.put("day",          "");
    bar.put("timeStart",    "");
    bar.put("timeEnd",      "");
    bar.put("kind",         "");
    bar.put("description",  "");
    bar.put("guests",       "");
    expected.put(1, bar);

    foobar.put("id",           "3");
    foobar.put("day",          "0000-00-00");
    foobar.put("timeStart",    "");
    foobar.put("timeEnd",      "");
    foobar.put("kind",         "other");
    foobar.put("description",  "beginning of common era");
    foobar.put("guests",       "");
    expected.put(3, foobar);


    // test

    assertThat(actual, is(expected));
  }

  @Test
  public void test_maxId() throws SQLException
  {
    Integer actual = q.maxId("events");

    Integer expected = 9;

    assertThat(actual, is(expected));
  }

  @Test
  public void test_nextId() throws SQLException
  {
    Integer actual = q.nextId("events");

    Integer expected = 10;

    assertThat(actual, is(expected));
  }

  @Test
  public void test_getGuests() throws SQLException
  {
    List<Integer> actual = q.getGuests();

    List<Integer> expected = Arrays.asList(1,2,3,4,5);

    assertThat(actual, is(expected));
  }

}
