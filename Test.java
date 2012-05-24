package gps.tasks.task3663;

import java.util.ArrayList;

public class Test
{
  
  public static void main(String[] args)
  {
    ArrayList<String> foo = new ArrayList<String>();
    
    System.out.println("foo: " + foo);
    
    foo.add("huzzah");
    
    System.out.println("foo: " + foo);
    
    String bar = foo.toString().substring(
        1, foo.toString().length() - 1);
    
    System.out.println("bar: " + bar);
  }

}
