#
# Script to compile the java source-files of the tasks
#

import os
from pathlib import Path
import subprocess

def compile() :
   # check first if the current javac is java-8:
   sp =  subprocess.run(["javac" , "-version"],
                          capture_output=True)
   o1 = sp.stdout
   o2 = sp.stderr
   isVersion8 = "javac 1.8" in str(o1) or "javac 1.8" in str(o2)
   if not isVersion8:
       #print("The current javac is not java-8. Aborting.")
       #return
       print("WARNING: the current javac is not java-8. Some verification tool may not be able to handle higher version of Java.")

   # reading the task-list
   with open("problems.list") as f :
     problems = [t.strip() for t in f  if not t.strip().startswith("#")]
   print(f"== {len(problems)} problems")
   for T in problems:
     path = Path(T)
     if (not path.exists()):
         print(f"* {path} does not exist. Igored.")
         continue
     Tname = path.name
     mainfile = path / "Main.java"
     outdir = path / "classes"
     if not outdir.exists():
        os.makedirs(outdir)
     print(f"* Compiling {T}")
     srcpathCommon = path / ".." / "common"
     sp =  subprocess.run(["javac" ,
            "-sourcepath", T,
            "-d", outdir,
            mainfile])

compile()
