package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.util.Logger;

/**
 * Drives with Speed and Brake adjustment for various situations:
 * 0: High Speed Coast
 * 1: High Speed Brake
 * 2: Low Speed Brake
 */

public class DriveWithAdjustment extends Command {

//  private Logger logger = new Logger(DriveWithAdjustment.class.getName());

  private static int driveState = 0;

  private final double LOW_SPEED_SCALAR = 0.5;
  private double speedScalar = 1.0;

  public DriveWithAdjustment() {
  //  logger.detail("constructor");
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
  //  logger.info("initialize");
  }

  @Override
  protected void execute() {
    if (Robot.isAuto) {
      return;
    }
    
    int dPov = Robot.oi.drivestick.getPOV();
    if(dPov == 0 ) { 
    //  logger.info("driveState changed from " + driveState + " to 0");
      driveState = 0;
      Robot.drivetrain.setCoastMode();
      speedScalar = 1.0;
    }

    if(dPov == 270) {
    //  logger.info("driveState changed from " + driveState + " to 1");
      driveState = 1;
      Robot.drivetrain.setBrakeMode();
      speedScalar = 1.0;
    }

    if (dPov == 180) {
    //  logger.info("driveState changed from " + driveState + " to 2");
      driveState = 2;
      Robot.drivetrain.setBrakeMode();
      speedScalar = LOW_SPEED_SCALAR;
    }

    double moveSpeed = Robot.oi.drivestick.getRawAxis(OI.DRIVER_MOVE_AXIS) * speedScalar;
    double rotateSpeed = Robot.oi.drivestick.getRawAxis(OI.DRIVER_ROTATE_AXIS);
  //  logger.detail("execute moveSpeed: " + moveSpeed + " rotateSpeed: " + rotateSpeed);
  //  logger.detail("driveState: " + driveState);
    if (Math.abs(moveSpeed) < 0.1) {
      Robot.drivetrain.arcadeDrive(moveSpeed, rotateSpeed);
    } else {
      Robot.drivetrain.curvatureDrive(moveSpeed, rotateSpeed);
    }
  }

  @Override
  protected boolean isFinished() {
  //  logger.detail("finished");
    return false;
  }

  @Override
  protected void end() {
  //  logger.info("end");
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
  //  logger.warning("interrupted");
    end();
  }
}
