package gps.tasks.task3663;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class Main
{
  /**
   *  Prints a month.
   *
   *  @param month  january is month 1
   */
  public static void printMonth(DateTime dt)
  {
    StringBuilder cal = new StringBuilder("");

    String month = dt.monthOfYear().getAsShortText();
    Integer year = dt.year().get();

    // TODO figure out how to auto center
    String title = String.format("%s %s\n", month, year);
    String days = " Mon   Tue   Wed   Thu   Fri   Sat   Sun";



    cal.append(title);
    cal.append(days);

    System.out.println(cal);
  }

  /**
   * Prints the current month
   */
  public static void printMonth()
  {
    printMonth(new DateTime());
  }

  public static void main(String[] args)
  {
    printMonth();
  }
}
