package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class HatchOut extends Command {
  public HatchOut() {
    requires(Robot.hatch);
  }

  @Override
  protected void initialize() {
    Robot.hatch.hatchOut();
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
