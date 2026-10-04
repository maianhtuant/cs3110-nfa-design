# Problem 8

Language: Starts with 01 and ends with 10 (confirmed by local course document). Alphabet: `{0,1}`.

This definition matches the local course document.

## NFA diagram

![NFA diagram](images/NFA-08-diagram.svg)

Generated from the supplied JFF transition relation; double circles denote accepting states.

## Test cases

Load `NFA-08t.txt` using Input → Multiple Run → Load Inputs. It contains only input strings, one per line, with accepted inputs first. Blank lines are whitespace and do not load the empty string; test ε separately using Enter Lambda.

| Input | Expected |
|---|---|
| `010` | Accept |
| `0110` | Accept |
| `01010` | Accept |
| `01110` | Accept |
| `010010` | Accept |
| `011010` | Accept |
| `0111110` | Accept |
| `0101010` | Accept |
| `0` | Reject |
| `1` | Reject |
| `01` | Reject |
| `10` | Reject |
| `00` | Reject |
| `11` | Reject |
| `0100` | Reject |
| `1010` | Reject |
| `0010` | Reject |
| `01101` | Reject |
| `01011` | Reject |
| ε (enter manually) | Reject |

The suite includes short inputs, boundary counts, varied symbol orders, and longer repetitions. Nonbinary input `2` should reject and can be entered manually.

## Verification status

The included independent simulator checks every binary string through length 12 against the predicate above. This is bounded evidence, and inferred predicates still require notebook confirmation. JFLAP runtime results are in `verification.txt`. **GUI Multiple Run screenshot remains to be captured**; runtime verification does not fulfill that screenshot requirement.

## Computation example `011010`

This is a proposed corner case for study, not a claim that the student made a mistake. Final result: **Accept**.

| Consumed prefix | Active states |
|---|---|
| ε | {q0} |
| `0` | {q1} |
| `01` | {q2, q3} |
| `011` | {q2, q3} |
| `0110` | {q2, q4} |
| `01101` | {q2, q3} |
| `011010` | {q2, q4} |

After `011`, retain both q2 and q3. A branch that guesses an ending too early can die while the loop branch continues. After `0110`, q2 and accepting q4 coexist; q4 has no outgoing edge, so the remaining input must be processed by the q2 branch.

**Student evidence pending:** draw this computation by hand, including all branches for #8, then capture JFLAP Step by State or Step with Closure at the initial configuration and after each symbol. Repeat for at least three problems. Add the real hand-drawn photo and screenshots here; the table above does not replace them.
