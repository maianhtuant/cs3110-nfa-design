import automata.*;
import automata.fsa.*;
import file.XMLCodec;
import gui.action.SimulateAction;
import gui.environment.*;
import gui.sim.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.*;
import java.util.*;
import javax.imageio.ImageIO;

/** Captures real JFLAP Swing panes after clicking their actual Step button. */
public class CaptureJflapSteps {
  static <T> T find(Component component, Class<T> type) {
    if(type.isInstance(component)) return type.cast(component);
    if(component instanceof Container) for(Component child:((Container)component).getComponents()) {
      T found=find(child,type); if(found!=null) return found;
    }
    return null;
  }
  static AbstractButton stepButton(Component component) {
    if(component instanceof AbstractButton && "Step".equals(((AbstractButton)component).getText()))
      return (AbstractButton)component;
    if(component instanceof Container) for(Component child:((Container)component).getComponents()) {
      AbstractButton found=stepButton(child); if(found!=null) return found;
    }
    return null;
  }
  static void capture(EnvironmentFrame frame, Path output) throws Exception {
    JRootPane root=frame.getRootPane();
    BufferedImage image=new BufferedImage(root.getWidth()*2,root.getHeight()*2,BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics=image.createGraphics();
    graphics.scale(2,2); root.printAll(graphics); graphics.dispose();
    ImageIO.write(image,"png",output.toFile());
  }
  static void run(Path root,int number,String input,String suffix) throws Exception {
    String base=String.format("n%02d",number);
    final EnvironmentFrame[] frame=new EnvironmentFrame[1];
    final Environment[] environment=new Environment[1];
    SwingUtilities.invokeAndWait(()-> {
      Automaton a=(Automaton)new XMLCodec().decode(root.resolve(base+".jff").toFile(),new HashMap<>());
      frame[0]=FrameFactory.createFrame(a);
      environment[0]=frame[0].getEnvironment();
      environment[0].setFile(root.resolve(base+".jff").toFile());
      frame[0].setTitle("JFLAP: "+base+".jff — "+input);
      frame[0].setSize(1360,850); frame[0].setLocation(40,40);
      AutomatonSimulator simulator=new FSAStepWithClosureSimulator(a);
      new SimulateAction(a,environment[0]).handleInteraction(a,simulator,simulator.getInitialConfigurations(input),input);
      frame[0].setVisible(true); frame[0].validate();
    });
    for(int i=0;i<=input.length();i++) {
      final int step=i;
      SwingUtilities.invokeAndWait(()-> {
        try {
          if(step>0) {
            AbstractButton button=stepButton(environment[0].getActive());
            if(button==null) throw new IllegalStateException("No JFLAP Step button");
            button.doClick();
          }
          frame[0].validate();
          ConfigurationPane pane=find(environment[0].getActive(),ConfigurationPane.class);
          StringJoiner configurations=new StringJoiner(", ");
          for(Configuration c:pane.getConfigurations()) {
            FSAConfiguration f=(FSAConfiguration)c;
            configurations.add(c.getCurrentState().getName()+" remaining="+f.getUnprocessedInput()+" status="+pane.getState(c));
          }
          capture(frame[0],root.resolve("images").resolve(base+suffix+String.format("-step-%02d.png",step)));
          String line=base+" input="+input+" step="+step+" "+configurations;
          Files.writeString(root.resolve("jflap-step-log.txt"),line+"\n",StandardOpenOption.CREATE,StandardOpenOption.APPEND);
          System.out.println(line);
        } catch(Exception e) {throw new RuntimeException(e);}
      });
    }
    SwingUtilities.invokeAndWait(()->frame[0].dispose());
  }
  public static void main(String[] args) throws Exception {
    Path root=Paths.get(args[0]).toAbsolutePath();
    Files.writeString(root.resolve("jflap-step-log.txt"),"Actual JFLAP GUI configurations: status 0=live, 1=accept, 2=reject.\n");
    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    SwingUtilities.invokeAndWait(()-> { gui.action.NewAction.showNew(); gui.action.NewAction.hideNew(); });
    run(root,8,"011010","");
    run(root,12,"010101000","");
    run(root,12,"0101011","-reject");
    run(root,16,"10100","");
    System.exit(0);
  }
}
