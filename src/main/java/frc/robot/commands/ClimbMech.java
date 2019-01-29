package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbMech extends Command {

  private static final boolean DEBUG = false;

  public ClimbMech() {
    requires(Robot.climb);
    debug("constructor");
  }

  @Override
  protected void initialize() {
    Robot.climb.climbMech();
    debug("initialized");
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
    end();
    debug("end");
  }

  private void debug(String s) {
		if (DEBUG) {
			System.out.println("ClimbMech Command: " + s);
		}
	}
}
