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
  public static final int CLIMB_FRONT_EXTEND = 4;  // driver Y
  public static final int CLIMB_FRONT_RETRACT = 2; // driver A
  public static final int CLIMB_REAR_EXTEND = 3;   // driver B
  public static final int CLIMB_REAR_RETRACT = 1;  // driver X
  public static final int CLIMB_MOVE = 8;          // driver RT

  // figure out feeder station stuff -- waiting on the stuff FEEDER STATION
  public static final int JOYSTICK_AUX = 1;
  public static final int ELEVATOR_AXIS = 1;
  public static final int ARM_AXIS = 3;
  public static final int SWITCH_TO_HATCH = 1; // aux X
  public static final int SWITCH_TO_CARGO = 3; // aux B
  public static final int WEAPONS_UP = 4;      // aux Y
  public static final int WEAPONS_DOWN = 2;    // aux A
  public static final int CALIBRATE = 7;       // aux LT
  public static final int HATCH_HOVER = 6;     // aux RB
  public static final int HATCH_DOWN = 8;      // aux RT

  // controllers defined
  public Joystick drivestick = new Joystick(JOYSTICK_DRIVER);
  public Joystick auxstick = new Joystick(JOYSTICK_AUX);

  // buttons defined
  public Button cargoIntake = new JoystickButton(drivestick, CARGO_INTAKE);
  public Button cargoOuttake = new JoystickButton(drivestick, CARGO_OUTTAKE);
  public Button hatchPush = new JoystickButton(drivestick, HATCH_PUSH);
  public Button climbFrontExtend = new JoystickButton(drivestick, CLIMB_FRONT_EXTEND);
  public Button climbFrontRetract = new JoystickButton(drivestick, CLIMB_FRONT_RETRACT);
  public Button climbRearExtend = new JoystickButton(drivestick, CLIMB_REAR_EXTEND);
  public Button climbRearRetract = new JoystickButton(drivestick, CLIMB_REAR_RETRACT);
  public Button climbMove = new JoystickButton(drivestick, CLIMB_MOVE);

  public Button switchToHatch = new JoystickButton(auxstick, SWITCH_TO_HATCH);
  public Button switchToCargo = new JoystickButton(auxstick, SWITCH_TO_CARGO);
  public Button weaponsUp = new JoystickButton(auxstick, WEAPONS_UP);
  public Button weaponsDown = new JoystickButton(auxstick, WEAPONS_DOWN);
  public Button calibrate = new JoystickButton(auxstick, CALIBRATE);
  public Button hatchHover = new JoystickButton(auxstick, HATCH_HOVER);
  public Button hatchDown = new JoystickButton(auxstick, HATCH_DOWN);

  public OI() {
    cargoIntake.whileHeld(new CargoIntake());
    cargoOuttake.whileHeld(new CargoOuttake());
    hatchPush.whenPressed(new HatchPush());
    climbFrontExtend.whenPressed(new ClimbFrontExtend());
    climbFrontRetract.whileHeld(new ClimbFrontRetract());
    climbRearExtend.whenPressed(new ClimbRearExtend());
    climbRearRetract.whileHeld(new ClimbRearRetract());
    climbMove.whileHeld(new ClimbRearMove());

    switchToHatch.whenPressed(new SwitchToHatch());
    switchToCargo.whenPressed(new SwitchToCargo());
    weaponsUp.whenPressed(new WeaponsUp());
    weaponsDown.whenPressed(new WeaponsDown());
    calibrate.whenPressed(new CalibratePosition());
    hatchHover.whenPressed(new ArmHatchHover());
    hatchDown.whileHeld(new ArmHatchDown());
  }
}
