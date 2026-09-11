#
# Define how to run a tool on a given verification task.
#
import os
from pathlib import Path
import subprocess
import shutil
import sys
sys.path.insert(0, "../lib")
import muBenchExec

toolname = "maze"

def toolrun(benchhomeDir,problem,tasktype,timebudget):
   tooldir = Path(".")
   tooljar = f"{tooldir}/maze-1.1.2-jar-with-dependencies.jar"
   CUTclassdir = benchhomeDir / problem / "classes"
   outputdir = tooldir / "out" / problem
   if outputdir.exists():
      shutil.rmtree(outputdir)
   os.makedirs(outputdir)
   #print(f">>> {CUTclassdir}")
   # print(f"* Running {toolname} on {task}...")

   if tasktype.endswith("valid-assert") :
      errorTypeToFind = "AssertionError"
   elif tasktype.endswith("no-runtime-exception") :
      errorTypeToFind = "UnexpectedException"
   else :
      print(f"* Unknown task-type {tasktype}! Aborting.")
      return "Unknown task-type."

   timebudget2 = timebudget - 10

   try:
       sp =  subprocess.run(["java",
                "-ea",
                #"-cp", f"{CUTclassdir}",
                "-jar", tooljar,
                f"-c={CUTclassdir}",
                "-n=Main",
                "-m=main",
                f"-o={outputdir}",
                "--verificationMode=1",
                f"--error-type-to-find={errorTypeToFind}",
                # well... it is a verification task, so we won't bother to minimize.
                # actually, we should not minimize to ensure the verification is as
                # exhaustive as it can, within the given bounds
                #"--minimalistic-suite=true",
                "-s=BFS",
                f"-b={timebudget2}",
                "--max-depth=700",
                "--max-array-size=10",
                "--constrain-FP-params-to-normal-numbers=true",
                "--check-divbyZero=true"
                ],
                timeout=timebudget,
                capture_output=True)
       o = str(sp.stdout)
       o1 = o.split("n.u.m.")
       N = len(o1)
       verdict = "CRASH" # no error found
       for i in range(N - 1, -1, -1):
          r = o1[i]
          if "Verification" in r and "ERROR" in r :
              verdict = False
              break
          if "Verification" in r and "PASS" in r :
              verdict = True
              break

   except subprocess.TimeoutExpired:
       print(f"Time budget {timebudget} exceeded. Killing the tool.")
       verdict = "TIMEOUT"

   #print(f">>> {verdict}")
   if (tooldir / "logs").exists() :
       # move logs:
       subprocess.run(["mv", f"{tooldir / "logs"}", f"{outputdir}/"])
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
      timebudget = int(sys.argv[2])
   runBench(theTaskType,timebudget)
