package frc.robot.util;

public class PixyMessage {

  private static final byte[] TURN_ON_LAMP = {
    (byte) 174, (byte) 193, (byte) 22, (byte) 2, (byte) 1, (byte) 0
  };

  private static final byte[] TURN_OFF_LAMP = {
    (byte) 174, (byte) 193, (byte) 22, (byte) 2, (byte) 1, (byte) 1
  };

  private static final byte[] LINE_TRACKING = {
    (byte) 174, (byte) 193, (byte) 48, (byte) 2, (byte) 0, (byte) 7
  };

  private static final byte[] OBJECT_TRACKING = {
    (byte) 174, (byte) 193, (byte) 32, (byte) 2, (byte) 1, (byte) 2
  };

  public static String bytesToString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append((int) (b & 0xFF));
      sb.append(' ');
    }
    return sb.toString();
  }
}
