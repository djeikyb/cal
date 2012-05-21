package gps.tasks.task3663;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarRegistry
{
  static QueryDb q = new QueryDb();

  static Map<Integer, Map<String, String>> eventBeans = new HashMap<Integer, Map<String, String>>();
  static Map<Integer, Map<String, String>> guestBeans = new HashMap<Integer, Map<String, String>>();

  /**
   * Populate list of event beans.
   * @throws SQLException
   */
  public static void addEventBeans(List<Integer> eids) throws SQLException
  {
    Map<Integer, Map<String, String>> beanMom = q.getRows("events", eids);

    eventBeans.putAll(beanMom);
  }

  /**
   * Populate list of guest beans.
   * @throws SQLException
   */
  public static void addGuestBeans(List<Integer> gids) throws SQLException
  {
    List<Map<String, String>> beanList = q.getRows("guests", gids);

    for (Map<String, String> bean : beanList)
    {
      guestBeans.add(bean);
    }
  }

  /**
   * Refresh all event beans.
   * 1. grab all the ids
   * 2. set the bean list to a fresh object
   * 3. call addEventBeans with list of ids
   */
  public static void refreshEventBeans()
  {
    List<Integer> eids = new ArrayList<Integer>();
  }

  /**
  public static Map<Integer, String> getEventTaglines(List<Integer> eids)
  {
    Map<String, String> taglines = new HashMap<String, String>();

    for (id : eids)
    {
      
    }

  }
   */

}
