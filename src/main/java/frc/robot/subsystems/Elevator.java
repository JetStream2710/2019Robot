package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ElevatorAndArmMove;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;

public class Elevator extends Subsystem {

  public static final int MIN_POSITION = Integer.MIN_VALUE;
  public static final int MAX_POSITION = Integer.MAX_VALUE;

  public static final double MAX_OUTPUT = 0.5;
  public static final double OUTPUT_INCREMENT = 0.05;
  public static final int DECELERATION_DISTANCE = 1024 / 2;
  private static final double MAX_VELOCITY = 1024.0 / 1000; // in milliseconds
  private static final int DISTANCE_BUFFER = 4;
  private static final double VELOCITY_BUFFER = 4.0 / 20;

  // CHECK
  public static final int[] HATCH_POSITIONS = new int[] {300, 400, 700, 1000};
  public static final int[] CARGO_POSITIONS = new int[] {300, 500, 800, 1100};

  private Logger logger = new Logger(Elevator.class.getName());
  private JetstreamTalon talon;
  private JetstreamVictor victor;
  private SpeedControllerGroup group;
  private Encoder encoder;

  private int currentLevel;
  private int targetPosition;
  private long lastTimestamp;
  private int lastPosition;

  public Elevator() {
    super();
    logger.detail("constructor");
    talon = new JetstreamTalon(RobotMap.ELEVATOR_TALON, encoder, MIN_POSITION, MAX_POSITION, 0.2, true);
    victor = new JetstreamVictor(RobotMap.ELEVATOR_VICTOR);
    group = new SpeedControllerGroup(talon, victor);
    encoder.reset();
  }

  /** Manually change the elevator move speed, like through a joystick. */
  public void elevatorMove(double speed) {
    logger.info("elevatorMove speed: " + speed + " position: " + talon.getPosition());
    group.set(speed);
  }

  /** Set the level of the elevator to a number from 0 to 4. */
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
      targetPosition = HATCH_POSITIONS[currentLevel];
    } else {
      targetPosition = CARGO_POSITIONS[currentLevel];
    }
  }

  /** return the level the elevator is currently at (a number from 0 to 4) */
  public int getLevel() {
    return currentLevel;
  }

  public void resetEncoder() {
    logger.info("resetEncoder");
    encoder.reset();
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
    if (Robot.isMovingElevator) {
      return;
    }
    int currentPosition = encoder.get();
    if (currentPosition > targetPosition + DISTANCE_BUFFER ||
        currentPosition < targetPosition - DISTANCE_BUFFER) {
      // negative position means we are below the target, positive position means we are above the target
      int position = currentPosition - targetPosition;
      // if we are far away, move as fast as we can to the target
      if (Math.abs(position) > DECELERATION_DISTANCE) {
        logger.info(String.format("periodic fast move to: %d from: %d relative-position: %d", targetPosition, currentPosition, position));
        group.set(position > 0 ? -MAX_OUTPUT : MAX_OUTPUT);
      } else {
        double velocity = (double)(position - lastPosition) / (timestamp - lastTimestamp);
        double targetVelocity = MAX_VELOCITY * (position / DECELERATION_DISTANCE);
        if (velocity + VELOCITY_BUFFER < targetVelocity) {
          logger.info(String.format("periodic slow increment from: %d to: %d relative-position: %d velocity: %f target-velocity: %f speed: %f", currentPosition, targetPosition, position, group.get() + OUTPUT_INCREMENT));
          group.set(group.get() + OUTPUT_INCREMENT);
        } else if (velocity - VELOCITY_BUFFER > targetVelocity) {
          logger.info(String.format("periodic slow decrement from: %d to: %d relative-position: %d velocity: %f target-velocity: %f speed: %f", currentPosition, targetPosition, position, group.get() - OUTPUT_INCREMENT));
          group.set(group.get() - OUTPUT_INCREMENT);
        } else {
          logger.info(String.format("periodic slow no-move from: %d to: %d relative-position: %d velocity: %f target-velocity: %f speed: %f", currentPosition, targetPosition, position, group.get()));
          group.set(group.get());
        }
      }
    }
    lastTimestamp = timestamp;
    lastPosition = currentPosition;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ElevatorAndArmMove());
  }
}
