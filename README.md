# java-svcomp-t

This project contains ported **java verification problems** from [SV-COMP](https://gitlab.com/sosy-lab/benchmarking/sv-benchmarks), adjusted to benchmark the testing tool [MAZE](https://github.com/ThijnK/maze). At the moment not all problems are migrated yet; but we will keep adding migrated problems 😊. Each verification problem in SVCOMP is represented by an entry point java method, e.g. _main(x,y,z)_. The task could be to verify that the program never throw an uncaught exception, with respect to all possible values of x,y,z. Or, the task could be to find an instance of x,y,z that would cause the program to violate an assertion. However,in SVCOMP these parameters x,y,z are represented through injections of code `Verifier.nondetType()` in the body of _main()_ rather than nicely as parameters _main(x,y,z)_. The port provided here basically make those parameters become normal parameters. This would make the problems easier to target by tools, including MAZE.

Currently included sets:

* _Algorithms_: complete
* _Jayhorn-recursive_ : complete
* _float-nonlinear-calculation_ : includes all false-valid-assert problems.
* _JDart-regression_ : complete
* _Java-ranger-regression_ : includes all false-valid-assert problems.
*  _Mine-pump_ : complete
* _Jpf-regression_ : all false-valid-assert problems.
* _Jbmc-regression_ : false-valid-assert problems.

Total 315 problems (730 tasks), of which 266 are false-valid-assert problems/tasks.

TO DO:

* security-specific problem-sets _juliet_ and _securibench_. These contain many use of I/O (e.g. Socket), which are currently beyond MAZE.


[Benchmark results 2026 Sept.](./tools/maze/Maze-false-valid-assert-130-2026-09.md)

Not included:

*
### Project structure:

* `orig-svcomp-java` : contains the original SVCOMP problems.
* `java` : contains migrated problems.
* The file [`java/all-problems.list`](./java/all-problems.list) lists all problems that have been ported.
The file [`java/problems.list`](./java/problems.list) lists all problems that are included when you run the benchmarking. So, you can copy all, or some, entries from `all-problems.list` to `problems.list`.


### Benchmark Structure

The benchmark consists of verification problems. It can be used to evaluate a verification _tool_ by giving the problems and their associated verification tasks to the tool, to see which tasks can be completed successfully by the tool, and which tasks are failed. Execution time is also measured.

A _verification problem_ _V_ consists of a _target program_, say _P_ and a property to verify. _P_ is not directly exposed to the _tool_. Instead, there is a method, _Main.main(params)_, that serves as an entry point for verification. The method will invoke _P_, but additionally may contain assertions to check or checks for uncaught exception thrown by _P_. _V_ also specifies one or more verification tasks that the _tool_ must perform on _P_. The verification task could be to verify that _P_ never violates the assertions in _main()_, but it could also be to verify that there exists an execution that violates an assertion. The first type of task is used to verify that _P_ satisfies a certain correctness property. The second type of task represents a buggy _P_, and is used to see if the verification _tool_ manages to find the bug.

Every verification problem has a name e.g. `algorithms/RedBlackTree-FunSat01`. The target program _P_ and its _Main.main()_ entry point are placed in a directory with the same name: [`java/algorithms/RedBlackTree-FunSat01`](java/algorithms/RedBlackTree-FunSat01). For example, it may contain a class named `Main`, containing the method _Main.main(..)_ as well as _P(..)_ as a method.
The verification tasks for this problem are specified in a yaml-file with the same name: [`java/algorithms/RedBlackTree-FunSat01.yml`](java/algorithms/RedBlackTree-FunSat01.yml). However, the tasks are formulated in SV-COMP lingo, that looks like this, for the above `RedBlackTree-FunSat01` verification problem:

```yaml
properties:
  - property_file: ../properties/valid-assert.prp
    expected_verdict: true
  - property_file: ../properties/no-runtime-exception.prp
    expected_verdict: true
```

Here, for the purpose of running our benchmarking script and selecting which tasks to do, we will use a simpler lingo. See below.

#### Available verification tasks

Given a verification problem _V_ with a target program _P_, and entry method _main(..)_.

* `true-valid-assert` task: verify that every execution of the target program, invoked from the entry method, respect the assertions formulated in _main_ (or in any program in _V_).

* `false-valid-assert` task : verify that there exists an execution of the target program, invoked from the entry method, that violates an assertion  in _main_ (or in any programs in _V_).

* `true-no-runtime-exception` task: verify that every execution of the target program, invoked from the entry method, does not throw an uncaught exception.

* `false-no-runtime-exception` task : verify that there exists an execution of the target program, invoked from the entry method, that throws an uncaught exception.

#### Modifications made

To make the verification problems targetable, some modifications are applied. These modification do not alter the underlying programs that are being verified.

* Calls to `v = Verifier.nondetType()` are replaced by introduction of explicit parameters to the target method. This is because MAZE requires symbolic variables to appear as method parameters rather than injected inside the method-body.
* `Verifier.assume(P)` construct is replaced by `if (! P) then return ;`.
* Because currently MAZE cannot handle updates to static variables, we refactor classes with static variables.
A class with static variables is refactored in one of the following ways. (1) the static variables are changed to become instance variables. Static members methods that refer to the variables are made instance methods as well. Or (2), a new class is introduced to hold the static variables.
* Some problems in SVCOMP form a family with some shared java source-files. For convenience, we copied share files to each problem-directory so it is easier to independently compile each problem.


### Building

You'll need to compile every verification problem to produce the corresponding Java bytecode (.class files). You can run the Python script `./java/compile.py` to produce those binaries. They will be placed in `/classes` sub-dir of every task directory. **NOTE:** SV-COMP requires them to be compiled with Java 1.8 (requirement from SV-COMP). But this actually depends on which Java versions your verification tools can handle. MAZE can verify targets in Java 1.8, and it can also handle Java 21. .

### Running the benchmark

Currently only one tool is available for benchmarking, namely the aforementioned MAZE. Go to `./tools/maze`, then run the Runtool.py Python script:

   `> python Runtool.py toolname tasktype timebudget`.

The results will be placed in `./tools/maze/out`.

* Toolname can be anything e.g. `maze-standard` or `maze-setup2` etc. This provides a way to organize the reulsts, as they will be placed in a folder whose name is prefixed by the tool name.
* Tasktype: see above on available verification tasks.
* Timebudget: in seconds. E.g. 60 or 90. Internally the benchmark adds 10s to your given timebudget. This extra time is meant to give an opportunity to the verification tool to properly terminate. If timebudget+10 seconds expires, the benchmark script will kill the process that runs the verification tool.


#### Benchmarking other verification tools

You can also use this project to benchmark other tools. The main difference with SVCOMP benchmarking method is that our benchmarking does not do any witness checking. SVCOMP requires witness as it is a tool competition. For plain  benchmarking, witnesses are not needed, assuming you trust that the benchmarked tools do not try to cheat 😁 (e.g. by memorizing the tasks' verdicts).


To benchmark a tool X, you need to integrate it to this benchmarking project.
You do that by creating a tool directory under `\tools`, and then implement your own script `Runtool.py` to run the tool X. A template is provided in `tools\my-tool`. You can also look at how the `Runtool` script is written for `maze`.
