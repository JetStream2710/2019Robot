package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutoCargo3;
import frc.robot.commands.AutoCargo4;
import frc.robot.commands.AutoCargo5;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Cargo;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hatch;
import frc.robot.util.Logger;
import frc.robot.util.SmartDash;

public class Robot extends TimedRobot {
  public static OI oi;
  public static Drivetrain drivetrain;
  public static Elevator elevator;
  public static Arm arm;
  public static Cargo cargo;
  public static Hatch hatch;
  public static Climb climb;

  public static Command autonomousCommand;
  public static SendableChooser autoChooser;

  public static AHRS ahrs = new AHRS(SPI.Port.kMXP);

  public static boolean isAuto;
  public static boolean isHatchMode;
  public static boolean isMovingElevator;
  public static boolean isMovingArm;
  // is the variable type correct?
  public static int currentHeight;

  private static Logger logger = new Logger(Robot.class.getName());

  @Override
  public void robotInit() {
    logger.detail("robotInit");
    drivetrain = new Drivetrain();
    elevator = new Elevator();
    arm = new Arm();
    cargo = new Cargo();
    hatch = new Hatch();
    climb = new Climb();
    oi = new OI();

    autoChooser = new SendableChooser();
    autoChooser.addDefault("AutoCargo3", new AutoCargo3());
    autoChooser.addObject("AutoCargo4", new AutoCargo4());
    autoChooser.addObject("AutoCargo5", new AutoCargo5());

    isHatchMode = true;
    isMovingElevator = false;
    isMovingArm = false;
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

    autonomousCommand = (Command) autoChooser.getSelected();
    autonomousCommand.start();
  }

  @Override
  public void autonomousPeriodic() {
    logger.detail("autonomousPeriodic");
    Scheduler.getInstance().run();
    updateSubsystems();
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
    updateSubsystems();
  }


  private void updateSubsystems() {
    long time = System.currentTimeMillis();
    SmartDash.put("Robot Mode", isHatchMode?"Hatch":"Cargo");
    SmartDash.put("Arm Status", isMovingArm?"Moving":"NOT Moving");
    SmartDash.put("Elevator Status", isMovingElevator?"Moving":"NOT Moving");
    arm.periodic(time);
    elevator.periodic(time);
  }


  public static boolean isHatchMode() {
    return isHatchMode;
  }

  public static void switchToHatchMode() {
    logger.info("isHatchMode mode on");
    isHatchMode = true;
  }

  public static void switchToCargoMode() {
    logger.info("isHatchMode mode off");
    isHatchMode = false;
  }
}
