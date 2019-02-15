package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ElevatorAndArmMove;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;
import frc.robot.util.SmartDash;

public class Elevator extends Subsystem {

  public static final int ELEVATOR_MIN = Integer.MIN_VALUE;
  public static final int ELEVATOR_MAX = Integer.MAX_VALUE;
  public static final double ELEVATOR_MIN_OUTPUT = -0.3;
  public static final double ELEVATOR_MAX_OUTPUT = 0.3;

  private static final int FAST_MOVEMENT_THRESHOLD = 1024 / 2;
  private static final int SLOW_MOVEMENT_THRESHOLD = 1024 / 5;
  private static final int FINE_MOVEMENT_THRESHOLD = 1024 / 50;
  private static final double FINE_INCREMENT = 0.001;
  private static final double STOP_SPEED = 0;

  private static final double MAX_VELOCITY = (1024.0 / 4) / 1000; // 1/4 revolution per second, in millis
  // CHECK
  public static final int[] HATCH_POSITIONS = new int[] {300, 400, 700, 1000};
  public static final int[] CARGO_POSITIONS = new int[] {300, 500, 800, 1100};

  private Logger logger = new Logger(Elevator.class.getName());
  private JetstreamTalon talon;
  private JetstreamVictor victor;
  private SpeedControllerGroup group;

  private int currentLevel;
  private Integer targetElevatorPosition;
  private long lastTimestamp;
  private int lastElevatorPosition;

  public Elevator() {
    super();
    logger.detail("constructor");

    talon = new JetstreamTalon("Elevator Talon", RobotMap.ELEVATOR_TALON, ELEVATOR_MIN, ELEVATOR_MAX, ELEVATOR_MIN_OUTPUT, ELEVATOR_MAX_OUTPUT, false);
    victor = new JetstreamVictor("Elevator Victor", RobotMap.ELEVATOR_VICTOR, ELEVATOR_MIN_OUTPUT, ELEVATOR_MAX_OUTPUT);
    group = new SpeedControllerGroup(talon, victor);
  }

  /** Manually change the elevator move speed, like through a joystick. */
  public void elevatorMove(double speed) {
    logger.info("elevatorMove speed: " + speed + " position: " + talon.getPosition());
    group.set(speed);
  }

  public void elevatorStop() {
    logger.info("elevatorStop");
    group.set(0);
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
      targetElevatorPosition = HATCH_POSITIONS[currentLevel];
    } else {
      targetElevatorPosition = CARGO_POSITIONS[currentLevel];
    }
  }

  /** return the level the elevator is currently at (a number from 0 to 4) */
  public int getLevel() {
    return currentLevel;
  }

  public void periodic(long timestamp) {
    talon.sendTelemetry();
    SmartDash.put("Elevator Level", currentLevel);
    if (Robot.isMovingElevator || targetElevatorPosition == null) {
      return;
    }

    int elevatorPosition = talon.getPosition();
    int relativePosition = elevatorPosition - targetElevatorPosition;
    int relativeDistance = Math.abs(relativePosition);
    if (relativeDistance < FINE_MOVEMENT_THRESHOLD) {
      autoMoveStop();
    } else if (relativeDistance < SLOW_MOVEMENT_THRESHOLD) {
      autoMoveFine(elevatorPosition, targetElevatorPosition, relativePosition, ELEVATOR_MAX_OUTPUT);
    } else if (relativeDistance < FAST_MOVEMENT_THRESHOLD) {
      autoMoveSlow(elevatorPosition, targetElevatorPosition, relativePosition, ELEVATOR_MAX_OUTPUT);
//      autoMoveVelocity(timestamp);
    } else {
      autoMoveFast(elevatorPosition, targetElevatorPosition, relativePosition, ELEVATOR_MAX_OUTPUT);
    }
    lastElevatorPosition = elevatorPosition;
    lastTimestamp = timestamp;
  }

  private void autoMoveFast(int currentPosition, int targetPosition, int relativePosition, double maxOutput) {
    double speed = relativePosition > 0 ? maxOutput : -maxOutput;
    logger.detail(String.format("autoMoveFast speed: %.4f current-position: %d target-position: %d relative-position: %d",
        speed, currentPosition, targetPosition, relativePosition));
    group.set(speed);
  }

  private void autoMoveSlow(int currentPosition, int targetPosition, int relativePosition, double maxOutput) {
    double ratio = relativePosition / (relativePosition > 0 ? FAST_MOVEMENT_THRESHOLD : -FAST_MOVEMENT_THRESHOLD);
    double speed = ELEVATOR_MAX_OUTPUT * ratio;
    logger.detail(String.format("autoMoveSlow speed: %.4f ratio: %.4f current-position: %d target-position: %d relative-position: %d",
        speed, ratio, currentPosition, targetPosition, relativePosition));
    group.set(speed);
  }

  private void autoMoveFine(int currentPosition, int targetPosition, int relativePosition, double maxOutput) {
    double increment = relativePosition > 0 ? FINE_INCREMENT : -FINE_INCREMENT;
    double speed = talon.get() + increment;
    logger.detail(String.format("autoMoveFine speed: %.4f increment: %.4f current-position: %d target-position: %d relative-position: %d",
        speed, increment, currentPosition, targetPosition, relativePosition));
    group.set(speed);
  }

  private void autoMoveStop() {
    logger.detail(String.format("autoMoveStop speed: %.4f angle %.4f", STOP_SPEED));
    group.set(STOP_SPEED);
  }

  private void autoMoveVelocity(long timestamp) {
    long timeDelta = timestamp - lastTimestamp;
    int position = talon.getPosition();
    int relativePosition = position - targetElevatorPosition;
    double velocity = (double)(position - lastElevatorPosition) / timeDelta;
    double ratio = relativePosition / (relativePosition > 0 ? FAST_MOVEMENT_THRESHOLD : -FAST_MOVEMENT_THRESHOLD);
    double velocityRatio = Math.sqrt(ratio);
    double targetVelocity = MAX_VELOCITY * velocityRatio;
    double speed = targetVelocity * timeDelta;
    logger.detail(String.format("autoMoveVelocity speed: %.4f target-velocity: %.4f velocity-ratio: %.4f ratio: %.4f current-velocity: %.4f relative-position: %d currentPosition: %d time-delta: %d",
      speed, targetVelocity, velocityRatio, ratio, velocity, relativePosition, position, timeDelta));
    group.set(speed);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ElevatorAndArmMove());
  }
}
