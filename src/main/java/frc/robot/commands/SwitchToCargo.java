package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class SwitchToCargo extends Command {

  private Logger logger = new Logger(SwitchToCargo.class.getName());

  public SwitchToCargo() {
    logger.detail("constructor");
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
    //    Robot.arm.switchToCargo();
  }

  @Override
  protected void execute() {
    logger.detail("execute");
  }

  @Override
  protected boolean isFinished() {
    logger.info("finished");
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
