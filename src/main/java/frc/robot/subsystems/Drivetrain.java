package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCommand;
import frc.robot.util.Logger;

// TODO: add speed limits
public class Drivetrain extends Subsystem {

  private Logger logger = new Logger(Drivetrain.class.getName());

  private WPI_TalonSRX frontLeftTalon;
  private WPI_TalonSRX frontRightTalon;
  private WPI_TalonSRX rearLeftTalon;
  private WPI_TalonSRX rearRightTalon;

  private SpeedControllerGroup leftGroup;
  private SpeedControllerGroup rightGroup;

  private DifferentialDrive differentialDrive = null;

  public Drivetrain() {
    super();
    logger.detail("constructor");

    frontLeftTalon = newTalon(RobotMap.DRIVETRAIN_FRONT_LEFT_TALON);
    frontRightTalon = newTalon(RobotMap.DRIVETRAIN_FRONT_RIGHT_TALON);
    rearLeftTalon = newTalon(RobotMap.DRIVETRAIN_REAR_LEFT_TALON);
    rearRightTalon = newTalon(RobotMap.DRIVETRAIN_REAR_RIGHT_TALON);
    leftGroup = new SpeedControllerGroup(frontLeftTalon, rearLeftTalon);
    rightGroup = new SpeedControllerGroup(frontRightTalon, rearRightTalon);
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

  private WPI_TalonSRX newTalon(int port) {
    WPI_TalonSRX talon = new WPI_TalonSRX(port);
    talon.setSafetyEnabled(false);
    talon.setNeutralMode(NeutralMode.Brake);
    talon.setSelectedSensorPosition(0);
    return talon;
  }
}
