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

def toolrun(benchhomeDir,problem,tasktype,timebudget):
   """
      Implement in this function, a call to your tool, and running the tool on the given
      verification problem, to do the given verification type.
      The function will be passed as a call-back to the actual benchmark runner,
      which is provided by the package muBenchExec. The runner will read available
      benchmarking problems, and feed them one at a time to this function, which in turn
      will run your tool on the problem given.

      The runner muBenchExec currently implements no mechanism to force the tool to terminate.
      So, in this toolrun-function you need to implement a way to terminate when the tool
      seems to hang.


      benchhomeDir : a Path-instance pointing to the root-directory of the benchmark problems.
                     This is projectdir/java

      problem : a directory containing the program to verify. A problem contains a Main.java file,
             containing a static main-method, which is the entry point for verification. The
             method may contain an assert. A verification task could be to verify that the assert is
             never violated, or to verify that the assert is violated.

      tasktype: the type of the verification task. For task types are available,
            namely: true-valid-assert, false-valid-assert, true-no-runtime-exception task,
            and false-no-runtime-exception task. The first is to verify that all
            asserts in the verification target hold (never violated). The second type is
            to verify that there is an execution that violates an assert.

      timebudget: time budget in second to complete the task. The tool is responsible
           to finish in timebudget + 10sec.

      The function returns True, or False, or "CRASH". True if no violation is found. False
      if a violation is found. Otherwise "CRASH" is returned.
   """
   tooldir = Path(".")
   # the location of the root-dir containing the source files of the verification target
   TARGETsrcdir = benchhomeDir / problem
   # the location of the root-dir containing the bytecodes/binary of the verification target
   CUTclassdir = benchhomeDir / problem / "classes"
   # subdirectory where you can put outputs of your tool (e.g. generated Junit tests, witness, logs, etc)
   outputdir = tooldir / "out" / tasktype / problem

   # run the tool here
   # verdict = run your tool....
   verdict = "dummy-verdict"
   return verdict

def runBench(taskType,timebudget):
    muBenchExec.runBench(toolrun,toolname,taskType,timebudget)

#
# run the benchmark. Syntax:
#     runBench(task-type,timebudget).
#  Or from cmd-line:
#     >pyhton Runtool.py <task-type> <timebudget>
#
# Available task-types:
#    true-valid-assert
#    false-valid-assert
#    true-no-runtime-exception
#    false-no-runtime-exception
#
if __name__ == '__main__':
   theTaskType = "false-valid-assert"
   timebudget = 60
   if len(sys.argv) > 1 :
      theTaskType = sys.argv[1]
   if len(sys.argv) > 2 :
      theTaskType = int(sys.argv[2])
   runBench(theTaskType,timebudget)
