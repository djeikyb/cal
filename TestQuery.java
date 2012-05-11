package gps.tasks.task3663;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

public class TestQuery extends DBTestCase
{
  Connection conn = new GimmeConn().conn;
  QueryDb q = new QueryDb(conn);

  public TestQuery(String name)
  {
    super( name );
    System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
    System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost/cal");
    System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "cal");
    System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "cal");
  }

  protected IDataSet getDataSet() throws Exception
  {
    return new FlatXmlDataSetBuilder().build(new FileInputStream("src/gps/tasks/task3663/dataset.xml"));
  }

  @Test
  public void test_getEventsThis_mayo() throws SQLException
  {
    List<Integer> expected = Arrays.asList(3,4);
    List<Integer> result = q.getEvents_this("2012-05-05");

    assertTrue(expected.toString().equals(
               result.toString()));
  }

  @Test
  public void test_getEventsThese_twoDays() throws SQLException
  {
    List<String> days = Arrays.asList("0000-00-00","2012-05-28");

    List<Integer> expected = Arrays.asList(1,6);
    List<Integer> result = q.getEvents_these(days);

    assertTrue(expected.toString().equals(
               result.toString()));
  }

}
