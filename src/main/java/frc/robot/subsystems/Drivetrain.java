package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCommand;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;

// TODO: add speed limits
public class Drivetrain extends Subsystem {

  private Logger logger = new Logger(Drivetrain.class.getName());

  private JetstreamTalon leftTalon;
  private JetstreamVictor leftVictor;
  private JetstreamTalon rightTalon;
  private JetstreamVictor rightVictor;

  private SpeedControllerGroup leftGroup;
  private SpeedControllerGroup rightGroup;

  private DifferentialDrive differentialDrive = null;

  public Drivetrain() {
    super();
    logger.detail("constructor");

    leftTalon = new JetstreamTalon("Drivetrain Left Talon", RobotMap.DRIVETRAIN_LEFT_TALON, true, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, false);
    leftVictor = new JetstreamVictor(RobotMap.DRIVETRAIN_LEFT_VICTOR);
    rightTalon = new JetstreamTalon("Drivetrain Right Talon", RobotMap.DRIVETRAIN_RIGHT_TALON, true, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, false);
    rightVictor = new JetstreamVictor(RobotMap.DRIVETRAIN_RIGHT_VICTOR);
    leftGroup = new SpeedControllerGroup(leftTalon, leftVictor);
    rightGroup = new SpeedControllerGroup(rightTalon, rightVictor);
    differentialDrive = new DifferentialDrive(leftGroup, rightGroup);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    logger.info("tank drive left: " + leftSpeed + " right: " + rightSpeed);
    leftGroup.set(leftSpeed);
    rightGroup.set(rightSpeed);
  }

  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
    logger.info("arcade drive movespeed: " + moveSpeed + " rotatespeed: " + rotateSpeed);
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }

  public void curvatureDrive(double moveSpeed, double rotateSpeed) {
    logger.info("curvature drive movespeed: " + moveSpeed + " rotatespeed: " + rotateSpeed);
    differentialDrive.curvatureDrive(moveSpeed, rotateSpeed, false);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveCommand());
  }
}
