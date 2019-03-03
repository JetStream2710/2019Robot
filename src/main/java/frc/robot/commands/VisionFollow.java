package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.util.Logger;

public class VisionFollow extends Command {

//  private Logger logger = new Logger(VisionFollow.class.getName());

  public VisionFollow() {
  //  logger.detail("constructor");
  }

  @Override
  protected void initialize() {
  //  logger.info("initialize");
  }

  @Override
  protected void execute() {
  //  logger.info("initialize");
  }

  @Override
  protected boolean isFinished() {
  //  logger.info("isFinished");
    return false;
  }

  @Override
  protected void end() {
  //  logger.info("end");
  }

  @Override
  protected void interrupted() {
  //  logger.warning("interrupted");
  }
}
