package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class ArmUp extends Command {

  private Logger logger = new Logger(ArmUp.class.getName());

  public ArmUp() {
    logger.detail("constructor");
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
  }

  // there's a complaint... would it be better to create a function inside the arm subsystem first
  // and then call it in the arm subsystem?
  @Override
  protected void execute() {
    logger.info("execute");
    Robot.arm.moveVerticalArm(Robot.arm.VERTICAL_MAX_OUTPUT);
  }

  @Override
  protected boolean isFinished() {
    logger.info("isFinished");
    if(Robot.arm.getVerticalPosition() < Robot.arm.VERTICAL_MAX){
      return false;
    } else{
      return true;
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
