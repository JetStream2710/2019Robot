package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class CargoIntake extends Command {

  private Logger logger = new Logger(CargoIntake.class.getName());

  public CargoIntake() {
    logger.detail("constructor");
    requires(Robot.cargo);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
    Robot.cargo.cargoIntake();
  }

  @Override
  protected void execute() {
    logger.detail("execute");
  }

  @Override
  protected boolean isFinished() {
    logger.detail("finished");
    return false;
  }

  @Override
  protected void end() {
    logger.info("end");
    Robot.cargo.cargoStop();
  }

  @Override
  protected void interrupted() {
    logger.info("interrupted");
    end();
  }
}
