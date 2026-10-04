import automata.Automaton;
import automata.fsa.FSAStepByStateSimulator;
import automata.fsa.FSAStepWithClosureSimulator;
import file.XMLCodec;
import java.nio.file.*;
import java.util.*;
public class JflapCheck {
  static boolean expected(int n, String s) {
    long zeros=s.chars().filter(c->c=='0').count(), ones=s.chars().filter(c->c=='1').count();
    switch(n) {
      case 8: return s.startsWith("01") && s.endsWith("10");
      case 12: return ones==3;
      case 16: for(int i=0;i<s.length();i+=2) if(s.charAt(i)!='1') return false; return true;
      case 20: return zeros%2==1 || ones%3==1;
      case 21: return zeros%2==1 && ones%3==1;
      default: throw new IllegalArgumentException();
    }
  }
  public static void main(String[] args) throws Exception {
    Path root=Paths.get(args[0]);
    System.out.println("Actual JFLAP 7.1 simulator verification (headless, not GUI Multiple Run)");
    System.out.println("Predicates for #12/#16/#20/#21 are inferred; notebook confirmation pending.");
    for(int n:new int[]{8,12,16,20,21}) {
      String base=String.format("NFA-%02d",n);
      Automaton a=(Automaton)new XMLCodec().decode(root.resolve(base+".jff").toFile(),new HashMap<>());
      System.out.println("\nProblem "+n);
      List<String> inputs=new ArrayList<>(Files.readAllLines(root.resolve(base+"t.txt")));
      inputs.add(""); inputs.add("2");
      for(String s:inputs) {
        boolean want=s.equals("2")?false:expected(n,s);
        boolean byState=new FSAStepByStateSimulator(a).simulateInput(s);
        boolean closure=new FSAStepWithClosureSimulator(a).simulateInput(s);
        if(byState!=want || closure!=want) throw new AssertionError(base+" input "+s);
        System.out.println((s.isEmpty()?"<epsilon>":s)+"\t"+(want?"Accept":"Reject")+"\tPASS (both simulators)");
      }
    }
  }
}
