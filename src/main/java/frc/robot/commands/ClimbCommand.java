/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class ClimbCommand extends Command {
  public ClimbCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.climb);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!Robot.isAuxClimbing) {
      return;
    }

    double leftValue = Robot.oi.auxstick.getRawAxis(OI.ELEVATOR_AXIS);
    double rightValue = Robot.oi.auxstick.getRawAxis(OI.ARM_AXIS);

    if (Math.abs(leftValue) > 0.1) {
      Robot.climb.climbFrontExtend(leftValue);
    } else{
      Robot.climb.climbFrontExtend(0);
    }
    if (Math.abs(rightValue) > 0.1) {
      Robot.climb.climbRearExtend(rightValue);
    } else{
      Robot.climb.climbRearExtend(0);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
