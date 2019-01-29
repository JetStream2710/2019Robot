package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveCommand extends Command {

  public static final boolean DEBUG = false;

  public DriveCommand() {
    debug("constructor");
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if(Robot.isAuto) {
      return;
    }
    double moveSpeed = Robot.oi.drivestick.getRawAxis(RobotMap.DRIVER_MOVE_AXIS);
    double rotateSpeed = Robot.oi.drivestick.getRawAxis(RobotMap.DRIVER_ROTATE_AXIS);
    debug("execute moveSpeed: " + moveSpeed + " rotateSpeed: " + rotateSpeed);
    Robot.drivetrain.arcadeDrive(moveSpeed, rotateSpeed);
  }

  @Override
  protected boolean isFinished() {
    debug("finished");
    return false;
  }

  @Override
  protected void end() {
    debug("end");
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    debug("interrupted");
    end();
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("DriveCommand command: " + s);
    }
  }
}
