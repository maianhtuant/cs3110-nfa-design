# Problem 16

Language: Every odd-numbered position is 1, positions start at 1 (inferred). Alphabet: `{0,1}`.

**Provisional:** this definition was inferred from the automaton. Confirm against the notebook before submission.

## NFA diagram

![NFA diagram](images/NFA-16-diagram.svg)

Generated from the supplied JFF transition relation; double circles denote accepting states.

## Test cases

Load `NFA-16t.txt` using Input → Multiple Run → Load Inputs. It contains only input strings, one per line, with accepted inputs first. Blank lines are whitespace and do not load the empty string; test ε separately using Enter Lambda.

| Input | Expected |
|---|---|
| `1` | Accept |
| `10` | Accept |
| `11` | Accept |
| `101` | Accept |
| `111` | Accept |
| `1010` | Accept |
| `10101` | Accept |
| `11111` | Accept |
| `101010` | Accept |
| `0` | Reject |
| `00` | Reject |
| `01` | Reject |
| `100` | Reject |
| `110` | Reject |
| `10100` | Reject |
| `1001` | Reject |
| `0101` | Reject |
| `110101` | Reject |
| ε (enter manually) | Accept |

The suite includes short inputs, boundary counts, varied symbol orders, and longer repetitions. Nonbinary input `2` should reject and can be entered manually.

## Verification status

The included independent simulator checks every binary string through length 12 against the predicate above. This is bounded evidence, and inferred predicates still require notebook confirmation. JFLAP runtime results are in `verification.txt`. **GUI Multiple Run screenshot remains to be captured**; runtime verification does not fulfill that screenshot requirement.

## Computation example `110101`

This is a proposed corner case for study, not a claim that the student made a mistake. Final result: **Reject**.

| Consumed prefix | Active states |
|---|---|
| ε | {q0} |
| `1` | {q1} |
| `11` | {q0} |
| `110` | ∅ |
| `1101` | ∅ |
| `11010` | ∅ |
| `110101` | ∅ |

After `11`, the machine is in q0. The next `0` has no transition, so the active set becomes empty and stays empty. Being in an accepting state before all input is read does not imply acceptance.

**Student evidence pending:** draw this computation by hand, including all branches for #8, then capture JFLAP Step by State or Step with Closure at the initial configuration and after each symbol. Repeat for at least three problems. Add the real hand-drawn photo and screenshots here; the table above does not replace them.
