# java-svcomp-t

This project contains migrated **java verification problems** from [SV-COMP](https://gitlab.com/sosy-lab/benchmarking/sv-benchmarks), adjusted to benchmark the testing tool [MAZE](https://github.com/ThijnK/maze). At the moment not all problems are migrated yet; but we will keep adding migrated problems 😊.

### Project structure:

* `orig-svcomp-java` : contains the original SVCOMP problems.
* `java` : contains migrated problems.
* The file [`java/problems.list`](./java/problems.list) lists all the migrated problems included the benchmark. You can comment-out some if you want to run the benchmarking with only a subset of them.

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


### Building

You'll need to compile every verification problem to produce the corresponding Java bytecode (.class files). You can run the Python script `./java/compile.py` to produce those binaries. They will be placed in `/classes` sub-dir of every task directory. **NOTE:** SV-COMP requires them to be compiled with Java 1.8. But this actually depends on which Java versions your verification tools can handle. MAZE can verify targets in Java 1.8, and it can also handle Java 21.

 (requirement from SV-COMP).

### Running the benchmark

Currently only one tool is available for benchmarking, namely the aforementioned MAZE. Go to `./tools/maze`, the run the Python script `>python Runtool.py tasktype`.

#### Availble task-types



#### Benchmarking other verification tools

You can also use this project to benchmark other tools. The main difference with SVCOMP benchmarking method is that our benchmarking does not do any witness checking. SVCOMP requires witness as it is a tool competition. For plain  benchmarking, witnesses are not needed, assuming you trust that the benchmarked tools do not try to cheat 😁 (e.g. by memorizing the tasks' verdicts).


To benchmark a tool X, you need to integrate it to this benchmarking project.
You do that by creating a tool directory under `\tools`, and then implement your own script `Runtool.py` to run the tool X. A template is provided in `tools\my-tool`. You can also look at how the `Runtool` script is written for `maze`.
