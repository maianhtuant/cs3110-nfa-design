# Problem 12

Language: Exactly three 1 symbols. Alphabet: `{0,1}`.

The language definition was supplied and confirmed by the student.

## NFA diagram

![NFA diagram](images/NFA-12-diagram.svg)

Generated from the supplied JFF transition relation; double circles denote accepting states.

## Why the design recognizes the language

State q0 means zero 1 symbols have been read, q1 means one, q2 means two, and q3 means three. Each `0` preserves the count; each `1` advances it. Only q3 accepts, and it has no `1` transition, so a fourth 1 kills the computation. Therefore acceptance is equivalent to exactly three 1 symbols.

## Test cases

Load `NFA-12t.txt` using Input → Multiple Run → Load Inputs. It contains only input strings, one per line, with accepted inputs first. Blank lines are whitespace and do not load the empty string; test ε separately using Enter Lambda.

| Input | Expected |
|---|---|
| `111` | Accept |
| `0111` | Accept |
| `1110` | Accept |
| `10101` | Accept |
| `0101010` | Accept |
| `000111000` | Accept |
| `1001001` | Accept |
| `0` | Reject |
| `1` | Reject |
| `11` | Reject |
| `1111` | Reject |
| `000` | Reject |
| `1010` | Reject |
| `11110` | Reject |
| `011110` | Reject |
| `0001111000` | Reject |
| ε (enter manually) | Reject |

The suite includes short inputs, boundary counts, varied symbol orders, and longer repetitions. Nonbinary input `2` should reject and can be entered manually.

## Verification status

The included independent simulator checks every binary string through length 12 against the predicate above. All checks passed against the confirmed language definition. This is bounded test evidence; the argument above explains correctness for arbitrary input lengths. JFLAP runtime results are in `verification.txt`. **GUI Multiple Run screenshot remains to be captured**; runtime verification does not fulfill that screenshot requirement.


## JFLAP and hand-drawn evidence

See [EVIDENCE.md](EVIDENCE.md) for capture instructions and filenames.

<!-- evidence:start -->

Pending: `NFA-12-batch.png`.

<!-- evidence:end -->
