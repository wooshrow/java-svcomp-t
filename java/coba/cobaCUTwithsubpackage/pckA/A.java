package pckA ;

public class A {

     B b ;

     public A(int x) {
       this.b = new B(x) ;
     }

     public int val() {
       return b.x ;
     }
}
