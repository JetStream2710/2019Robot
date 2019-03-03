package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class HatchPush extends Command {

  public static final long HATCH_DELAY_TIME_MILLIS = 2000;

//  private Logger logger = new Logger(HatchPush.class.getName());
  private long time;

  public HatchPush() {
  //  logger.detail("constructor");
    requires(Robot.hatch);
  }

  @Override
  protected void initialize() {
    time = System.currentTimeMillis() + HATCH_DELAY_TIME_MILLIS;
  //  logger.info("initialize delay: " + HATCH_DELAY_TIME_MILLIS);
    Robot.hatch.hatchOut();
  }

  @Override
  protected void execute() {
  //  logger.detail("execute");
  }

  @Override
  protected boolean isFinished() {
  //  logger.detail("finished");
    return System.currentTimeMillis() > time;
  }

  @Override
  protected void end() {
  //  logger.info("end");
    Robot.hatch.hatchIn();
  }

  @Override
  protected void interrupted() {
  //  logger.warning("interrupted");
    end();
  }
}
