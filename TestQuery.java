package gps.tasks.task3663;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import static org.junit.Assert.assertFalse;


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
  private ModifyDb mod = new ModifyDb(gconn);
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
    List<Integer> expected = Arrays.asList(5,6);
    List<Integer> result = q.getEvents_this("2012-05-05");

    assertTrue(expected.toString().equals(
               result.toString()));
  }

  @Test
  public void test_getEventsThese_twoDays() throws SQLException
  {
    List<String> days = Arrays.asList("0000-00-00","2012-05-28");

    List<Integer> expected = Arrays.asList(3,8);
    List<Integer> result = q.getEvents_these(days);

    assertTrue(expected.toString().equals(
               result.toString()));
  }

  @Test
  public void test_getDescriptions() throws SQLException
  {
    List<Integer> eids = Arrays.asList(1, 2, 5, 8);

    List<String> expected = Arrays.asList(
      "",
      "halloween",
      "cinco de mayo happy hour",
      "memorial day");
    List<String> result = q.getDescriptions(eids);

    assertTrue(expected.toString().equals(
               result.toString()));
  }

  @Test
  public void test_getRows_single() throws SQLException
  {
    List<Map<String, String>> result = q.getRows("events", Arrays.asList(5));
    List<Map<String, String>> expected = new ArrayList<Map<String, String>>();

    Map<String, String> foo = new HashMap<String, String>();
      foo.put("id",           "5");
      foo.put("day",          "2012-05-05");
      foo.put("timeStart",    "15:00");
      foo.put("timeEnd",      "18:00");
      foo.put("kind",         "other");
      foo.put("description",  "cinco de mayo happy hour");
      foo.put("guests",       "");
    expected.add(foo);

    assertTrue(expected.toString().equals(
               result.toString()));
  }

  @Test
  public void test_getRows_multiple() throws SQLException
  {
    List<Map<String, String>> result = q.getRows("events", Arrays.asList(5,1,3));

    Map<String, String> foo = new HashMap<String, String>();
    Map<String, String> foobar = new HashMap<String, String>();
    Map<String, String> bar = new HashMap<String, String>();
    List<Map<String, String>> expected = Arrays.asList(foo, bar, foobar);

    foo.put("id",           "5");
    foo.put("day",          "2012-05-05");
    foo.put("timeStart",    "15:00");
    foo.put("timeEnd",      "18:00");
    foo.put("kind",         "other");
    foo.put("description",  "cinco de mayo happy hour");
    foo.put("guests",       "");

    bar.put("id",           "1");
    bar.put("day",          "");
    bar.put("timeStart",    "");
    bar.put("timeEnd",      "");
    bar.put("kind",         "");
    bar.put("description",  "");
    bar.put("guests",       "");

    foobar.put("id",           "3");
    foobar.put("day",          "0000-00-00");
    foobar.put("timeStart",    "");
    foobar.put("timeEnd",      "");
    foobar.put("kind",         "other");
    foobar.put("description",  "beginning of common era");
    foobar.put("guests",       "");

    assertTrue(expected.toString().equals(
               result.toString()));
  }

  @Test
  public void test_maxId() throws SQLException
  {
    Integer actual = q.maxId("events");
    Integer expected = 9;

    assertTrue(String.format("expected %s, but was %s", expected, actual), actual.toString().equals(expected.toString()));
  }

  @Test
  public void test_nextId() throws SQLException
  {
    Integer actual = q.nextId("events");
    Integer expected = 10;

    assertTrue(String.format("expected %s, but was %s", expected, actual), actual.toString().equals(expected.toString()));
  }

}
