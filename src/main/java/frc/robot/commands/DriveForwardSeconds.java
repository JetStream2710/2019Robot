package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

/**
 *
 */
public class DriveForwardSeconds extends Command {

    Logger logger = new Logger(DriveForwardSeconds.class.getName());

	private long targetTime;
	private long millis;
    private long time; 
    private double maxSpeed;

	public DriveForwardSeconds(long millis, double maxSpeed) {
        this.millis = millis/3;
        this.maxSpeed = maxSpeed;
        requires(Robot.drivetrain);
    }

    protected void initialize() {
        targetTime = System.currentTimeMillis() + millis;
        logger.info("initialize: " + targetTime + " " + maxSpeed);
    	Robot.drivetrain.arcadeDrive(maxSpeed, 0.0);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        time = System.currentTimeMillis();
        if (time > targetTime) {
            logger.info("finished");
        }
        return (time >= targetTime);
    }

    protected void end() {
    	Robot.drivetrain.arcadeDrive(0, 0);
		System.out.println("STOP");
    }

    protected void interrupted() {
    	end();
    }
}
 