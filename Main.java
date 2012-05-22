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

    // TODO figure out how to auto center
    String month = dt.monthOfYear().getAsShortText();
    Integer year = dt.year().get();
    String title = String.format("%s %s\n", month, year);
    cal.append(title);

    String days = " Mon   Tue   Wed   Thu   Fri   Sat   Sun";
    cal.append(days);


    LocalDate thisMonth = new LocalDate(dt.year().get(), dt.monthOfYear().get(), 1);


    System.out.println(dt.getDayOfMonth());
    System.out.println(thisMonth);



    StringBuilder week = new StringBuilder("                                        ");  // forty spaces
    /*  for (day : month)
     *    switch (day.ofWeek)
     *      case (0):
     *        if (eventful)
     *          week.replace(0, 3, String.format("(%-3s)", day));
     *        else
     *          week.replace(1, 2, String.format("(%-2s)", day));
     *        break;
     *      case (1):
     *        if (eventful)
     *          week.replace(6, 9, String.format("(%-3s)", day));
     *        else
     *          week.replace(7, 8, String.format("(%-2s)", day));
     *        break;
     *      case (2):
     *        if (eventful)
     *          week.replace(12, 15, String.format("(%-3s)", day));
     *        else
     *          week.replace(13, 14, String.format("(%-2s)", day));
     *        break;
     *      case (3):
     *        if (eventful)
     *          week.replace(18, 21, String.format("(%-3s)", day));
     *        else
     *          week.replace(19, 20, String.format("(%-2s)", day));
     *        break;
     *      case (4):
     *        if (eventful)
     *          week.replace(24, 27, String.format("(%-3s)", day));
     *        else
     *          week.replace(25, 26, String.format("(%-2s)", day));
     *        break;
     *      case (5):
     *        if (eventful)
     *          week.replace(30, 33, String.format("(%-3s)", day));
     *        else
     *          week.replace(31, 32, String.format("(%-2s)", day));
     *        break;
     *      case (6):
     *        if (eventful)
     *          week.replace(36, 39, String.format("(%-3s)", day));
     *        else
     *          week.replace(37, 38, String.format("(%-2s)", day));
     *        cal.append(week);
     *        break;
     *      default:
     *        throw Exception("foo is not a day");
     *        break;
     */



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
