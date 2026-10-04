# Evidence inventory

The saved JFLAP designs and all expected test results have been checked. The report files embed the following evidence.

| Problem | Batch run | Computation screenshots |
|---|---|---|
| 8 | [Complete test suite](images/n08-batch.png) | `011010`: initial state and six symbol steps; Accept |
| 12 | [Complete test suite](images/n12-batch.png) | `010101000`: initial state and nine symbol steps; Accept. `0101011`: initial state and seven attempted steps; Reject |
| 16 | [Complete test suite](images/n16-batch.png) | `10100`: initial state and five attempted steps; Reject |
| 20 | [Complete test suite](images/n20-batch.png) | Additional computation example is explained in the report |
| 21 | [Complete test suite](images/n21-batch.png) | Additional computation example is explained in the report |

Each batch screenshot contains its entire problem-specific test file, with accepted inputs before rejected inputs. Earlier runs are retained as `images/nXX-epsilon.png` to document the empty-string checks: #16 accepts ε; all others reject it.

## Handwritten evidence

[Homework-NFA.pdf](Homework-NFA.pdf) contains the original design diagrams for all five problems. [handwritetest.pdf](handwritetest.pdf) contains the revised handwritten input traces for #8, #12, and #16. All revised traces are correct. The #12 report explains the earlier fourth-1 mistake and its correction; commit history preserves the change.

The handwritten #8 page records the active-state sets after each prefix. Its matching GUI screenshots show the individual live and rejected branches. The handwritten #12 and #16 computations have one branch.

## JFLAP capture verification

All 31 step images were captured from the real JFLAP 7.1 Swing simulation panes by an authorized Java helper invoking the actual Step button. These are window-content captures, not simulated drawings of the interface. They include the original input and its consumed/remaining portions, highlighted states, and configuration colors. Blue indicates a live configuration, green indicates acceptance, and red indicates rejection.

[jflap-step-log.txt](jflap-step-log.txt) records each actual GUI configuration. Every live-state set and remaining input was checked against the independent transition simulator. A rejected configuration can remain visible with the failing symbol unread; it is no longer a live branch.
