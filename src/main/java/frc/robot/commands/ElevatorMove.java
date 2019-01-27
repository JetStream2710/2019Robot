package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ElevatorMove extends Command {

  public static final boolean DEBUG = false;

  public ElevatorMove() {
    debug("constructor");
    requires(Robot.elevator);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if(Robot.isAuto) {
			return;
		}
		double moveSpeed = Robot.oi.auxstick.getRawAxis(RobotMap.VERTICAL_AXIS);
    Robot.elevator.elevatorMove(moveSpeed);
    debug("execute movespeed: " + moveSpeed);
  }

  @Override
  protected boolean isFinished() {
    debug("finished");
    return false;
  }

  @Override
  protected void end() {
    debug("end");
  }

  @Override
  protected void interrupted() {
    debug("interrupted");
  }

  private void debug(String s) {
		if (DEBUG) {
			System.out.println("ElevatorMove command: " + s);
		}
	}

}
