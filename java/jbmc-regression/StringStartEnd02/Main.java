/*
 * Origin of the benchmark:
 *     license: 4-clause BSD (see /java/jbmc-regression/LICENSE)
 *     repo: https://github.com/diffblue/cbmc.git
 *     branch: develop
 *     directory: regression/jbmc-strings/StringStartEnd02
 * The benchmark was taken from the repo: 24 January 2018
 */
//import org.sosy_lab.sv_benchmarks.Verifier;

public class Main {
  public static void main(String s0, String s1, String s2, String s3) {
    String[] strings = new String[4];
    //strings[0] = Verifier.nondetString();
    //strings[1] = Verifier.nondetString();
    //strings[2] = Verifier.nondetString();
    //strings[3] = Verifier.nondetString();
    strings[0] = s0 ;
    strings[1] = s1 ;
    strings[2] = s2 ;
    strings[3] = s3 ;

    int i = 0;
    for (String string : strings) {
      if (string.startsWith("te")) ++i;
    }
    assert i == 1;
  }
}
