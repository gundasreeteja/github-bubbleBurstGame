import java.awt.*;

public class Bubble {

  private int x;
  private int y;
  private int radius;
  private int bubbleId;

  public Bubble(int bubbleId, int x, int y, int radius) {
    this.bubbleId = bubbleId;
    this.x = x;
    this.y = y;
    this.radius = radius;
  }

  public int getBubbleId() {
    return bubbleId;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  /* to draw bubble */
  public void draw(Graphics g) {
    g.setColor(Color.black);
    g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
  }

  /* to check if the click is inside the bubble */
  public boolean isInside(int px, int py) {
    int dx = (px - x) * (px - x);
    int dy = (py - y) * (py - y);
    return (dx + dy) < (radius * radius);
  }

}