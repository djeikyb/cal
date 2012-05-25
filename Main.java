package gps.tasks.task3663;

import java.sql.SQLException;

import org.joda.time.LocalDate;

public class Main
{
  public static void main(String[] args)  throws SQLException
  {
    Tui ui = new Tui();

    /*
    // beginning
    ui.viewMonth(new LocalDate());
    
    // view existing day
    ui.viewDay(new LocalDate(2012,10,31));
    
    // view existing event
    ui.viewEvent(new LocalDate(2012,10,31), CalendarRegistry.getEvent(2));
    
    // edit existing event
    ui.editEvent(new LocalDate(2012,10,31), CalendarRegistry.getEvent(2));
    
    // new event
    ui.editEvent(new LocalDate(), new Event());
     */

    ui.viewMonth(new LocalDate());
  }
}
