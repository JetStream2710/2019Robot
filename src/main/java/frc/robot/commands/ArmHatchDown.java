package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.util.Logger;

public class ArmHatchDown extends Command {

  private Logger logger = new Logger(ArmHatchDown.class.getName());

  public ArmHatchDown() {
    logger.detail("constructor");
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
  }

  @Override
  protected void execute() {
    logger.info("execute");
    Robot.arm.moveVerticalArm(Arm.VERTICAL_MAX_OUTPUT);
  }

  @Override
  protected boolean isFinished() {
    logger.info("isFinished");
    if(Robot.arm.getVerticalPosition() >= Arm.VERTICAL_MAX){
      return true;
    } else{
      return false;
    }
  }

  @Override
  protected void end() {
    logger.info("end");
  }

  @Override
  protected void interrupted() {
    logger.warning("interrupted");
  }
}
