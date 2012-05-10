package gps.tasks.task3663;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryDb
{
  private Connection conn;
  private PreparedStatement ps;


  /**
   *  Don't use this constructor. It creates a new database connection using
   *  GimmeConn, and never closes it. Might be useful when unit testing.
   */
  QueryDb()
  {
    this.conn = new GimmeConn().conn;
  }

  /**
   *  @param conn   Methods will use this database connection.
   */
  QueryDb(Connection conn)
  {
    this.conn = conn;
  }



//------------------------------------------------------------------------------
//  actions
//------------------------------------------------------------------------------

  /**
   * Check if a primary key exists in a table.
   * @param table
   * @param keyName   primary key name
   * @param keyValue  primary key value
   * @return
   * @throws SQLException
   */
  public boolean keyExists(String table, String keyName, String keyValue) throws SQLException
  {
    ps = conn.prepareStatement(
      String.format("select %s from %s where %s = ?", keyName, table, keyName));
    ps.setString(1, keyName);

    return ps.executeQuery().next();
  }


}
