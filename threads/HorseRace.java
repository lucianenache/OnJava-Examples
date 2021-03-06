// threads/HorseRace.java
// (c)2016 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://mindviewinc.com/Books/OnJava/ for more book information.
// Using CyclicBarriers
import java.util.concurrent.*;
import java.util.*;

class Horse implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private int strides = 0;
  private static SplittableRandom rand = new SplittableRandom(47);
  private static CyclicBarrier barrier;
  public Horse(CyclicBarrier b) { barrier = b; }
  public synchronized int getStrides() { return strides; }
  @Override
  public void run() {
    try {
      while(!Thread.interrupted()) {
        synchronized(this) {
          strides += rand.nextInt(3); // Yeilds 0, 1 or 2
        }
        barrier.await();
      }
    } catch(InterruptedException e) {
      // A legitimate way to exit
    } catch(BrokenBarrierException e) {
      // This one we want to know about
      throw new RuntimeException(e);
    }
  }
  @Override
  public String toString() { return "Horse " + id + " "; }
  public String tracks() {
    StringBuilder s = new StringBuilder();
    for(int i = 0; i < getStrides(); i++)
      s.append("*");
    s.append(id);
    return s.toString();
  }
}

public class HorseRace {
  static final int FINISH_LINE = 75;
  private List<Horse> horses = new ArrayList<>();
  private ExecutorService exec =
    Executors.newCachedThreadPool();
  private CyclicBarrier barrier;
  public HorseRace(int nHorses, final int pause) {
    barrier = new CyclicBarrier(nHorses, () -> {
      StringBuilder s = new StringBuilder();
      for(int i = 0; i < FINISH_LINE; i++)
        s.append("="); // The fence on the racetrack
      System.out.println(s);
      for(Horse horse : horses)
        System.out.println(horse.tracks());
      for(Horse horse : horses)
        if(horse.getStrides() >= FINISH_LINE) {
          System.out.println(horse + "won!");
          exec.shutdownNow();
          return;
        }
      try {
        TimeUnit.MILLISECONDS.sleep(pause);
      } catch(InterruptedException e) {
        System.out.println(
          "barrier-action sleep interrupted");
      }
    });
    for(int i = 0; i < nHorses; i++) {
      Horse horse = new Horse(barrier);
      horses.add(horse);
      exec.execute(horse);
    }
  }
  public static void main(String[] args) {
    int nHorses = 7;
    int pause = 200;
    if(args.length > 0) { // Optional argument
      int n = new Integer(args[0]);
      nHorses = n > 0 ? n : nHorses;
    }
    if(args.length > 1) { // Optional argument
      int p = new Integer(args[1]);
      pause = p > -1 ? p : pause;
    }
    new HorseRace(nHorses, pause);
  }
}
/* Output: (First and Last 18 Lines)
===========================================================
================
0
**1
2
*3
**4
*5
**6
===========================================================
================
**0
***1
**2
**3
***4
*5
****6
===========================================================
================
**0
...________...________...________...________...
***********************************************************
*******6
===========================================================
================
***********************************************************
**********0
***********************************************************
**********1
***********************************************************
***********2
***********************************************************
***************3
********************************************************4
*********************************************************5
***********************************************************
*******6
===========================================================
================
***********************************************************
***********0
***********************************************************
**********1
***********************************************************
************2
***********************************************************
*****************3
*********************************************************4
**********************************************************5
***********************************************************
*******6
Horse 3 won!
*/
