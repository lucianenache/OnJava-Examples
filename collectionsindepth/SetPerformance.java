// collectionsindepth/SetPerformance.java
// (c)2016 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://mindviewinc.com/Books/OnJava/ for more book information.
// Demonstrates performance differences in Sets
// Small to keep build testing short:
// {java SetPerformance 100 5000}
import java.util.*;

public class SetPerformance {
  static List<Test<Set<Integer>>> tests =
    new ArrayList<>();
  static {
    tests.add(new Test<Set<Integer>>("add") {
      @Override
      int test(Set<Integer> set, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          set.clear();
          for(int j = 0; j < size; j++)
            set.add(j);
        }
        return loops * size;
      }
    });
    tests.add(new Test<Set<Integer>>("contains") {
      @Override
      int test(Set<Integer> set, TestParam tp) {
        int loops = tp.loops;
        int span = tp.size * 2;
        for(int i = 0; i < loops; i++)
          for(int j = 0; j < span; j++)
            set.contains(j);
        return loops * span;
      }
    });
    tests.add(new Test<Set<Integer>>("iterate") {
      @Override
      int test(Set<Integer> set, TestParam tp) {
        int loops = tp.loops * 10;
        for(int i = 0; i < loops; i++) {
          Iterator<Integer> it = set.iterator();
          while(it.hasNext())
            it.next();
        }
        return loops * set.size();
      }
    });
  }
  public static void main(String[] args) {
    if(args.length > 0)
      Tester.defaultParams = TestParam.array(args);
    Tester.fieldWidth = 10;
    Tester.run(new TreeSet<>(), tests);
    Tester.run(new HashSet<>(), tests);
    Tester.run(new LinkedHashSet<>(), tests);
  }
}
/* Output:
------------- TreeSet -------------
 size       add  contains   iterate
  100       256       162        31
------------- HashSet -------------
 size       add  contains   iterate
  100        88        58        43
---------- LinkedHashSet ----------
 size       add  contains   iterate
  100       100        46        23
*/
