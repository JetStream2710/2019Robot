package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

/**
 *
 */
public class DriveForward extends Command {

	long startTime;
	long targetTime;
	long error;
	
    public DriveForward(long durationSeconds) {
        startTime = System.currentTimeMillis();
        targetTime = startTime + 1000*durationSeconds;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	error = (targetTime - System.currentTimeMillis())/(targetTime-startTime);
    	Robot.drivetrain.arcadeDrive(error, 0.6);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= targetTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
