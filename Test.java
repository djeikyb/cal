package gps.tasks.task3663;

import java.util.ArrayList;

public class Test
{
  
  public static void main(String[] args)
  {
    
    String[] s = String.format("").split(",");
    
    System.out.println("s: " + s + "|");
    
    ArrayList<String> foo = new ArrayList<String>();
    System.out.println("size of foo: " + foo.size());
    
    for (String o : s)
    {
      System.out.println(o + "|");
      
      if (!o.isEmpty()) foo.add(o);
      
      System.out.println(foo);
    }
    
    System.out.println("size of foo: " + foo.size());
    System.out.println("foo: " + foo);
    
    foo.add("huzzah");
    
    System.out.println("foo: " + foo);
    
    String bar = foo.toString().substring(
        1, foo.toString().length() - 1);
    
    System.out.println("bar: " + bar);
    
  }

}
