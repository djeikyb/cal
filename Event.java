package gps.tasks.task3663;

import java.util.ArrayList;
import java.util.Arrays;

public class Event
{
  private String id;
  private String date;
  private String timeStart;
  private String timeEnd;
  private String kind;
  private String description;
  private String guests;



  Event()
  {
    id = "-1";
    date = "0000-00-00";
    setTimeStart("00:00");
    setTimeEnd("23:59");
    kind = "";
    description = "";
    guests = "";
  }



//------------------------------------------------------------------------------
// lol getter
//------------------------------------------------------------------------------

  /**
   * get all bean attributes as a list-of-list
   */
  public ArrayList<ArrayList<String>> getLol()
  {
    ArrayList<ArrayList<String>> lol = new ArrayList<ArrayList<String>>();

    lol.add(new ArrayList<String>(Arrays.asList("id",           getId())));
    lol.add(new ArrayList<String>(Arrays.asList("date",         getDate())));
    lol.add(new ArrayList<String>(Arrays.asList("timeStart",    getTimeStart())));
    lol.add(new ArrayList<String>(Arrays.asList("timeEnd",      getTimeEnd())));
    lol.add(new ArrayList<String>(Arrays.asList("kind",         getKind())));
    lol.add(new ArrayList<String>(Arrays.asList("description",  getDescription())));
    lol.add(new ArrayList<String>(Arrays.asList("guests",       getGuests())));

    return lol;
  }



//------------------------------------------------------------------------------
// getters and setters
//------------------------------------------------------------------------------

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }


  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }


  public String getTimeStart()
  {
    return timeStart;
  }

  public void setTimeStart(String timeStart)
  {
    this.timeStart = timeStart;
  }


  public String getTimeEnd()
  {
    return timeEnd;
  }

  public void setTimeEnd(String timeEnd)
  {
    this.timeEnd = timeEnd;
  }


  public String getKind()
  {
    return kind;
  }

  public void setKind(String kind)
  {
    this.kind = kind;
  }


  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }


  public String getGuests()
  {
    return guests;
  }

  public void setGuests(String guests)
  {
    this.guests = guests;
  }

}
