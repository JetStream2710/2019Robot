package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;

// TODO: insert limits for movement and decide if we want joystick movement
public class Elevator extends Subsystem {

  private static final boolean DEBUG = false;
  private static final double MAX = Double.MAX_VALUE;
  private static final double MIN = Double.MIN_VALUE;

  public static int TOP_HATCH_POSITION = 1000;
  public static int MID_HATCH_POSITION = 700;
  public static int LOW_HATCH_POSITION = 400;
  public static int TOP_CARGO_POSITION = 1050;
  public static int MID_CARGO_POSITION = 750;
  public static int LOW_CARGO_POSITION = 450;

  private JetstreamTalon leftTalon;
  private JetstreamTalon rightTalon;

  private SpeedControllerGroup group;

  public Elevator() {
    super();
    leftTalon = new JetstreamTalon(RobotMap.ELEVATOR_LEFT_TALON, MIN, MAX);
    rightTalon = new JetstreamTalon(RobotMap.ELEVATOR_RIGHT_TALON, MIN, MAX);

    group = new SpeedControllerGroup(leftTalon, rightTalon);

    debug("constructor");
  }

  public void elevatorMove(double speed){
    debug("elevatorMove speed: " + speed);

    group.set(speed);
  }

  @Override
  public void initDefaultCommand() {
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("Elevator Subsystem: " + s);
    }
  }
}
