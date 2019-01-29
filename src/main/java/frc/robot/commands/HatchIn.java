package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

// TODO: let's talk about combining hatch in/out. it may be easier to do in/out with one button press
public class HatchIn extends Command {

  private static final boolean DEBUG = false;

  public HatchIn() {
    debug("constructor");
    requires(Robot.hatch);
  }

  @Override
  protected void initialize() {
    debug("initialize");
    Robot.hatch.hatchIn();
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    debug("finished");
    return true;
  }

  @Override
  protected void end() {
    debug("end");
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
