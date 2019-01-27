package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SwitchToHatch extends Command {

  private static final boolean DEBUG = false;

  public SwitchToHatch() {
    debug("constructor");
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
    Robot.arm.switchToHatch();
    System.out.println("Switch to Hatch");
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
    debug("interrupred");
    end();
  }

  private void debug(String s) {
		if (DEBUG) {
			System.out.println("SwitchToHatch command: " + s);
		}
	}

}
