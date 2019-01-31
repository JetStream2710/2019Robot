package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;

public class Cargo extends Subsystem {

  public static final double SPEED_IN = -0.5;
  public static final double SPEED_OUT = 0.5;

  private Logger logger = new Logger(Cargo.class.getName());
  private JetstreamVictor leftVictor;
  private JetstreamVictor rightVictor;

  public Cargo() {
    super();
    logger.detail("constructor");

    leftVictor = new JetstreamVictor(RobotMap.CARGO_LEFT_VICTOR);
    rightVictor = new JetstreamVictor(RobotMap.CARGO_RIGHT_VICTOR);
  }

  //CHECK THESE VALUES
  public void cargoIntake() {
    logger.info("cargoIntake speed: " + SPEED_IN);
    leftVictor.setSpeed(SPEED_IN);
    rightVictor.setSpeed(SPEED_IN);
  }

  public void cargoOuttake(){
    logger.info("cargoOuttake speed: " + SPEED_OUT);
    leftVictor.setSpeed(SPEED_OUT);
    rightVictor.setSpeed(SPEED_OUT);
  }

  public void cargoStop(){
    logger.info("cargoStop");
    leftVictor.setSpeed(0);
    rightVictor.setSpeed(0);
  }

  @Override
  public void initDefaultCommand() {
  }
}
