package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SwitchToCargo extends Command {

  public static final boolean DEBUG = false;

  public SwitchToCargo() {
    debug("constructor");
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
    debug("initialize");
    Robot.arm.switchToCargo();
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
      System.out.println("SwitchToCargo command: " + s);
    }
  }
}
