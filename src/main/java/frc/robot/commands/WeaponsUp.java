package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class WeaponsUp extends Command {

//  private Logger logger = new Logger(WeaponsUp.class.getName());

  public WeaponsUp() {
  //  logger.detail("constructor");
    requires(Robot.arm);
    requires(Robot.elevator);
  }

  @Override
  protected void initialize() {
  //  logger.info("initialize");
    Robot.elevator.setLevel(Robot.elevator.getLevel() + 1);
    Robot.arm.setLevel(Robot.arm.getLevel() + 1);
  }

  @Override
  protected void execute() {
  //  logger.detail("execute");
  }

  @Override
  protected boolean isFinished() {
  //  logger.detail("finished");
    return true;
  }

  @Override
  protected void end() {
  //  logger.info("end");
  }

  @Override
  protected void interrupted() {
  //  logger.warning("interrupted");
    end();
  }
}
