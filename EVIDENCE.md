# Evidence capture checklist

The automata and expected results are verified. Five student-provided batch screenshots now show every input in each problem-specific test file, with all results checked and correct. Earlier runs are retained as `images/nXX-epsilon.png` to document the empty-string checks. The student will capture the JFLAP screenshots and supply the hand-drawn photos; the README reflection has been added from the student’s notes with AI editing assistance. Save images in this repo's `images` folder.

## Batch runs for all five problems

Open each `nXX.jff` in JFLAP and choose Input → Multiple Run. Use Load Inputs to load `nXXt.txt`, then Run Inputs. Compare every result with the report's expected table. Keep accepted strings above rejected strings.

Test the empty string with Enter Lambda: #16 accepts; the other four reject. In #16, place the empty-string input before the rejected rows if possible, or capture its result in a separate batch run. Blank lines in a loaded input file do not test ε.

Capture the diagram and all test results together if readable. If the whole result table does not fit, take consecutive screenshots with overlapping rows to show every input. Use the following names for the primary screenshot:

| Problem | Save as |
|---|---|
| 8 | `images/n08-batch.png` |
| 12 | `images/n12-batch.png` |
| 16 | `images/n16-batch.png` |
| 20 | `images/n20-batch.png` |
| 21 | `images/n21-batch.png` |

Name additional screenshots `nXX-batch-02.png`, etc., and embed those separately in the report. The existing SVG diagrams already provide NFA pictures; the batch screenshots provide evidence from JFLAP itself.

## Uploaded handwritten notes

[Homework-NFA.pdf](Homework-NFA.pdf) contains the student’s original design diagrams for all five problems. Pages are embedded in the corresponding reports. These notes do not yet show computation trees for the selected input strings. The reports record the missing accepting-state `0` loop in the #12 handwritten sketch and clarify the #21 note about AND and ε; the JFLAP designs are already correct.

## Uploaded handwritten test traces

[handwritetest.pdf](handwritetest.pdf) contains revised active-state traces for #8, #12, and #16, all checked and correct. For #12, the revised notes correctly reject a fourth 1 and accept extra zeros after the third 1. Matching JFLAP step screenshots remain pending. The #8 trace lists state sets; drawing individual branches and marking dead branches would make its computation tree explicit.

## Hand-drawn trees and step screenshots for three problems

Use Input → Step by State or Step with Closure. There are no ε transitions, so either mode works. Capture the initial configuration as step 00. Show the entire JFLAP pane, including the remaining input and active configurations. For each tree, label nodes with the state and remaining input, label edges with the input symbol, and mark dead branches and final acceptance/rejection.

### Problem 8: `011010` — Accept

Draw all branches. The active-state sets are:

| Step | Prefix consumed | Active states | Remaining input |
|---|---|---|---|
| 00 | ε | {q0} | `011010` |
| 01 | `0` | {q1} | `11010` |
| 02 | `01` | {q2, q3} | `1010` |
| 03 | `011` | {q2, q3} | `010` |
| 04 | `0110` | {q2, q4} | `10` |
| 05 | `01101` | {q2, q3} | `0` |
| 06 | `011010` | {q2, q4} | ε |

At step 02, q1 splits to q2 and q3. On the next `1`, the old q3 branch dies, while q2 produces a continuing q2 branch and a new q3 branch. At step 04, q4 is accepting but still has `10` unread, so this branch has not accepted the full input and dies on the next `1`. The q2 branch continues and creates the final accepting q4 branch at step 06.

Save the photograph as `n08-tree.png` and the seven screenshots as `n08-step-00.png` through `n08-step-06.png` in `images/`.

### Problem 16: `110101` — Reject

Draw q0 → q1 on the first `1`, then q1 → q0 on the second `1`. The third symbol is `0`, which has no transition from q0: mark this branch dead, with the attempted `0` and remaining suffix `101`. JFLAP stops here; do not invent further GUI steps. Earlier accepting states do not imply acceptance while input remains.

Save the photograph as `n16-tree.png` and screenshots of the initial state, after each of the two `1`s, and the rejection on `0` as `n16-step-00.png` through `n16-step-03.png`.

### Problem 20: `1111` — Accept

Draw the chain q0 → q2 → q4 → q0 → q2, with each edge labeled `1`. Four 1 symbols equal 3(1)+1, so the first part of the OR condition holds even though the 0 count is even. Only the final q2 configuration, after all input is consumed, establishes acceptance.

Save the photograph as `n20-tree.png` and screenshots as `n20-step-00.png` through `n20-step-04.png`.

These examples are corner cases selected for study. Describe an actual surprise or mistake only if it happened; otherwise say what the examples helped you check. The drawings must be yours, and the screenshots must show actual JFLAP execution.
