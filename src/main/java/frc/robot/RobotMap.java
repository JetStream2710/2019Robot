package frc.robot;

public class RobotMap {
    //joystick
    public static final int JOYSTICK_DRIVER = 0;
    public static final int DRIVER_MOVE_AXIS = 1;
    public static final int DRIVER_ROTATE_AXIS = 0;
    public static final int VERTICAL_AXIS = 0;
    public static final int NOTHER_AXIS = 0;

    public static final int JOYSTICK_AUX = 1;
    public static final int CARGO_INTAKE = 0;
    public static final int CARGO_OUTTAKE = 0;
    public static final int HATCH_IN = 0;
    public static final int HATCH_OUT = 0;
    public static final int SWITCH_TO_HATCH = 0;
    public static final int SWITCH_TO_CARGO = 0;

    //drivetrain
    public static final int DRIVETRAIN_FRONT_LEFT_TALON = 0;
    public static final int DRIVETRAIN_FRONT_RIGHT_TALON = 0;
    public static final int DRIVETRAIN_REAR_LEFT_TALON = 0;
    public static final int DRIVETRAIN_REAR_RIGHT_TALON = 0;

    //elevator
    public static final int ELEVATOR_LEFT_TALON = 0;
    public static final int ELEVATOR_RIGHT_TALON = 0;

    //cargo
    public static final int CARGO_LEFT_VICTOR = 0;
    public static final int CARGO_RIGHT_VICTOR = 0;

    //hatch
    public static final int HATCH_SOLENOID_ON = 0;
    public static final int HATCH_SOLENOID_OFF = 0;

    //arm
    //when the motor controller is figured out, replace the "MOTOR" with the correct controller
    public static final int ARM_VERTICAL_TALON = 0;
    public static final int ARM_SWIVEL_TALON = 0;

    //pneumatics
    public static final int PCM = 0;

}
