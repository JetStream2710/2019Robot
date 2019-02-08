package frc.robot;

public class RobotMap {
  // joystick
  public static final int JOYSTICK_DRIVER = 0;
  public static final int DRIVER_MOVE_AXIS = 1;
  public static final int DRIVER_ROTATE_AXIS = 2;
  public static final int CARGO_INTAKE = 5;
  public static final int CARGO_OUTTAKE = 6;
  public static final int HATCH_CONTROL = 7;

  // figure out feeder station stuff -- waiting on the stuff FEEDER STATION
  public static final int JOYSTICK_AUX = 1;
  public static final int SWITCH_TO_HATCH = 0;
  public static final int SWITCH_TO_CARGO = 0;
  public static final int ELEVATOR_AXIS = 1;
  public static final int ELEVATOR_CARGO_ONE = 0;
  public static final int ELEVATOR_CARGO_TWO = 0;
  public static final int ELEVATOR_CARGO_THREE = 0;
  public static final int ELEVATOR_HATCH_ONE = 0;
  public static final int ELEVATOR_HATCH_TWO = 0;
  public static final int ELEVATOR_HATCH_THREE = 0;

  // drivetrain
  // master = talon    slave = victor
  public static final int DRIVETRAIN_LEFT_MASTER = 0;
  public static final int DRIVETRAIN_LEFT_SLAVE = 1;
  public static final int DRIVETRAIN_RIGHT_MASTER = 15;
  public static final int DRIVETRAIN_RIGHT_SLAVE = 14;

  // elevator
  // master = talon     slave = victor
  public static final int ELEVATOR_MASTER = 0;
  public static final int ELEVATOR_SLAVE = 0;

  // cargo
  public static final int CARGO_VICTOR = 11;

  // hatch
  public static final int HATCH_TALON = 10;

  // arm
  public static final int ARM_VERTICAL_TALON = 13;
  public static final int ARM_SWIVEL_TALON = 12;

  // climb
  public static final int CLIMB_FRONT_VICTOR = 9;
  public static final int CLIMB_BACK_VICTOR = 4;
  public static final int CLIMB_MOVE_VICTOR = 5;
}
