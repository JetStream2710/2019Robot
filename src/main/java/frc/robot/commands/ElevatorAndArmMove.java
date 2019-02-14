package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.Logger;

public class ElevatorAndArmMove extends Command {

  private static long lastPovChange = 0;
  private static final long POV_TIME_BUFFER = 1000;

  private Logger logger = new Logger(ElevatorAndArmMove.class.getName());

  public ElevatorAndArmMove() {
    logger.detail("constructor");
    requires(Robot.elevator);
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
    logger.detail("initailize");
  }

  @Override
  protected void execute() {
    logger.detail("execute");
    // Joypad Control
    if (System.currentTimeMillis() - lastPovChange > POV_TIME_BUFFER) {
      int pov = Robot.oi.auxstick.getPOV();
      if (pov == 0) {
        Robot.elevator.setLevel(Robot.elevator.getLevel() + 1);
        Robot.arm.setLevel(Robot.arm.getLevel() + 1);
        logger.info("execute pov: 0");
        lastPovChange = System.currentTimeMillis();
      } else if (pov == 180) {
        Robot.elevator.setLevel(Robot.elevator.getLevel() - 1);
        Robot.arm.setLevel(Robot.arm.getLevel() - 1);
        logger.info("execute pov: 180");
        lastPovChange = System.currentTimeMillis();
      }
    }
    // Elevator Joystick
    // TODO: FIX THIS AFTER TESTING -- change back to auxstick
//    double elevatorSpeed = Robot.oi.auxstick.getRawAxis(RobotMap.ELEVATOR_AXIS);
    double elevatorSpeed = Robot.oi.drivestick.getRawAxis(RobotMap.ELEVATOR_AXIS);
    if (elevatorSpeed < 0.08 && elevatorSpeed > -0.08) {
      if (Robot.isMovingElevator){
        logger.info("execute stop elevator");
        Robot.elevator.elevatorMove(0);
      }
      Robot.isMovingElevator = false;
    } else {
      Robot.isMovingElevator = true;
      logger.info("execute elevatorSpeed: " + elevatorSpeed);
      Robot.elevator.elevatorMove(elevatorSpeed);
    }
    // Arm Joystick
    // TODO: FIX THIS AFTER TESTING -- change this back to auxstick
//    double armSpeed = Robot.oi.auxstick.getRawAxis(RobotMap.ELEVATOR_AXIS);
    double armSpeed = Robot.oi.drivestick.getRawAxis(RobotMap.ARM_AXIS);
    if (armSpeed < 0.08 && armSpeed > -0.08) {
      if (Robot.isMovingArm) {
        logger.info("execute stop arm");
        Robot.arm.stopMovingVerticalArm();
        Robot.arm.stopMovingSwivelArm();
      }
      Robot.isMovingArm = false;
    } else {
      Robot.isMovingArm = true;
      logger.info("execute armSpeed: " + armSpeed);
//      Robot.arm.moveTogether(armSpeed);
//      Robot.arm.moveSwivelArm(armSpeed);
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
    logger.warning("end");
    Robot.isMovingElevator = false;
    Robot.isMovingArm = false;
  }

  @Override
  protected void interrupted() {
    logger.warning("interrupted");
    end();
  }
}
