package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Cargo extends Subsystem {

  private static final boolean DEBUG = false;

  private WPI_VictorSPX leftVictor = null;
  private WPI_VictorSPX rightVictor = null;

  private double SPEED_IN = -0.5;
  private double SPEED_OUT = 0.5;

  public Cargo(){
    super();
    leftVictor = new WPI_VictorSPX(RobotMap.CARGO_LEFT_VICTOR);
    rightVictor = new WPI_VictorSPX(RobotMap.CARGO_RIGHT_VICTOR);

    leftVictor.setNeutralMode(NeutralMode.Brake);
    rightVictor.setNeutralMode(NeutralMode.Brake);

    leftVictor.setSafetyEnabled(false);
    rightVictor.setSafetyEnabled(false);

    debug("constructor");
  }

  //CHECK THESE VALUES
  public void cargoIntake(){
    debug("cargoIntake called");

    leftVictor.set(ControlMode.PercentOutput, SPEED_IN);
    rightVictor.set(ControlMode.PercentOutput, SPEED_IN);
  }

  public void cargoOuttake(){
    debug("cargoOuttake called");

    leftVictor.set(ControlMode.PercentOutput, SPEED_OUT);
    rightVictor.set(ControlMode.PercentOutput, SPEED_OUT);
  }

  public void cargoStop(){
    debug("cargoStop called");

    leftVictor.set(ControlMode.PercentOutput, 0);
    rightVictor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void initDefaultCommand() {
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("Cargo Subsystem: " + s);
    }
  }
}
