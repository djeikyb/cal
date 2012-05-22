package gps.tasks.task3663;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModifyDb
{
  private Connection conn;
  private PreparedStatement ps;
  private QueryDb qry;


  /**
   *  Don't use this constructor. It creates a new database connection using
   *  GimmeConn, and never closes it. Might be useful when unit testing.
   */
  ModifyDb()
  {
    this(new GimmeConn().conn);
  }

  /**
   *  @param conn   Methods will use this database connection.
   */
  ModifyDb(Connection conn)
  {
    this.conn = conn;
    qry = new QueryDb(conn);
  }



//------------------------------------------------------------------------------
//  actions
//------------------------------------------------------------------------------

  /**
   * Add a new row to a table
   * @param table
   * @param keyName   primary key name e
   * @param keyValue  primary key valueue
   * @throws SQLException
   */
  public void addRow(String table, String keyName, Integer keyValue) throws SQLException//{{{
  {
    if (keyValue < 0)
    {
      keyValue = 0; // mysql auto_incr starts at 1, using 0 forces auto_incr
    }

    if (qry.keyExists(table, keyName, keyValue))
    {
      throw new SQLException(
        String.format("Primary key (%s) already exists in table (%s).", keyValue, table));
    }

    ps = conn.prepareStatement(
      String.format("insert into %s ", table) +
      String.format("set %s = ?", keyName));
    ps.setInt(1, keyValue);

    ps.executeUpdate();
  }//}}}


  /**
   *  Add data (from a bean) to a row in a table. The row will be created if it
   *  doesn't exist.
   *
   *  @param table
   *  @param bean_in  map of column/value pairs
   *  @throws SQLException
   */
  public void modRow(String table, Map<String, String> bean) throws SQLException//{{{
  {
    Map<String, String> mybean = new HashMap<String, String>();
    mybean.putAll(bean);

    // get primary key, then remove it from map
    String pkName = "id";
    Integer pkValue = Integer.valueOf(mybean.get("id"));
    mybean.remove("id");

    // if primary key isn't in table, add it
    if (!qry.keyExists(table, pkName, pkValue))
    {
      addRow(table, pkName, pkValue);
      pkValue = qry.maxId(table);
    }

    // loop through map, updating column/value pairs
    for (String key : mybean.keySet())
    {
      ps = conn.prepareStatement(
        String.format("update %s set %s = ? ", table, key) +
        String.format("where %s = ?", pkName));
      ps.setString(1, mybean.get(key));
      ps.setInt(2, pkValue);

      ps.executeUpdate();
    }
  }//}}}


}
