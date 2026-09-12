//import org.sosy_lab.sv_benchmarks.Verifier;

public class Main {

  public static void main(int[] PedalPosz, boolean[] AutoBrakez, boolean[] Skidz) {

    if (PedalPosz == null || PedalPosz.length !=2)
       return ;
    if (AutoBrakez == null || AutoBrakez.length !=2)
       return ;
    if (Skidz == null || Skidz.length !=2)
       return ;

    WBS wbs = new WBS();
    int PedalPos;
    boolean AutoBrake, Skid;
    for (int i = 0; i < 2; i++) {
      //PedalPos = Verifier.nondetInt();
      //AutoBrake = Verifier.nondetBoolean();
      //Skid = Verifier.nondetBoolean();
      PedalPos = PedalPosz[i] ;
      AutoBrake = AutoBrakez[i] ;
      Skid = Skidz[i] ;
      wbs.update(PedalPos, AutoBrake, Skid);
      // This assertion should prove:
      assert ((PedalPos > 0 && PedalPos <= 4 && !Skid)
          ? (wbs.Alt_Pressure > 0 || wbs.Nor_Pressure > 0)
          : true);
    }
  }
}
