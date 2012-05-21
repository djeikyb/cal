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

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;


//@SuppressWarnings("unchecked")
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
  public void setUp() throws Exception  //{{{
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
    CalendarRegistry.eventBeans = new HashMap<Integer, Map<String, String>>();

    CalendarRegistry.guestBeans = null;
    CalendarRegistry.guestBeans = new HashMap<Integer, Map<String, String>>();
  } //}}}

  @After
  public void tearDown() throws Exception //{{{
  {
    dbtester.onTearDown();
  } //}}}

  protected IDataSet getDataSet(String f) throws Exception  //{{{
  {
    return
      new FlatXmlDataSetBuilder().build(
        new FileInputStream(path + f));
  } //}}}



//------------------------------------------------------------------------------
//  Tests
//------------------------------------------------------------------------------

  @Test
  public void test_addEventBeans() throws SQLException  //{{{
  {
    // expected value of CalendarRegistry.eventBeans

    Map<String, String> ev0 = new HashMap<String, String>();
    Map<String, String> ev1 = new HashMap<String, String>();
    Map<String, String> ev2 = new HashMap<String, String>();
    Map<Integer, Map<String, String>> expected = new HashMap<Integer, Map<String, String>>();

    ev0.put("id",           "5");
    ev0.put("day",          "2012-05-05");
    ev0.put("timeStart",    "15:00");
    ev0.put("timeEnd",      "18:00");
    ev0.put("kind",         "other");
    ev0.put("description",  "cinco de mayo happy hour");
    ev0.put("guests",       "");
    expected.put(5, ev0);

    ev1.put("id",           "1");
    ev1.put("day",          "");
    ev1.put("timeStart",    "");
    ev1.put("timeEnd",      "");
    ev1.put("kind",         "");
    ev1.put("description",  "");
    ev1.put("guests",       "");
    expected.put(1, ev1);

    ev2.put("id",           "3");
    ev2.put("day",          "0000-00-00");
    ev2.put("timeStart",    "");
    ev2.put("timeEnd",      "");
    ev2.put("kind",         "other");
    ev2.put("description",  "beginning of common era");
    ev2.put("guests",       "");
    expected.put(3, ev2);


    // use the method

    CalendarRegistry.fetchEvents(Arrays.asList(5,1,3));


    // capture side effect

    Map<Integer, Map<String, String>> result = CalendarRegistry.eventBeans;


    // will it blend?

    assertThat(result, is(expected));
  } //}}}

  @Test
  public void test_addGuestBeans_weirdName() throws SQLException  //{{{
  {
    //  expected value of CalendarRegistry.guestBeans

    Map<String, String> guest0 = new HashMap<String, String>();
    Map<Integer, Map<String, String>> expected = new HashMap<Integer, Map<String, String>>();

    guest0.put("id",     "1");
    guest0.put("name",   " null 0m*[2~*[2~*[2~*l*OQ0's'S'F2498 7*[5~*[3~*[4~df asdf[sd fw erfj203409faf; dsf;lsk dfas;df j2-sa;fsdf s\"i$(*%$R*$#(*R $(*R@J#R*@R$# R( $#*) $ R@# R$($* R(i   \"\"\"j;sdf asdfasfsadfs m1f93mf 933f134- f4398 `9t834p14-r039qtp3pq38t1tp3tq3 pgjasd;lfksdj fas");
    guest0.put("email",  "foo@bar.biz");
    expected.put(1, guest0);


    //  use the method

    CalendarRegistry.fetchGuests(Arrays.asList(1));


    //  capture side effect

    Map<Integer, Map<String, String>> result = CalendarRegistry.guestBeans;


    //  will it blend?

    assertThat(result, is(expected));
  } //}}}

  @Test
  public void test_addGuestBeans() throws SQLException  //{{{
  {
    // expected value of CalendarRegistry.GuestBeans

    Map<String, String> guest0 = new HashMap<String, String>();
    Map<String, String> guest1 = new HashMap<String, String>();
    Map<String, String> guest2 = new HashMap<String, String>();
    Map<Integer, Map<String, String>> expected = new HashMap<Integer, Map<String, String>>();

    guest0.put("id",     "3");
    guest0.put("name",   "Dweezil");
    guest0.put("email",  "bugs@zappa.fam");
    expected.put(3, guest0);

    guest1.put("id",     "2");
    guest1.put("name",   "Moon Unit");
    guest1.put("email",  "lazers@zappa.fam");
    expected.put(2, guest1);

    guest2.put("id",     "5");
    guest2.put("name",   "Diva Thin Muffin Pigeen");
    guest2.put("email",  "pdgn@zappa.fam");
    expected.put(5, guest2);


    // use the method

    CalendarRegistry.fetchGuests(Arrays.asList(3,2,5));


    // capture side effect

    Map<Integer, Map<String, String>> result = CalendarRegistry.guestBeans;


    // will it blend?

    assertThat(result, is(expected));
  } //}}}

  @Test
  public void test_unregEvents() throws SQLException  //{{{
  {
    // modify eventBeans to test that method ignores existing eids

    Map<String, String> ev1 = new HashMap<String, String>();

    ev1.put("id",           "1");
    ev1.put("day",          "");
    ev1.put("timeStart",    "");
    ev1.put("timeEnd",      "");
    ev1.put("kind",         "");
    ev1.put("description",  "");
    ev1.put("guests",       "");

    CalendarRegistry.eventBeans.put(1, ev1);


    // expected

    List<Integer> expected = Arrays.asList(5,3);


    // result

    List<Integer> result = CalendarRegistry.unregEvents(Arrays.asList(5,1,3));


    // will it blend?

    assertThat(result, is(expected));
  } //}}}

  @Test
  public void test_getEventTagLines() throws SQLException //{{{
  {
    // expected

    Map<Integer, String> expected = new HashMap<Integer, String>();

    expected.put(2, "all hallows eve, when ghouls moan and banshees scr");
    expected.put(6, "cinco de mayo");


    // result

    Map<Integer, String> result = CalendarRegistry.getEventTaglines(Arrays.asList(2, 6));


    // will it blend?

    assertThat(result, is(expected));
  } //}}}

  @Test
  public void test_getEvent_cached() throws SQLException//{{{
  {
    // expected

    Map<String, String> expected = new HashMap<String, String>();
    expected.put("id",           "5");
    expected.put("day",          "2012-05-05");
    expected.put("timeStart",    "15:00");
    expected.put("timeEnd",      "18:00");
    expected.put("kind",         "other");
    expected.put("description",  "cinco de mayo happy hour");
    expected.put("guests",       "");


    // add to event bean list

    CalendarRegistry.eventBeans.put(5, expected);


    // result

    Map<String, String> result = CalendarRegistry.getEvent(5);


    // will it blend?

    assertThat(result, is(expected));
  }//}}}

  @Test
  public void test_getEvent_uncached() throws SQLException//{{{
  {
    // expected

    Map<String, String> expected = new HashMap<String, String>();
    expected.put("id",           "5");
    expected.put("day",          "2012-05-05");
    expected.put("timeStart",    "15:00");
    expected.put("timeEnd",      "18:00");
    expected.put("kind",         "other");
    expected.put("description",  "cinco de mayo happy hour");
    expected.put("guests",       "");


    // result

    Map<String, String> result = CalendarRegistry.getEvent(5);


    // will it blend?

    assertThat(result, is(expected));//}}}
  }

  @Test
  public void test_getDatesInRange()//{{{
  {
    // expected

    List<LocalDate> expected = Arrays.asList(
      new LocalDate(2012, 05, 28),
      new LocalDate(2012, 05, 29),
      new LocalDate(2012, 05, 30),
      new LocalDate(2012, 05, 31),
      new LocalDate(2012, 06, 01),
      new LocalDate(2012, 06, 02),
      new LocalDate(2012, 06, 03));


    // actual

    List<LocalDate> result = CalendarRegistry.getDatesInRange(
      new LocalDate(2012, 05, 28),
      new LocalDate(2012, 06, 03));


    // will it blend?

    assertThat(result, is(expected));
  }//}}}

  @Test
  public void test_eventDates() throws SQLException
  {
    // expected

    List<LocalDate> expected = Arrays.asList(
      new LocalDate(2012, 05, 05),
      new LocalDate(2012, 05, 05),
      new LocalDate(2012, 05, 13),
      new LocalDate(2012, 05, 28));


    // actual

    List<LocalDate> result = CalendarRegistry.eventDatesFor(
      new LocalDate(2012, 05, 01),
      new LocalDate(2012, 05, 31));


    // will it blend?

    assertThat(result, is(expected));
  }

  @Test
  public void test_updateEventBeans() throws SQLException
  {
    // expected value of CalendarRegistry.eventBeans

    Map<String, String> ev0 = new HashMap<String, String>();
    Map<String, String> ev1 = new HashMap<String, String>();
    Map<String, String> ev2 = new HashMap<String, String>();
    Map<Integer, Map<String, String>> expected = new HashMap<Integer, Map<String, String>>();

    ev0.put("id",           "5");
    ev0.put("day",          "2012-05-05");
    ev0.put("timeStart",    "15:00");
    ev0.put("timeEnd",      "18:00");
    ev0.put("kind",         "other");
    ev0.put("description",  "cinco de mayo happy hour");
    ev0.put("guests",       "");
    expected.put(5, ev0);

    ev1.put("id",           "1");
    ev1.put("day",          "");
    ev1.put("timeStart",    "");
    ev1.put("timeEnd",      "");
    ev1.put("kind",         "");
    ev1.put("description",  "");
    ev1.put("guests",       "");
    expected.put(1, ev1);

    ev2.put("id",           "3");
    ev2.put("day",          "0000-00-00");
    ev2.put("timeStart",    "");
    ev2.put("timeEnd",      "");
    ev2.put("kind",         "other");
    ev2.put("description",  "beginning of common era");
    ev2.put("guests",       "");
    expected.put(3, ev2);


    // modify eventBeans to test that method ignores existing eids

    CalendarRegistry.eventBeans.put(1, ev1);


    // use method

    CalendarRegistry.updateEventBeans(Arrays.asList(5,1,3));


    // capture side effect

    Map<Integer, Map<String, String>> result = CalendarRegistry.eventBeans;


    // will it blend?

    assertThat(result, is(expected));
  }

  @Test
  public void test_getEvent_noexist()
  {
    assertTrue("Not sure I need to worry. Ui should only ask for an event that the registry has told it about.", false);
  }
}
