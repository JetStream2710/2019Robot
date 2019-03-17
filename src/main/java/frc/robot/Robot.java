package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Auto1;
import frc.robot.commands.Auto2;
import frc.robot.commands.AutoCargo3;
import frc.robot.commands.AutoCargo4;
import frc.robot.commands.AutoCargo5;
import frc.robot.commands.DriveWithAdjustment;
import frc.robot.commands.TestAuto;
import frc.robot.commands.TurnDegrees;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Cargo;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hatch;
import frc.robot.util.Logger;
import frc.robot.util.PixyLine;
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
  public static AHRS ahrs = new AHRS(SPI.Port.kMXP);

  public static Command autonomousCommand;
//  public static SendableChooser<Command> autoChooser;

  public static boolean isAuto;
  public static boolean isMovingElevator;
  public static boolean isMovingArm;
  public static boolean isAuxClimbing;

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

    //CameraServer.getInstance().startAutomaticCapture();
     
    new Thread(() -> {
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//      camera.setResolution(640, 480);
//camera.setVideoMode(new VideoMode(PixelFormat.kGray, 320, 240, 30));
//camera.setVideoMode(new VideoMode(PixelFormat.kRGB565, 640, 480, 30));
//camera.setVideoMode(new VideoMode(PixelFormat.kGray, 1280, 960, 10));
      /*
      CvSink cvSink = CameraServer.getInstance().getVideo();
      CvSource outputStream = CameraServer.getInstance().putVideo("POV", 640, 480);

      Mat source = new Mat();
      Mat output = new Mat();

      while(!Thread.interrupted()) {
        cvSink.grabFrame(source);
        Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
        outputStream.putFrame(output);
      }
      */
    }).start();


/*
    autoChooser = new SendableChooser<Command>();
    autoChooser.setDefaultOption("AutoCargo 3", new AutoCargo3());
    autoChooser.addOption("AutoCargo 4", new AutoCargo4());
    autoChooser.addOption("AutoCargo 5", new AutoCargo5());
*/
    isAuxClimbing = false;
    isMovingElevator = false;
    isMovingArm = false;
  }

  @Override
  public void robotPeriodic() {
  //  logger.detail("robotPeriodic");
  }

  @Override
  public void disabledInit() {
  //  logger.detail("disabledInit");
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
    Auto2 auto = new Auto2();
    //auto.start();

    /*autonomousCommand = autoChooser.getSelected();
    autonomousCommand.start();*/
    
    
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

  }

  @Override
  public void teleopPeriodic() {
  //  logger.detail("teleopInit");
    Scheduler.getInstance().run();
    updateSubsystems();
 
    //arm.setVerticalSpeedManually(arm.getStopSpeed());
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
