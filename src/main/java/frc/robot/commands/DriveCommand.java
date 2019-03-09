package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class DriveCommand extends Command {

  private static double leftSpeed = 0;
  private static double rightSpeed = 0;

  private static final double maxDriveAccel = 0.02;
  private static final double maxTurnAccel = 0.04;

//  private Logger logger = new Logger(DriveCommand.class.getName());

  public DriveCommand() {
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

    double leftValue = Robot.oi.drivestick.getRawAxis(1)*0.7;
    double rightValue = Robot.oi.drivestick.getRawAxis(2)*0.7;

    if(leftValue > leftSpeed) {
      if(Math.abs(leftSpeed - leftValue) > maxDriveAccel) {
        leftSpeed += maxDriveAccel;
      } else {
        leftSpeed = leftValue;
      } 
    }

    if(leftValue < leftSpeed) {
      if(Math.abs(leftSpeed - leftValue) > maxDriveAccel) {
        leftSpeed -= maxDriveAccel;
      } else {
        leftSpeed = leftValue;
      }
    }

    if(rightValue > rightSpeed) {
      if(Math.abs(rightSpeed - rightValue) > maxTurnAccel) {
        rightSpeed += maxTurnAccel;
      } else {
        rightSpeed = rightValue;
      } 
    }

    if(rightValue < rightSpeed) {
      if(Math.abs(rightSpeed - rightValue) > maxTurnAccel) {
        rightSpeed -= maxTurnAccel;
      } else {
        rightSpeed = rightValue;
      }
    }

    if(rightValue > 0.4 && Math.abs(rightSpeed) < 0.4) {
      rightSpeed = 0.4;
    }
    if(rightValue < -0.4 && Math.abs(rightSpeed) < 0.4) {
      rightSpeed = -0.4;
    }


    if(Math.abs(leftValue) <= 0.1) {
      leftSpeed = leftValue;
    }
    if(Math.abs(rightValue) <= 0.1) {
      rightSpeed = rightValue;
    }

//    System.out.println("left joy: " + leftValue + " right joy: " + rightValue + " drivespeed: " + leftSpeed + " turnspeed: " + rightSpeed);
    if (Math.abs(leftValue) > 0.1) {
      Robot.drivetrain.curvatureDrive(leftSpeed, rightSpeed);
    } else {
      Robot.drivetrain.arcadeDrive(leftSpeed, rightSpeed);
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

  private double getAxis(double value){
    return value*(Math.abs(value));
  }
}
