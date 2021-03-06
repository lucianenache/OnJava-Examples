// onjava/Tuple4.java
// (c)2016 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://mindviewinc.com/Books/OnJava/ for more book information.
package onjava;

public class Tuple4<A, B, C, D>
  extends Tuple3<A, B, C> {
  public final D _4;
  public Tuple4(A a, B b, C c, D d) {
    super(a, b, c);
    _4 = d;
  }
  @Override
  public String rep() {
    return super.rep() + ", " + _4;
  }
}
