package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.util.Logger;

public class ArmHatchDown extends Command {

//  private Logger logger = new Logger(ArmHatchDown.class.getName());

  public ArmHatchDown() {
  //  logger.detail("constructor");
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
  //  logger.info("initialize");
    Robot.arm.setVerticalPosition(Arm.VERTICAL_HATCH_DOWN_POSITION);
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
  //  logger.detail("isFinished");
    return false;
  }

  @Override
  protected void end() {
  //  logger.info("end");
    Robot.arm.setVerticalPosition(Arm.VERTICAL_HATCH_HOVER_POSITION);
    Robot.arm.setSwivelPosition(Arm.SWIVEL_HATCH_HOVER_POSITION);
  }

  @Override
  protected void interrupted() {
  //  logger.warning("interrupted");
  }
}
