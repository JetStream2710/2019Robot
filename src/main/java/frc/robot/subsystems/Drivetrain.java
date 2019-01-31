package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCommand;
import frc.robot.util.JetstreamTalon;

// TODO: add speed limits
public class Drivetrain extends Subsystem {
  private static final boolean DEBUG = false;

  //motor controllers initialized
  private JetstreamTalon frontLeftTalon = null;
  private JetstreamTalon frontRightTalon = null;
  private JetstreamTalon rearLeftTalon = null;
  private JetstreamTalon rearRightTalon = null;

  //differential drive initialized
  private DifferentialDrive differentialDrive = null;

  //establish motors
  public Drivetrain() {
    super();
    frontLeftTalon = new JetstreamTalon(RobotMap.DRIVETRAIN_FRONT_LEFT_TALON, Double.MIN_VALUE, Double.MAX_VALUE);
    frontRightTalon = new JetstreamTalon(RobotMap.DRIVETRAIN_FRONT_RIGHT_TALON, Double.MIN_VALUE, Double.MAX_VALUE);
    rearLeftTalon = new JetstreamTalon(RobotMap.DRIVETRAIN_REAR_LEFT_TALON, Double.MIN_VALUE, Double.MAX_VALUE);
    rearRightTalon = new JetstreamTalon(RobotMap.DRIVETRAIN_REAR_RIGHT_TALON, Double.MIN_VALUE, Double.MAX_VALUE);

    SpeedControllerGroup leftGroup = new SpeedControllerGroup(frontLeftTalon, rearLeftTalon);
    SpeedControllerGroup rightGroup = new SpeedControllerGroup(frontRightTalon, rearRightTalon);
    differentialDrive = new DifferentialDrive(leftGroup, rightGroup);

    debug("constructor");
  }

  public void arcadeDrive(double moveSpeed, double rotateSpeed) {
    debug("arcade drive movespeed: " + moveSpeed + " rotatespeed: " + rotateSpeed);
    differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }

  public void curvatureDrive(double moveSpeed, double rotateSpeed) {
    debug("curvature drive movespeed: " + moveSpeed + " rotatespeed: " + rotateSpeed);
    differentialDrive.curvatureDrive(moveSpeed, rotateSpeed, false);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveCommand());
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("Drivetrain Subsystem: " + s);
    }
  }
}
