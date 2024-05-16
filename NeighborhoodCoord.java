public class NeighborhoodCoord {
  private int maxX, maxY, minX, minY, bubbleId;

  public NeighborhoodCoord(int bubbleId, int maxX, int maxY, int minX, int minY) {
    this.bubbleId = bubbleId;
    this.maxX = maxX;
    this.maxY = maxY;
    this.minX = minX;
    this.minY = minY;
  }

  public int getBubbleId() {
    return bubbleId;
  }

  public int getMaxX() {
    return maxX;
  }

  public int getMaxY() {
    return maxY;
  }

  public int getMinX() {
    return minX;
  }

  public int getMinY() {
    return minY;
  }

}