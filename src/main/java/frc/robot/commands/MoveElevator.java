package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class MoveElevator extends Command {

  private Logger logger = new Logger(MoveElevator.class.getName());

  private int position;
  public MoveElevator(int position) {
    logger.detail("constructor");
    requires(Robot.climb);
    this.position = position;
  }

  @Override
  protected void initialize() {
    logger.detail("initailize");
    Robot.elevator.setPosition(position);
  }

  @Override
  protected void execute() {
    logger.detail("execute");
    // owo
  }

  @Override
  protected boolean isFinished() {
    logger.detail("finished");
    logger.info("thingy: " + (Robot.elevator.getPosition() - position));
    return Math.abs(Robot.elevator.getPosition() - position) < 50;
  }

  @Override
  protected void end() {
    logger.warning("end");
  }

  @Override
  protected void interrupted() {
    logger.warning("interrupted");
    end();
  }
}
