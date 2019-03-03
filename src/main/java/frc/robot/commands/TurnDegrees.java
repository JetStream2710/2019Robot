package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.util.Logger;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the robot a requested amount of degrees
 * 
 * @param degrees Degrees to turn; positive is right, negative is left
 * @param maxTurnSpeed The fastest speed you want the robot to turn at; postive value
 */
public class TurnDegrees extends Command {

//    private Logger logger = new Logger(DriveCommand.class.getName());

    private double targetAngle;
	private double degrees;
	private boolean turnLeft;

	private double maxTurnSpeed;
	private double minTurnSpeed;
    
    public TurnDegrees(double degrees, double maxTurnSpeed) {
    //    logger.detail("constructor " + toString());
    	requires(Robot.drivetrain);
    	this.degrees = degrees;
    	this.maxTurnSpeed = maxTurnSpeed;
	}

    @Override
	protected void initialize() {
		turnLeft = degrees < 0.0;
		targetAngle = Robot.ahrs.getAngle() + degrees; 
        minTurnSpeed = (Math.abs(degrees) <= 15) ? 0.6 : 0.4;
    //    logger.info("initialize " + toString());
    }

    @Override
	protected void execute() {
		double currentAngle = Robot.ahrs.getAngle();
		double errorDegrees = targetAngle - currentAngle;
		double turnSpeed = Math.abs(errorDegrees / 60.0);
		if (turnSpeed < minTurnSpeed) {
			turnSpeed = minTurnSpeed;
		}
		if (turnSpeed > maxTurnSpeed) {
			turnSpeed = maxTurnSpeed;
        }
    //    logger.info(String.format("execute speed: %f error-degrees: %f %s", turnSpeed, errorDegrees, toString()));
    	Robot.drivetrain.arcadeDrive(0.0, turnLeft ? turnSpeed : -turnSpeed);
    }

    @Override
    protected boolean isFinished() {
    //    logger.detail("isFinished");
        return (!turnLeft && Robot.ahrs.getAngle() <= targetAngle) ||
            (turnLeft && Robot.ahrs.getAngle() >= targetAngle);
    }

    @Override
    protected void end() {
    //    logger.info("end");
    	Robot.drivetrain.arcadeDrive(0.0, 0.0);
	}

    @Override
    protected void interrupted() {
    //    logger.warning("interrupted");
    	end();
    }

    @Override
    public String toString() {
        return String.format("current-angle: %f target-angle: %f degrees: %f left: %b min: %f max: %f",
            Robot.ahrs.getAngle(), targetAngle, degrees, turnLeft, minTurnSpeed, maxTurnSpeed);
    }
}
