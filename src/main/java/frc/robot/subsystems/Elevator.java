package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

// TODO: insert limits for movement and decide if we want joystick movement
public class Elevator extends Subsystem {

  private static final boolean DEBUG = false;

  private WPI_TalonSRX leftTalon;
  private WPI_TalonSRX rightTalon;

  private SpeedControllerGroup group;

  // TODO: write discreet elevator stops assuming we know what the encoder distance is between levels
  public Elevator() {
    super();
    leftTalon = new WPI_TalonSRX(RobotMap.ELEVATOR_LEFT_TALON);
    rightTalon = new WPI_TalonSRX(RobotMap.ELEVATOR_RIGHT_TALON);

    leftTalon.setNeutralMode(NeutralMode.Brake);
    rightTalon.setNeutralMode(NeutralMode.Brake);

    leftTalon.setSafetyEnabled(false);
    rightTalon.setSafetyEnabled(false);

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
