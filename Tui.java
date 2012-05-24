package gps.tasks.task3663;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.joda.time.LocalDate;

public class Tui
{
  private Scanner sc = new Scanner(System.in);
  private Calendar cal = new Calendar();



//------------------------------------------------------------------------------
//  ui helpers
//------------------------------------------------------------------------------

  /**
   *  Returns prompt.
   */
  public String prompt()
  {
    return("\n> ");
  }

  /**
   *  Returns menu footer.
   */
  public String menuFoot()
  {
    StringBuilder sb = new StringBuilder();

    sb.append("q) Quit\n");
    sb.append(prompt());

    return sb.toString();
  }

  /**
   *  Returns menu header.
   */
  public String menuHead()
  {
    StringBuilder sb = new StringBuilder();

    return sb.toString();
  }

  /**
   *  Clears the screen by printing newlines.
   */
  public void clearScreen()
  {
    Integer end = 1000;    // how many newlines to print
    StringBuilder sb = new StringBuilder(end);
    for (Integer i = 0; i < end; i++)
    {
      sb.append("\n");
    }
    System.out.println(sb);
  }

  /**
   *  Gets input from system input
   */
  public String getInput()
  {
    // pretend input didn't have any white space
    return sc.nextLine().replaceAll("\\s","");
  }

  /**
   *  Read "^[:digit:]*"
   *
   *  @param s  String that begins with digits
   *  @return   Integer created from first digit sequence
   */
  public Integer readInteger(String s)
  {
    StringBuilder digits = new StringBuilder();
    for (Character c : s.toCharArray())
    {
      if (Character.isDigit(c))
        digits.append(c);
      else
        break;
    }

    return Integer.valueOf(digits.toString());
  }

  /**
   *  Judge intent by first character. Helps evaluate user input.
   *
   *  @return first character, or 'z';
   */
  public Character getIntent(String input)
  {
    if (input.length() != 0)
      return input.charAt(0);
    else
      return 'z';
  }

  /**
   *  Returns formatted event details.
   */
  public String eventDetail(Event bean)
  {
    StringBuilder sb = new StringBuilder();

    sb.append("Description: "    + bean.getDescription() + "\n"); // TODO: wrap text
    sb.append("Start time: "     + bean.getTimeStart()   + "\n");
    sb.append("End time: "       + bean.getTimeEnd()     + "\n");
    sb.append("Kind: "           + bean.getKind()        + "\n");
    sb.append("Guests: "         + bean.getGuests()      + "\n");

    return sb.toString();
  }


//------------------------------------------------------------------------------
//  ui flow
//------------------------------------------------------------------------------

  /**
   *  View month.
   *
   *  Leads to day.
   *  TODO: lead to week.
   *
   *  @param now  View month of this date.
   */
  public void viewMonth(LocalDate now)
  {
    clearScreen();

    // set now to beginning of month
    now = new LocalDate(now.year().get(), now.monthOfYear().get(), 1);

    // when are we?
    System.out.println(cal.month(now) + "\n");

    // command
    System.out.println("#) View day #");
    System.out.println();

    // navigation
    System.out.println("p) Previous month");
    System.out.println("n) Next month");

    // footer
    System.out.print(menuFoot());

    // input
    String input = getInput();
    Character intent = getIntent(input);

    if (Character.isDigit(intent))  // if digit, intent is viewDay()
    {
      Integer n = readInteger(input);

      // Call viewDay() with $now incremented by the number we claim the user
      // intended. Handy, cause it avoids error handling by avoiding an error.
      // User *may* not expect it, though nothing is destroyed, and getting
      // back is relatively easy. Even better, might grow to be considered a
      // "power-user" feature!
      viewDay(new LocalDate(now.plusDays(n - 1)));
    }
    else  // else if != digit, intent is navigation
    {
      switch (intent)
      {
        case 'p': viewMonth(now.minusMonths(1));  break;
        case 'n': viewMonth(now.plusMonths(1));   break;
        case 'q': System.exit(0);                 break;
        default:  viewMonth(now);                 break;  // ultimate else
      }
    }

  }

  /**
   *  Views a day and its events.
   *
   *  Leads to view event. Indirectly leads to edit event. Also leads to
   *  [view week XOR view month]
   */
  public void viewDay(LocalDate now)
  {
    clearScreen();

    // when are we?
    System.out.println(now);

    // get the day's events, if any
    List<Integer> eids = new ArrayList<Integer>();
    try
    {
      eids = CalendarRegistry.getEventsFor(now);
    }
    catch (SQLException e)
    {
      System.err.println("database error");
    }

    // if no events, say so, otherwise make options
    Map<Integer, Integer> options = new HashMap<Integer, Integer>();
    if (eids.isEmpty())
    {
      System.out.println("No events this day.");
    }
    else
    {
      // get taglines
      Map<Integer, String> taglines = new HashMap<Integer, String>();
      try
      {
        taglines = CalendarRegistry.getEventTaglines(eids);
      }
      catch (SQLException e)
      {
        System.err.println("database error");
      }

      // make options
      System.out.printf("\nToday's events:\n\n");
      Integer counter = 0;
      for (Integer id : eids)
      {
        counter++;
        options.put(counter, id);
        System.out.printf("%s. %s\n", counter, taglines.get(id));
      }
      System.out.println();
    }

    // command
    System.out.println("#) View event #");
    System.out.println("a) Add new event");
    System.out.println();

    // navigation
    System.out.println("b) Back to month");
    System.out.println("p) Previous day");
    System.out.println("n) Next day");

    // footer
    System.out.print(menuFoot());

    // input
    String input = getInput();
    Character intent = getIntent(input);

    if (Character.isDigit(intent))  // if digit, intent is viewEvent
    {
      Integer n = readInteger(input);

      // ignore command if the option doesn't exist
      if (options.containsKey(n))
      {
        try
        {
          viewEvent(now, CalendarRegistry.getEvent(options.get(n)));
        }
        catch (SQLException e)
        {
          System.err.println("Database error");
        }
      }
      else viewDay(now);
    }
    else
    {
      switch (intent)
      {
        case 'a': editEvent(now, new Event());              break;

        // navigation
        case 'b': viewMonth(now);             break;
        case 'p': viewDay(now.minusDays(1));  break;
        case 'n': viewDay(now.plusDays(1));   break;
        case 'q': System.exit(0);             break;
        default:  viewDay(now);               break;  // ultimate else
      }
    }

  }

  /**
   *  Views an event.
   *
   *  Leads to edit event, attach guests, and view day.
   */
  public void viewEvent(LocalDate now, Event bean)
  {
    clearScreen();

    // when are we?
    System.out.println(now + "\n");


    // print event details
    System.out.println(eventDetail(bean) + "\n");


    // command
    //System.out.println("d) Delete this event");   // TODO
    System.out.println("e) Edit this event");
    System.out.println("a) Attach guests");
    System.out.println();

    // navigation
    System.out.println("b) Back to day");
    //System.out.println("p) Previous event");
    //System.out.println("n) Next event");

    // footer
    System.out.print(menuFoot());

    // input
    String input = getInput();
    Character intent = getIntent(input);

    switch (intent)
    {
    /*
      case 'd':
        try
        {
          CalendarRegistry.deleteEvent(bean.getId());
        }
        catch (SQLException e)
        {
          System.err.println("Database error");
        }
        finally
        {
          viewDay(now);
        }
        break;
     */
      case 'e': editEvent(now, bean);      break;
      case 'a': attachGuest(now, bean);   break;

      case 'b': viewDay(now);
    }
  }

  /**
   *  Edits an event. Pushes to view event.
   */
  public void editEvent(LocalDate now, Event bean)
  {
    String input;

    System.out.println(eventDetail(bean) + "\n");


// {{{ auto generated with editevent.py
      System.out.println("Description:");
      System.out.print(prompt());
      // TODO: make while..try..catch less fugly
      while (true)
      {
        try
        {
          input = sc.nextLine();
          if (!input.isEmpty())   bean.setDescription(input);
          break;
        }
        catch (IllegalArgumentException e)
        {
          System.out.println(e.getMessage());
          System.out.print(prompt());
        }
      }

      System.out.println("Start time:");
      System.out.print(prompt());
      // TODO: make while..try..catch less fugly
      while (true)
      {
        try
        {
          input = sc.nextLine();
          if (!input.isEmpty())   bean.setTimeStart(input);
          break;
        }
        catch (IllegalArgumentException e)
        {
          System.out.println(e.getMessage());
          System.out.print(prompt());
        }
      }

      System.out.println("End time:");
      System.out.print(prompt());
      // TODO: make while..try..catch less fugly
      while (true)
      {
        try
        {
          input = sc.nextLine();
          if (!input.isEmpty())   bean.setTimeEnd(input);
          break;
        }
        catch (IllegalArgumentException e)
        {
          System.out.println(e.getMessage());
          System.out.print(prompt());
        }
      }

      System.out.println("Kind:");
      System.out.print(prompt());
      // TODO: make while..try..catch less fugly
      while (true)
      {
        try
        {
          input = sc.nextLine();
          if (!input.isEmpty())   bean.setKind(input);
          break;
        }
        catch (IllegalArgumentException e)
        {
          System.out.println(e.getMessage());
          System.out.print(prompt());
        }
      }

// }}} END auto generated with editevent.py


    // save to database
    try
    {
      CalendarRegistry.save("event", bean.getMap());
    }
    catch (SQLException e)
    {
      System.err.println("Database error");
    }

    // push back to day view
    viewEvent(now, bean);

  }

  /**
   *  Attaches a guest to an event. Pushes to view event.
   *  TODO allow scrolling more or less style
   */
  public void attachGuest(LocalDate now, Event bean)
  {
    // get list of guests, if any
    List<Integer> gids = new ArrayList<Integer>();
    try
    {
      gids = CalendarRegistry.getGuests();
    }
    catch (SQLException e)
    {
      System.err.println("Database error");
    }

    // if no guests, say so, otherwise generate options
    Map<Integer, Integer> options = new HashMap<Integer, Integer>();
    if (gids.isEmpty())
    {
      System.out.println("No guests in database.");
    }
    else
    {
      // get names
      Map<Integer, String> names = new HashMap<Integer, String>();
      try
      {
        names = CalendarRegistry.getGuestNames(gids);
      }
      catch (SQLException e)
      {
        System.err.println("Database error");
      }

      // generate options
      Integer counter = 0;
      for (Integer id : gids)
      {
        counter++;
        options.put(counter, id);
        System.out.printf("%s. %s\n", counter, names.get(id));
      }
      System.out.println();
    }

    // command
    System.out.println("#) Select guest #");

    // navigation
    System.out.println("b) Back to event");

    // input
    String input = getInput();
    Character intent = getIntent(input);

    if (Character.isDigit(intent))  // if digit, intent is adding a guest
    {
      Integer n = readInteger(input);

      // ignore command if option doesn't exist
      if (options.containsKey(n))
      {
        // csv to list
        String[] temp = bean.getGuests().split(",");

System.out.println("temp: " + temp); // debug
System.out.println("split bean: " + bean.getGuests().split(","));

        ArrayList<String> guest_list = new ArrayList<String>();

System.out.println("new guest_list: " + guest_list); // debug

        for (String s : temp)
        {
          guest_list.add(s);
        }

System.out.println("after adding existing guest_list: " + guest_list); // debug


        // if not in list, add the selected guest
        if (!guest_list.contains(n.toString()))
        {

System.out.println("options.get(n): " + options.get(n));  // debug

          guest_list.add(options.get(n).toString());

System.out.println("list after adding new: " + guest_list); // debug

          // hack to convert back to simple csv
          bean.setGuests(guest_list.toString().substring(
              1, guest_list.toString().length() - 1));

System.out.println("bean guests: " + bean.getGuests());   // debug
        }
      }
    }

    //viewEvent(now, bean);   // no matter what, go here
  }
}
