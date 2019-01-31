package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Cargo;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hatch;

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
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    isAuto = true;
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
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
    isHatchMode = true;
  }

  public void switchToCargoMode(){
    isHatchMode = false;
  }
}
