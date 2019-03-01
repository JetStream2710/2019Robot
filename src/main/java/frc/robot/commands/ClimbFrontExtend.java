package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class ClimbFrontExtend extends Command {

  private Logger logger = new Logger(ClimbFrontExtend.class.getName());

  public ClimbFrontExtend() {
    logger.detail("constructor");
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
    logger.detail("initialize");
  }

  @Override
  protected void execute() {
    logger.info("execute");
    Robot.climb.climbFrontExtend();
  }

  @Override
  protected boolean isFinished() {
    logger.detail("finished");
    return true;
  }

  @Override
  protected void end() {
    logger.info("end");
  }

  @Override
  protected void interrupted() {
    logger.warning("interrupted");
    end();
  }
}
