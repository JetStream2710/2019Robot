package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.CargoIntake;
import frc.robot.commands.CargoOuttake;
import frc.robot.commands.HatchPush;
import frc.robot.commands.SwitchToCargo;
import frc.robot.commands.SwitchToHatch;

public class OI {
  // controllers defined
  public Joystick drivestick = new Joystick(RobotMap.JOYSTICK_DRIVER);
  public Joystick auxstick = new Joystick(RobotMap.JOYSTICK_AUX);

  // buttons defined
  // x = 1, a = 2, b = 3, y = 4
  // lb (left top trigger) = 5, rb (right top trigger) = 6
  // lt (left bottom trigger) = 7, rt (right bottom trigger) = 8
  public Button d_LB = new JoystickButton(drivestick, RobotMap.CARGO_INTAKE);
  public Button d_RB = new JoystickButton(drivestick, RobotMap.CARGO_OUTTAKE);
  public Button d_LT = new JoystickButton(drivestick, RobotMap.HATCH_CONTROL);

  public Button a_A = new JoystickButton(auxstick, RobotMap.SWITCH_TO_HATCH);
  public Button a_Y = new JoystickButton(auxstick, RobotMap.SWITCH_TO_CARGO);

  public OI() {
    d_LB.whileHeld(new CargoIntake());
    d_RB.whileHeld(new CargoOuttake());
    d_LT.whenPressed(new HatchPush());

    a_A.whenPressed(new SwitchToHatch());
    a_Y.whenPressed(new SwitchToCargo());
  }
}
