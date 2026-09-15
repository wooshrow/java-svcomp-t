// This file is part of the SV-Benchmarks collection of verification tasks:
// https://gitlab.com/sosy-lab/benchmarking/sv-benchmarks
//
// SPDX-FileCopyrightText: 2011-2013 Alexander von Rhein, University of Passau
// SPDX-FileCopyrightText: 2011-2021 The SV-Benchmarks Community
//
// SPDX-License-Identifier: Apache-2.0

//import org.sosy_lab.sv_benchmarks.Verifier;

public class Main {

  private static int cleanupTimeShifts = 2;

  public static void main(boolean[] actions) {
    if (actions==null || actions.length != 3*4)
       return ;
    //randomSequenceOfActions(3);
    randomSequenceOfActions(actions,3);
  }

  /*
  public static boolean getBoolean() {
    return Verifier.nondetBoolean();
  }
  */

  //public static void randomSequenceOfActions(int maxLength) {
  public static void randomSequenceOfActions(boolean[] actions, int maxLength) {

    Actions a = new Actions();

    int counter = 0;
    while (counter < maxLength) {
      counter++;

      //boolean action1 = getBoolean();
      //boolean action2 = getBoolean();
      //boolean action3 = getBoolean();
      boolean action1 = actions[4*(counter-1)] ;
      boolean action2 = actions[4*(counter-1) + 1] ;
      boolean action3 = actions[4*(counter-1) + 2] ;

      boolean action4 = false;
      if (!action3) 
         //action4 = getBoolean();
         action4 = actions[4*(counter-1) + 3] ;

      if (action1) {
        a.waterRise();
      }

      if (action2) {
        a.methaneChange();
      }

      if (action3) {
        a.startSystem();
      } else if (action4) {
        a.stopSystem();
      }

      a.timeShift();
    }

    for (counter = 0; counter < cleanupTimeShifts; counter++) {
      a.timeShift();
    }
  }
}
