# CS3110 NFA design exercises

Problems **8, 12, 16, 20, and 21**. The initial commit preserves the five original JFLAP files; the next commit adds tests, diagrams, reports, and reproducible verification.

All five automata match the student-supplied language definitions. The repo contains the required JFLAP files, ordered test files, NFA diagrams, complete batch screenshots, handwritten design notes and input traces, and the learning reflection. Matching JFLAP step captures are included for #8, #12, and #16, with an additional fourth-1 rejection series for #12. The screenshots were captured from real JFLAP 7.1 simulation panes using a Java helper authorized by the student. The handwritten #8 page lists active-state sets; its accompanying JFLAP series shows the individual live and dead branches.

| Problem | Language currently checked | Report |
|---|---|---|
| 8 | {string s\| s starts with 01 and ends with 10 } | [n08r.md](n08r.md) |
| 12 | {string s\| s contains exactly 3 1's } | [n12r.md](n12r.md) |
| 16 | {string s\| every odd position of s is 1, starting at 1 in positional index } (e.g. 101 is in the lang.) | [n16r.md](n16r.md) |
| 20 | {string s\| s has 3k+1 of 1's, where k>=0 or odd number of 0's} | [n20r.md](n20r.md) |
| 21 | {string s\| s has 3k+1 of 1's, where k>=0 and odd number of 0's} | [n21r.md](n21r.md) |

## Files and verification

For each problem, `nXX.jff` is the original automaton, `nXXt.txt` is a JFLAP input file, and `nXXr.md` contains its diagram, expected test results, and evidence status. Tests contain only strings, one per line, with accepting strings first. The empty string must be added manually with **Enter Lambda**; a blank line in the text file is not an empty-string test. [JFLAP's official tutorial](https://www.jflap.org/tutorial/fa/createfa/fa.html) documents whitespace-delimited loading and Multiple Run.

The original designs were not changed: no discrepancy was found against the current predicates. No artificial mistakes or backdated commits were introduced. Four machines are deterministic (with #16 partial); these are mathematically valid NFAs, though the instructor may want explicit nondeterminism.

Run the independent check:

```sh
python3 tools/verify.py
```

It checks **8,191 binary strings per machine**, including ε, through length 12. All checks pass against the confirmed definitions. Each problem report also explains why its state structure recognizes the language for arbitrary input lengths.

`verification.txt` records actual JFLAP 7.1 Step by State and Step with Closure simulator results for every prepared test, plus ε and an out-of-alphabet input. This uses the JFLAP runtime without a window and **does not replace GUI Multiple Run or screenshot evidence**. To reproduce with your own JFLAP 7.1 jar:

```sh
mkdir -p /tmp/cs3110-jflap-check
javac -cp /path/to/JFLAP7.1.jar -d /tmp/cs3110-jflap-check tools/JflapCheck.java
java -Djava.awt.headless=true -cp /tmp/cs3110-jflap-check:/path/to/JFLAP7.1.jar JflapCheck .
```

On Windows use `;` instead of `:` between classpath entries.

## JFLAP computation evidence

See [EVIDENCE.md](EVIDENCE.md) for the evidence inventory. The report for #8 captures `011010` accepting, #12 captures `010101000` accepting and `0101011` rejecting, and #16 captures `10100` rejecting. The handwritten traces and these captures agree. [jflap-step-log.txt](jflap-step-log.txt) records the actual JFLAP configurations at each step; all 31 snapshots were checked against the independent simulator.

The images were saved directly from the live JFLAP window content after activating its real Step button. They exclude unrelated desktop content. The helper source is [tools/CaptureJflapSteps.java](tools/CaptureJflapSteps.java).

**Step by State vs Step with Closure:** Step by State exposes individual transitions, including ε transitions. Step with Closure incorporates ε-reachable states automatically around symbol steps. All five supplied files have no ε transitions, so the two simulators agree here. #8 branches because the same state has two outgoing transitions labeled 1. Keep every possible next state; acceptance requires at least one accepting branch after consuming the entire input.

For a screenshot on macOS, press Shift–Command–4 and drag a region, or press Space after that shortcut to choose a window. On Windows, use Windows–Shift–S. On Linux, use the desktop Screenshot application. Include the input, result, and relevant state information in each capture.

## Learning reflection

### 1. Challenges and AI support

Problems 20 and 21 gave me the most trouble because I had to combine the number of 1's with the parity of the number of 0's. I did not avoid any problem because it was challenging. I asked AI to check my automata and help generate multiple test strings so I could identify cases I might have missed. The checks helped me compare my designs with the language definitions.

### 2. What I learned about AND, OR, NFAs, and DFAs

Problems 20 and 21 helped me understand the difference between OR and AND. Separate nondeterministic branches naturally express OR because a string is accepted if at least one branch accepts. That construction alone does not express AND: both conditions must hold for the same input. An NFA can still recognize an AND condition by tracking both conditions together. In my design for problem 21, each state records the number of 1's modulo 3 and whether the number of 0's is odd or even. Only the state satisfying both conditions accepts.

For example, `1111` belongs to problem 20's language because it has 3(1)+1 ones. It does not belong to problem 21's language because it has zero zeros, which is even. This is a useful test for distinguishing OR from AND.

I also learned that NFAs and DFAs share the same basic components: states, transitions, an initial state, and accepting states. A DFA has exactly one next state for each state and input symbol, while an NFA may have several possible next states, no next state, or transitions that consume no input. A DFA is a special case of an NFA.

To avoid missing next states in future controller or compiler tasks, projects, and exams, I will write down the complete set of reachable states after each input symbol and keep every branch until it has no valid transition. I will also check that the entire input has been consumed before deciding that a string is accepted.

### 3. Other insights and questions

This exercise showed me why testing should include the shortest accepted strings, the empty string, typical inputs, and boundary cases. For counting conditions, I should test values immediately below, at, and above the required count. For combined conditions, I should test all four possibilities: both conditions true, only the first true, only the second true, and both false.

Writing down what each state means makes a design easier to explain and check. Keeping the original design and later changes in GitHub also makes the work easier to review. One question I would like to explore further is when an NFA makes a language easier to design, and when tracking several conditions together in a DFA is clearer.
