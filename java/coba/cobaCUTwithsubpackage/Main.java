import pckA.A ;

public class Main {

  public static void main(int x) {
      A a = new A(x) ;
      assert a.val() <= 9 ;
  }
}
