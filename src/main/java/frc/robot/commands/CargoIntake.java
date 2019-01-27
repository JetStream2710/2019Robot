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
    Robot.cargo.cargoIntake();
    System.out.println("Cargo Intake");
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    debug("finished");
    return false;
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
			System.out.println("CorgoIntake command: " + s);
		}
	}

}
