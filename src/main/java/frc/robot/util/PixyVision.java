package frc.robot.util;

import edu.wpi.first.wpilibj.SPI;

public class PixyVision {
  private static final long POLL_FREQUENCY_MILLIS = 1 + (1000 / 60);

  private Logger logger = new Logger(PixyVision.class.getName());
  private PixyLine latestLine;
  private PixyBlock leftBlock;
  private PixyBlock rightBlock;

  private boolean turnOnLamp;
  private boolean turnOffLamp;
  private boolean trackLines;
  private boolean trackObjects;

  private boolean isRunning;
  private PixyVisionThread thread;

  public PixyVision(boolean trackLines, boolean trackObjects) {
    this.trackLines = trackLines;
    this.trackObjects = trackObjects;
  }

  public PixyLine getLatestLine() {
    return latestLine;
  }

  public PixyBlock getLeftBlock() {
    return leftBlock;
  }

  public PixyBlock getRightBlock() {
    return rightBlock;
  }

  public void turnOnLamp() {
    turnOnLamp = true;
  }

  public void turnOffLamp() {
    turnOffLamp = true;
  }

  public synchronized void start() {
    if (thread == null) {
      thread = new PixyVisionThread();
      isRunning = true;
      thread.start();
    }
  }

  public synchronized void stop() {
    if (thread != null) {
      isRunning = false;
      thread.interrupt();
      thread = null;
    }
  }

  class PixyVisionThread extends Thread {
    // private PixyI2CDriver driver = new PixyI2CDriver();
    private PixySpiDriver lineDriver = new PixySpiDriver(SPI.Port.kOnboardCS0);
    private PixySpiDriver blockDriver = new PixySpiDriver(SPI.Port.kOnboardCS1);

    @Override
    public void run() {
      logger.info("starting thread");
      while (isRunning) {
        if (turnOnLamp) {
          lineDriver.turnOnLamp();
          blockDriver.turnOnLamp();
          turnOnLamp = false;
        }
        if (turnOffLamp) {
          lineDriver.turnOffLamp();
          blockDriver.turnOffLamp();
          turnOffLamp = false;
        }
        if (trackLines) {
          PixyLine line = lineDriver.lineTracking();
          if (line != null && isValid(line)) {
            logger.detail("found line: " + line);
            latestLine = line;
          }
        }
        if (trackObjects) {
          PixyBlock[] blocks = blockDriver.objectTracking();
          if (blocks != null && isValid(blocks)) {
            if (blocks[0].getCenterX() < blocks[1].getCenterX()) {
              leftBlock = blocks[0];
              rightBlock = blocks[1];
            } else {
              leftBlock = blocks[1];
              rightBlock = blocks[0];
            }
            logger.detail("found objects left: " + leftBlock + " right: " + rightBlock);
          }
        }

        try {
          Thread.sleep(POLL_FREQUENCY_MILLIS);
        } catch (InterruptedException e) {
          logger.info("interrupted thread");
        }
      }
      logger.info("stopping thread");
    }
  }

  private boolean isValid(PixyLine line) {
    return line.getLowerX() != line.getUpperX() ||
        line.getLowerY() != line.getUpperY();
  }

  private boolean isValid(PixyBlock[] blocks) {
    return blocks.length == 2 &&
        blocks[0].getSignature() != 0 &&
        blocks[1].getSignature() != 0;
  }
}
