import os
from pathlib import Path
import subprocess
import time
import yaml
import Runtool

benchhomeDir = Path("../../java")


def isAssertionCheckingTask(task, expectedVerdict):
    """
    Check if the task is a task to check the valid-assert property, with
    the given expected verdict.
    """
    # get task's yaml property-file:
    propfile = benchhomeDir / Path(str(task) + ".yml")
    with open(propfile, 'r') as f:
       taskPropertyYaml = yaml.load(f, Loader=yaml.SafeLoader)
    properties = taskPropertyYaml["properties"]
    for P in properties:
        #print(f">>> {P["expected_verdict"]}")
        if P["property_file"].endswith("valid-assert.prp") and P["expected_verdict"] == expectedVerdict :
           return True
    return False


def isFindAssertionViolationTask(task):
    """
    Check if the task is a task to check the valid-assert property, with
    false as the expected verdict. So, the task is to find an assert
    violation.
    """
    return isAssertionCheckingTask(task,False)

def isCheckAbsenceOfAssertionViolationTask(task):
    """
    Check if the task is a task to check the valid-assert property, with
    true as the expected verdict. So, the task is to verify that the subject
    program has no assert violation.
    """
    return isAssertionCheckingTask(task,True)

def getTasks(taskType):
    with open(benchhomeDir / "tasks.list") as f :
       tasks = [t.strip() for t in f if not t.strip().startswith("#")]
    if taskType == "false-valid-assert":
        return [ T for T in tasks if isFindAssertionViolationTask(T)]
    if taskType == "true-valid-assert":
        return [ T for T in tasks if isCheckAbsenceOfAssertionViolationTask(T)]
    print (">>> can't check tasks of that type.")
    return []


def runBench(toolname,taskType) :
    # reading the task-list
    tasks = getTasks(taskType)
    testedTasks = 0
    correct = 0
    print(f"== start benchmarking {toolname}")
    for T in tasks:
      path = benchhomeDir / Path(T)
      if (not path.exists()):
          print(f"* {path} does not exist. Ignored.")
          continue
      testedTasks += 1
      print(f"* task {T}")
      if taskType == "true-valid-assert" :
          expectedVerdict = True
      if taskType == "false-valid-assert" :
          expectedVerdict = False
          
      starttime = time.time()
      try :
         verdict = Runtool.toolrun(benchhomeDir,T)
         duration = time.time() - starttime
      except :
         verdict = "CRASH"
         duration = None
      print(f"  expectded verdict:{expectedVerdict}, {toolname}:{verdict}. T={duration}")
      if verdict==expectedVerdict : correct += 1
    print("==")
    print(f"== #tasks:{len(tasks)}, tested:{testedTasks}, correct:{correct}")
    print("==")


#runBench("maze","false-valid-assert")
runBench("maze","true-valid-assert")
