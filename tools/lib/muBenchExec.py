import os
import sys
from pathlib import Path
import subprocess
import time
import yaml

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
        print (f"* Can't check tasks of that type: {taskType}!")
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


def runBench(tool,toolname,taskType) :
    """
    Run the benchmark. Tasks to run are as listed in projecthome/java/tasks.list.

    This function takes a function that would run a verification tool,
    the name of the tool (just for the purpose of reporting), and the task type
    to run. The tool-function is of the form:

        tool(bmhome,problem,taskType)

    This should run the intended tool on the problem, with the specified task-type.
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

    # reading the task-list
    problems = getProblems(taskType)
    testedTasks = 0
    correct = 0
    print(f"== start benchmarking {toolname}; task: {taskType}")
    for P in problems:
      path = benchhomeDir / Path(P)
      if (not path.exists()):
          print(f"* {path} does not exist. Ignored.")
          continue
      testedTasks += 1

      print(f"* problem {P}")

      starttime = time.time()
      try :
         verdict = tool(benchhomeDir.absolute(),P,taskType)
         duration = time.time() - starttime
      except :
         verdict = "CRASH"
         duration = None
      print(f"  expectded verdict:{expectedVerdict}, {toolname}:{verdict}. T={duration}")
      if verdict==expectedVerdict : correct += 1
    print("==")
    print(f"== {taskType} task, #problems:{len(problems)}, tested:{testedTasks}, correct:{correct}")
    print("==")
