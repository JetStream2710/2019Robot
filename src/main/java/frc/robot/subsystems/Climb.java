package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamControllerGroup;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;

public class Climb extends Subsystem {

  private Logger logger = new Logger(Climb.class.getName());

  // TODO: no need to initialize these to null
  private JetstreamVictor frontLeftVictor = null;
  private JetstreamVictor frontRightVictor = null;
  private JetstreamVictor backVictor = null;
  private JetstreamControllerGroup frontGroup;

  public Climb() {
    super();
    logger.detail("constructor");

    frontLeftVictor = new JetstreamVictor(RobotMap.CLIMB_FRONT_LEFT_VICTOR);
    frontRightVictor = new JetstreamVictor(RobotMap.CLIMB_FRONT_RIGHT_VICTOR);
    backVictor = new JetstreamVictor(RobotMap.CLIMB_BACK_VICTOR);
    frontGroup = new JetstreamControllerGroup(frontLeftVictor, frontRightVictor);
  }

  // Did we want to be able to control movement of the victors independently?
  // Brian: Probably not, just front/back is fine

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
