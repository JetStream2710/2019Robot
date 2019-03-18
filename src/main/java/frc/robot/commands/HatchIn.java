package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class HatchIn extends Command {
  public HatchIn() {
    requires(Robot.hatch);
  }

  @Override
  protected void initialize() {
    Robot.hatch.hatchIn();
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
    end();
  }
}
