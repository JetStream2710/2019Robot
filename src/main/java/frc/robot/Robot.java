package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Cargo;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hatch;

public class Robot extends TimedRobot {
  private static final boolean DEBUG = false;

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

  @Override
  public void robotInit() {
    oi = new OI();
    drivetrain = new Drivetrain();
    elevator = new Elevator();
    arm = new Arm();
    cargo = new Cargo();
    hatch = new Hatch();
    climb = new Climb();

    isHatchMode = true;

    debug("robotInit");
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
    debug("disabledInit");
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    debug("autonomousInit");
    isAuto = true;
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    debug("teleopInit");
    isAuto = false;
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  public boolean isHatchMode(){
    return isHatchMode;
  }

  public void switchToHatchMode(){
    debug("isHatchMode mode on");
    isHatchMode = true;
  }

  public void switchToCargoMode(){
    debug("isHatchMode mode off");
    isHatchMode = false;
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("Robot: " + s);
      SmartDashboard.putString("Robot: ", s);
    }
  }
}
