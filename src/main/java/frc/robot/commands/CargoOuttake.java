package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CargoOuttake extends Command {
  
  public static final boolean DEBUG = false;
  
  public CargoOuttake() {
    debug("constructor");
    requires(Robot.cargo);
  }

  @Override
  protected void initialize() {
    Robot.cargo.cargoOuttake();
    System.out.println("Cargo Outake");
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
  }

  private void debug(String s) {
		if (DEBUG) {
			System.out.println("CargoOuttake command: " + s);
		}
	}

}
