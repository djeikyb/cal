package gps.tasks.task3663;

import org.joda.time.LocalDate;

public class Main
{
  public static void main(String[] args)
  {
    Tui ui = new Tui();

    /*
    ui.viewMonth(new LocalDate());  // primes tui with today's date
    ui.viewDay(new LocalDate(2012,10,31));
    ui.viewEvent(new LocalDate(2012,10,31), new Event(2));
    ui.editEvent(new Event(2));
     */
    ui.viewEvent(new LocalDate(2012,10,31), new Event(2));

  }
}
