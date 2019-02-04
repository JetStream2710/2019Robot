package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamControllerGroup;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;

// TODO: insert limits for movement and decide if we want joystick movement
public class Elevator extends Subsystem {

  private static final double MIN = Double.MIN_VALUE;
  private static final double MAX = Double.MAX_VALUE;

  // CHECK
  public static int TOP_HATCH_POSITION = 1000;
  public static int MID_HATCH_POSITION = 700;
  public static int LOW_HATCH_POSITION = 400;
  public static int TOP_CARGO_POSITION = 1050;
  public static int MID_CARGO_POSITION = 750;
  public static int LOW_CARGO_POSITION = 450;

  private Logger logger = new Logger(Elevator.class.getName());
  private JetstreamTalon leftTalon;
  private JetstreamTalon rightTalon;
  private JetstreamControllerGroup group;

  public Elevator() {
    super();
    logger.detail("constructor");
    leftTalon = new JetstreamTalon(RobotMap.ELEVATOR_LEFT_TALON, MIN, MAX);
    rightTalon = new JetstreamTalon(RobotMap.ELEVATOR_RIGHT_TALON, MIN, MAX);
    group = new JetstreamControllerGroup(leftTalon, rightTalon, MIN, MAX);
  }

  public void elevatorMove(double speed) {
    logger.info("elevatorMove speed: " + speed);
    group.setSpeed(speed);
  }

  @Override
  public void initDefaultCommand() {
  }
}
