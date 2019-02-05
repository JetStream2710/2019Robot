package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.Logger;

public class DriveCommand extends Command {

  private Logger logger = new Logger(DriveCommand.class.getName());

  public DriveCommand() {
    logger.detail("constructor");
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
    logger.detail("initialize");
  }

  @Override
  protected void execute() {
    logger.info("execute");
    if (Robot.isAuto) {
      return;
    }
    double moveSpeed = Robot.oi.drivestick.getRawAxis(RobotMap.DRIVER_MOVE_AXIS);
    double rotateSpeed = Robot.oi.drivestick.getRawAxis(RobotMap.DRIVER_ROTATE_AXIS);
    logger.detail("execute moveSpeed: " + moveSpeed + " rotateSpeed: " + rotateSpeed);
    Robot.drivetrain.arcadeDrive(moveSpeed, rotateSpeed);
  }

  @Override
  protected boolean isFinished() {
    logger.info("finished");
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
