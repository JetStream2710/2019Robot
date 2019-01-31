package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

// TODO: talk to drivers to figure out how this is going to work
public class ClimbFront extends Command {

  private static final boolean DEBUG = false;
  private static final double SPEED = 0.5;

  public ClimbFront() {
    debug("constructor");
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
    debug("initialize");
    Robot.climb.setFrontMotorSpeed(SPEED);
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    debug("finished");
    return Robot.climb.getFrontEncoderValue() > ;
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
