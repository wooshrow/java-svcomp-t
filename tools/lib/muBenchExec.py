import os
import sys
from pathlib import Path
import subprocess
import time
import yaml
import csv

import logging
logging.basicConfig(
   format="{asctime} {levelname}: {message}",
   style="{",
   datefmt="%Y-%m-%d %H:%M",
   level=logging.INFO,
   filename="svcomp.log",
   encoding="utf-8",
   filemode="a",
   )

logging.getLogger().addHandler(logging.StreamHandler())

benchhomeDir = Path("../../java")

def isTaskTypePresent(problem,taskType):
    """
    Check if the given tasktype is available for the given problem.
    """
    if taskType.startswith("true"):
        expectedVerdict = True
        taskType_ = taskType[5:]
    elif taskType.startswith("false"):
        expectedVerdict = False
        taskType_ = taskType[6:]
    else:
        logging.error(f"* Can't check tasks of that type: {taskType}!")
        return False

    # get task's yaml property-file:
    propfile = benchhomeDir / Path(str(problem) + ".yml")
    with open(propfile, 'r') as f:
       taskPropertyYaml = yaml.load(f, Loader=yaml.SafeLoader)
    properties = taskPropertyYaml["properties"]
    for P in properties:
        #print(f">>> {P["expected_verdict"]}")
        if P["property_file"].endswith(f"{taskType_}.prp") and P["expected_verdict"] == expectedVerdict :
           return True
    return False

def getProblems(taskType):
    """
    get all problems, for which the given task-type is present.
    """
    with open(benchhomeDir / "problems.list") as f :
       problems = [P.strip() for P in f if not P.strip().startswith("#")]
    return [ P for P in problems if isTaskTypePresent(P,taskType)]

def saveResultsToCSV(dir,toolname,taskType,timebudget,results):
    fname = f"{toolname}-{taskType}-{timebudget}.csv"
    fpath = dir / fname
    with open(str(fpath), 'w', newline='') as csvfile:
       fieldnames = ['problem', 'expected', 'verdict', 'time', 'load-issue']
       writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
       writer.writeheader()
       writer.writerows(results)



def runBench(tool,toolname,taskType,timebudget) :
    """
    Run the benchmark. Tasks to run are as listed in projecthome/java/tasks.list.

    This function takes a function that would run a verification tool,
    the name of the tool (just for the purpose of reporting), and the task type
    to run. The tool-function is of the form:

        tool(bmhome,problem,taskType,timebudget)

    This should run the intended tool on the problem, with the specified task-type and
    specified time budget (in second).
    The function returns either true (the subject program shows no violation of
    type as specified by the taskType), false (the subject program shows a violation),
    or "CRASH".

    Available task-types:
       true-valid-assert
       false-valid-assert
       true-no-runtime-exception
       false-no-runtime-exception
    """

    if taskType == "true-valid-assert" or taskType == "true-no-runtime-exception":
        expectedVerdict = True
    elif taskType == "false-valid-assert"  or taskType == "false-no-runtime-exception" :
        expectedVerdict = False
    else :
        print("* Unknown task-type! Aborting.")
        return

    # budget, extended with extra 10s before the tool will be forced to terminate
    timebudget2 = timebudget+10

    # reading the task-list
    problems = getProblems(taskType)
    testedTasks = 0
    correct = 0
    logging.info(f"== START benchmarking. tool:{toolname}, tasktype:{taskType}")
    results = []
    for P in problems:
      path = benchhomeDir / Path(P)
      if (not path.exists()):
          logging.warning(f"* {path} does not exist. Ignored.")
          results.append({"problem":P, "load-issue":"path does not exists" })
          continue
      testedTasks += 1

      #logging.info(f"* {P}")

      starttime = time.time()
      try :
         # giving extra 10s to the tool to close
         timebudget2
         verdict = tool(benchhomeDir.absolute(),P,taskType,timebudget2)
         duration = time.time() - starttime
      except :
         verdict = "CRASH"
         duration = timebudget2
      duration = round(duration,3)
      logging.info(f"P:{P},  verdict:{verdict}, expecting:{expectedVerdict}, T={duration}")
      results.append({"problem":P, "expected":expectedVerdict, "verdict":verdict, "time":duration, "load-issue":None })
      if verdict==expectedVerdict : correct += 1
    logging.info("== END")
    logging.info(f"== tasktype:{taskType}, #problems:{len(problems)}, tested:{testedTasks}, correct:{correct}")
    saveResultsToCSV(Path("out"),toolname,taskType,timebudget2,results)
