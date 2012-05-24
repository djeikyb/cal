#!/usr/bin/env python2

low = ["event", "guest"]
cap = ["Event", "Guest"]
ids  = ["e", "g"]

i = 0
print "// {{{ auto generated with genCacheHelpers.py"
for item in low:
  print """  /**
   * Populate map of %s beans from db.
   *
   *  @param ids   %s ids to fetch from database
   *  @throws SQLException
   */
  public static void fetch%ss(List<Integer> ids) throws SQLException//{{{
  {
    Map<Integer, Map<String, String>> beanMom = q.getRows("%ss", ids);

    %sBeans.putAll(beanMom);
  }//}}}
""" % (low[i], low[i], cap[i], low[i], low[i])

  print """  /**
   *  Returns a list of "unregistered" %s ids.
   *
   *  @param ids  List of %s ids to check
   *  @throws SQLException
   */
  public static List<Integer> unreg%ss(List<Integer> ids) throws SQLException//{{{
  {
    List<Integer> missingIds = new ArrayList<Integer>();

    //  if not in beanlist store in list
    for (Integer id : ids)
    {
      if (!%sBeans.containsKey(id))
      {
        missingIds.add(id);
      }
    }

    return missingIds;
  }//}}}
""" % (low[i], low[i], cap[i], low[i])

  print """  /**
   *  Updates list of %s beans with any it doesn't have.
   *
   *  @param ids   list of %s ids
   *  @throws SQLException
   */
  public static void update%sBeans(List<Integer> ids) throws SQLException//{{{
  {
    // find any "unregistered" ids
    List<Integer> missing = unreg%ss(ids);

    //  if there were any missing, hit the db and add them
    if (!missing.isEmpty())
    {
      fetch%ss(missing);
    }
  }//}}}
""" % (low[i], low[i], cap[i], cap[i], cap[i])
  i += 1

print "// }}} END auto generated with genCacheHelpers.py"
