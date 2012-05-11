package gps.tasks.task3663;

import java.io.FileOutputStream;
import java.sql.Connection;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.*;

import org.dbunit.dataset.IDataSet;

public class ExportDataset
{
  public static void main(String[] args) throws Exception
  {
    // dbunit db connection
    Class driverClass = Class.forName("com.mysql.jdbc.Driver");
    IDatabaseConnection connection = new DatabaseConnection(new GimmeConn().conn);
    
    // export full database
    IDataSet fullDataSet = connection.createDataSet();
    FlatXmlDataSet.write(fullDataSet, new FileOutputStream("src/gps/tasks/task3663/dataset.xml"));
  }
}
