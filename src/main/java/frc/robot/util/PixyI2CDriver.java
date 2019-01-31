package frc.robot.util;

import edu.wpi.first.wpilibj.I2C;

public class PixyI2CDriver {
  private Logger logger = new Logger(PixyI2CDriver.class.getName());
  private I2C pixy;

  public PixyI2CDriver() {
    pixy = new I2C(I2C.Port.kOnboard, 0x54);
  }

  public void turnOnLamp() {
    logger.info("turnOnLamp send: " + PixyMessage.bytesToString(TURN_ON_LAMP));
    byte[] data = new byte[9];
    pixy.transaction(PixyMesage.TURN_ON_LAMP, TURN_ON_LAMP.length, dataReceived, dataReceived.length);
    logger.info("turnOnLamp receive: " + PixyMessage.bytesToString(data));
  }

  public void turnOffLamp() {
    logger.info("turnOffLamp send: " + PixyMessage.bytesToString(TURN_OFF_LAMP));
    byte[] data = new byte[9];
    pixy.transaction(PixyMessage.TURN_OFF_LAMP, TURN_OFF_LAMP.length, dataReceived, dataReceived.length);
    logger.info("turnOffLamp receive: " + PixyMessage.bytesToString(data));
  }

  public PixyLine lineTracking() {
    logger.info("lineTracking send: " + PixyMessage.bytesToString(LINE_TRACKING));
    byte[] data = new byte[14];
    pixy.transaction(PixyMessage.LINE_TRACKING, LINE_TRACKING.length, dataReceived, dataReceived.length);
    logger.info("lineTracking receive: " + PixyMessage.bytesToString(data));
    return new PixyLine(dataReceived);
  }
}
