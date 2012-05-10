package gps.tasks.task3663;

import java.util.ArrayList;
import java.util.Arrays;

public class Guest
{
  private String id;
  private String name;
  private String email;



  public Guest()
  {
    id = "-1";
    name = "";
    email = "";
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

    lol.add(new ArrayList<String>(Arrays.asList("id",     getId())));
    lol.add(new ArrayList<String>(Arrays.asList("name",   getName())));
    lol.add(new ArrayList<String>(Arrays.asList("email",  getEmail())));

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


  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }


  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

}
