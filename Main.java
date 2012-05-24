package gps.tasks.task3663;

import java.sql.SQLException;

import org.joda.time.LocalDate;

public class Main
{
  public static void main(String[] args)  throws SQLException
  {
    Tui ui = new Tui();

    /*
    ui.viewMonth(new LocalDate());  // primes tui with today's date
    ui.viewDay(new LocalDate(2012,10,31));
    ui.viewEvent(new LocalDate(2012,10,31), CalendarRegistry.getEvent(2));
    ui.editEvent(new LocalDate(2012,10,31), CalendarRegistry.getEvent(2));
     */
    ui.viewEvent(new LocalDate(2012,10,31), CalendarRegistry.getEvent(2));

  }
}
