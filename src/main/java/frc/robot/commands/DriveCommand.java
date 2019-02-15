package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class DriveCommand extends Command {

  private Logger logger = new Logger(DriveCommand.class.getName());

  public DriveCommand() {
    logger.detail("constructor");
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
  }

  @Override
  protected void execute() {
    if (Robot.isAuto) {
      return;
    }
    double moveSpeed = Robot.oi.drivestick.getRawAxis(OI.DRIVER_MOVE_AXIS);
    double rotateSpeed = Robot.oi.drivestick.getRawAxis(OI.DRIVER_ROTATE_AXIS);
    logger.detail("execute moveSpeed: " + moveSpeed + " rotateSpeed: " + rotateSpeed);
    Robot.drivetrain.arcadeDrive(moveSpeed, rotateSpeed);
  }

  @Override
  protected boolean isFinished() {
    logger.detail("finished");
    return false;
  }

  @Override
  protected void end() {
    logger.info("end");
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    logger.warning("interrupted");
    end();
  }
}
