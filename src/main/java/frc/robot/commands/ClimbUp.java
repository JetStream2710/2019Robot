package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbUp extends Command {
  public ClimbUp() {
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.climb.climbUp();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.climb.climbStop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
