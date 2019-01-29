package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class VerticalArm extends Command {

  public static final boolean DEBUG = false;

  public VerticalArm() {
    debug("constructor");
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if(Robot.isAuto) {
      return;
    }
    double moveSpeed = Robot.oi.auxstick.getRawAxis(RobotMap.NOTHER_AXIS);
    debug("execute move speed: " + moveSpeed);
    Robot.arm.verticalArm(moveSpeed);
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
      System.out.println("VerticalArm Command: " + s);
    }
  }
}
