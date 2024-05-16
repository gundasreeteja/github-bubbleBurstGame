import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class GameFrame extends Frame {
  private ArrayList<Bubble> bubbles = new ArrayList<>();
  private ArrayList<NeighborhoodCoord> nCoords = new ArrayList<>();
  private int score = 0, finalScore = 0;
  private int round = 1, totalRounds = 10;
  private Timer timer;
  private int time = 0;
  private int neighbourhood = 50;
  private int roundTime = 15;
  private int bubbleCount = 5, bubbleDone = 0;
  private int frameHeight = 400, frameWidth = 400;
  private int radius = 10;
  private boolean isSelectionDone = false;
  private boolean isGameOver = false;
  private boolean isInsideBubble = false;
  Messages msg = new Messages();
  Random random = new Random();

  public GameFrame(int bubbleCount) {
    this.bubbleCount = bubbleCount;
    setTitle("Game Field");
    setSize(frameWidth, frameHeight);
    setVisible(true);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        timer.stop();
        dispose();
      }
    });

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent me) {
        /*
         * round 1 code
         * Will check if the selection is over and bubbles are done
         * bubbleDone<bubbleCount -> to check if the required bubbles are selected
         * isSelectionDone -> Once selection is Done, next mouse click will remove
         * bubbles if clicked for round 1
         */
        if (round == 1 && bubbleDone < bubbleCount && !isSelectionDone) {
          int x = me.getX();
          int y = me.getY();
          System.out.println("one x: " + x + " y: " + y);
          if (isValidPointForBubble(x, y)) {
            System.out.println("two x: " + x + " y: " + y);
            /* Increasing bubble count as we are creating new bubble here */
            bubbleDone++;
            /* Passing bubbleDone as bubble Id */
            Bubble bubble = new Bubble(bubbleDone, x, y, radius);
            bubbles.add(bubble);
            repaint();
            if (bubbleDone == bubbleCount) {
              calNeighbourhoods(neighbourhood);
              msg.selectionCompleteMsg(roundTime);
              /*
               * Setting to true as the next mouse click will move to removal of bubbles check
               */
              isSelectionDone = true;
              timer.restart();
            }
          } else {
            msg.selectionErrorMsg();
          }
        } else {
          int x = me.getX();
          int y = me.getY();
          System.out.println("three x: " + x + " y: " + y);
          isInsideBubble = false;
          for (Bubble bubble : bubbles) {
            if (bubble.isInside(x, y)) {
              isInsideBubble = true;
              score++;
              int bubbleId = bubble.getBubbleId();
              bubbles.remove(bubble);
              for (NeighborhoodCoord nCoord : nCoords) {
                if (nCoord.getBubbleId() == bubbleId) {
                  nCoords.remove(nCoord);
                }
              }
            }
          }
          /* Checking if the click is not within any bubble */
          if (!isInsideBubble) {
            timer.stop();
            msg.gameOverOutsideBubbleMsg();
            isGameOver = true;
          }

          repaint();
        }
      }
    });

    timer = new Timer(1000, new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        time++;
        /*
         * If rounds more than 10 then game completed
         */
        if (round > totalRounds) {
          System.out.println("Hello");
          timer.stop();
        } else {
          /*
           * if time is over
           */
          if (time == roundTime) {
            timer.stop();
            /*
             * if time is over and bubbles are not burst then game is over
             */
            if (round <= totalRounds && bubbles.isEmpty()) {
              System.out.println("End of Round " + round + ". Score for this round: " + score);
              finalScore += score;
              msg.roundCompleteMsg(round);
              round++;
              repositionGlobal();
            } else {
              System.out.println("Game Over! Final Score: " + finalScore);
              timer.stop();
              msg.gameOverMsg();
              isGameOver = true;
              repaint();
            }
          } else {
            /*
             * if time is not over
             */
            if (round <= totalRounds) {
              repositionLocal();
              // overlaps();
              repaint();
              /*
               * if time is not over and bubbles are empty, proceed to next round
               */
              if (bubbles.isEmpty()) {
                timer.stop();
                System.out.println("End of Round " + round + ". Score for this round: " + score);
                finalScore += score;
                msg.roundCompleteMsg(round);
                round++;
                repositionGlobal();
                // overlaps();
              }
            } else {
              timer.stop();
              System.out.println("Game Over!!! Final Score: " + finalScore);
              msg.gameOverMsg();
              isGameOver = true;
              repaint();
            }
          }
        }
      }
    });
    repositionGlobal();
    // overlaps();
  }

  /* Calculating neighbourhoods and storing */
  public void calNeighbourhoods(int neighborhood) {
    for (Bubble bubble : bubbles) {
      int x = bubble.getX();
      int y = bubble.getY();
      int bubbleId = bubble.getBubbleId();
      int maxX = 0, minX = 0, maxY = 0, minY = 0;
      /* checking max and min are within neighborhoods and within frames */
      if (x - neighborhood <= 20) {
        minX = radius + 20;
      } else {
        minX = x - neighborhood;
      }
      if (x + neighborhood >= frameWidth - radius) {
        maxX = frameWidth - 2 * radius;
      } else {
        maxX = x + neighborhood;
      }
      if (y + neighborhood >= frameHeight - radius) {
        /* Extra subraction(radius-1) is required due to Frame Header */
        maxY = frameHeight - 2 * radius - (radius - 1);
      } else {
        maxY = y + neighborhood;
      }
      if (y - neighborhood <= 30) {
        /* Extra 20 for Frame Header */
        minY = radius + 30 + 20;
      } else {
        minY = y - neighborhood;
      }
      System.out.println(minX + " " + maxX + " " + minY + " " + maxY);
      nCoords.add(new NeighborhoodCoord(bubbleId, maxX, maxY, minX, minY));
    }
  }

  /*
   * Repositions bubbles and calculates neighborhoods after every round
   */
  private void repositionGlobal() {
    if (round == 1) {
      timer.stop();
    } else if (round <= totalRounds) {
      score = 0;
      roundTime -= 1;
      System.out.println("Round " + round + " started.");
      msg.roundStartedMsg(round, roundTime);
      time = 0;
      neighbourhood += 18;
      drawBubbles(bubbleCount);
      calNeighbourhoods(neighbourhood);
      timer.restart();
    } else {
      repaint();
      timer.stop();
    }
  }

  /*
   * Repositions bubbles for every second in each round
   */
  private void repositionLocal() {
    for (NeighborhoodCoord nCoord : nCoords) {
      int maxX = nCoord.getMaxX();
      int minX = nCoord.getMinX();
      int maxY = nCoord.getMaxY();
      int minY = nCoord.getMinY();
      Point randomPoint = getRandomCoordinates(maxX, minX, maxY, minY);
      int bubbleId = nCoord.getBubbleId();
      for (Bubble bubble : bubbles) {
        if (bubble.getBubbleId() == bubbleId) {
          bubble.setX(randomPoint.getX());
          bubble.setY(randomPoint.getY());
        }
      }
    }
  }

  /* to check for overlapping */
  public void overlaps() {
    for (Bubble one : bubbles) {
      for (Bubble two : bubbles) {
        int dx = one.getX() - two.getX();
        int dy = one.getY() - two.getY();
        if (dx * dx + dy * dy < radius * radius && one != two) {
          System.out.println("Bubble Intersects");
          int bubbleId = one.getBubbleId();
          int minX = 0, maxX = 0, minY = 0, maxY = 0;
          for (NeighborhoodCoord nCoord : nCoords) {
            if (nCoord.getBubbleId() == bubbleId) {
              minX = nCoord.getMinX();
              maxX = nCoord.getMaxX();
              minY = nCoord.getMinY();
              maxY = nCoord.getMaxY();
            }
          }
          if (dx < 0) {
            if (one.getX() - radius * 2 < minX) {
              one.setX(one.getX() + 40);
            } else {
              one.setX(one.getX() - 20);
            }
          }
          if (dx > 0) {
            if (one.getX() + radius * 4 > maxX) {
              one.setX(one.getX() - 40);
            } else {
              one.setX(one.getX() + 20);
            }
          }
          if (dy < 0) {
            if (one.getY() - radius * 2 < minY) {
              one.setY(one.getY() + 40);
            } else {
              one.setY(one.getY() - 20);
            }
          }
          if (dy > 0) {
            if (one.getY() + radius * 4 > maxY) {
              one.setY(one.getY() - 40);
            } else {
              one.setY(one.getY() + 20);
            }
          }
          repaint();
        }
      }
    }
  }

  /* Get random point */
  public Point getRandomCoordinates(int maxX, int minX, int maxY, int minY) {
    int x = random.nextInt(maxX - minX) + minX;
    int y = random.nextInt(maxY - minY) + minY;
    System.out.println("Local x " + x + " y " + y);
    return new Point(x, y);
  }

  /* this method is called to check if the bubble can fit inside the panel */
  public boolean isValidPointForBubble(int x, int y) {
    if (x < radius || y < 3 * radius || x > frameWidth - radius || y > frameHeight - radius || x - radius < -radius
        || y - radius < -radius) {
      return false;
    }
    if (bubbleDone > 0) {
      for (Bubble bubble : bubbles) {
        int dx = x - bubble.getX();
        int dy = y - bubble.getY();
        if (dx * dx + dy * dy < radius * radius) {
          return false;
        }
      }
    }
    return true;
  }

  /*
   * Generates bubbles
   */
  public void drawBubbles(int bubbleCount) {
    bubbles.clear();
    /*
     * Looping from 1 its needs to be set as bubbleId
     */
    for (int i = 1; i < bubbleCount + 1; i++) {
      int x = random.nextInt(frameWidth - 2 * radius) + radius;
      if (x < 10) {
        x += 10;
      }
      // /* Extra subraction is required due to Frame header */
      int y = random.nextInt(frameHeight - 2 * radius) + radius;
      if (y < 30) {
        y += 30;
      }
      Bubble bubble = new Bubble(i, x, y, radius);
      bubbles.add(bubble);
    }
  }

  public void paint(Graphics g) {
    if (round > totalRounds || isGameOver == true) {
      timer.stop();
      g.drawString("Game Completed!", 75, 100);
      g.drawString("Play again!! Close this and use the restart button", 75, 150);
      g.drawString("Final Score: " + finalScore, 75, 200);
    } else {
      for (Bubble bubble : bubbles) {
        bubble.draw(g);
      }
      g.setColor(Color.BLACK);
      g.drawString("Round: " + round + " Score: " + score + " Timer: " + time, 20, 30);
    }

  }
}
