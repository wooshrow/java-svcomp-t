MAZE results on false-valid-asserts problems (72). Sept. 2026.

Configuration:

  * Strategy: BFS (Breadth First Search)
  * Time budget: 120s
  * Max depth: 700
  * 256 MB heapsize (Java default)

For all problems the expected vedict is _FALSE_ (the problems violate some assertions). In the column MAZE, you can see MAZE's verdict on the problems. When it says _FALSE_ it means that MAZE found the violation.

For comparison, we also include the verdicts by other tools from [SVCOMP 2025](https://sv-comp.sosy-lab.org/2025/results/results-verified/META_JavaOverall.table.html#/). Included tools: COASTAL, GDART, Java Ranger, Jayhorn, JBMC, JDart, MLB, SWS, and SWAT. Note that their verdicts were taken over from the SVCOMP data; we did not rerun the tools.


| problem | expected | MAZE-verdict | time | COASTAL | GDART | Java Ranger | JayHorn | JBMC | JDart | MLB | SWS | SWAT |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| algorithms/BellmanFord-FunSat02 | False | FALSE | 7.65 | unknown | FALSE | FALSE | unknown | ERROR | FALSE | FALSE | unknown | ERROR |
| algorithms/BellmanFord-FunUnsat01 | False | FALSE | 1.864 | unknown | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | unknown | FALSE |
| algorithms/BellmanFord-FunUnsat02 | False | FALSE | 1.78 | unknown | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| algorithms/BellmanFord-MemUnsat01 | False | FALSE | 1.672 | unknown | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | unknown | FALSE |
| algorithms/BellmanFord-MemUnsat02 | False | FALSE | 1.291 | unknown | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| algorithms/BinaryTreeSearch-FunUnsat01 | False | FALSE | 1.6 | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | unknown | FALSE |
| algorithms/BinaryTreeSearch-MemUnsat01 | False | FALSE | 1.895 | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| algorithms/BinaryTreeSearch-MemUnsat02 | False | FALSE | 1.563 | FALSE | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | FALSE | FALSE |
| algorithms/InsertionSort-FunUnsat01 | False | FALSE | 1.975 | unknown | FALSE | unknown | FALSE | FALSE | FALSE | FALSE | unknown | FALSE |
| algorithms/InsertionSort-MemUnsat01 | False | FALSE | 1.281 | TIMEOUT | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | unknown | FALSE |
| algorithms/MergeSortIterative-FunUnsat01 | False | FALSE | 2.436 | unknown | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | unknown | unknown |
| algorithms/MergeSortIterative-MemSat01 | False | TRUE | 121.189 | unknown | unknown | TRUE | TIMEOUT | ERROR | TIMEOUT | TIMEOUT | unknown | ERROR |
| algorithms/MergeSortIterative-MemUnsat01 | False | FALSE | 1.463 | TIMEOUT | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | unknown | FALSE |
| algorithms/RedBlackTree-FunUnsat01 | False | FALSE | 1.288 | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| algorithms/RedBlackTree-MemUnsat01 | False | FALSE | 2.023 | FALSE | FALSE | FALSE | TIMEOUT | FALSE | FALSE | FALSE | unknown | ERROR |
| algorithms/SortedListInsert-FunUnsat01 | False | FALSE | 1.24 | FALSE | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | OUT OF MEMORY | FALSE |
| algorithms/SortedListInsert-MemUnsat01 | False | FALSE | 1.338 | FALSE | unknown | FALSE | unknown | FALSE | FALSE | FALSE | OUT OF MEMORY | FALSE |
| algorithms/Trie-FunUnsat01 | False | FALSE | 1.236 | FALSE | FALSE | FALSE | TIMEOUT | FALSE | FALSE | FALSE | FALSE | FALSE |
| algorithms/Trie-MemUnsat01 | False | FALSE | 1.198 | FALSE | FALSE | FALSE | TIMEOUT | FALSE | FALSE | FALSE | FALSE | FALSE |
| algorithms/Tsp-FunSat01 | False | FALSE | 4.264 | unknown | FALSE | FALSE | unknown | OUT OF MEMORY | FALSE | TIMEOUT | unknown | ERROR |
| algorithms/Tsp-FunUnsat01 | False | FALSE | 2.417 | unknown | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| algorithms/Tsp-MemUnsat01 | False | FALSE | 1.779 | unknown | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| jayhorn-recursive/Ackermann01 | False | FALSE | 1.303 | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| jayhorn-recursive/InfiniteLoop | False | FALSE | 1.477 | FALSE | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | FALSE | FALSE |
| jayhorn-recursive/UnsatAckermann01 | False | FALSE | 1.433 | unknown | OUT OF MEMORY | unknown | FALSE | FALSE | TIMEOUT | FALSE | FALSE | ERROR |
| jayhorn-recursive/UnsatAddition01 | False | FALSE | 1.821 | unknown | FALSE | unknown | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| jayhorn-recursive/UnsatAddition02 | False | FALSE | 33.222 | unknown | unknown | unknown | TRUE | OUT OF MEMORY | FALSE | TIMEOUT | TRUE | ERROR |
| jayhorn-recursive/UnsatEvenOdd01 | False | FALSE | 1.078 | unknown | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | unknown | FALSE |
| jayhorn-recursive/UnsatFibonacci01 | False | FALSE | 1.766 | FALSE | OUT OF MEMORY | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| jayhorn-recursive/UnsatFibonacci02 | False | FALSE | 25.897 | FALSE | unknown | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| jayhorn-recursive/UnsatMccarthy91 | False | FALSE | 1.198 | unknown | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| float-nonlinear-calculation/EulerMethod | False | FALSE | 1.162 | unknown | unknown | unknown | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| float-nonlinear-calculation/MathSin | False | TRUE | 3.687 | unknown | unknown | unknown | TIMEOUT | unknown | unknown | FALSE | unknown | FALSE |
| float-nonlinear-calculation/Optimization1 | False | FALSE | 111.937 | unknown | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| float-nonlinear-calculation/Optimization2 | False | TIMEOUT | 130.052 | unknown | unknown | unknown | unknown | FALSE | FALSE | FALSE | unknown | unknown |
| float-nonlinear-calculation/coral1 | False | FALSE | 2.017 | unknown | unknown | unknown | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| float-nonlinear-calculation/coral2 | False | TRUE | 1.645 | FALSE | unknown | unknown | unknown | unknown | TRUE | FALSE | unknown | unknown |
| float-nonlinear-calculation/coral10 | False | TRUE | 2.414 | TRUE | unknown | unknown | unknown | unknown | TRUE | FALSE | unknown | unknown |
| float-nonlinear-calculation/coral11 | False | FALSE | 1.71 | unknown | unknown | unknown | unknown | unknown | FALSE | FALSE | unknown | FALSE |
| float-nonlinear-calculation/coral12 | False | FALSE | 1.192 | unknown | unknown | unknown | unknown | unknown | FALSE | FALSE | unknown | FALSE |
| float-nonlinear-calculation/coral13 | False | FALSE | 1.333 | unknown | unknown | unknown | unknown | unknown | FALSE | FALSE | unknown | FALSE |
| float-nonlinear-calculation/coral14 | False | FALSE | 1.474 | unknown | unknown | unknown | unknown | unknown | FALSE | FALSE | unknown | FALSE |
| float-nonlinear-calculation/coral15 | False | TRUE | 1.326 | unknown | unknown | unknown | unknown | unknown | TRUE | FALSE | unknown | unknown |
| float-nonlinear-calculation/coral16 | False | TIMEOUT | 130.048 | unknown | unknown | unknown | unknown | unknown | TRUE | FALSE | unknown | unknown |
| float-nonlinear-calculation/coral17 | False | TRUE | 1.256 | FALSE | unknown | unknown | unknown | unknown | TRUE | FALSE | unknown | unknown |
| float-nonlinear-calculation/coral18 | False | TRUE | 1.07 | TRUE | unknown | unknown | unknown | unknown | TRUE | FALSE | unknown | unknown |
| float-nonlinear-calculation/coral19 | False | TRUE | 1.171 | TRUE | unknown | unknown | unknown | unknown | TRUE | FALSE | unknown | unknown |
| float-nonlinear-calculation/coral20 | False | TRUE | 20.013 | TRUE | unknown | unknown | unknown | unknown | TRUE | FALSE | unknown | unknown |
| jdart-regression/addition01 | False | FALSE | 8.209 | unknown | FALSE | unknown | FALSE | ERROR | FALSE | FALSE | FALSE | ERROR |
| jdart-regression/array-iteration01 | False | FALSE | 1.158 | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | unknown | FALSE |
| jdart-regression/boundcheck30 | False | FALSE | 1.427 | FALSE | FALSE | FALSE | TIMEOUT | FALSE | FALSE | FALSE | FALSE | FALSE |
| jdart-regression/boundcheck100 | False | FALSE | 3.026 | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| jdart-regression/boundcheck200 | False | FALSE | 3.867 | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | TIMEOUT | FALSE | FALSE |
| jdart-regression/double2long | False | FALSE | 10.318 | unknown | FALSE | unknown | unknown | FALSE | FALSE | FALSE | unknown | unknown |
| jdart-regression/float | False | FALSE | 6.055 | FALSE | unknown | unknown | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| jdart-regression/OverapproximationString01 | False | FALSE | 1.071 | FALSE | FALSE | FALSE | TIMEOUT | FALSE | FALSE | FALSE | FALSE | FALSE |
| jdart-regression/radians | False | TRUE | 1.101 | TRUE | FALSE | unknown | unknown | unknown | FALSE | FALSE | unknown | unknown |
| jdart-regression/shifting | False | FALSE | 1.09 | FALSE | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| jdart-regression/shifting2 | False | FALSE | 1.212 | FALSE | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| jdart-regression/shifting3 | False | FALSE | 1.196 | TRUE | FALSE | FALSE | unknown | FALSE | FALSE | FALSE | unknown | FALSE |
| jdart-regression/URLDecoder02 | False | TRUE | 1.163 | unknown | FALSE | unknown | unknown | FALSE | unknown | FALSE | FALSE | FALSE |
| java-ranger-regression/loopCharAt | False | TRUE | 1.126 | unknown | unknown | FALSE | TIMEOUT | FALSE | unknown | unknown | TIMEOUT | ERROR |
| java-ranger-regression/nanoxml_eqchk/prop2 | False | TRUE | 1.183 | TRUE | unknown | unknown | unknown | ERROR | FALSE | FALSE | unknown | unknown |
| java-ranger-regression/nanoxml_eqchk/prop3 | False | TRUE | 1.296 | TRUE | unknown | unknown | TIMEOUT | ERROR | FALSE | TIMEOUT | unknown | unknown |
| java-ranger-regression/printtokens_eqchk/prop2 | False | FALSE | 1.25 | FALSE | FALSE | FALSE | TIMEOUT | OUT OF MEMORY | FALSE | FALSE | FALSE | FALSE |
| java-ranger-regression/replace5_eqchk/prop2 | False | FALSE | 112.216 | FALSE | unknown | FALSE | unknown | FALSE | FALSE | FALSE | FALSE | FALSE |
| java-ranger-regression/siena_eqchk/prop2 | False | TRUE | 10.057 | unknown | FALSE | FALSE | unknown | unknown | FALSE | FALSE | unknown | ERROR |
| java-ranger-regression/TCAS_prop1 | False | FALSE | 11.574 | FALSE | unknown | FALSE | FALSE | FALSE | FALSE | TIMEOUT | FALSE | ERROR |
| java-ranger-regression/WBS/prop1 | False | FALSE | 3.643 | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| java-ranger-regression/WBS/prop3 | False | FALSE | 3.318 | FALSE | unknown | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| java-ranger-regression/WBS/prop4 | False | FALSE | 3.226 | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE | FALSE |
| TOTAL KILLS |  | 54 |  | 29 | 42 | 43 | 24 | 48 | 58 | 64 | 24 | 47 |
