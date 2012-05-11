package gps.tasks.task3663;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    this(new GimmeConn().conn);
  }

  /**
   *  @param conn   Methods will use this database connection.
   */
  QueryDb(Connection conn)
  {
    this.conn = conn;
  }


//------------------------------------------------------------------------------
//  supporting actions
//------------------------------------------------------------------------------

  /**
   * Converts a column of a ResultSet to a list of Integers
   * @param rs    the ResultSet to parse
   * @param col   the column to listify
   * @return      ArrayList
   * @throws SQLException
   */
  private List<Integer> resultInt(ResultSet rs, String col) throws SQLException
  {
    List<Integer> r = new ArrayList<Integer>();

    while (rs.next())
    {
      r.add(rs.getInt(col));
    }

    return r;
  }

  /**
   * Converts a column of a ResultSet to a list of Strings
   * @param rs    the ResultSet to parse
   * @param col   the column to listify
   * @return      ArrayList
   * @throws SQLException
   */
  private List<String> resultStr(ResultSet rs, String col) throws SQLException
  {
    List<String> r = new ArrayList<String>();

    while (rs.next())
    {
      r.add(rs.getString(col));
    }

    return r;
  }


//------------------------------------------------------------------------------
//  generic queries
//------------------------------------------------------------------------------

  /**
   *  Check if a primary key exists in a table.
   *  @param table
   *  @param keyName   primary key name
   *  @param keyValue  primary key value
   *  @return
   *  @throws SQLException
   */
  public boolean keyExists(String table, String keyName, String keyValue) throws SQLException
  {
    ps = conn.prepareStatement(
      String.format("select %s from %s where %s = ?", keyName, table, keyName));
    ps.setString(1, keyName);

    return ps.executeQuery().next();
  }



//------------------------------------------------------------------------------
//  event table queries
//------------------------------------------------------------------------------

  public List<Integer> getEvents_this(String day) throws SQLException
  {
    ps = conn.prepareStatement(
      "select id from events where day = ?");
    ps.setString(1, day);

    return resultInt(ps.executeQuery(), "id");
  }

  public List<Integer> getEvents_these(List<String> days) throws SQLException
  {
    List<Integer> result = new ArrayList<Integer>();

    ps = conn.prepareStatement(
      "select id from events where day = ?");

    for (String date : days)
    {
      ps.setString(1, date);
      result.add(ps.executeQuery().getInt("id"));
    }

    return result;
  }

  public List<String> getDescriptions(List<Integer> eids) throws SQLException
  {
    // TODO: try without = new, see if can avoid imposing a list type
    List<String> result = new ArrayList<String>();

    ps = conn.prepareStatement(
      "select description from events where id = ?");

    for (Integer id : eids)
    {
      ps.setInt(1, id);
      result.add(ps.executeQuery().getString("description"));
    }

    return result;
  }



//------------------------------------------------------------------------------
//  guest table queries
//------------------------------------------------------------------------------



}
