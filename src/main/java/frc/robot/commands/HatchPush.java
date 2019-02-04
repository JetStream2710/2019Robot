package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

// TODO: let's talk about combining hatch in/out. it may be easier to do in/out with one button
// press
public class HatchPush extends Command {

  private Logger logger = new Logger(HatchPush.class.getName());

  public HatchPush() {
    logger.detail("constructor");
    requires(Robot.hatch);
  }

  @Override
  protected void initialize() {
    logger.detail("initialize");
  }

  @Override
  protected void execute() {
    logger.info("execute");
    Robot.hatch.hatchPush();
  }

  @Override
  protected boolean isFinished() {
    logger.info("finished");
    return false;
  }

  @Override
  protected void end() {
    logger.info("end");
    // TODO: anything we need to worry about for the end state?
  }

  @Override
  protected void interrupted() {
    debug("interrupted");
    end();
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("HatchIn command: " + s);
    }
  }
}
