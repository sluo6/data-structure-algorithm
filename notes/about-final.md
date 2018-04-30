# What's the meaning of "final" in java?

The following is adapted from [this link](https://stackoverflow.com/questions/15655012/how-does-the-final-keyword-in-java-work-i-can-still-modify-an-object).

```java
import java.util.ArrayList;
import java.util.List;

class Test {
    private final List foo;
    // private static final List foo = new ArrayList();
    // Results in compile time error.

    public Test() {
        foo = new ArrayList();
        foo.add("foo"); // Modification-1
    }

    public void setFoo(List foo) {
       //this.foo = foo; Results in compile time error.
    }
    
    public static void main(String[] args) 
      {
          Test t = new Test();
          t.foo.add("bar"); // Modification-2
          System.out.println("print - " + t.foo);
      }
}
```

In the above example, a 'Test' constructor is defined, and given a 'setFoo' method.
`foo` is an **instance variable**. When we create `Test` class object, then the instance variable `foo` will be copied
inside the object of `Test` class, and instantiated to an ArrayList with "foo" content. **The value of a final variable
can only be assigned one time**. So `this.foo = foo'` in the 'setFoo' method will cause a compile time error.

If `private final List foo;` is changed to `private static final List foo = new ArrayList();`  The compiler will yell at
you. That is because, in this case, `foo` is a **static** variable. A static variable is shared by all instances of that
class. If every new instance of that class can see the `foo` variable, and try to re-assign it to a new ArrayList when
 constructor is called, the compiler will start to yell.

Modification-1 and Modification-2 are both allowed. Because those code were only adding contents to `foo`. They are not
re-assigning `foo` to something else. In other words, `final` is only about the **reference** itself, and not about the
contents of the referenced object.

The above examples gives a clear explanation of `final` variable.

Other rules for `final` key word:  
**final** classes cannot be subclassed.  
**final** method cannot be overridden by subclasses, but can override.