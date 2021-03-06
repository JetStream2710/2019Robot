package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

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

  public static final int ELEVATOR_MIN = -54000;
  public static final int ELEVATOR_MAX = 0;
  public static final double ELEVATOR_MIN_OUTPUT = -0.52;
  public static final double ELEVATOR_MAX_OUTPUT = 0.2;
  public static final double STOP_SPEED = -0.18;

  private static final int FAST_MOVEMENT_THRESHOLD = 1024 * 2;
  //private static final int FAST_MOVEMENT_THRESHOLD = 1024 * 4; works with -0.45 min output
  //private static final int FAST_MOVEMENT_THRESHOLD = 1024 * 6; works with -0.45 min output
  private static final int SLOW_MOVEMENT_THRESHOLD = 1024 / 4;
  private static final int FINE_MOVEMENT_THRESHOLD = 1024 / 25;
  private static final double FINE_INCREMENT = 0.001;
  private static final double MIN_UP_SPEED = STOP_SPEED - .02;
  private static final double VICTOR_SPEED_LIMIT  = -0.6;
 
  private static final double MAX_VELOCITY = (1024.0 / 4) / 1000; // 1/4 revolution per second, in millis
  
  // CHECK
  public static final int[] HATCH_POSITIONS = new int[] {300, 400, 700, 1000};
  public static final int[] CARGO_POSITIONS = new int[] {300, 500, 800, 1100};

//  private Logger logger = new Logger(Elevator.class.getName());

  private JetstreamTalon talon;
//  private JetstreamVictor victor;
  private WPI_VictorSPX victor;
  private SpeedControllerGroup group;

  private int currentLevel;
  private Integer targetElevatorPosition;
  private long lastTimestamp;
  private int lastElevatorPosition;

  public Elevator() {
    super();
  //  logger.detail("constructor");

    talon = new JetstreamTalon("Elevator Talon", RobotMap.ELEVATOR_TALON, ELEVATOR_MIN, ELEVATOR_MAX, ELEVATOR_MIN_OUTPUT, ELEVATOR_MAX_OUTPUT, true);
//    victor = new JetstreamVictor("Elevator Victor", RobotMap.ELEVATOR_VICTOR, ELEVATOR_MIN_OUTPUT, ELEVATOR_MAX_OUTPUT);
    victor = new WPI_VictorSPX(RobotMap.ELEVATOR_VICTOR);
    victor.setInverted(false);
    victor.setSafetyEnabled(false);
    victor.setNeutralMode(NeutralMode.Brake);
    victor.configVoltageCompSaturation(12);
    victor.enableVoltageCompensation(true);
    group = new SpeedControllerGroup(talon, victor);

    lastTimestamp = System.currentTimeMillis();
    lastElevatorPosition = talon.getPosition();

    //targetElevatorPosition = -50000;
  }

  public int getPosition() {
    return talon.getPosition();
  }

  public void setPosition(int position) {
    targetElevatorPosition = position;
  }

  /** Manually change the elevator move speed, like through a joystick. */
  public void elevatorMove(double speed) {
  //  logger.info("elevatorMove speed: " + speed + " position: " + talon.getPosition() + " talon voltage: " + talon.getVoltage() + " victor voltage: " + victor.getMotorOutputVoltage());

        talon.set(speed);
        double victorSpeed = (speed < VICTOR_SPEED_LIMIT) ? VICTOR_SPEED_LIMIT : speed;
        victor.set(victorSpeed);
      //  logger.detail("elevatorMove speed: " + speed + " victorSpeed: " + victorSpeed);
}

  public void elevatorStop() {
  //  logger.info("elevatorStop");
    group.set(0);
  }

  /** Set the level of the elevator to a number from 0 to 4. */
  public void setLevel(int level) {
  //  logger.info("setLevel level: " + level);
   
  
  /*if (level < 0) {
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
    */
  }

  /** return the level the elevator is currently at (a number from 0 to 4) */
  public int getLevel() {
    return currentLevel;
  }

  public void reset() {
    talon.reset();
  }

  public void periodic(long timestamp) {
    talon.sendTelemetry();
    SmartDash.put("Elevator Level", currentLevel);
    if (Robot.isMovingElevator || targetElevatorPosition == null) {
      return;
    }

    int elevatorPosition = talon.getPosition();
    int relativePosition = targetElevatorPosition - elevatorPosition;
    int relativeDistance = Math.abs(relativePosition);
    if (relativeDistance < FINE_MOVEMENT_THRESHOLD) {
      autoMoveStop();
    } else if (relativeDistance < SLOW_MOVEMENT_THRESHOLD) {
      autoMoveFine(elevatorPosition, targetElevatorPosition, relativePosition, ELEVATOR_MAX_OUTPUT);
    } else if (relativeDistance < FAST_MOVEMENT_THRESHOLD) {
      autoMoveSlow(elevatorPosition, targetElevatorPosition, relativePosition, ELEVATOR_MIN_OUTPUT, ELEVATOR_MAX_OUTPUT);
//      autoMoveVelocity(timestamp);
    } else {
      autoMoveFast(elevatorPosition, targetElevatorPosition, relativePosition, ELEVATOR_MIN_OUTPUT, ELEVATOR_MAX_OUTPUT);
    }
    lastElevatorPosition = elevatorPosition;
    lastTimestamp = timestamp;
  }

  private void autoMoveFast(int currentPosition, int targetPosition, int relativePosition, double minOutput, double maxOutput) {
    double speed = relativePosition > 0 ? maxOutput : minOutput;
    //logger.detail(String.format("autoMoveFast speed: %f current-position: %d target-position: %d relative-position: %d",speed, currentPosition, targetPosition, relativePosition));
    
        talon.set(speed);
      double victorSpeed = (speed < VICTOR_SPEED_LIMIT) ? VICTOR_SPEED_LIMIT : speed;
      victor.set(victorSpeed);
    //  logger.detail("autoMoveFast speed: " + speed + " victorSpeed: " + victorSpeed);
    }

  private double minSlowSpeed = -0.23;
  private void autoMoveSlow(int currentPosition, int targetPosition, int relativePosition, double minOutput, double maxOutput) {
    double ratio = Math.abs((double) relativePosition / (FAST_MOVEMENT_THRESHOLD));
    minOutput = minOutput - minSlowSpeed;
    minOutput = (minOutput * ratio) + minSlowSpeed;
    double speed = relativePosition < 0 ? minOutput : maxOutput * ratio;
    // If we're moving up (in the negative direction), apply a min speed to prevent stalling.
    if (getPosition() < -23000) {
      // second stage elevator requires a larger speed limit
      if (speed < 0 && speed > -.330) {
        speed = -.330;
      }
    } else {
      if (speed < 0 && speed > -.13) {
        speed = -.15;
      }
    }
  //  logger.detail(String.format("autoMoveSlow speed: %f ratio: %f current-position: %d target-position: %d relative-position: %d", speed, ratio, currentPosition, targetPosition, relativePosition));
    
        talon.set(speed);
      double victorSpeed = (speed < VICTOR_SPEED_LIMIT) ? VICTOR_SPEED_LIMIT : speed;
      victor.set(victorSpeed);
    //  logger.detail("autoMoveSlow speed: " + speed + " victorSpeed: " + victorSpeed);
  }

  private void autoMoveFine(int currentPosition, int targetPosition, int relativePosition, double maxOutput) {
    double increment = relativePosition > 0 ? FINE_INCREMENT : -FINE_INCREMENT;
    double speed = talon.get() + increment;
    if (speed < 0 && speed > -.15) {
      speed = -.15;
    }
  //  logger.detail(String.format("autoMoveFine speed: %f increment: %f current-position: %d target-position: %d relative-position: %d",  speed, increment, currentPosition, targetPosition, relativePosition));
    
        talon.set(speed);
      double victorSpeed = (speed < VICTOR_SPEED_LIMIT) ? VICTOR_SPEED_LIMIT : speed;
      victor.set(victorSpeed);
    //  logger.detail("autoMoveFine speed: " + speed + " victorSpeed: " + victorSpeed);
  }

  private void autoMoveStop() {
    int elevatorPosition = talon.getPosition();
    int relativePosition = targetElevatorPosition - elevatorPosition;
  //  logger.detail(String.format("autoMoveStop speed: %f current-position: %d target-position: %d relative-position: %d",STOP_SPEED, elevatorPosition, targetElevatorPosition, relativePosition));
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
  //  logger.detail(String.format("autoMoveVelocity speed: %.4f target-velocity: %.4f velocity-ratio: %.4f ratio: %.4f current-velocity: %.4f relative-position: %d currentPosition: %d time-delta: %d",speed, targetVelocity, velocityRatio, ratio, velocity, relativePosition, position, timeDelta));
    
      talon.set(speed);
      double victorSpeed = (speed < VICTOR_SPEED_LIMIT) ? VICTOR_SPEED_LIMIT : speed;
      victor.set(victorSpeed);
    //  logger.detail("autoMoveVelocity speed: " + speed + " victorSpeed: " + victorSpeed);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ElevatorAndArmMove());
  }
}
