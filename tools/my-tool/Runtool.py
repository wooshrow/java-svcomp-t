#
# An example of a Runtool for some Java verification tool-X. You need to implement
# the function toolrun below.
#

# some useful imports:
#import os
#import subprocess
#import shutil

# you need this imports:
from pathlib import Path
import sys
sys.path.insert(0, "../lib")
import muBenchExec

toolname = "X"

def toolrun(benchhomeDir,problem,tasktype):
   """
      Implement in this function, a call to your tool, and running the tool on the given
      verification problem, to do the given verification type.

      benchhomeDir : a Path-instance pointing to the root-directory of the benchmark problems.
                     This is projectdir/java

      problem : a directory containting the program to verify. A problem contains a Main.java file,
             containing a main-method, which is the entry point for verification. The
             method may contain an assert. A verification task could be to verify that the assert is
             never violated, or to verify that the assert is violated.

      tasktype: the type of the verification task. For task types are available,
            namely: true-valid-assert, false-valid-assert, true-no-runtime-exception task,
            and false-no-runtime-exception task. The first is to verify that all
            asserts in the verification target hold (never violated). The second type is
            to verify that there is an execution that violates an assert.


      The function returns True, or False, or "CRASH". True if no violation is found. False
      if a violation is found. Otherwise "CRASH" is returned.
   """
   tooldir = Path(".")
   # the location of the root-dir containing the source files of the verification target
   TARGETsrcdir = benchhomeDir / problem
   # the location of the root-dir containing the bytecodes/binary of the verification target

   # run the tool here
   # verdict = run your tool....
   verdict = "dummy-verdict"
   return verdict

def runBench(taskType):
    muBenchExec.runBench(toolrun,toolname,taskType)

#
# run the benchmark. Syntax: runBench(task-type)
# Available task-types:
#    true-valid-assert, false-valid-assert
#
if __name__ == '__main__':
   theTaskType = "false-valid-assert"
   if len(sys.argv) > 1 :
      theTaskType = sys.argv[1]
   runBench(theTaskType)
