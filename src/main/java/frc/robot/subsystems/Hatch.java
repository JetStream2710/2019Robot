package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;

public class Hatch extends Subsystem {

  public static final int MAX = Integer.MAX_VALUE;
  public static final int MIN = Integer.MIN_VALUE;
  public static final double SPEED = 0.5;

  private Logger logger = new Logger(Hatch.class.getName());
  private JetstreamVictor victor;

  public Hatch() {
    super();
    logger.detail("constructor");
    victor = new JetstreamVictor(RobotMap.HATCH_VICTOR);
  }

  public void hatchPush() {
    logger.info("hatchPush called");
    victor.set(SPEED);
  }

  public void hatchIn(){
    logger.info("hatchIn called");
    victor.set(-SPEED);
  }

  @Override
  public void initDefaultCommand() {}
}
