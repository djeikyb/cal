#!/usr/bin/env python2

longs = ["Description", "Start time", "End time", "Kind"]
meths = ["Description", "TimeStart", "TimeEnd", "Kind"]

i = 0
print "// {{{ auto generated with editevent.py"
for item in longs:
  print """      System.out.println("%s:");
      System.out.print(prompt());
      // TODO: make while..try..catch less fugly
      while (true)
      {
        try
        {
          input = sc.nextLine();
          if (!input.isEmpty())   bean.set%s(input);
          break;
        }
        catch (IllegalArgumentException e)
        {
          System.out.println(e.getMessage());
          System.out.print(prompt());
        }
      }
""" % (longs[i], meths[i])
  i += 1
print "// }}} END auto generated with editevent.py"
