package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class ClimbFrontExtend extends Command {

  private Logger logger = new Logger(ClimbFrontExtend.class.getName());
  private static final double SPEED = 0.5;

  public ClimbFrontExtend() {
    logger.detail("constructor");
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
    Robot.climb.setFrontMotorSpeed(SPEED);
  }

  @Override
  protected void execute() {
    logger.detail("execute");
  }

  @Override
  protected boolean isFinished() {
    logger.info("finished");
    return false;
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
