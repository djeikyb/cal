package gps.tasks.task3663;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

/**
 *  Provides methods to get a formatted calendar. Currently only support a month
 *  view.
 */
public class Calendar
{

  /**
   *  Writes parenthesis around the day of month.
   *
   *  @param sb   string builder to modify
   *  @param o    start writing this many chars in
   */
  public StringBuilder wrapParen(StringBuilder sb, Integer o)
  {
    sb.setCharAt(o+1, ')');

    if (sb.charAt(o-1) == ' ')  sb.setCharAt(o-1, '(');
    else                        sb.setCharAt(o-2, '(');

    return sb;
  }


  /**
   *  Writes a day-of-month number to the appropriate spot of a 40-char string
   *  builder. Then passes it to wrapParen if there is an event on the date.
   *
   *  @param l    list of events for a time period
   *  @param d    the current date to be written
   *  @param sb   string builder to modify
   *  @param o    start writing this many chars in
   */
  public StringBuilder writeDay(List<LocalDate> l, LocalDate d, StringBuilder sb, Integer o)
  {
    sb.replace(o-1, o, String.format("%2s", d.dayOfMonth().get()));
    if (l.contains(d))   wrapParen(sb, o);

    return sb;
  }


  /**
   *  Returns a string holding a formatted calendar month.
   *  overloaded
   *
   *  @param ld  a joda-time LocalDate, january is month 1
   */
  public String month(LocalDate ld)
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
      switch (d.dayOfWeek().get())
      {
        case 1:
          writeDay(events, d, week, 2);
          break;
        case 2:
          writeDay(events, d, week, 8);
          break;
        case 3:
          writeDay(events, d, week, 14);
          break;
        case 4:
          writeDay(events, d, week, 20);
          break;
        case 5:
          writeDay(events, d, week, 26);
          break;
        case 6:
          writeDay(events, d, week, 32);
          break;
        default:
          writeDay(events, d, week, 38);
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
   * overloaded
   */
  public String month()
  {
    return month(new LocalDate());
  }

}
