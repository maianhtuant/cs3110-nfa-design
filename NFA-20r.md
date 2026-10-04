# Problem 20

Language: Odd number of 0 symbols OR number of 1 symbols congruent to 1 modulo 3 (inferred). Alphabet: `{0,1}`.

**Provisional:** this definition was inferred from the automaton. Confirm against the notebook before submission.

## NFA diagram

![NFA diagram](images/NFA-20-diagram.svg)

Generated from the supplied JFF transition relation; double circles denote accepting states.

## Test cases

Load `NFA-20t.txt` using Input → Multiple Run → Load Inputs. It contains only input strings, one per line, with accepted inputs first. Blank lines are whitespace and do not load the empty string; test ε separately using Enter Lambda.

| Input | Expected |
|---|---|
| `0` | Accept |
| `1` | Accept |
| `01` | Accept |
| `10` | Accept |
| `1111` | Accept |
| `001` | Accept |
| `000` | Accept |
| `1111111` | Accept |
| `01010101` | Accept |
| `11` | Reject |
| `111` | Reject |
| `00` | Reject |
| `0011` | Reject |
| `00111` | Reject |
| `0101` | Reject |
| `1010` | Reject |
| `000000` | Reject |
| `111111` | Reject |
| ε (enter manually) | Reject |

The suite includes short inputs, boundary counts, varied symbol orders, and longer repetitions. Nonbinary input `2` should reject and can be entered manually.

## Verification status

The included independent simulator checks every binary string through length 12 against the predicate above. This is bounded evidence, and inferred predicates still require notebook confirmation. JFLAP runtime results are in `verification.txt`. **GUI Multiple Run screenshot remains to be captured**; runtime verification does not fulfill that screenshot requirement.

## Computation example `1111`

This is a proposed corner case for study, not a claim that the student made a mistake. Final result: **Accept**.

| Consumed prefix | Active states |
|---|---|
| ε | {q0} |
| `1` | {q2} |
| `11` | {q4} |
| `111` | {q0} |
| `1111` | {q2} |

Four 1 symbols end in q2 and accept even though there are zero 0 symbols. An OR condition accepts when either condition is satisfied.

**Student evidence pending:** draw this computation by hand, including all branches for #8, then capture JFLAP Step by State or Step with Closure at the initial configuration and after each symbol. Repeat for at least three problems. Add the real hand-drawn photo and screenshots here; the table above does not replace them.
