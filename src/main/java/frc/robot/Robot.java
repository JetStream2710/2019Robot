package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Cargo;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hatch;
import frc.robot.util.Logger;

public class Robot extends TimedRobot {
  public static OI oi;
  public static Drivetrain drivetrain;
  public static Elevator elevator;
  public static Arm arm;
  public static Cargo cargo;
  public static Hatch hatch;
  public static Climb climb;

  public static AHRS ahrs = new AHRS(SPI.Port.kMXP);
  public static boolean isAuto;
  public static boolean isHatchMode;
  public static boolean isMovingElevatorArm;
  // is the variable type correct?
  public static int currentHeight;

  private Logger logger = new Logger(Robot.class.getName());

  @Override
  public void robotInit() {
    logger.detail("robotInit");
    oi = new OI();
    drivetrain = new Drivetrain();
    elevator = new Elevator();
    arm = new Arm();
    cargo = new Cargo();
    hatch = new Hatch();
    climb = new Climb();

    isHatchMode = true;
    isMovingElevatorArm = false;
  }

  @Override
  public void robotPeriodic() {
    logger.detail("robotPeriodic");
  }

  @Override
  public void disabledInit() {
    logger.detail("disabledInit");
  }

  @Override
  public void disabledPeriodic() {
    logger.detail("disabledPeriodic");
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    logger.detail("autonomousInit");
    isAuto = true;
  }

  @Override
  public void autonomousPeriodic() {
    logger.detail("autonomousPeriodic");
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    logger.detail("teleopInit");
    isAuto = false;
  }

  @Override
  public void teleopPeriodic() {
    logger.detail("teleopInit");
    Scheduler.getInstance().run();
  }



  public boolean isHatchMode() {
    logger.info("isHatchMode " + isHatchMode);
    return isHatchMode;
  }

  public void switchToHatchMode() {
    logger.info("isHatchMode mode on");
    isHatchMode = true;
  }

  public void switchToCargoMode() {
    logger.info("isHatchMode mode off");
    isHatchMode = false;
  }

  public boolean isMovingElevatorArm(){
    logger.info("isMovingElevatorArm " + isMovingElevatorArm);
    return isMovingElevatorArm;    
  }
}
