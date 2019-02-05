package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class CargoOuttake extends Command {

  private Logger logger = new Logger(CargoOuttake.class.getName());

  public CargoOuttake() {
    logger.detail("constructor");
    requires(Robot.cargo);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
    Robot.cargo.cargoOuttake();
  }

  @Override
  protected void execute() {
    logger.detail("execute");
  }

  @Override
  protected boolean isFinished() {
    logger.info("finished");
    return true;
  }

  @Override
  protected void end() {
    logger.info("end");
    Robot.cargo.cargoStop();
  }

  @Override
  protected void interrupted() {
    logger.warning("interrupted");
    end();
  }
}
