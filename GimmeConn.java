package gps.tasks.task3663;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GimmeConn
{
  private final String url = "jdbc:mysql://localhost/calendar";
  private final String user = "sa";

  public Connection conn;

  GimmeConn()
  {
    try
    {
      conn = DriverManager.getConnection(url, user, "");
    }
    catch (SQLException e)
    {
      System.err.println("GimmeCon failed to open a connection.");
      e.printStackTrace();
    }
  }

  public void close()
  {
    try
    {
      conn.close();
    }
    catch (SQLException e)
    {
      System.err.println("GimmeCon failed to close the connection.");
    }
  }


}
