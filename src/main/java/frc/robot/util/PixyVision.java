package frc.robot.util;

public class PixyVision {
  private static final long POLL_FREQUENCY_MILLIS = 1 + (1000 / 60);
  private static final long LINE_TIMEOUT_MILLIS = 250;

// private Logger logger = new Logger(PixyVision.class.getName());

  private PixyLine latestLine1;
  private PixyLine latestLine2;
  private PixyBlock leftBlock;
  private PixyBlock rightBlock;

  private boolean turnOnLamp;
  private boolean turnOffLamp;
  private boolean trackLines;
  private boolean trackObjects;

  private boolean isRunning;
  private PixyVisionThread thread;

  private long lastLine1Valid;
  private long lastLine2Valid;


  public PixyVision(boolean trackLines, boolean trackObjects) {
  //  logger.detail("constructor");
    this.trackLines = trackLines;
    this.trackObjects = trackObjects;
  }

  public PixyLine getLatestLine1() {
    return latestLine1;
  }

  public PixyLine getLatestLine2() {
    return latestLine2;
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
    turnOffLamp = false;
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
    private PixyI2CDriver driver = new PixyI2CDriver(0x54);
    private PixyI2CDriver driver2 = new PixyI2CDriver(0x53);
    
    @Override
    public void run() {
    //  logger.info("running thread");
      while (isRunning) {
        long currentTime = System.currentTimeMillis();
        if (turnOnLamp) {
        //  logger.info("turning on lamp");
          driver.turnOnLamp();
          driver2.turnOnLamp();
          turnOnLamp = false;
        }
        if (turnOffLamp) {
        //  logger.info("turning off lamp");
          driver.turnOffLamp();
          driver2.turnOffLamp();
          turnOffLamp = false;
        }
        if (trackLines) {
          PixyLine line = driver.lineTracking(true);
          PixyLine line2 = driver2.lineTracking(false);
          if (line != null && isValid(line)) {
          //  logger.info("found line: " + line);
            latestLine1 = line;
            lastLine1Valid = currentTime;
          } else if (lastLine1Valid + LINE_TIMEOUT_MILLIS < currentTime) {
            latestLine1 = null;
          }
          if (line2 != null && isValid(line2)) {
            //  logger.info("found line: " + line);
              latestLine2 = line2;
              lastLine2Valid = currentTime;
          } else if (lastLine2Valid + LINE_TIMEOUT_MILLIS < currentTime) {
            latestLine2 = null;
          }
        }
        if (trackObjects) {
          PixyBlock[] blocks = driver2.objectTracking();
          if (blocks != null && isValid(blocks)) {
            if (blocks[0].getCenterX() < blocks[1].getCenterX()) {
              leftBlock = blocks[0];
              rightBlock = blocks[1];
            } else {
              leftBlock = blocks[1];
              rightBlock = blocks[0];
            }
          //  logger.info("found objects left: " + leftBlock + " right: " + rightBlock);
          }
        }

        try {
          Thread.sleep(POLL_FREQUENCY_MILLIS);
        } catch (InterruptedException e) {
        //  logger.warning("interrupted thread");
        }
      }
    }
  }

  private boolean isValid(PixyLine line) {
    if (line.getLowerX() == line.getUpperX() && 
        line.getLowerY() == line.getUpperY()) {
      return false;
    }
    return true;
  }

  private boolean isValid(PixyBlock[] blocks) {
    return blocks.length == 2 && blocks[0].getSignature() != 0 && blocks[1].getSignature() != 0;
  }
}