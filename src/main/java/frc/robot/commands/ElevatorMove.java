package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.Logger;

public class ElevatorMove extends Command {

  private Logger logger = new Logger(ElevatorMove.class.getName());

  public ElevatorMove() {
    logger.detail("constructor");
    requires(Robot.elevator);
  }

  @Override
  protected void initialize() {
    logger.detail("initailize");
  }

  // I MOVED THE LOGGER TO THE BOTTOM TO GET MOVESPEED VALUE 
  @Override
  protected void execute() {
    double moveSpeed = Robot.oi.auxstick.getRawAxis(RobotMap.ELEVATOR_AXIS);
    Robot.elevator.elevatorMove(moveSpeed);
    logger.info("execute moveSpeed: " + moveSpeed);
  }

  @Override
  protected boolean isFinished() {
    logger.detail("finished");
    return false;
  }

  @Override
  protected void end() {
    logger.info("end");
    Robot.elevator.elevatorVoid();
  }

  // HERE WOULD IT BE POSSIBLE TO ALWAYS LET THIS GO -- THIS IS MEANT TO BE OVERRIDE FUNCTION
  @Override
  protected void interrupted() {
    logger.warning("interrupted");
    end();
  }
}
