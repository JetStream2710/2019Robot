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
    LOG_LEVELS.put(Arm.class.getName(), Level.INFO);
    LOG_LEVELS.put(Cargo.class.getName(), Level.INFO);
    LOG_LEVELS.put(Climb.class.getName(), Level.INFO);
    LOG_LEVELS.put(Drivetrain.class.getName(), Level.INFO);
    LOG_LEVELS.put(Elevator.class.getName(), Level.INFO);
    LOG_LEVELS.put(Hatch.class.getName(), Level.INFO);

    // Commands
    LOG_LEVELS.put(CargoIntake.class.getName(), Level.WARNING);
    LOG_LEVELS.put(CargoOuttake.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ClimbFront.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ClimbMech.class.getName(), Level.WARNING);
    LOG_LEVELS.put(DriveCommand.class.getName(), Level.WARNING);
    LOG_LEVELS.put(ElevatorMove.class.getName(), Level.WARNING);
    LOG_LEVELS.put(HatchIn.class.getName(), Level.WARNING);
    LOG_LEVELS.put(HatchOut.class.getName(), Level.WARNING);
    LOG_LEVELS.put(MoveTogether.class.getName(), Level.WARNING);
    LOG_LEVELS.put(SwitchToCargo.class.getName(), Level.WARNING);
    LOG_LEVELS.put(SwitchToHatch.class.getName(), Level.WARNING);
    LOG_LEVELS.put(VerticalArm.class.getName(), Level.WARNING);
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
      StringBuilder sb = new StringBuilder();
      sb.append(level);
      sb.append(" ");
      sb.append(name);
      sb.append(": ");
      SmartDashboard.putString(sb.toString(), s);
      sb.append(s);
      System.out.println(sb.toString());
    }
  }
}
