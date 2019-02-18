package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import java.util.HashMap;
import java.util.Map;

public class Logger {

  public static final Map<String, Level> LOG_LEVELS = new HashMap<>();

  static {
    // Robot systems
    LOG_LEVELS.put(Robot.class.getName(), Level.INFO);
    LOG_LEVELS.put(OI.class.getName(), Level.INFO);

    // Subsystems
    LOG_LEVELS.put(Arm.class.getName(), Level.WARNING);
    LOG_LEVELS.put(Cargo.class.getName(), Level.WARNING);
    LOG_LEVELS.put(Climb.class.getName(), Level.WARNING);
    LOG_LEVELS.put(Drivetrain.class.getName(), Level.WARNING);
    LOG_LEVELS.put(Elevator.class.getName(), Level.DETAIL);
    LOG_LEVELS.put(Hatch.class.getName(), Level.WARNING);

    // Autonomous Commands
    LOG_LEVELS.put(AutoCargo3.class.getName(), Level.WARNING);
    LOG_LEVELS.put(AutoCargo4.class.getName(), Level.WARNING);
    LOG_LEVELS.put(AutoCargo5.class.getName(), Level.WARNING);
    LOG_LEVELS.put(AutoRightRocket.class.getName(), Level.WARNING);
    
    // Commands
    LOG_LEVELS.put(MoveArm.class.getName(), Level.INFO);
    LOG_LEVELS.put(CalibratePosition.class.getName(), Level.INFO);
    LOG_LEVELS.put(CargoIntake.class.getName(), Level.WARNING);
    LOG_LEVELS.put(CargoOuttake.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ClimbFrontExtend.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ClimbFrontRetract.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ClimbMech.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ClimbRearExtend.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ClimbRearMove.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ClimbRearRetract.class.getName(), Level.WARNING);
    LOG_LEVELS.put(DriveCommand.class.getName(), Level.WARNING);
    LOG_LEVELS.put(DriveDistance.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ElevatorAndArmMove.class.getName(), Level.INFO);
    LOG_LEVELS.put(HatchPush.class.getName(), Level.WARNING);
    LOG_LEVELS.put(SwitchToCargo.class.getName(), Level.WARNING);
    LOG_LEVELS.put(SwitchToHatch.class.getName(), Level.WARNING);
    LOG_LEVELS.put(TurnDegrees.class.getName(), Level.WARNING);
    LOG_LEVELS.put(VisionFollow.class.getName(), Level.WARNING);
    LOG_LEVELS.put(WeaponsDown.class.getName(), Level.INFO);
    LOG_LEVELS.put(WeaponsUp.class.getName(), Level.INFO);

    // Utilities
    LOG_LEVELS.put(JetstreamTalon.class.getName(), Level.WARNING);
    LOG_LEVELS.put(JetstreamVictor.class.getName(), Level.WARNING);
    LOG_LEVELS.put(PixyI2CDriver.class.getName(), Level.WARNING);
    LOG_LEVELS.put(PixyVision.class.getName(), Level.WARNING);
  }

  public enum Level {
    OFF(0),
    SEVERE(1),
    WARNING(2),
    INFO(3),
    DETAIL(4);

    private final int value;

    Level(int value) {
      this.value = value;
    }

    public int value() {
      return value;
    }
  }

  private String name;

  public Logger(String name) {
    this.name = name;
  }

  public void detail(String s) {
    log(name, Level.DETAIL, s);
  }

  public void info(String s) {
    log(name, Level.INFO, s);
  }

  public void warning(String s) {
    log(name, Level.WARNING, s);
  }

  public void severe(String s) {
    log(name, Level.SEVERE, s);
  }

  private void log(String name, Level level, String s) {
    if (LOG_LEVELS.get(name).value() >= level.value()) {
      System.out.println(String.format("%s %s: %s", level.toString(), name, s));
    }
    if (Level.WARNING.value() >= level.value()) {
      SmartDashboard.putString(level.toString(), String.format("%s: %s", name, s));
    }
  }
}
