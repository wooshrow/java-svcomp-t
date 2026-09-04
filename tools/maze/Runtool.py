#
# Define how to run a tool on a given verification task.
#
import os
from pathlib import Path
import subprocess
import shutil
toolname = "maze"

def toolrun(benchhomeDir,task):
   tooldir = Path(".")
   tooljar = f"{tooldir}/maze-1.1.2-jar-with-dependencies.jar"
   CUTclassdir = benchhomeDir / task / "classes"
   outputdir = tooldir / "out" / task
   if outputdir.exists():
      shutil.rmtree(outputdir)
   os.makedirs(outputdir)
   # print(f"* Running {toolname} on {task}...")
   sp =  subprocess.run(["java",
            "-ea",
            "-jar", tooljar,
            f"-c={CUTclassdir}",
            "-n=Main",
            "-m=main",
            f"-o={outputdir}",
            "--verificationMode=1",
            "--minimalistic-suite=true",
            "-s=BFS",
            "-b=60",
            "--max-depth=400",
            "--max-array-size=10",
            "--constrain-FP-params-to-normal-numbers=true",
            "--check-divbyZero=true"
            ],
            capture_output=True)
   o1 = str(sp.stdout)
   N = len(o1)
   verdict = True # no error found
   for i in range(N - 1, -1, -1):
      if "ERROR" in str(o1) :
          verdict = False
          break
   #print(f">>> {verdict}")
   if (tooldir / "logs").exists() :
       # move logs:
       subprocess.run(["mv", f"{tooldir / "logs"}", f"{outputdir}/"])
   return verdict
