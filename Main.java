import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class Main {
  public static void main(String[] args) {

    JFrame frame = new JFrame("Bubble Burst Game");
    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    /* Program starts from here */
    BubbleBurst bb = new BubbleBurst();
    bb.bubbleBurstProgram(frame);

  }
}