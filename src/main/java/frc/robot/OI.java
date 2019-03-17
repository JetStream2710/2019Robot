package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;

public class OI {
  // X = 1, Y = 4
  // A = 2, B = 3
  // LB (left top trigger) = 5, RB (right top trigger) = 6
  // LT (left bottom trigger) = 7, RT (right bottom trigger) = 8

  // driver joystick
  public static final int JOYSTICK_DRIVER = 0;
  public static final int DRIVER_MOVE_AXIS = 1;
  public static final int DRIVER_ROTATE_AXIS = 2;
  public static final int CARGO_INTAKE = 5;        // driver LB
  public static final int CARGO_OUTTAKE = 6;       // driver RB
  public static final int HATCH_PUSH = 7;          // driver LT
  public static final int CLIMB_MOVE_FORWARD = 2;  // driver A
  public static final int CLIMB_MOVE_BACK = 3;     // driver B
  public static final int PIXY_FOLLOW_LINE = 4;          // driver Y

  // figure out feeder station stuff -- waiting on the stuff FEEDER STATION
  public static final int JOYSTICK_AUX = 1;
  public static final int ELEVATOR_AXIS = 1;
  public static final int ARM_AXIS = 3;
  public static final int TOGGLE_CLIMB = 1; // aux X
  public static final int WEAPONS_UP = 4;      // aux Y
  public static final int WEAPONS_DOWN = 2;    // aux A
  public static final int LEVEL_1 = 3;

  // controllers defined
  public Joystick drivestick = new Joystick(JOYSTICK_DRIVER);
  public Joystick auxstick = new Joystick(JOYSTICK_AUX);

  // buttons defined
  public Button cargoIntake = new JoystickButton(drivestick, CARGO_INTAKE);
  public Button cargoOuttake = new JoystickButton(drivestick, CARGO_OUTTAKE);
  public Button hatchPush = new JoystickButton(drivestick, HATCH_PUSH);
  public Button climbMoveForward = new JoystickButton(drivestick, CLIMB_MOVE_FORWARD);
  public Button climbMoveBackward = new JoystickButton(drivestick, CLIMB_MOVE_BACK);
  public Button pixyFollowLine = new JoystickButton(drivestick, PIXY_FOLLOW_LINE);

  public Button weaponsUp = new JoystickButton(auxstick, WEAPONS_UP);
  public Button weaponsDown = new JoystickButton(auxstick, WEAPONS_DOWN);
  public Button toggleClimb = new JoystickButton(auxstick, TOGGLE_CLIMB);
  public Button armLevel1 = new JoystickButton(auxstick, LEVEL_1);

  public OI() {
    cargoIntake.whileHeld(new CargoIntake());
    cargoOuttake.whileHeld(new CargoOuttake());
    hatchPush.whenPressed(new HatchPush());
    climbMoveForward.whileHeld(new ClimbMoveForward());
    climbMoveBackward.whileHeld(new ClimbMoveBackward());
    pixyFollowLine.whenPressed(new FollowLine());

    weaponsUp.whenPressed(new WeaponsUp());
    weaponsDown.whenPressed(new WeaponsDown());
    toggleClimb.whileHeld(new ToggleClimb());
    armLevel1.whenActive(new ArmLevel1());
  }
}
