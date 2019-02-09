package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
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

  private Encoder leftEncoder;
  private Encoder rightEncoder;

  private DifferentialDrive differentialDrive = null;

  public Drivetrain() {
    super();
    logger.detail("constructor");

    leftEncoder = new Encoder(RobotMap.DRIVETRAIN_LEFT_ENCODER_A, RobotMap.DRIVETRAIN_LEFT_ENCODER_B);
    leftTalon = new JetstreamTalon(RobotMap.DRIVETRAIN_LEFT_TALON, leftEncoder, Integer.MIN_VALUE, Integer.MAX_VALUE);
    leftVictor = new JetstreamVictor(RobotMap.DRIVETRAIN_LEFT_VICTOR);
    rightTalon = new JetstreamTalon(RobotMap.DRIVETRAIN_RIGHT_TALON, rightEncoder, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
