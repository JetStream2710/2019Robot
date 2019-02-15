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
  public static final int SWITCH_TO_HATCH = 2;
  public static final int SWITCH_TO_CARGO = 4;
  public static final int ELEVATOR_AXIS = 1;
  public static final int ARM_AXIS = 3;

  // drivetrain
  // master = talon    slave = victor
  public static final int DRIVETRAIN_LEFT_TALON = 16;
  public static final int DRIVETRAIN_LEFT_VICTOR = 1;
  public static final int DRIVETRAIN_RIGHT_TALON = 15;
  public static final int DRIVETRAIN_RIGHT_VICTOR = 14;

  public static final int DRIVETRAIN_LEFT_ENCODER_A = 0;
  public static final int DRIVETRAIN_LEFT_ENCODER_B = 1;
  public static final int DRIVETRAIN_RIGHT_ENCODER_A = 2;
  public static final int DRIVETRAIN_RIGHT_ENCODER_B = 3;

  // elevator
  public static final int ELEVATOR_TALON = 2;
  public static final int ELEVATOR_VICTOR = 3;

  // cargo
  public static final int CARGO_VICTOR = 11;

  // arm
  public static final int ARM_VERTICAL_TALON = 13;
  public static final int ARM_SWIVEL_TALON = 12;
  // climb
  public static final int CLIMB_FRONT_VICTOR = 9;
  public static final int CLIMB_BACK_VICTOR = 4;
  public static final int CLIMB_MOVE_VICTOR = 5;

  // pneumatics
  public static final int PCM_NODE = 0;
  public static final int HATCH_SOLENOID_ON = 0;
  public static final int HATCH_SOLENOID_OFF = 1;
}
