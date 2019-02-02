package frc.robot.util;

import edu.wpi.first.wpilibj.SPI;

public class PixySPIDriver {
  private Logger logger = new Logger(PixySPIDriver.class.getName());
  private SPI pixy;

  public PixySPIDriver(SPI.Port port) {
    logger.info("constructor port: " + port);
    pixy = new SPI(port);
    pixy.setMSBFirst();
    pixy.setChipSelectActiveLow();
    pixy.setClockRate(1000);
    pixy.setSampleDataOnTrailingEdge();
    pixy.setClockActiveLow();
  }

  public void turnOnLamp() {
    logger.info("turnOnLamp send: " + PixyMessage.bytesToString(PixyMessage.TURN_ON_LAMP));
    byte[] data = new byte[9];
    pixy.transaction(PixyMessage.TURN_ON_LAMP, data, 2);
    logger.info("turnOnLamp receive: " + PixyMessage.bytesToString(data));
  }

  public void turnOffLamp() {
    logger.info("turnOffLamp send: " + PixyMessage.bytesToString(PixyMessage.TURN_OFF_LAMP));
    byte[] data = new byte[9];
    pixy.transaction(PixyMessage.TURN_OFF_LAMP, data, 2);
    logger.info("turnOffLamp receive: " + PixyMessage.bytesToString(data));
  }

  public PixyLine lineTracking() {
    logger.info("lineTracking send: " + PixyMessage.bytesToString(PixyMessage.LINE_TRACKING));
    byte[] data = new byte[14];
    pixy.transaction(PixyMessage.LINE_TRACKING, data, 2);
    logger.info("lineTracking receive: " + PixyMessage.bytesToString(data));
    return new PixyLine(data);
  }

  public PixyBlock[] objectTracking() {
    logger.info("objectTracking send: " + PixyMessage.bytesToString(PixyMessage.OBJECT_TRACKING));
    byte[] data = new byte[17];
    // byte[] data = new byte[34];
    pixy.transaction(PixyMessage.OBJECT_TRACKING, data, 2);
    logger.info("objectTracking receive: " + PixyMessage.bytesToString(data));
    byte[] data2 = new byte[34];
    pixy.read(false, data2, 2);
    logger.info("objectTracking receive2: " + PixyMessage.bytesToString(data2));
    return new PixyBlock[] {new PixyBlock(data), new PixyBlock(data2)};
  }
}
