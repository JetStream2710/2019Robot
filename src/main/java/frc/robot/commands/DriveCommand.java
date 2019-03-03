package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class DriveCommand extends Command {

//  private Logger logger = new Logger(DriveCommand.class.getName());

private static Timer leftTimer = new Timer();
private static Timer rightTimer = new Timer();

private static double leftTime = 0;
private static double rightTime = 0;

private static final long leftMaxTime = 1000;
private static final long rightMaxTime = 500;

private static double leftValue = 0;
private static double rightValue = 0;

private static double prevLeftValue = 0;
private static double prevRightValue = 0;

  public DriveCommand() {
  //  logger.detail("constructor");
    requires(Robot.drivetrain);
  }

  public double[] getAdjustedDriveSpeeds() {
    double leftMovespeed = leftTime / leftMaxTime;
    double rightMovespeed = rightTime / rightMaxTime;
    leftMovespeed = (leftMovespeed > leftValue) ? leftValue : leftMovespeed;
    rightMovespeed = (rightMovespeed > rightValue) ? rightValue : rightMovespeed;

    double[] speeds = new double[] {leftMovespeed, rightMovespeed};
    return speeds;
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

//    double moveSpeed = Robot.oi.drivestick.getRawAxis(OI.DRIVER_MOVE_AXIS);
//    double rotateSpeed = Robot.oi.drivestick.getRawAxis(OI.DRIVER_ROTATE_AXIS);

// changed received joystick values for less sensitvity
    prevLeftValue = leftValue;
    rightValue = rightValue;
    leftValue = getAxis(Robot.oi.drivestick.getRawAxis(OI.DRIVER_MOVE_AXIS));
    rightValue = getAxis(Robot.oi.drivestick.getRawAxis(OI.DRIVER_ROTATE_AXIS));

    if(leftValue < 0.1) {
      leftTimer.stop();
      leftTimer.reset();
    } 
    if(rightValue < 0.1) {
      rightTimer.stop();
      rightTimer.reset();
    }
    if(prevLeftValue < 0.1 && leftValue >= 0.1) {
      leftTimer.start();
    }
    if(prevRightValue < 0.1 && rightValue >= 0.1) {
      rightTimer.start();
    }

    leftTime = leftTimer.get();
    rightTime = rightTimer.get();
  
    double leftSpeed = getAdjustedDriveSpeeds()[0];
    double rightSpeed = getAdjustedDriveSpeeds()[1];

//  logger.detail("execute moveSpeed: " + moveSpeed + " rotateSpeed: " + rotateSpeed);

    //Robot.drivetrain.tankDrive(leftValue, rightValue);
    
    if (Math.abs(leftValue) < 0.1) {
      
      Robot.drivetrain.arcadeDrive(leftSpeed, rightSpeed);
    } else {
      Robot.drivetrain.curvatureDrive(leftSpeed, rightSpeed);
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
