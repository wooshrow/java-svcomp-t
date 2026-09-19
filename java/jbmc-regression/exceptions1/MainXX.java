/*
 * Origin of the benchmark:
 *     license: 4-clause BSD (see /java/jbmc-regression/LICENSE)
 *     repo: https://github.com/diffblue/cbmc.git
 *     branch: develop
 *     directory: regression/cbmc-java/exceptions1
 * The benchmark was taken from the repo: 24 January 2018
 */


class A extends Throwable {}

class B extends A {}

//import X.A ;
//import X.B ;

public class Main {
  
  //static class A extends Throwable {} 
  //static class B extends A {}
  //public static class C extends B {}

  //public static class D extends C {}

  public static void main() {
    try {
      //D d = new D();
      //C c = new C();
      //B b = new B();
      //A a = new A();
      //A e = a;
      //throw e;
      //B b = new B() ;
      throw new A() ;
    } 
    //catch (D exc) { assert false; } 
    //catch (C exc) { assert false; } 
    catch (B exc) { assert false; } 
    catch (A exc) { assert false; }
  }
}
