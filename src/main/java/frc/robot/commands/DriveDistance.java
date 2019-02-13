package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class DriveDistance extends Command {

  private Logger logger = new Logger(DriveDistance.class.getName());

  private int targetPosition;
  private int distance;

  private double maxSpeed;
  private double minSpeed;

  public DriveDistance(int distance, double maxSpeed) {
    logger.detail("constructor " + toString());
    requires(Robot.drivetrain);
    this.distance = distance;
    this.maxSpeed = maxSpeed;
  }

  private int getPosition() {
      // TODO: review and see if we want to make this more complex
      return Robot.drivetrain.getLeftPosition();
  }

  @Override
  protected void initialize() {
    targetPosition = getPosition() + distance;
    minSpeed = distance < 1000 ? 0.6 : 0.4;
    logger.info("initialize " + toString());
}

  @Override
  protected void execute() {
    int errorDistance = targetPosition - getPosition();
    double speed = errorDistance / 1000;
    if (speed > maxSpeed) {
      speed = maxSpeed;
    }
    if (speed < minSpeed) {
      speed = minSpeed;
    }
    logger.info(String.format("execute speed: %f error-distance: %f %s", speed, errorDistance, toString()));
    Robot.drivetrain.arcadeDrive(speed, 0);
  }

  @Override
  protected boolean isFinished() {
    logger.detail("finished");
    return distance > 0 ? getPosition() >= targetPosition : getPosition() <= targetPosition;
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

  @Override
  public String toString() {
      return String.format("current-position: %f target-position: %f distance: %f min: %f max: %f",
          getPosition(), targetPosition, distance, minSpeed, maxSpeed);
  }
}
