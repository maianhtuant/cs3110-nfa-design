#!/usr/bin/env python3
"""Compare the JFF transition relations against provisional language predicates."""
from pathlib import Path
from itertools import product
import xml.etree.ElementTree as ET
ROOT = Path(__file__).resolve().parent.parent
LANGUAGES = {
 8: ("Starts with 01 and ends with 10 (confirmed by local course document)", lambda s: s.startswith("01") and s.endswith("10")),
 12: ("Exactly three 1 symbols (inferred; notebook confirmation pending)", lambda s: s.count("1") == 3),
 16: ("Every odd-numbered position is 1, positions start at 1 (inferred)", lambda s: all(c == "1" for c in s[::2])),
 20: ("Odd number of 0 symbols OR number of 1 symbols congruent to 1 modulo 3 (inferred)", lambda s: s.count("0") % 2 == 1 or s.count("1") % 3 == 1),
 21: ("Odd number of 0 symbols AND number of 1 symbols congruent to 1 modulo 3 (inferred)", lambda s: s.count("0") % 2 == 1 and s.count("1") % 3 == 1),
}
def read(n):
 a=ET.parse(ROOT / f"NFA-{n:02}.jff").getroot().find("automaton")
 start={s.attrib["id"] for s in a.findall("state") if s.find("initial") is not None}
 final={s.attrib["id"] for s in a.findall("state") if s.find("final") is not None}
 edges=[(t.findtext("from"),t.findtext("read") or "",t.findtext("to")) for t in a.findall("transition")]
 return start,final,edges

def trace(n,s):
 start,final,edges=read(n)
 def close(states):
  states=set(states)
  while True:
   nxt=states | {b for a,c,b in edges if a in states and c == ""}
   if nxt == states: return states
   states=nxt
 states=close(start); snapshots=[states]
 for c in s:
  states=close({b for a,x,b in edges if a in states and x == c}); snapshots.append(states)
 return bool(states & final),snapshots

def check():
 for n,(desc,predicate) in LANGUAGES.items():
  count=0
  for length in range(13):
   for chars in product("01",repeat=length):
    s="".join(chars); actual,_=trace(n,s)
    if actual != predicate(s): raise AssertionError((n,s,actual,predicate(s)))
    count+=1
  previous=True
  for s in (ROOT/f"NFA-{n:02}t.txt").read_text().split():
   expected=predicate(s); actual,_=trace(n,s)
   assert actual == expected
   assert previous or not expected, "Accepted input after rejected input"
   previous=expected
  print(f"#{n}: {count} binary strings through length 12 match predicate; tests ordered correctly")
 print("Notebook confirmation is still required for inferred predicates. GUI verification is separate.")
if __name__ == "__main__": check()
