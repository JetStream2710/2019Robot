package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;
import frc.robot.util.Logger.Level;

public class Arm extends Subsystem {

  public static final int VERTICAL_MAX = 0;
  public static final int VERTICAL_MIN = -3000;
  public static final double VERTICAL_MIN_OUTPUT = -0.1;
  public static final double VERTICAL_MAX_OUTPUT = 0.3;

  public static final int SWIVEL_MAX = 3500;
  public static final int SWIVEL_MIN = -3500;
  public static final double SWIVEL_MIN_OUTPUT = -0.2;
  public static final double SWIVEL_MAX_OUTPUT = 0.2;

  private static final int FAST_MOVEMENT_THRESHOLD = 1024 / 2;
  private static final int SLOW_MOVEMENT_THRESHOLD = 1024 / 5;
  private static final int FINE_MOVEMENT_THRESHOLD = 1024 / 50;
  private static final double FINE_INCREMENT = 0.001;
  private static final double STOP_SPEED = 0.18;
  private static final double ENCODER_TO_RADIANS = Math.PI / 7000;

  private static final double MAX_VELOCITY = (1024.0 / 4) / 1000; // 1/4 revolution per second, in millis

  // CHECK
  public static final int[] VERTICAL_HATCH_POSITIONS = new int[] {0, 0, 0, 0};
  public static final int[] VERTICAL_CARGO_POSITIONS = new int[] {0, 0, 0, 0};
  public static final int[] SWIVEL_HATCH_POSITIONS = new int[] {0, 0, 0, 0};
  public static final int[] SWIVEL_CARGO_POSITIONS = new int[] {0, 0, 0, 0};

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
    logger.detail("constructor");

    verticalTalon = new JetstreamTalon("Arm Talon", RobotMap.ARM_VERTICAL_TALON, VERTICAL_MIN, VERTICAL_MAX, VERTICAL_MIN_OUTPUT, VERTICAL_MAX_OUTPUT, true);
    swivelTalon = new JetstreamTalon("Swivel Talon", RobotMap.ARM_SWIVEL_TALON, SWIVEL_MIN, SWIVEL_MAX, SWIVEL_MIN_OUTPUT, SWIVEL_MAX_OUTPUT, true);

    lastTimestamp = System.currentTimeMillis();
    lastVerticalPosition = verticalTalon.getPosition();
  }

  public void moveVerticalArm(double speed) {
    targetVerticalPosition = null;
    logger.info("moveVerticalArm speed: " + speed + " arm-pos: " + verticalTalon.getPosition());
    verticalTalon.set(speed);
  }

  public void stopMovingVerticalArm(){
    logger.info("stopMovingVerticalArm");
    verticalTalon.set(0);
    // Try to stay at the current position
//    targetVerticalPosition = verticalTalon.getPosition();
  }

  public void moveSwivelArm(double speed) {
    logger.info("moveSwivelArm speed: " + speed + " swivel-pos: " + swivelTalon.getPosition());
    swivelTalon.set(speed);
  }

  public void stopMovingSwivelArm() {
    swivelTalon.set(0);
//    targetSwivelPosition = swivelTalon.getPosition();
  }

  // MAKE SURE TO TEST THIS BECAUSE IT MIGHT BE BACKWARDS
  public void moveTogether(double speed) {
    if (verticalTalon.isValidSpeed(speed) && swivelTalon.isValidSpeed(-speed)) {
      logger.info("moveTogether speed: " + speed);
      verticalTalon.set(speed);
      swivelTalon.set(-speed);
    } 
  }

  /** Set the level of the arm to a number from 0 to 4. */
  public void setLevel(int level) {
    logger.info("setLevel level: " + level);
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
  }

  /** return the level the arm is currently at (a number from 0 to 4) */
  public int getLevel() {
    return currentLevel;
  }

  public void periodic(long timestamp) {
    verticalTalon.sendTelemetry();
    swivelTalon.sendTelemetry();
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
    int relativePosition = verticalPosition - targetVerticalPosition;
    int relativeDistance = Math.abs(relativePosition);
    if (relativeDistance < FINE_MOVEMENT_THRESHOLD) {
      autoMoveStop(verticalTalon);
    } else if (relativeDistance < SLOW_MOVEMENT_THRESHOLD) {
      autoMoveFine(verticalPosition, targetVerticalPosition, relativePosition, verticalTalon, VERTICAL_MAX_OUTPUT);
    } else if (relativeDistance < FAST_MOVEMENT_THRESHOLD) {
      autoMoveSlow(verticalPosition, targetVerticalPosition, relativePosition, verticalTalon, VERTICAL_MAX_OUTPUT);
//      autoMoveVelocity(timestamp);
    } else {
      autoMoveFast(verticalPosition, targetVerticalPosition, relativePosition, verticalTalon, VERTICAL_MAX_OUTPUT);
    }
    lastVerticalPosition = verticalPosition;
    lastTimestamp = timestamp;
  }

  private void periodicSwivel(long timestamp) {
    int swivelPosition = swivelTalon.getPosition();
    int relativePosition = swivelPosition - targetSwivelPosition;
    int relativeDistance = Math.abs(relativePosition);
    if (relativeDistance < FINE_MOVEMENT_THRESHOLD) {
      logger.detail("swivelMoveStop");
      swivelTalon.set(0);
    } else if (relativeDistance < FAST_MOVEMENT_THRESHOLD) {
      autoMoveSlow(swivelPosition, targetSwivelPosition, relativePosition, swivelTalon, SWIVEL_MAX_OUTPUT);
    } else {
      autoMoveFast(swivelPosition, targetSwivelPosition, relativePosition, swivelTalon, SWIVEL_MAX_OUTPUT);
    }
  }

  private void autoMoveFast(int currentPosition, int targetPosition, int relativePosition, JetstreamTalon talon, double maxOutput) {
    double speed = relativePosition > 0 ? maxOutput : -maxOutput;
    logger.detail(String.format("autoMoveFast speed: %.4f current-position: %d target-position: %d relative-position: %d",
        speed, currentPosition, targetPosition, relativePosition));
    talon.set(speed);
  }

  private void autoMoveSlow(int currentPosition, int targetPosition, int relativePosition, JetstreamTalon talon, double maxOutput) {
    double ratio = relativePosition / (relativePosition > 0 ? FAST_MOVEMENT_THRESHOLD : -FAST_MOVEMENT_THRESHOLD);
    double speed = VERTICAL_MAX_OUTPUT * ratio;
    logger.detail(String.format("autoMoveSlow speed: %.4f ratio: %.4f current-position: %d target-position: %d relative-position: %d",
        speed, ratio, currentPosition, targetPosition, relativePosition));
    talon.set(speed);
  }

  private void autoMoveFine(int currentPosition, int targetPosition, int relativePosition, JetstreamTalon talon, double maxOutput) {
    double increment = relativePosition > 0 ? FINE_INCREMENT : -FINE_INCREMENT;
    double speed = verticalTalon.get() + increment;
    logger.detail(String.format("autoMoveFine speed: %.4f increment: %.4f current-position: %d target-position: %d relative-position: %d",
        speed, increment, currentPosition, targetPosition, relativePosition));
    talon.set(speed);
  }

  private void autoMoveStop(JetstreamTalon talon) {
    double angleInRadians = ENCODER_TO_RADIANS * talon.getPosition();
    double speed = STOP_SPEED * Math.cos(angleInRadians);
    logger.detail(String.format("autoMoveStop speed: %.4f angle %.4f", speed, Math.toDegrees(angleInRadians)));
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
    logger.detail(String.format("autoMoveVelocity speed: %.4f target-velocity: %.4f velocity-ratio: %.4f ratio: %.4f current-velocity: %.4f relative-position: %d currentPosition: %d time-delta: %d",
      speed, targetVelocity, velocityRatio, ratio, velocity, relativePosition, position, timeDelta));
    verticalTalon.set(speed);
  }

  @Override
  public void initDefaultCommand() {
    // Do nothing, the Elevator subsystem already initializes the ElevatorAndArmMove class.
  }
}
