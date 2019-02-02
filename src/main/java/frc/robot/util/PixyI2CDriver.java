package frc.robot.util;

import edu.wpi.first.wpilibj.I2C;

public class PixyI2CDriver {
  private Logger logger = new Logger(PixyI2CDriver.class.getName());
  private I2C pixy;

  public PixyI2CDriver() {
    pixy = new I2C(I2C.Port.kOnboard, 0x54);
  }

  public void turnOnLamp() {
    logger.info("turnOnLamp send: " + PixyMessage.bytesToString(PixyMessage.TURN_ON_LAMP));
    byte[] data = new byte[9];
    pixy.transaction(PixyMessage.TURN_ON_LAMP, PixyMessage.TURN_ON_LAMP.length, data, data.length);
    logger.info("turnOnLamp receive: " + PixyMessage.bytesToString(data));
  }

  public void turnOffLamp() {
    logger.info("turnOffLamp send: " + PixyMessage.bytesToString(PixyMessage.TURN_OFF_LAMP));
    byte[] data = new byte[9];
    pixy.transaction(PixyMessage.TURN_OFF_LAMP, PixyMessage.TURN_OFF_LAMP.length, data, data.length);
    logger.info("turnOffLamp receive: " + PixyMessage.bytesToString(data));
  }

  public PixyLine lineTracking() {
    logger.info("lineTracking send: " + PixyMessage.bytesToString(PixyMessage.LINE_TRACKING));
    byte[] data = new byte[14];
    pixy.transaction(PixyMessage.LINE_TRACKING, PixyMessage.LINE_TRACKING.length, data, data.length);
    logger.info("lineTracking receive: " + PixyMessage.bytesToString(data));
    return new PixyLine(data);
  }
}
