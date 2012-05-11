package gps.tasks.task3663;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChangeDb
{
  private Connection conn;
  private PreparedStatement ps;
  private QueryDb qry;


  /**
   *  Don't use this constructor. It creates a new database connection using
   *  GimmeConn, and never closes it. Might be useful when unit testing.
   */
  ChangeDb()
  {
    this(new GimmeConn().conn);
  }

  /**
   *  @param conn   Methods will use this database connection.
   */
  ChangeDb(Connection conn)
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
  public void addRow(String table, String keyName, String keyValue) throws SQLException
  {
    if (qry.keyExists(table, keyName, keyValue))
    {
      throw new SQLException(
        String.format("Primary key (%s) already exists in table (%s).", keyValue, table));
    }

    ps = conn.prepareStatement(
      String.format("insert into %s ", table) +
      String.format("set %s = ?", keyName));
    ps.setString(1, keyValue);

    ps.executeUpdate();
  }


  /**
   * Add data to a row in a table. The row will be created if it doesn't exist.
   * @param table
   * @param lolcols   list of column/value pairs
   * @throws SQLException
   */
  public void modRow(String table, ArrayList<ArrayList<String>> lolcols) throws SQLException
  {
    // get primary key, then remove it from list
    String pkName = lolcols.get(0).get(0);
    String pkValue = lolcols.get(0).get(1);
    lolcols.remove(0);

    // if primary key isn't in table, add it
    if (!qry.keyExists(table, pkName, pkValue))
    {
      addRow(table, pkName, pkValue);
    }

    // loop through list, updating column/value pairs
    for (ArrayList<String> list : lolcols)
    {
      String colName = list.get(0);
      String colVal = list.get(1);

      ps = conn.prepareStatement(
        String.format("update %s set %s = ?", table, colName) +
        String.format("where %s = ?", pkName));
      ps.setString(1, colVal);
      ps.setString(2, pkValue);

      ps.executeUpdate();
    }
  }


}
