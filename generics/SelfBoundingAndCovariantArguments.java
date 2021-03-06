// generics/SelfBoundingAndCovariantArguments.java
// (c)2016 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://mindviewinc.com/Books/OnJava/ for more book information.

interface SelfBoundSetter<T extends SelfBoundSetter<T>> {
  void set(T arg);
}

interface Setter extends SelfBoundSetter<Setter> {}

public class SelfBoundingAndCovariantArguments {
  void testA(Setter s1, Setter s2, SelfBoundSetter sbs) {
    s1.set(s2);
    //- s1.set(sbs);
    // error: method set in interface SelfBoundSetter<T>
    // cannot be applied to given types;
    //     s1.set(sbs);
    //       ^
    //   required: Setter
    //   found: SelfBoundSetter
    //   reason: argument mismatch;
    // SelfBoundSetter cannot be converted to Setter
    //   where T is a type-variable:
    //     T extends SelfBoundSetter<T> declared in
    //     interface SelfBoundSetter
    // 1 error
  }
}
