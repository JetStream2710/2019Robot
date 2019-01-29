package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CargoIntake extends Command {

  private static final boolean DEBUG = false;

  public CargoIntake() {
    debug("constructor");
    requires(Robot.cargo);
  }

  @Override
  protected void initialize() {
    debug("initialize");
    Robot.cargo.cargoIntake();
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    debug("finished");
    return true;
  }

  @Override
  protected void end() {
    debug("end");
    Robot.cargo.cargoStop();
  }

  @Override
  protected void interrupted() {
    debug("interrupted");
    end();
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("CargoIntake command: " + s);
    }
  }
}
