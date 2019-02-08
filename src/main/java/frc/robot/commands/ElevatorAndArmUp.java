package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class ElevatorAndArmUp extends Command {

  private Logger logger = new Logger(ElevatorAndArmUp.class.getName());

  public ElevatorAndArmUp() {
    logger.info("constructor");
    requires(Robot.elevator);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
    if(Robot.isMovingElevatorArm){
      end();
    } else{
      Robot.isMovingElevatorArm = true;
    }
  }

  @Override
  protected void execute() {
    logger.info("execute");
  }

  @Override
  protected boolean isFinished() {
    logger.info("isFinished");
    return false;
  }

  @Override
  protected void end() {
    logger.info("end");
    Robot.isMovingElevatorArm = false;
  }

  @Override
  protected void interrupted() {
    logger.warning("interrupted");
  }
}
