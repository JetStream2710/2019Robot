package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Arm extends Subsystem {

  public static final boolean DEBUG = false;
  public static boolean verticalMax = false;
  public static boolean swivelMax = false;

  private WPI_TalonSRX verticalTalon = null;
  private WPI_TalonSRX swivelTalon = null;

  private Encoder encoderVertical;
  private Encoder encoderSwivel;

  // TODO: insert safety parameters, how far vertical and swivel talon can turn in current mode
  public Arm(){
    super();
    verticalTalon = new WPI_TalonSRX(RobotMap.ARM_VERTICAL_TALON);
    swivelTalon = new WPI_TalonSRX(RobotMap.ARM_SWIVEL_TALON);

    verticalTalon.setSafetyEnabled(false);
    swivelTalon.setSafetyEnabled(false);

    verticalTalon.setNeutralMode(NeutralMode.Brake);
    swivelTalon.setNeutralMode(NeutralMode.Brake);

    // Set max and min sensor positions here based the current position
    verticalTalon.getSelectedSensorPosition();

    encoderVertical = new Encoder(RobotMap.ENCODER_VERTICAL_DI1, RobotMap.ENCODER_VERTICAL_DI2, 
      false, Encoder.EncodingType.k2X);
    encoderVertical.setDistancePerPulse(RobotMap.ENCODER_TALON_GEAR_RATIO_DPP);
    encoderVertical.setSamplesToAverage(RobotMap.ENCODER_SAMPLES_TO_AVERAGE);

    encoderSwivel = new Encoder(RobotMap.ENCODER_VERTICAL_DI1, RobotMap.ENCODER_SWIVEL_DI2, 
      false, Encoder.EncodingType.k2X);
    encoderSwivel.setDistancePerPulse(RobotMap.ENCODER_TALON_GEAR_RATIO_DPP);
    encoderSwivel.setSamplesToAverage(RobotMap.ENCODER_SAMPLES_TO_AVERAGE);

    debug("constructor");
  }

  // provide functions that can return the max/min/current vertical/swivel encoder values
  
  public void verticalArm(double speed){
    debug("verticalArm encoder speed:" + speed + " swivelMax: " + swivelMax);
    
    // check to see if the current is = > < max/min values and set speed to 0 instead if so
    if(!swivelMax){
      verticalTalon.set(ControlMode.PercentOutput, speed);
    }
    else{
      System.out.println("vertical arm max reached");
    }
  }

  // MAKE SURE TO TEST THIS BECAUSE IT MIGHT BE BACKWARDS
  public void moveTogether(double speed){  
    debug("moveTogether speed: " + speed + " swivelMax: " + swivelMax + " verticalMax: " + verticalMax);
    if(!swivelMax && !verticalMax){
      verticalTalon.set(ControlMode.PercentOutput, speed);
      swivelTalon.set(ControlMode.PercentOutput, -speed);
    }    else{
      System.out.println("some max reached");
    }
  }

  public void switchToHatch(){
    debug("switchToHatch called");    
  }

  public void switchToCargo(){
    debug("switchtoCargo called");
  }

  public void maximumCheck(){
    double verticalDistance = encoderVertical.getDistance();
    double swivelDistance = encoderSwivel.getDistance();

    debug("maximumCheck vertical distance: " + verticalDistance + " swivel distance: " + swivelDistance
      + " vertical boolean: " + verticalMax + " swivel boolean: " + swivelMax);

    if(verticalDistance>0 && verticalDistance<0){
      verticalMax = false;
    } else{
      verticalMax = true;
    }

    if(swivelDistance>0 && swivelDistance<0){
      swivelMax = false;
    } else{
      swivelMax = true;
    }
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
