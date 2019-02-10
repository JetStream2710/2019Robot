package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;

public class Arm extends Subsystem {

  public static final int VERTICAL_MAX = Integer.MAX_VALUE;
  public static final int VERTICAL_MIN = Integer.MIN_VALUE;
  public static final int SWIVEL_MAX = Integer.MAX_VALUE;
  public static final int SWIVEL_MIN = Integer.MIN_VALUE;

  public static final double MAX_OUTPUT = 0.5;
  public static final double OUTPUT_INCREMENT = 0.05;
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
  private Encoder verticalEncoder;
  private Encoder swivelEncoder;

  private int currentLevel;
  private int targetVerticalPosition;
  private int targetSwivelPosition;
  private long lastTimestamp;
  private int lastVerticalPosition;
  private int lastSwivelPosition;

  public Arm() {
    super();
    logger.detail("constructor");

    verticalEncoder = new Encoder(RobotMap.ARM_VERTICAL_ENCODER_A, RobotMap.ARM_VERTICAL_ENCODER_B);
    swivelEncoder = new Encoder(RobotMap.ARM_SWIVEL_ENCODER_A, RobotMap.ARM_SWIVEL_ENCODER_B);
    verticalTalon = new JetstreamTalon(RobotMap.ARM_VERTICAL_TALON, verticalEncoder, VERTICAL_MIN, VERTICAL_MAX);
    swivelTalon = new JetstreamTalon(RobotMap.ARM_SWIVEL_TALON, swivelEncoder, SWIVEL_MIN, SWIVEL_MAX);
  }

  public void moveVerticalArm(double speed) {
    logger.info("moveVerticalArm speed: " + speed + " arm-pos: " + verticalTalon.getPosition());
    verticalTalon.set(speed);
  }

  public void moveSwivelArm(double speed) {
    logger.info("moveSwivelArm speed: " + speed);
    swivelTalon.set(speed);
  }

  // MAKE SURE TO TEST THIS BECAUSE IT MIGHT BE BACKWARDS
  public void moveTogether(double speed) {
    if (verticalTalon.isValidSpeed(speed) && swivelTalon.isValidSpeed(-speed)) {
      logger.info("moveTogether speed: " + speed + " arm-pos: " + verticalTalon.getPosition());
      verticalTalon.set(speed);
      swivelTalon.set(-speed);
    } else {
      logger.warning("moveTogether stopped + arm-pos: " + verticalTalon.getPosition());
      verticalTalon.set(0);
      swivelTalon.set(0);
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
    verticalEncoder.reset();
    swivelEncoder.reset();
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
    if (1 + 1 == 2) {
      return;
    }
    if (Robot.isMovingElevatorArm()) {
      return;
    }

    periodic(timestamp, verticalEncoder, verticalTalon, targetVerticalPosition, lastVerticalPosition);
    periodic(timestamp, swivelEncoder, swivelTalon, targetSwivelPosition, lastSwivelPosition);
  }

  public void periodic(long timestamp, Encoder encoder, SpeedController speedController, int targetPosition, int lastPosition) {
    int currentPosition = encoder.get();
    if (currentPosition > targetPosition + DISTANCE_BUFFER ||
        currentPosition < targetPosition - DISTANCE_BUFFER) {
      // negative position means we are below the target, positive position means we are above the target
      int position = currentPosition - targetPosition;
      // if we are far away, move as fast as we can to the target
      if (Math.abs(position) > DECELERATION_DISTANCE) {
        logger.info(String.format("periodic fast move to: %d from: %d relative-position: %d", targetPosition, currentPosition, position));
        speedController.set(position > 0 ? -MAX_OUTPUT : MAX_OUTPUT);
      } else {
        double velocity = (double)(position - lastPosition) / (timestamp - lastTimestamp);
        double targetVelocity = MAX_VELOCITY * (position / DECELERATION_DISTANCE);
        if (velocity + VELOCITY_BUFFER < targetVelocity) {
          logger.info(String.format("periodic slow increment from: %d to: %d relative-position: %d velocity: %f target-velocity: %f speed: %f", currentPosition, targetPosition, position, speedController.get() + OUTPUT_INCREMENT));
          speedController.set(speedController.get() + OUTPUT_INCREMENT);
        } else if (velocity - VELOCITY_BUFFER > targetVelocity) {
          logger.info(String.format("periodic slow decrement from: %d to: %d relative-position: %d velocity: %f target-velocity: %f speed: %f", currentPosition, targetPosition, position, speedController.get() - OUTPUT_INCREMENT));
          speedController.set(speedController.get() - OUTPUT_INCREMENT);
        } else {
          logger.info(String.format("periodic slow no-move from: %d to: %d relative-position: %d velocity: %f target-velocity: %f speed: %f", currentPosition, targetPosition, position, speedController.get()));
          speedController.set(speedController.get());
        }
      }
    }
    lastTimestamp = timestamp;
    lastPosition = currentPosition;
  }

  @Override
  public void initDefaultCommand() {
  }
}
