package gps.tasks.task3663;

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
   *  Returns menu footer.
   */
  public String menuFoot()
  {
    StringBuilder sb = new StringBuilder();

    sb.append("q) Enter 'q' to quit.\n");
    sb.append("\n> ");

    return sb.toString();
  }

  /**
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
   *  @return   Integer created from first sequence of digits
   */
  public Integer readInteger(String s)
  {
    StringBuilder digits = new StringBuilder();
    for (Character c : s.toCharArray())
    {
      if (Character.isDigit(c))
      {
        digits.append(c);
      }
      else break;
    }

    return Integer.valueOf(digits.toString());
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

    System.out.println(cal.month(now) + "\n");


    // navigation
    System.out.println("p) Previous month.");
    System.out.println("n) Next month.");

    // command
    System.out.println("#) View day #.");

    // footer
    System.out.print(menuFoot());

    // input
    String input = getInput();

    // judge intent by first character
    Character intent;
    if (input.length() != 0)
    {
      intent = input.charAt(0);
    }
    else intent = 'z';

    if (Character.isDigit(intent))  // if first char == digit, intent is command
    {
      Integer n = readInteger(input);

      // Call viewDay() with $now incremented by the number we claim the user
      // intended. Handy, cause it avoids error handling by avoiding an error.
      // User *may* not expect it, though nothing is destroyed, and getting
      // back is relatively easy.
      viewDay(new LocalDate(now.plusDays(n - 1)));
    }
    else  // else if first char != digit, intent is navigation
    {
      switch (intent)
      {
        case 'p': viewMonth(now.minusMonths(1));  break;
        case 'n': viewMonth(now.plusMonths(1));   break;
        default:  viewMonth(now);                 break;  // ultimate else
      }
    }

  }

  /**
   *  Views a day and its events. Leads to view event. Indirectly leads to edit
   *  event. Also leads to [view week XOR view month]
   */
  public void viewDay(LocalDate now)
  {
    clearScreen();

    // navigation
    System.out.println("b) Back to [month|week].");
    System.out.println("p) Previous day.");
    System.out.println("n) Next day.");

    // command
    System.out.println("#) View event #.");
    System.out.println("a) Add new event.");

    // footer
    System.out.println(menuFoot());

    // input
    String input = getInput();
  }

  /**
   *  Views an event. Leads to edit event, attach guests, and view day.
   */
  public void viewEvent()
  {
    // navigation
    System.out.println("b) Back to day.");
    System.out.println("h) Previous event.");
    System.out.println("l) Next event.");

    // command
    System.out.println("d) Delete this event.");
    System.out.println("e) Edit this event.");
    System.out.println("a) Attach guests.");

    // footer
    System.out.println(menuFoot());
  }

  /**
   *  Edits an event. Pushes to view event.
   */
  public void editEvent()
  {
    /*  should show old info while prompting for new, if any exists
     *  should keep old info if blank line
     *  don't need:
     *    - id, because it's auto generated
     *    - day, because the only path to this screen is a day
     *    - guests, because it's handled at view event screen
     */
    System.out.println("Description:");
    System.out.println("Start time:");
    System.out.println("End time:");
    System.out.println("Kind:");
  }

  /**
   *  Attaches a guest to an event. Leads or pushes to view event.
   *  TODO allow scrolling more or less style
   */
  public void attachGuest()
  {
    // navigation
    System.out.println("b) Back to event.");

    // command
    System.out.println("#) Select guest #.");
  }
}
