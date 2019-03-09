package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;
import frc.robot.util.SmartDash;

public class Arm extends Subsystem {

  public static final int VERTICAL_MAX = 3500;
  public static final int VERTICAL_MIN = -3500;
  public static final double VERTICAL_MIN_OUTPUT = -0.5;
  public static final double VERTICAL_MAX_OUTPUT = 0.6;  

  public static final int VERTICAL_HATCH_HOVER_POSITION = 500;
  public static final int VERTICAL_HATCH_DOWN_POSITION = 0;  

  public static final int SWIVEL_MAX = 3500;
  public static final int SWIVEL_MIN = -3500;
  public static final double SWIVEL_MIN_OUTPUT = -0.3;
  public static final double SWIVEL_MAX_OUTPUT = 0.3;

  public static final int SWIVEL_HATCH_HOVER_POSITION = -2800;

  private static final int FAST_MOVEMENT_THRESHOLD = 1024 / 2;
  private static final int SLOW_MOVEMENT_THRESHOLD = 1024 / 5;
  private static final int FINE_MOVEMENT_THRESHOLD = 1024 / 50;
  private static final double FINE_INCREMENT = 0.001;
  private static final double STOP_SPEED = 0.16;
  private static final double ENCODER_TO_RADIANS = Math.PI / 7000;

  private static final double MAX_VELOCITY = (1024.0 / 4) / 1000; // 1/4 revolution per second, in millis

  // CHECK
  public static final int[] VERTICAL_HATCH_POSITIONS = new int[] {0, -1900, -2050, -2050};
  public static final int[] VERTICAL_CARGO_POSITIONS = new int[] {0, -2500, -2500, -2500};
  public static final int[] SWIVEL_HATCH_POSITIONS = new int[] {0, -980, -980, -980};
  public static final int[] SWIVEL_CARGO_POSITIONS = new int[] {0, -1600, -1600, -1600};

  // floor: swivel -2800

  private Logger logger = new Logger(Arm.class.getName());
  private JetstreamTalon verticalTalon;
  private JetstreamTalon swivelTalon;

  private int currentLevel;

  private Integer targetVerticalPosition;

  private Integer targetSwivelPosition;
  private long lastTimestamp;
  private int lastVerticalPosition;

  public Arm() {
    super();
  //  logger.detail("constructor");

    verticalTalon = new JetstreamTalon("Arm Talon", RobotMap.ARM_VERTICAL_TALON, VERTICAL_MIN, VERTICAL_MAX, VERTICAL_MIN_OUTPUT, VERTICAL_MAX_OUTPUT, false);
    swivelTalon = new JetstreamTalon("Swivel Talon", RobotMap.ARM_SWIVEL_TALON, SWIVEL_MIN, SWIVEL_MAX, SWIVEL_MIN_OUTPUT, SWIVEL_MAX_OUTPUT, true);

    lastTimestamp = System.currentTimeMillis();
    lastVerticalPosition = verticalTalon.getPosition();

    //targetVerticalPosition = 1500;
  }

  public void calibrate() {
    /*
    if (Robot.isHatchMode) {
      VERTICAL_HATCH_POSITIONS[currentLevel] = getVerticalArmPosition();
      SWIVEL_HATCH_POSITIONS[currentLevel] = getSwivelPosition();
    } else {
      VERTICAL_CARGO_POSITIONS[currentLevel] = getVerticalArmPosition();
      SWIVEL_CARGO_POSITIONS[currentLevel] = getSwivelPosition();
    }
    */
  }

  public void setLevel1() {
    targetSwivelPosition = 2000;
    targetVerticalPosition = -2000;
  }

  public int getVerticalArmPosition() {
    return verticalTalon.getPosition();
  }

  public void setVerticalPosition(int position) {
    targetVerticalPosition = position;
  }

  public void moveVerticalArm(double speed) {
    targetVerticalPosition = null;
    logger.info("moveVerticalArm speed: " + speed + " arm-position: " + verticalTalon.getPosition());
    verticalTalon.set(speed + getCompensation());
  }

  public void stopMovingVerticalArm(){
  //  logger.info("stopMovingVerticalArm");
    verticalTalon.set(0);
    // Try to stay at the current position
//    targetVerticalPosition = verticalTalon.getPosition();
  }

  public int getSwivelPosition() {
    return swivelTalon.getPosition();
  }

  public void setSwivelPosition(int position) {
    targetSwivelPosition = position;
  }

  public void moveSwivelArm(double speed) {
    logger.info("moveSwivelArm speed: " + speed + " swivel-positions: " + swivelTalon.getPosition());
    swivelTalon.set(speed);
  }

  public int getVerticalPosition() { 
    return verticalTalon.getPosition();
  }

  public void stopMovingSwivelArm() {
    swivelTalon.set(0);
//    targetSwivelPosition = swivelTalon.getPosition();
  }

  public void moveTogether(double speed) {
    if (!verticalTalon.isValidSpeed(speed) || !swivelTalon.isValidSpeed(-speed)) {
    //  logger.warning(String.format("INVALID set speed: %f", speed));
    }
  //  logger.info("moveTogether speed: " + speed + " swivel-pos: " + swivelTalon.getPosition() + " vert-pos: " + verticalTalon.getPosition());
    verticalTalon.set(speed + getCompensation());
    swivelTalon.set(speed * .75);
  }

  public double getCompensation() {
    double angleInRadians = ENCODER_TO_RADIANS * verticalTalon.getPosition();
    double speed = STOP_SPEED * Math.cos(angleInRadians);
    return speed;
  }

  /**
   * Sets Vertical speed manually to a given value
   * only used for testing
   */
  public void setVerticalSpeedManually(double speed) {
  //  logger.info("setVerticalSpeedManually Position: " + verticalTalon.getPosition() + "Speed: " + speed);
    verticalTalon.set(speed);
  }

  /** Set the level of the arm to a number from 0 to 4. */
  public void setLevel(int level) {
  //  logger.info("setLevel level: " + level);

  /*
    if (level < 0) {
      level = 0;
    }
    if (level > 3) {
      level = 3;
    }
    currentLevel = level;
    if (Robot.isHatchMode()) {
      targetVerticalPosition = VERTICAL_HATCH_POSITIONS[currentLevel];
      targetSwivelPosition = SWIVEL_HATCH_POSITIONS[currentLevel];
    } else {
      targetVerticalPosition = VERTICAL_CARGO_POSITIONS[currentLevel];
      targetSwivelPosition = SWIVEL_CARGO_POSITIONS[currentLevel];
    }

    */
  }

  /** return the level the arm is currently at (a number from 0 to 4) */
  public int getLevel() {
    return currentLevel;
  }

  public void reset() {
    verticalTalon.reset();
    swivelTalon.reset();
  }

  private boolean doNextPeriodic3 = true;
  public void periodic9(long timestamp) {
    if (verticalTalon.getPosition() > targetVerticalPosition - 20) {
      doNextPeriodic3 = true;
    }
    if (doNextPeriodic3) {
      periodic3(timestamp);
    } else {
      periodic(timestamp);
      nextChangeTimestamp = timestamp;
    }
  }

  private long nextChangeTimestamp = 0;
  private double nextSpeed = STOP_SPEED;
  public void periodic3(long timestamp) {
    if (System.currentTimeMillis() > nextChangeTimestamp) {
      nextSpeed -= 0.01;
      nextChangeTimestamp = System.currentTimeMillis() + 1000;
      verticalTalon.set(nextSpeed);
    }
  //  logger.info("Arm encoder: " + verticalTalon.getPosition() + " speed: " + verticalTalon.get() + " voltage: " + verticalTalon.getVoltage());
  }

  public void periodic(long timestamp) {
  //  logger.info("Arm encoder: " + verticalTalon.getPosition() + " swivel: " + swivelTalon.getPosition() + " target: " + targetVerticalPosition);
//    verticalTalon.sendTelemetry();
//    swivelTalon.sendTelemetry();
    SmartDash.put("Arm Level", currentLevel);
    if (Robot.isMovingArm) {
      return;
    }
    if (targetVerticalPosition != null) {
      periodicVertical(timestamp);
    }
    Logger.Level temp = Logger.LOG_LEVELS.get(this.getClass().getName());
    Logger.LOG_LEVELS.put(this.getClass().getName(), Level.WARNING);
    if (targetSwivelPosition != null) {
      periodicSwivel(timestamp);
    }
    Logger.LOG_LEVELS.put(this.getClass().getName(), temp);
  }

  private void periodicVertical(long timestamp) {
    int verticalPosition = verticalTalon.getPosition();
    int relativePosition = targetVerticalPosition - verticalPosition;
    int relativeDistance = Math.abs(relativePosition);
    if (relativeDistance < FINE_MOVEMENT_THRESHOLD) {
      autoMoveStop(verticalTalon);
    } else if (relativeDistance < SLOW_MOVEMENT_THRESHOLD) {
      autoMoveFine(verticalPosition, targetVerticalPosition, relativePosition, verticalTalon, VERTICAL_MAX_OUTPUT);
    } else if (relativeDistance < FAST_MOVEMENT_THRESHOLD) {
      autoMoveSlow(verticalPosition, targetVerticalPosition, relativePosition, verticalTalon, VERTICAL_MIN_OUTPUT, VERTICAL_MAX_OUTPUT);
//      autoMoveVelocity(timestamp);
    } else {
      autoMoveFast(verticalPosition, targetVerticalPosition, relativePosition, verticalTalon, VERTICAL_MIN_OUTPUT, VERTICAL_MAX_OUTPUT);
    }
    lastVerticalPosition = verticalPosition;
    lastTimestamp = timestamp;
  }

  private void periodicSwivel(long timestamp) {
    int swivelPosition = swivelTalon.getPosition();
    int relativePosition = targetSwivelPosition - swivelPosition;
    int relativeDistance = Math.abs(relativePosition);
    if (relativeDistance < FINE_MOVEMENT_THRESHOLD) {
    //  logger.detail("swivelMoveStop");
      swivelTalon.set(0);
    } else if (relativeDistance < FAST_MOVEMENT_THRESHOLD) {
      autoMoveSlow(swivelPosition, targetSwivelPosition, relativePosition, swivelTalon, SWIVEL_MIN_OUTPUT, SWIVEL_MAX_OUTPUT);
    } else {
      autoMoveFast(swivelPosition, targetSwivelPosition, relativePosition, swivelTalon, SWIVEL_MIN_OUTPUT, SWIVEL_MAX_OUTPUT);
    }
  }

  private void autoMoveFast(int currentPosition, int targetPosition, int relativePosition, JetstreamTalon talon, double minOutput, double maxOutput) {
    double speed = relativePosition < 0 ? minOutput : maxOutput;
  //  logger.detail(String.format("autoMoveFast speed: %.4f current-position: %d target-position: %d relative-position: %d", speed, currentPosition, targetPosition, relativePosition));
    talon.set(speed);
  }

  private void autoMoveSlow(int currentPosition, int targetPosition, int relativePosition, JetstreamTalon talon, double minOutput, double maxOutput) {
    double ratio = Math.abs((double) relativePosition / FAST_MOVEMENT_THRESHOLD);
    double speed = relativePosition < 0 ? minOutput * ratio : maxOutput * ratio;
    // If we're moving up (in the negative direction), apply a min speed to prevent stalling.
    if (speed < 0 && speed > -0.2) {
      speed = -0.2;
    }
  //  logger.detail(String.format("autoMoveSlow speed: %.4f ratio: %.4f current-position: %d target-position: %d relative-position: %d",speed, ratio, currentPosition, targetPosition, relativePosition));
    talon.set(speed);
  }

  private void autoMoveFine(int currentPosition, int targetPosition, int relativePosition, JetstreamTalon talon, double maxOutput) {
    double increment = relativePosition > 0 ? FINE_INCREMENT : -FINE_INCREMENT;
    double speed = verticalTalon.get() + increment;
  //  logger.detail(String.format("autoMoveFine speed: %.4f increment: %.4f current-position: %d target-position: %d relative-position: %d",  speed, increment, currentPosition, targetPosition, relativePosition));
    talon.set(speed);
  }

  private void autoMoveStop(JetstreamTalon talon) {
//    double angleInRadians = ENCODER_TO_RADIANS * talon.getPosition();
//    double speed = STOP_SPEED;// * Math.cos(angleInRadians);
//    logger.detail(String.format("autoMoveStop speed: %.4f angle %.4f", speed, Math.toDegrees(angleInRadians)));
    double speed = getStopSpeed();
  //  logger.detail(String.format("autoMoveStop speed: %.4f", speed));
    talon.set(speed);
  }

  private void autoMoveVelocity(long timestamp) {
    long timeDelta = timestamp - lastTimestamp;
    int position = verticalTalon.getPosition();
    int relativePosition = position - targetVerticalPosition;
    double velocity = (double)(position - lastVerticalPosition) / timeDelta;
    double ratio = relativePosition / (relativePosition > 0 ? FAST_MOVEMENT_THRESHOLD : -FAST_MOVEMENT_THRESHOLD);
    double velocityRatio = Math.sqrt(ratio);
    double targetVelocity = MAX_VELOCITY * velocityRatio;
    double speed = targetVelocity * timeDelta;
  //  logger.detail(String.format("autoMoveVelocity speed: %.4f target-velocity: %.4f velocity-ratio: %.4f ratio: %.4f current-velocity: %.4f relative-position: %d currentPosition: %d time-delta: %d", speed, targetVelocity, velocityRatio, ratio, velocity, relativePosition, position, timeDelta));
    verticalTalon.set(speed);
  }

   /**
    * @return calculated stopping speed based on previously determined quantity of vroom graph
    */
  public double getStopSpeed() {
    /*double position = verticalTalon.getPosition();
    double speed = (-0.0000456 * position) + 0.5;
    return (position > 2750) ? 0.3 : speed;*/
    return STOP_SPEED;
  }

  @Override
  public void initDefaultCommand() {
    // Do nothing, the Elevator subsystem already initializes the ElevatorAndArmMove class.
  }
}
