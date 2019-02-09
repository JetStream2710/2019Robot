package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;

public class Climb extends Subsystem {

  private Logger logger = new Logger(Climb.class.getName());

  private JetstreamVictor frontVictor;
  private JetstreamVictor backVictor;
  private JetstreamVictor moveVictor;

  public Climb() {
    super();
    logger.detail("constructor");

    frontVictor = new JetstreamVictor(RobotMap.CLIMB_FRONT_VICTOR);
    backVictor = new JetstreamVictor(RobotMap.CLIMB_BACK_VICTOR);
    moveVictor = new JetstreamVictor(RobotMap.CLIMB_MOVE_VICTOR);
  }

  public void setFrontMotorSpeed(double speed) {
    logger.info("setFrontMotorSpeed speed: " + speed);
    frontVictor.set(speed);
  }

  public void setBackMotorSpeed(double speed) {
    logger.info("setBackMotorSpeed speed : " + speed);
    backVictor.set(speed);
  }

  @Override
  public void initDefaultCommand() {
  }
}
