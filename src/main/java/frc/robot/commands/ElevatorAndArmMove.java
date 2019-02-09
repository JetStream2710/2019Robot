package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.Logger;

public class ElevatorAndArmMove extends Command {

  private static long lastPovChange = 0;

  private Logger logger = new Logger(ElevatorAndArmMove.class.getName());

  public ElevatorAndArmMove() {
    logger.detail("constructor");
    requires(Robot.elevator);
    requires(Robot.arm);
  }

  // do we create another variable like the elevator moving one to always make this key command?
  @Override
  protected void initialize() {
    logger.detail("initailize");
  }

  // I MOVED THE LOGGER TO THE BOTTOM TO GET MOVESPEED VALUE 
  @Override
  protected void execute() {
    // Joypad Control
    if (System.currentTimeMillis() - lastPovChange > 1000) {
//      int pov = Robot.oi.auxstick.getPOV();
      int pov = Robot.oi.drivestick.getPOV();
      if (pov == 0) {
        Robot.elevator.setLevel(Robot.elevator.getLevel() + 1);
        lastPovChange = System.currentTimeMillis();
      } else if (pov == 180) {
        Robot.elevator.setLevel(Robot.elevator.getLevel() - 1);
        lastPovChange = System.currentTimeMillis();
      }
    }
    // Elevator Joystick
    // TODO: FIX THIS
//    double elevatorSpeed = Robot.oi.auxstick.getRawAxis(RobotMap.ELEVATOR_AXIS);
    double elevatorSpeed = Robot.oi.drivestick.getRawAxis(RobotMap.ELEVATOR_AXIS);
    if (elevatorSpeed < 0.01 && elevatorSpeed > -0.01) {
      Robot.isMovingElevatorArm = false;
    } else {
      Robot.isMovingElevatorArm = true;
      logger.info("execute elevatorSpeed: " + elevatorSpeed);
      Robot.elevator.elevatorMove(elevatorSpeed);
    }
    // Arm Joystick
    // TODO: FIX THIS
//    double armSpeed = Robot.oi.auxstick.getRawAxis(RobotMap.ELEVATOR_AXIS);
    double armSpeed = Robot.oi.drivestick.getRawAxis(RobotMap.ARM_AXIS);
//    if (armSpeed < 0.01 && armSpeed > -0.01) {
    if (armSpeed == 0) {
        Robot.isMovingElevatorArm = false;
    } else {
      Robot.isMovingElevatorArm = true;
      logger.info("execute armSpeed: " + armSpeed);
//      Robot.arm.moveTogether(armSpeed);
      Robot.arm.moveVerticalArm(armSpeed);
    }
  }

  @Override
  protected boolean isFinished() {
    logger.detail("finished");
    return false;
  }

  @Override
  protected void end() {
    logger.info("end");
    Robot.isMovingElevatorArm = false;
  }

  // HERE WOULD IT BE POSSIBLE TO ALWAYS LET THIS GO -- THIS IS MEANT TO BE OVERRIDE FUNCTION
  @Override
  protected void interrupted() {
    logger.warning("interrupted");
    end();
  }
}
