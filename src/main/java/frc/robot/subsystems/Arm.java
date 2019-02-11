package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;

public class Arm extends Subsystem {

  public static final int VERTICAL_MAX = 3000;
  public static final int VERTICAL_MIN = 0;
  public static final int SWIVEL_MAX = 3500;
  public static final int SWIVEL_MIN = -3500;

  public static final double VERTICAL_MAX_OUTPUT = 0.3;
  public static final double SWIVEL_MAX_OUTPUT = 0.2;
  public static final double OUTPUT_INCREMENT = 0.01;
  public static final int DECELERATION_DISTANCE = 1024 / 2; // half a revolution
  private static final double MAX_VELOCITY = 1024.0 / 1000; // one encoder revolution per second
  private static final int DISTANCE_BUFFER = 4;
  private static final double VELOCITY_BUFFER = 4.0 / 20;   // encoder positions per 20 millis

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
  private int lastSwivelPosition;

  public Arm() {
    super();
    logger.detail("constructor");

    verticalTalon = new JetstreamTalon(RobotMap.ARM_VERTICAL_TALON, null, VERTICAL_MIN, VERTICAL_MAX, VERTICAL_MAX_OUTPUT, true);
    swivelTalon = new JetstreamTalon(RobotMap.ARM_SWIVEL_TALON, null, SWIVEL_MIN, SWIVEL_MAX, SWIVEL_MAX_OUTPUT, true);

    targetVerticalPosition = 1000;
  }

  public void moveVerticalArm(double speed) {
    if (Math.abs(speed) < 0.08) {
      return;
    }
    targetVerticalPosition = null;
    logger.info("moveVerticalArm speed: " + speed + " arm-pos: " + verticalTalon.getPosition());
    verticalTalon.set(speed);
  }

  public void stopMovingVerticalArm(){
    verticalTalon.set(0);
//    logger.info("stopMovingVerticalArm");
//    targetVerticalPosition = verticalTalon.getPosition();
  }

  public void moveSwivelArm(double speed) {
    if (Math.abs(speed) < 0.08) {
      return;
    }
    targetSwivelPosition = null;
    logger.info("moveSwivelArm speed: " + speed + " swivel-pos" + swivelTalon.getPosition());
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

  public void resetEncoders() {
    logger.info("resetEncoder");
   }

  // reminder to check first that
  // - higher position = higher encoder value
  // - positive speed on motor = move upward
  /**
   * This function is called during periodic loops to
   * (1) move the elevator to a target height and
   * (2) keep the elevator stable at a target height
   */
  public void periodic(long timestamp) {
    if (Robot.isMovingArm) {
      return;
    }

    if(targetVerticalPosition != null) {
      periodic(timestamp, verticalTalon, targetVerticalPosition, lastVerticalPosition);
    }
//  if(targetSwivelPoisition != null)  periodic(timestamp, swivelTalon, targetSwivelPosition, lastSwivelPosition);
  }

  public void periodic(long timestamp, JetstreamTalon talon, int targetPosition, int lastPosition) {
    int currentPosition = talon.getPosition();
    if (currentPosition > targetPosition + DISTANCE_BUFFER ||
        currentPosition < targetPosition - DISTANCE_BUFFER) {
      // negative position means we are below the target, positive position means we are above the target
      int position = currentPosition - targetPosition;
      // if we are far away, move as fast as we can to the target
      if (Math.abs(position) > DECELERATION_DISTANCE) {
        double speed = position > 0 ? -VERTICAL_MAX_OUTPUT : VERTICAL_MAX_OUTPUT;
        logger.info(String.format("periodic fast move to: %d from: %d relative-position: %d  speed: %f", targetPosition, currentPosition, position, speed));
        talon.set(speed);
      } else if (Math.abs(position) < 20) {
        double speed = position / -102.4;
        logger.info(String.format("periodic still to: %d from: %d relative-position: %d  speed: %f", targetPosition, currentPosition, position, speed));
        talon.set(speed);
    } else if (Math.abs(position) < 200) {
          double speed = talon.get();
          if (position > 0) {
            speed = speed - 0.0008;
          } else{
            speed = speed + 0.0008;
          } 
          logger.info(String.format("fine tune to: %d from: %d relative-position: %d  speed: %f", targetPosition, currentPosition, position, speed));
          talon.set(speed);
      } else {
        // proportional speed calculation
        if (position + DISTANCE_BUFFER < 0) {
          double speed = .9 * position / -1024.0;
          logger.info(String.format("periodic slow up to: %d from: %d relative-position: %d  speed: %f", targetPosition, currentPosition, position, speed));
          talon.set(speed);
        } else if (position - DISTANCE_BUFFER > 0) {
          double speed = 0.2 * position / 1024.0;
          logger.info(String.format("periodic slow down to: %d from: %d relative-position: %d  speed: %f", targetPosition, currentPosition, position, speed));
          talon.set(speed);
        }
        /*
        double velocity = (double)(position - lastPosition) / (timestamp - lastTimestamp);
        double targetVelocity = MAX_VELOCITY * (position / DECELERATION_DISTANCE);
        if (velocity + VELOCITY_BUFFER < targetVelocity) {
          logger.info(String.format("periodic slow increment from: %d to: %d relative-position: %d velocity: %f target-velocity: %f speed: %f", currentPosition, targetPosition, position, velocity, targetVelocity, talon.get() + OUTPUT_INCREMENT));
          talon.set(talon.get() + OUTPUT_INCREMENT);
        } else if (velocity - VELOCITY_BUFFER > targetVelocity) {
          logger.info(String.format("periodic slow decrement from: %d to: %d relative-position: %d velocity: %f target-velocity: %f speed: %f", currentPosition, targetPosition, position, velocity, targetVelocity, talon.get() - OUTPUT_INCREMENT));
          talon.set(talon.get() - OUTPUT_INCREMENT);
        } else {
          logger.info(String.format("periodic slow no-move from: %d to: %d relative-position: %d velocity: %f target-velocity: %f speed: %f", currentPosition, targetPosition, position, velocity, targetVelocity, talon.get()));
          talon.set(talon.get());
        }
        */
      }
    }
    lastTimestamp = timestamp;
    lastPosition = currentPosition;
  }

  //would t be better to just make the commands here and just
  public int getVerticalPosition(){
    return verticalTalon.getPosition();
  }

  @Override
  public void initDefaultCommand() {
  }
}
