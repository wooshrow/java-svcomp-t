# java-svcomp-t

This project contains migrated java verification tasks from [SV-COMP](https://gitlab.com/sosy-lab/benchmarking/sv-benchmarks), adjusted to benchmark the testing tool [MAZE](https://github.com/ThijnK/maze). Modifications:

* Calls to `v = Verifier.nondetType()` are replaced by introduction of explicit parameters to the target method. This is because MAZE requires symbolic variables to appear as method parameters rather than injected inside the method-body.
* `Verifier.assume(P)` construct is replaced by `if (! P) then return ;`.

### Project structure:

* `orig-svcomp-java` : contains the original SVCOMP tasks.
* `java` : contains adjusted tasks.
* The file `java\tasks.list` contains all the tasks included the benchmark. You can comment-out some if you just want a subset of them.

### Building

You'll need to compile every task to produce the corresponding Java bytecode (.class files). You can run the Python script `./java/compile.py` to produce those binaries. They will be placed in `/classes` sub-dir of every task directory. **Note:** the tasks have to be compiler with Java 1.8 (requirement from SV-COMP).

### Running the benchmark

Currently only one tool is available for benchmarking, namely the aforementioned MAZE. Go to `./tools/maze`, the run the Python script `muBenchExec.py`. You can adjust the task-type to target in the script.

### Availble task-types

* `false-valid-assert` : find an execution that violates an `assert` statement.
* `true-valid-assert` : verify that the subject program does not violate any `assert`.
