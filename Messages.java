import javax.swing.*;

class Messages {

  /*
   * this method is called in case if the user clicks on restart for the first
   * time
   */
  public void startErrorMsg() {
    JOptionPane.showInternalMessageDialog(null, "Please use Start Button for first time!", "ERROR",
        JOptionPane.ERROR_MESSAGE);
  }

  /*
   * this method is called in case if the user clicks on point where the circle
   * cannot fit
   */
  public void selectionErrorMsg() {
    JOptionPane.showInternalMessageDialog(null,
        "Invalid Selection! \nPlease click again!", "ERROR",
        JOptionPane.ERROR_MESSAGE);
  }

  /* this method is called when game starts */
  public void selectionPlaceMsg(int bubbleCount) {
    JOptionPane.showInternalMessageDialog(null, "Place the " + bubbleCount + " bubbles in the field", "FIRST STEP",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /* this method is called when the selection of bubbles is completed */
  public void selectionCompleteMsg(int roundTime) {
    JOptionPane.showInternalMessageDialog(null,
        "Selection Complete! \nYour game starts now! \nYou will have " + roundTime
            + " seconds to complete the round!",
        "DONE",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /* this method is called to show round completed */
  public void roundCompleteMsg(int round) {
    JOptionPane.showInternalMessageDialog(null, "Round " + round + " Completed!", "DONE",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /* this method is called to show round completed */
  public void roundStartedMsg(int round, int roundTime) {
    JOptionPane.showInternalMessageDialog(null,
        "Round " + round + " Starts! \nYou will have " + roundTime + " seconds to complete the round!", "GET READY",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /* this method is called to show game over */
  public void gameOverMsg() {
    JOptionPane.showInternalMessageDialog(null, "Game Over!", "GAME OVER",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /* this method is called to show game over */
  public void gameOverOutsideBubbleMsg() {
    JOptionPane.showInternalMessageDialog(null, "Game Over! You clicked outside the bubble", "GAME OVER",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /* this method is called to show game completed after round 10 */
  public void gameCompletedMsg() {
    JOptionPane.showInternalMessageDialog(null, "Game Completed!", "SUCCESS",
        JOptionPane.INFORMATION_MESSAGE);
  }

}