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
  public static final int DRIVETRAIN_FRONT_LEFT_TALON = 0;
  public static final int DRIVETRAIN_FRONT_RIGHT_TALON = 0;
  public static final int DRIVETRAIN_REAR_LEFT_TALON = 0;
  public static final int DRIVETRAIN_REAR_RIGHT_TALON = 0;

  // elevator
  public static final int ELEVATOR_LEFT_TALON = 0;
  public static final int ELEVATOR_RIGHT_TALON = 0;

  // cargo
  public static final int CARGO_LEFT_VICTOR = 0;
  public static final int CARGO_RIGHT_VICTOR = 0;

  // hatch
  public static final int HATCH_TALON = 0;

  // arm
  public static final int ARM_VERTICAL_TALON = 0;
  public static final int ARM_SWIVEL_TALON = 0;

  // climb
  public static final int CLIMB_FRONT_LEFT_VICTOR = 0;
  public static final int CLIMB_FRONT_RIGHT_VICTOR = 0;
  public static final int CLIMB_BACK_VICTOR = 0;

  // encoders
  public static final int ENCODER_VERTICAL_DI1 = 0;
  public static final int ENCODER_VERTICAL_DI2 = 0;

  public static final int ENCODER_SWIVEL_DI1 = 0;
  public static final int ENCODER_SWIVEL_DI2 = 0;

  public static final int ENCODER_TALON_GEAR_RATIO_DPP = 0;
  public static final int ENCODER_SAMPLES_TO_AVERAGE = 0;
}
