package gps.tasks.task3663;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDb
{
  private Connection conn;
  private PreparedStatement ps;
  private ResultSet rs;


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
  public boolean keyExists(String table, String keyName, Integer keyValue) throws SQLException
  {
    ps = conn.prepareStatement(
      String.format("select %s from %s where %s = ?", keyName, table, keyName));
    ps.setInt(1, keyValue);

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
      rs = ps.executeQuery();
      rs.next();
      result.add(rs.getInt(1));
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
      rs = ps.executeQuery();
      rs.next();
      result.add(rs.getString("description"));
    }

    return result;
  }

  /**
   * @param table   table to get data from
   * @param ids     list of primary keys for table
   * @return        a list of maps, each representing a row
   */
  public List<Map<String, String>> getRows(String table, List<Integer> ids) throws SQLException
  {
    List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();

    // gets data from a single row
    ps = conn.prepareStatement(
      String.format("select * from %s where id = ?", table));

    // generate the maps for rowList
    for (Integer id : ids)
    {
      ResultSetMetaData rsmd;
      Map<String, String> row = new HashMap<String, String>();
      List<String> columnList = new ArrayList<String>();

      // set the primary key and execute the query
      ps.setInt(1, id);
      rs = ps.executeQuery();

      // generate list of columns
      rsmd = rs.getMetaData();
      for (Integer i = 1; i <= rsmd.getColumnCount(); i++)
      {
        columnList.add(rsmd.getColumnName(i));
      }

      // get the single row
      rs.next();

      // for each element in list of columns..
      // map the column value to the column name
      for (String column : columnList)
      {
        row.put(column, rs.getString(column));
      }

      // add row to list of rows
      rowList.add(row);
    }


    return rowList;
  }



//------------------------------------------------------------------------------
//  guest table queries
//------------------------------------------------------------------------------


}
