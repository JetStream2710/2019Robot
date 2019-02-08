package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;

public class Climb extends Subsystem {

  private Logger logger = new Logger(Climb.class.getName());

  private JetstreamVictor frontLeftVictor;
  private JetstreamVictor frontRightVictor;
  private JetstreamVictor backVictor;
  private SpeedControllerGroup frontGroup;

  public Climb() {
    super();
    logger.detail("constructor");

    frontLeftVictor = new JetstreamVictor(RobotMap.CLIMB_FRONT_LEFT_VICTOR);
    frontRightVictor = new JetstreamVictor(RobotMap.CLIMB_FRONT_RIGHT_VICTOR);
    backVictor = new JetstreamVictor(RobotMap.CLIMB_BACK_VICTOR);
    frontGroup = new JetstreamControllerGroup(frontLeftVictor, frontRightVictor);
  }

  public void setFrontMotorSpeed(double speed) {
    logger.info("setFrontMotorSpeed speed: " + speed);
    frontGroup.setSpeed(speed);
  }

  public void setBackMotorSpeed(double speed) {
    logger.info("setBackMotorSpeed speed : " + speed);
    backVictor.setSpeed(speed);
  }

  @Override
  public void initDefaultCommand() {
  }
}
