package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;

public class Hatch extends Subsystem {

  public static final double MAX = Double.MAX_VALUE;
  public static final double MIN = Double.MIN_VALUE;
  public static final double SPEED = 0.5;

  private Logger logger = new Logger(Hatch.class.getName());
  private JetstreamTalon talon;

  public Hatch() {
    super();
    logger.detail("constructor");
    talon = new JetstreamTalon(RobotMap.HATCH_TALON, MAX, MIN);
  }

  public void hatchPush() {
    logger.info("hatchPush called");
    talon.setSpeed(SPEED);
  }

  @Override
  public void initDefaultCommand() {}
}
