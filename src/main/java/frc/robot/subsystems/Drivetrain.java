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

  public static final int MIN_POSITION = Integer.MIN_VALUE;
  public static final int MAX_POSITION = Integer.MAX_VALUE;
  public static final double MIN_OUTPUT = -1.0;
  public static final double MAX_OUTPUT = 1.0;
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

    leftTalon = new JetstreamTalon("Drivetrain Left Talon", RobotMap.DRIVETRAIN_LEFT_TALON, MIN_POSITION, MAX_POSITION, MIN_OUTPUT, MAX_OUTPUT, false);
    leftVictor = new JetstreamVictor("Drivetrain Left Victor", RobotMap.DRIVETRAIN_LEFT_VICTOR, MIN_OUTPUT, MAX_OUTPUT);
    rightTalon = new JetstreamTalon("Drivetrain Right Talon", RobotMap.DRIVETRAIN_RIGHT_TALON, MIN_POSITION, MAX_POSITION, MIN_OUTPUT, MAX_OUTPUT, false);
    rightVictor = new JetstreamVictor("Drivetrain Right Victor", RobotMap.DRIVETRAIN_RIGHT_VICTOR, MIN_OUTPUT, MAX_OUTPUT);
    leftGroup = new SpeedControllerGroup(/*leftTalon),*/ leftVictor);
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

  public int getLeftPosition() {
    return 0;
//    return leftTalon.getPosition();
  }

  public int getRightPosition() {
    return 0;
//    return rightTalon.getPosition();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveCommand());
  }
}
