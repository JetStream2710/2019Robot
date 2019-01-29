package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class HatchOut extends Command {

  public static final boolean DEBUG = false;

  public HatchOut() {
    debug("constructor");
    requires(Robot.hatch);
  }

  @Override
  protected void initialize() {
    debug("initialize");
    Robot.hatch.hatchOut();
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
  }

  @Override
  protected void interrupted() {
    debug("interrupted");
    end();
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("HatchOut command: " + s);
    }
  }
}
