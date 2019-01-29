package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
  //controllers defined
  public Joystick drivestick = new Joystick(RobotMap.JOYSTICK_DRIVER);
  public Joystick auxstick = new Joystick(RobotMap.JOYSTICK_AUX);

  //buttons defined
  public Button A;
  public Button B;
  public Button X;
  public Button Y;

  public OI() {
  }
}
