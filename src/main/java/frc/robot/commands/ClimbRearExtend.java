package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class ClimbRearExtend extends Command {

  private Logger logger = new Logger(ClimbRearExtend.class.getName());

  public ClimbRearExtend() {
    logger.detail("constructor");
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
    Robot.climb.climbRearExtend();
  }

  @Override
  protected void execute() {
    logger.detail("execute");
  }

  @Override
  protected boolean isFinished() {
    logger.detail("isFinished");
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
