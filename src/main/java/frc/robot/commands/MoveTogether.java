package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

// TODO: make some pre-set positions
public class MoveTogether extends Command {

  public static final boolean DEBUG = false;

  public MoveTogether() {
    debug("constructor");
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    // TODO: may need to set isAuto during button-triggered movements
    if(Robot.isAuto) {
      return;
    }
    double moveSpeed = Robot.oi.auxstick.getRawAxis(RobotMap.VERTICAL_AXIS);
    debug("execute movespeed: " + moveSpeed);
    Robot.arm.moveTogether(moveSpeed);

  }

  @Override
  protected boolean isFinished() {
    debug("finished");
    return false;
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
      System.out.println("MoveTogether Command: " + s);
    }
  }
}
