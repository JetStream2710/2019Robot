package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Elevator;
import frc.robot.util.Logger;

public class CalibratePosition extends Command {

  private Logger logger = new Logger(CalibratePosition.class.getName());

  public CalibratePosition() {
    logger.detail("constructor");
    requires(Robot.climb);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
    int arm, swivel, elevator;
    if (Robot.isHatchMode()) {
      arm = Arm.VERTICAL_HATCH_POSITIONS[Robot.arm.getLevel()];
      swivel = Arm.SWIVEL_HATCH_POSITIONS[Robot.arm.getLevel()];
      elevator = Elevator.HATCH_POSITIONS[Robot.elevator.getLevel()];
    } else {
      arm = Arm.VERTICAL_CARGO_POSITIONS[Robot.arm.getLevel()];
      swivel = Arm.SWIVEL_CARGO_POSITIONS[Robot.arm.getLevel()];
      elevator = Elevator.CARGO_POSITIONS[Robot.elevator.getLevel()];
    }
    Robot.arm.calibrate();
    Robot.elevator.calibrate();
    logger.warning(String.format("Recalibrated level: %d arm: %d swivel: %d elevator %d, previous: arm %d swivel %d elevator %d",
      Robot.arm.getLevel(), Robot.arm.getVerticalArmPosition(), Robot.arm.getSwivelPosition(), Robot.elevator.getPosition(), arm, swivel, elevator));
  }

  @Override
  protected void execute() {
    logger.detail("execute");
  }

  @Override
  protected boolean isFinished() {
    logger.detail("finished");
    return false;
  }

  @Override
  protected void end() {
    logger.info("end");
  }

  @Override
  protected void interrupted() {
    logger.info("interrupted");
    end();
  }
}
