package gps.tasks.task3663;

import org.joda.time.LocalDate;

public class Main
{
  public static void main(String[] args)
  {
    Tui ui = new Tui();

    ui.viewMonth(new LocalDate());  // primes tui with today's date

  }
}
