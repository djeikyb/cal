package gps.tasks.task3663;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

public class Main
{
  /**
   */
  public static StringBuilder wrapParen(StringBuilder sb, Integer o)
  {
    sb.setCharAt(o+1, ')');
    if (sb.charAt(o-1) == ' ')  sb.setCharAt(o-1, '(');
    else                      sb.setCharAt(o-2, '(');

    return sb;
  }

  /**
   */
  public static StringBuilder writeDay(List<LocalDate> l, LocalDate d, StringBuilder sb, Integer o)
  {
    sb.replace(o-1, o, String.format("%2s", d.dayOfMonth().get()));
    if (l.contains(d))   wrapParen(sb, o);

    return sb;
  }

  /**
   *  Returns a string holding a formatted calendar month.
   *
   *  @param ld  a joda-time LocalDate, january is month 1
   */
  public static String monthCal(LocalDate ld)
  {
    // holds the in progress calendar

    StringBuilder cal = new StringBuilder("");


    // title (month, year)
    // TODO figure out how to auto center

    String month = ld.monthOfYear().getAsShortText();
    Integer year = ld.year().get();
    String title = String.format("%s %s\n", month, year);
    cal.append(title);


    // names of days

    String days = " Mon   Tue   Wed   Thu   Fri   Sat   Sun";
    cal.append(days);


    // numbers of days

    LocalDate startMonth = new LocalDate(ld.year().get(), ld.monthOfYear().get(), 1);
    LocalDate endMonth = startMonth.plusMonths(1).minusDays(1);

    List<LocalDate> events;
    try
    {
      events = CalendarRegistry.eventDatesFor(startMonth, endMonth);
    }
    catch (SQLException e)
    {
      System.err.println("Something went wrong with the database. Not displaying this month's events");
      events = new ArrayList<LocalDate>();
    }

    StringBuilder week = new StringBuilder("                                        ");  // forty spaces

    List<LocalDate> daysOfMonth = CalendarRegistry.getDatesInRange(startMonth, endMonth);
    for (LocalDate d : daysOfMonth)
    {
      Integer o;  // offset
      switch (d.dayOfWeek().get())
      {
        case 1:
          o = 2;
          writeDay(events, d, week, o);
          break;
        case 2:
          o = 8;
          writeDay(events, d, week, o);
          break;
        case 3:
          o = 14;
          writeDay(events, d, week, o);
          break;
        case 4:
          o = 20;
          writeDay(events, d, week, o);
          break;
        case 5:
          o = 26;
          writeDay(events, d, week, o);
          break;
        case 6:
          o = 32;
          writeDay(events, d, week, o);
          break;
        default:
          o = 38;
          writeDay(events, d, week, o);
          cal.append("\n" + week);
          week = new StringBuilder("                                        ");  // forty spaces
          break;
      }
    }

    // If the last week doesn't end on a sunday, default won't be hit, and it
    // won't be shlepped to the calendar string. Can't just append the week
    // one last time because for months like 2012-09, the last day is a sunday,
    // so you end up with an extra newline.
    //
    // If there are any dangling days, position two will definitely not be
    // empty.
    if (week.charAt(2) != ' ')
    {
      cal.append("\n" + week);
    }


    return cal.toString();
  }

  /**
   * Prints the current month
   */
  public static String monthCal()
  {
    return monthCal(new LocalDate());
  }

  public static void main(String[] args)
  {
    System.out.println(monthCal());
    System.out.println(monthCal(new LocalDate(2012, 9, 1)));
  }
}
