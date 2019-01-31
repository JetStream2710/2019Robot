package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbMech extends Command {

  private static final boolean DEBUG = false;

  public ClimbMech() {
    debug("constructor");
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
    debug("initialize");
    //    Robot.climb.climbMech();
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
      System.out.println("ClimbMech Command: " + s);
    }
  }
}
