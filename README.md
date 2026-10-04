# CS3110 NFA design exercises

Problems **8, 12, 16, 20, and 21**. The initial commit preserves the five original JFLAP files; the next commit adds tests, diagrams, reports, and reproducible verification.

**Work in progress — not ready for grading.** Problem #8 was confirmed from a local course document. Definitions for #12, #16, #20, and #21 were inferred from the supplied machines and need confirmation against the Teams notebook. Real JFLAP GUI screenshots, hand-drawn computation trees for at least three problems, and the student's personal learning reflection remain outstanding.

| Problem | Language currently checked | Report |
|---|---|---|
| 8 | Starts with 01 and ends with 10 | [NFA-08r.md](NFA-08r.md) |
| 12 | Exactly three 1 symbols — inferred | [NFA-12r.md](NFA-12r.md) |
| 16 | Every odd position is 1, counting from 1 — inferred | [NFA-16r.md](NFA-16r.md) |
| 20 | Odd number of 0 symbols OR number of 1 symbols is 1 modulo 3 — inferred | [NFA-20r.md](NFA-20r.md) |
| 21 | Odd number of 0 symbols AND number of 1 symbols is 1 modulo 3 — inferred | [NFA-21r.md](NFA-21r.md) |

## Files and verification

For each problem, `NFA-XX.jff` is the original automaton, `NFA-XXt.txt` is a JFLAP input file, and `NFA-XXr.md` contains its diagram, expected test results, and evidence status. Tests contain only strings, one per line, with accepting strings first. The empty string must be added manually with **Enter Lambda**; a blank line in the text file is not an empty-string test. [JFLAP's official tutorial](https://www.jflap.org/tutorial/fa/createfa/fa.html) documents whitespace-delimited loading and Multiple Run.

The original designs were not changed: no discrepancy was found against the current predicates. No artificial mistakes or backdated commits were introduced. Four machines are deterministic (with #16 partial); these are mathematically valid NFAs, though the instructor may want explicit nondeterminism.

Run the independent check:

```sh
python3 tools/verify.py
```

It checks **8,191 binary strings per machine**, including ε, through length 12. This is a bounded check, not a proof that the inferred predicates match the assigned problems.

`verification.txt` records actual JFLAP 7.1 Step by State and Step with Closure simulator results for every prepared test, plus ε and an out-of-alphabet input. This uses the JFLAP runtime without a window and **does not replace GUI Multiple Run or screenshot evidence**. To reproduce with your own JFLAP 7.1 jar:

```sh
mkdir -p /tmp/cs3110-jflap-check
javac -cp /path/to/JFLAP7.1.jar -d /tmp/cs3110-jflap-check tools/JflapCheck.java
java -Djava.awt.headless=true -cp /tmp/cs3110-jflap-check:/path/to/JFLAP7.1.jar JflapCheck .
```

On Windows use `;` instead of `:` between classpath entries.

## Finish the JFLAP evidence

1. Confirm all five definitions against the notebook. If one differs, commit the corrected design and tests with an explanation.
2. Open each `NFA-XX.jff`, select Input → Multiple Run, load `NFA-XXt.txt`, add ε with Enter Lambda, and click Run Inputs. Compare every result with its report. Save screenshots in `images/` and embed them in the matching report.
3. For at least three problems, use the proposed computation examples or an actual surprising string. Draw the complete computation tree by hand, photograph it, and capture the initial JFLAP configuration and each subsequent step. The reports supply state sets to check your drawing against, not substitutes for hand-drawn evidence.
4. Complete the personal reflection below from your actual experience, then commit it.

**Step by State vs Step with Closure:** Step by State exposes individual transitions, including ε transitions. Step with Closure incorporates ε-reachable states automatically around symbol steps. All five supplied files have no ε transitions, so the two simulators agree here. #8 branches because the same state has two outgoing transitions labeled 1. Keep every possible next state; acceptance requires at least one accepting branch after consuming the entire input.

For a screenshot on macOS, press Shift–Command–4 and drag a region, or press Space after that shortcut to choose a window. On Windows, use Windows–Shift–S. On Linux, use the desktop Screenshot application. Include the input, result, and relevant state information in each capture.

## Learning reflection — student input required

These prompts need the student's own experience; no personal struggle or surprise has been invented.

- Which problem gave you the most trouble, and why? Did you skip a harder problem because of the deadline? What did you ask AI or the instructor?
- Which strings actually surprised you? Which next states did you overlook, and why? Explain the fix and reference the corresponding commit and evidence.
- How will you avoid these mistakes in future controller/compiler tasks, projects, and exams?
- What other insights or questions should the grader know?

Technical observations you can consider: #8 requires retaining both branches after `011`; #16 demonstrates that reaching a final state early does not guarantee acceptance; #20 and #21 distinguish OR from AND when combining parity and remainder conditions. These are verified observations, not claims about the student's personal experience.

AI assistance was used to inspect the existing automata, infer provisional predicates, generate tests and diagrams, and run verification. The student still needs to confirm the statements and supply personal reflections and hand-drawn/GUI evidence.
