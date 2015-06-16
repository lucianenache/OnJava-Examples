//: generics/GenericHolder.java
// �2015 MindView LLC: see Copyright.txt

public class GenericHolder<T> {
  private T obj;
  public void set(T obj) { this.obj = obj; }
  public T get() { return obj; }
  public static void main(String[] args) {
    GenericHolder<String> holder =
      new GenericHolder<>();
    holder.set("Item");
    String s = holder.get();
  }
} /* Output: (None) *///:~