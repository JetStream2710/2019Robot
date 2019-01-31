package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamVictor;

public class Cargo extends Subsystem {

  private static final boolean DEBUG = false;

  private JetstreamVictor leftVictor = null;
  private JetstreamVictor rightVictor = null;

  private double SPEED_IN = -0.5;
  private double SPEED_OUT = 0.5;

  public Cargo(){
    super();

    leftVictor = new JetstreamVictor(RobotMap.CARGO_LEFT_VICTOR);
    rightVictor = new JetstreamVictor(RobotMap.CARGO_RIGHT_VICTOR);

    debug("constructor");
  }

  //CHECK THESE VALUES
  public void cargoIntake(){
    debug("cargoIntake called");

    leftVictor.setSpeed(SPEED_IN);
    rightVictor.setSpeed(SPEED_IN);
  }

  public void cargoOuttake(){
    debug("cargoOuttake called");

    leftVictor.setSpeed(SPEED_OUT);
    rightVictor.setSpeed(SPEED_OUT);
  }

  public void cargoStop(){
    debug("cargoStop called");

    leftVictor.setSpeed(0);
    rightVictor.setSpeed(0);
  }

  @Override
  public void initDefaultCommand() {
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("Cargo Subsystem: " + s);
    }
  }
}
