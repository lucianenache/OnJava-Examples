// polymorphism/Shapes.java
// (c)2016 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://mindviewinc.com/Books/OnJava/ for more book information.
// Polymorphism in Java
import polymorphism.shape.*;

public class Shapes {
  public static void main(String[] args) {
    RandomShapes gen = new RandomShapes();
    // Make polymorphic method calls:
    for(Shape shape : gen.array(9))
      shape.draw();
  }
}
/* Output:
Triangle.draw()
Triangle.draw()
Square.draw()
Triangle.draw()
Square.draw()
Triangle.draw()
Square.draw()
Triangle.draw()
Circle.draw()
*/
