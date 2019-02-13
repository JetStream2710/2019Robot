package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;

public class Climb extends Subsystem {

  public static final double CLIMB_FRONT_MIN_OUTPUT = -1;
  public static final double CLIMB_FRONT_MAX_OUTPUT = 1;
  public static final double CLIMB_BACK_MIN_OUTPUT = -1;
  public static final double CLIMB_BACK_MAX_OUTPUT = 1;
  public static final double CLIMB_MOVE_MIN_OUTPUT = -.2;
  public static final double CLIMB_MOVE_MAX_OUTPUT = 0.2;

  private Logger logger = new Logger(Climb.class.getName());

  private JetstreamVictor frontVictor;
  private JetstreamVictor backVictor;
  private JetstreamVictor moveVictor;

  public Climb() {
    super();
    logger.detail("constructor");

    frontVictor = new JetstreamVictor("Climb Front Victor", RobotMap.CLIMB_FRONT_VICTOR, CLIMB_FRONT_MIN_OUTPUT, CLIMB_FRONT_MAX_OUTPUT);
    backVictor = new JetstreamVictor("Climb Back Victor", RobotMap.CLIMB_BACK_VICTOR, CLIMB_BACK_MIN_OUTPUT, CLIMB_BACK_MAX_OUTPUT);
    moveVictor = new JetstreamVictor("Climb Move Victor", RobotMap.CLIMB_MOVE_VICTOR, CLIMB_MOVE_MIN_OUTPUT, CLIMB_MOVE_MAX_OUTPUT);
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
