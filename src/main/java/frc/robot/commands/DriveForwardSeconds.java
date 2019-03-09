package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

/**
 *
 */
public class DriveForwardSeconds extends Command {

	long startTime;
    long targetTime;
    long durationSeconds;
    long error;
    double maxSpeed;
	
    public DriveForwardSeconds(long durationSeconds) {
        this.durationSeconds = durationSeconds;
        this.maxSpeed = 0.5;
    }

    public DriveForwardSeconds(long durationSeconds, double maxspeed) {
        this.durationSeconds = durationSeconds;
        this.maxSpeed = maxspeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        startTime = System.currentTimeMillis();
        targetTime = startTime + 1000*durationSeconds;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        
//    	error = (targetTime - System.currentTimeMillis())/(targetTime-startTime);
    	Robot.drivetrain.arcadeDrive(maxSpeed, 0.0);
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
