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
  public static final int JOYSTICK_AUX = 2;
  public static final int SWITCH_TO_HATCH = 0;
  public static final int SWITCH_TO_CARGO = 0;
  public static final int ELEVATOR_AXIS = 1;
  public static final int ARM_AXIS = 3;
  public static final int ELEVATOR_CARGO_ONE = 0;
  public static final int ELEVATOR_CARGO_TWO = 0;
  public static final int ELEVATOR_CARGO_THREE = 0;
  public static final int ELEVATOR_HATCH_ONE = 0;
  public static final int ELEVATOR_HATCH_TWO = 0;
  public static final int ELEVATOR_HATCH_THREE = 0;

  // drivetrain
  // master = talon    slave = victor
  public static final int DRIVETRAIN_LEFT_TALON = 0;
  public static final int DRIVETRAIN_LEFT_VICTOR = 1;
  public static final int DRIVETRAIN_RIGHT_TALON = 15;
  public static final int DRIVETRAIN_RIGHT_VICTOR = 14;

  public static final int DRIVETRAIN_LEFT_ENCODER_A = 10;
  public static final int DRIVETRAIN_LEFT_ENCODER_B = 9;
  public static final int DRIVETRAIN_RIGHT_ENCODER_A = 8;
  public static final int DRIVETRAIN_RIGHT_ENCODER_B = 7;

  // elevator
  // master = talon     slave = victor
  public static final int ELEVATOR_TALON = 2;
  public static final int ELEVATOR_VICTOR = 3;
  public static final int ELEVATOR_ENCODER_A = 0;
  public static final int ELEVATOR_ENCODER_B = 1;

  // cargo
  public static final int CARGO_VICTOR = 11;

  // hatch
  public static final int HATCH_VICTOR = 10;

  // arm
  public static final int ARM_VERTICAL_TALON = 13;
  public static final int ARM_SWIVEL_TALON = 12;
  public static final int ARM_VERTICAL_ENCODER_A = 2;
  public static final int ARM_VERTICAL_ENCODER_B = 3;
  public static final int ARM_SWIVEL_ENCODER_A = 4;
  public static final int ARM_SWIVEL_ENCODER_B = 5;

  // climb
  public static final int CLIMB_FRONT_VICTOR = 9;
  public static final int CLIMB_BACK_VICTOR = 4;
  public static final int CLIMB_MOVE_VICTOR = 5;
}
