package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Arm extends Subsystem {

  public static final boolean DEBUG = false;

  private WPI_TalonSRX verticalTalon = null;
  private WPI_TalonSRX swivelTalon = null;

  // TODO: insert safety parameters, how far vertical and swivel talon can turn in current mode
  // TODO: create a function that will move both the vertical arm and the swivel together while keeping the intake orientation stable
  public Arm(){
    super();
    verticalTalon = new WPI_TalonSRX(RobotMap.ARM_VERTICAL_TALON);
    swivelTalon = new WPI_TalonSRX(RobotMap.ARM_SWIVEL_TALON);

    verticalTalon.setSafetyEnabled(false);
    swivelTalon.setSafetyEnabled(false);

    verticalTalon.setNeutralMode(NeutralMode.Brake);
    swivelTalon.setNeutralMode(NeutralMode.Brake);

    debug("constructor");
  }
  
  public void verticalArm(double speed){
    debug("verticalArm speed: " + speed);
    verticalTalon.set(ControlMode.PercentOutput, speed);
  }

  public void switchToHatch(){
    debug("switchToHatch called");    
  }

  public void switchToCargo(){
    debug("switchtoCargo called");
  }

  @Override
  public void initDefaultCommand() {
  }

  private void debug(String s) {
		if (DEBUG) {
			System.out.println("Arm Subsystem: " + s);
		}
	}

}
