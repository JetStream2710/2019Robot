package frc.robot.util;

public class PixyLine {

  private byte[] bytes;
  private boolean isUpsideDown;

  public PixyLine(byte[] bytes, boolean isUpsideDown) {
    this.bytes = bytes;
    this.isUpsideDown = isUpsideDown;
  }

  /** @return a number between 0 to 78 */
  public int getLowerX() {
    if (shouldFlip()) {
      return mirrorAdjust((int) bytes[10], 78);
    }
    return mirrorAdjust((int) bytes[8], 78);
  }

  /** @return a number between 0 to 51 */
  public int getLowerY() {
    if (shouldFlip()) {
      return mirrorAdjust((int) bytes[11],51);
    }
    return mirrorAdjust((int) bytes[9],51);
  }

  /** @return a number between 0 to 78 */
  public int getUpperX() {
    if (shouldFlip()) {
      return mirrorAdjust((int) bytes[8],78);
    }
    return mirrorAdjust((int) bytes[10],78);
  }

  /** @return a number between 0 to 51 */
  public int getUpperY() {
    if (shouldFlip()) {
      return mirrorAdjust((int) bytes[9],51);
    }
    return mirrorAdjust((int) bytes[11],51);
  }

  public int getId() {
    return (int) bytes[12];
  }

  private boolean shouldFlip() {
    return (int) bytes[9] < (int) bytes[11];
  }

  private int mirrorAdjust(int num, int max) {
    if (isUpsideDown) {
      return max - num;
    } else {
      return num;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("lower coordinates: (");
    sb.append(getLowerX());
    sb.append(",");
    sb.append(getLowerY());
    sb.append(") upper coordinates: (");
    sb.append(getUpperX());
    sb.append(",");
    sb.append(getUpperY());
    sb.append(") id: ");
    sb.append(getId());
    return sb.toString();
  }
}
