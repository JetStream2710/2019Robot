package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.util.Logger;

public class ArmHatchHover extends Command {

//  private Logger logger = new Logger(ArmHatchHover.class.getName());

  public ArmHatchHover() {
  //  logger.detail("constructor");
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
  //  logger.info("initialize");
    Robot.arm.setVerticalPosition(Arm.VERTICAL_HATCH_HOVER_POSITION);
    Robot.arm.setSwivelPosition(Arm.SWIVEL_HATCH_HOVER_POSITION);
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
  //  logger.info("isFinished");
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
