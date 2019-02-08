package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;

public class Hatch extends Subsystem {

  public static final int MAX = Integer.MAX_VALUE;
  public static final int MIN = Integer.MIN_VALUE;
  public static final double SPEED = 0.5;

  private Logger logger = new Logger(Hatch.class.getName());
  private JetstreamTalon talon;

  public Hatch() {
    super();
    logger.detail("constructor");
    talon = new JetstreamTalon(RobotMap.HATCH_TALON, null, MAX, MIN);
  }

  public void hatchPush() {
    logger.info("hatchPush called");
    talon.set(SPEED);
  }

  public void hatchIn(){
    logger.info("hatchIn called");
    talon.set(-SPEED);
  }

  @Override
  public void initDefaultCommand() {}
}
