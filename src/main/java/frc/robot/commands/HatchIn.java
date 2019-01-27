package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class HatchIn extends Command {

  private static final boolean DEBUG = false;

  public HatchIn() {
    debug("constructor");
    requires(Robot.hatch);
  }

  @Override
  protected void initialize() {
    Robot.hatch.hatchIn();
    System.out.println("Hatch In");
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
			System.out.println("HatchIn command: " + s);
		}
	}

}
