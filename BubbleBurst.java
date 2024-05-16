import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;

public class BubbleBurst implements ActionListener {

  boolean startButton = false;
  int bubbleCount = 5;
  JPanel panel;
  JButton startBtn, restartBtn;
  JLabel header;
  JSlider slider;

  public void bubbleBurstProgram(JFrame frame) {

    panel = new JPanel();
    startBtn = new JButton("Start");
    restartBtn = new JButton("Restart");

    header = new JLabel("Bubble Sort Game! Click Start to begin.", JLabel.CENTER);

    slider = new JSlider(JSlider.VERTICAL, 4, 6, 5);
    slider.setMajorTickSpacing(1);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

    Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
    labelTable.put(4, new JLabel("Easy (4 bubbles)"));
    labelTable.put(5, new JLabel("Medium (5 bubbles)"));
    labelTable.put(6, new JLabel("High (6 bubbles)"));
    slider.setLabelTable(labelTable);

    panel.add(header);
    panel.add(startBtn);
    panel.add(restartBtn);
    panel.add(slider);
    frame.add(panel);

    // getting the slider value
    slider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        bubbleCount = slider.getValue();
      }
    });
    startBtn.addActionListener(this);
    restartBtn.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == startBtn) {
      startButton = true;
      new GameFrame(bubbleCount);

    }
    if (e.getSource() == restartBtn) {
      // First time start button is needed
      if (startButton == true) {
        new GameFrame(bubbleCount);
      } else {
        Messages startErrMsg = new Messages();
        startErrMsg.startErrorMsg();
      }
    }
  }
}