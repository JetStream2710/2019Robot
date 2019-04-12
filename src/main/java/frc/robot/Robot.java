package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Cargo;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hatch;
import frc.robot.util.Logger;
import frc.robot.util.PixyVision;
import frc.robot.util.SmartDash;

public class Robot extends TimedRobot {
  public static OI oi;
  public static Drivetrain drivetrain;
  public static Elevator elevator;
  public static Arm arm;
  public static Cargo cargo;
  public static Hatch hatch;
  public static Climb climb;
  public static PixyVision pixy;
  public static AHRS ahrs = null;//new AHRS(SPI.Port.kMXP);

//  public static Command autonomousCommand;
//  public static SendableChooser<Command> autoChooser;

  public static boolean isAuto;
  public static boolean isMovingElevator;
  public static boolean isMovingArm;
  public static boolean isAuxClimbing;
  public static boolean isFollowingLine;

//  private static Logger logger = new Logger(Robot.class.getName());

  @Override
  public void robotInit() {
  //  logger.detail("robotInit");
    drivetrain = new Drivetrain();
    elevator = new Elevator();
    arm = new Arm();
    cargo = new Cargo();
    hatch = new Hatch();
    climb = new Climb();
    oi = new OI();
    pixy = new PixyVision(true, false);
    pixy.start();

UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();

//CameraServer.getInstance().startAutomaticCapture();
     
    isAuxClimbing = false;
    isMovingElevator = false;
    isMovingArm = false;

    Robot.drivetrain.setCoastMode();
  }

  @Override
  public void robotPeriodic() {
  //  logger.detail("robotPeriodic");
  //  SmartDashboard.putNumber("Vertical Arm Encoder Position", Robot.arm.getVerticalArmPosition());
}

  @Override
  public void disabledInit() {
  //  logger.detail("disabledInit");
  //  Robot.arm.talonsCoastMode();
  Robot.drivetrain.setCoastMode();
  }

  @Override
  public void disabledPeriodic() {
  //  logger.detail("disabledPeriodic");
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
  //  logger.detail("autonomousInit");
    isAuto = false;
    // get rid of reset when we want to start with the arm up
    arm.reset();
    elevator.reset();
    Robot.drivetrain.setBrakeMode();
  }

  @Override
  public void autonomousPeriodic() {
  //  logger.detail("autonomousPeriodic");
    Scheduler.getInstance().run();
    updateSubsystems();

  }

  @Override
  public void teleopInit() {
  //  logger.detail("teleopInit");
    isAuto = false;
    //  get rid of reset when we want to start with the arm up
    arm.reset();
    elevator.reset();
    Robot.drivetrain.setBrakeMode();
  }

  @Override
  public void teleopPeriodic() {
  //  logger.detail("teleopInit");
    Scheduler.getInstance().run();
    updateSubsystems();
  }


  private void updateSubsystems() {
    long time = System.currentTimeMillis();
    /*SmartDash.put("Climb Mode", isAuxClimbing?"Climb":"Drive");
    SmartDash.put("Arm Status", isMovingArm?"Moving":"NOT Moving");
    SmartDash.put("Elevator Status", isMovingElevator?"Moving":"NOT Moving");*/
    arm.periodic(time);
    elevator.periodic(time);
  }

  public static void toggleClimbMode() {
  //  logger.info("ClimbMode Enabled");
    isAuxClimbing = !isAuxClimbing;
  }
}
