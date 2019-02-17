package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class MoveArm extends Command {

  private Logger logger = new Logger(MoveArm.class.getName());

  private int position;
  public MoveArm(int position) {
    logger.detail("constructor");
    requires(Robot.climb);
    this.position = position;
  }

  @Override
  protected void initialize() {
    logger.detail("initailize");
    Robot.arm.setVerticalPosition(position);
  }

  @Override
  protected void execute() {
    logger.detail("execute");
  }

  @Override
  protected boolean isFinished() {
    logger.detail("finished");
    return Math.abs(Robot.arm.getVerticalArmPosition() - position) < 20;
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
