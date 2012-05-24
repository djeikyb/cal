package gps.tasks.task3663;

import java.util.HashMap;
import java.util.Map;

public class Event
{
  private String id;
  private String day;
  private String timeStart;
  private String timeEnd;
  private String kind;
  private String description;
  private String guests;



  public Event()
  {
    this("-1");
  }

  public Event(Integer eid)
  {
    this(eid.toString());
  }

  public Event(String eid)
  {
    id = eid;
    day = "";
    timeStart = "";
    timeEnd = "";
    kind = "";
    description = "";
    guests = "";
  }



//------------------------------------------------------------------------------
// bean getter
//------------------------------------------------------------------------------

  public Map<String, String> getBean()
  {
    Map<String, String> bean = new HashMap<String, String>();

    bean.put("id",           getId());
    bean.put("day",          getDay());
    bean.put("timeStart",    getTimeStart());
    bean.put("timeEnd",      getTimeEnd());
    bean.put("kind",         getKind());
    bean.put("description",  getDescription());
    bean.put("guests",       getGuests());

    return bean;
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

  public String getDay()
  {
    return day;
  }

  public void setDay(String day)
  {
    this.day = day;
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
