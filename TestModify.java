package gps.tasks.task3663;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.Assertion;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertTrue;

public class TestModify
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

  @Test (expected = SQLException.class)
  public void test_addRow_duplicate() throws SQLException//{{{
  {
    mod.addRow("events", "id", 2);
  }//}}}

  @Test
  public void test_addRow_newRow() throws Exception//{{{
  {
    // expected table values

    IDataSet expectedDs = getDataSet("expected_addRow_newRow.xml");
    ITable expectedTable = expectedDs.getTable("events");


    // use the function

    mod.addRow("events", "id", 20);


    // capture side effects, viz table modification

    IDataSet actualDs = dbuconn.createDataSet();
    ITable actualTable = actualDs.getTable("events");


    // will it blend?

    Assertion.assertEquals(expectedTable, actualTable);
  }//}}}

  @Test
  public void test_modRow_newRow() throws Exception//{{{
  {
    // expected table values

    IDataSet expectedDs = getDataSet("expected_modRow_newRow.xml");
    ITable expectedTable = expectedDs.getTable("events");


    // use the function

    Event e = new Event();
    e.setDay("2012-12-31");
    e.setTimeStart("");
    e.setTimeEnd("");
    e.setKind("other");
    e.setDescription("end of the world");
    e.setGuests("");

    mod.modRow("events", e.getMap());


    // capture side effects

    IDataSet actualDs = dbuconn.createDataSet();
    ITable actualTable = actualDs.getTable("events");


    // will it blend?

    Assertion.assertEquals(expectedTable, actualTable);
  }//}}}

  @Test
  public void test_modRow_update() throws Exception//{{{
  {
    // expected table values

    IDataSet expectedDs = getDataSet("expected_modRow_update.xml");
    ITable expectedTable = expectedDs.getTable("events");


    // use the function

    Event e = new Event();
    e.setId("3");
    e.setTimeStart("00:00");

    mod.modRow("events", e.getMap());


    // capture side effects

    IDataSet actualDs = dbuconn.createDataSet();
    ITable actualTable = actualDs.getTable("events");


    // will it blend?

    Assertion.assertEquals(expectedTable, actualTable);
  }//}}}

}
