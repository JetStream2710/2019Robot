package frc.robot;

public class RobotMap {
  // drivetrain
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
  // label as, but also mislabelled
  public static final int ARM_VERTICAL_TALON = 12;
  // label as, but also mislabelled
  public static final int ARM_SWIVEL_TALON = 13;
  // climb
  public static final int CLIMB_FRONT_VICTOR = 4;
  public static final int CLIMB_BACK_VICTOR = 9;
  public static final int CLIMB_MOVE_VICTOR = 5;

  // pneumatics
  public static final int PCM_NODE = 0;
  public static final int HATCH_SOLENOID_ON = 2;
  public static final int HATCH_SOLENOID_OFF = 3;
}
