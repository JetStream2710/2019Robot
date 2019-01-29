package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.VerticalArm;

public class Arm extends Subsystem {

  public static final boolean DEBUG = false;
  // TODO: change values from 0
  public static final double verticalMax = 0;
  public static final double verticalMin = 0;
  public static final double swivelMax = 0;
  public static final double swivelMin = 0;
  private static final long pollFrequencyMillis = 16;

  // TODO: review thread vs non-thread pros/cons
  private ArmThread thread;
  private boolean isRunning;

  public static double verticalMeasure;
  public static double swivelMeasure;

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
    //CAN I DEFINE IT ABOVE?
    // Brian: Yes, you should use the constants you've defined for vertical/swivel min/max

    // TODO: Let's assume we are using the sensor controls provided on the talon
    // https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#software-select-sensor-type
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
    if(!verticalIsValid()){
      verticalTalon.set(ControlMode.PercentOutput, 0);
    }
    verticalTalon.set(ControlMode.PercentOutput, speed);
  }

  // MAKE SURE TO TEST THIS BECAUSE IT MIGHT BE BACKWARDS
  public void moveTogether(double speed){
    debug("moveTogether speed: " + speed + " swivelMax: " + swivelMax + " verticalMax: " + verticalMax);
    if(!verticalIsValid()){
      verticalTalon.set(ControlMode.PercentOutput, 0);
    }
    if(!swivelIsValid()){
      swivelTalon.set(ControlMode.PercentOutput, 0);
    }
    verticalTalon.set(ControlMode.PercentOutput, speed);
    swivelTalon.set(ControlMode.PercentOutput, -speed);
  }

  // TODO: since this is something of interest to a lot of systems, lets keep these functions/variables on the robot
  public void switchToHatch(){
    debug("switchToHatch called");
  }

  public void switchToCargo(){
    debug("switchtoCargo called");
  }

  @Override
  public void initDefaultCommand() {
  }

  public final class ArmThread extends Thread{
    @Override
    public void run(){
        System.out.println("starting arm thread");
        while(isRunning){
          verticalMeasure = verticalTalon.getSelectedSensorPosition();
          swivelMeasure = swivelTalon.getSelectedSensorPosition();
        }
          try{
           Thread.sleep(pollFrequencyMillis);
          }  catch(InterruptedException e){
           System.out.println("arm thread was interrupted");
           }
        System.out.println("stopping arm thread");
        debug("vertical Measure: " + verticalMeasure + " swivel Measure: " + swivelMeasure);
      }
    }
  
  private boolean verticalIsValid(){
    if(verticalMax < verticalMeasure || verticalMin > verticalMeasure){
      return false;
   }
    return true;
    }
  private boolean swivelIsValid(){
    if(swivelMax < swivelMeasure || swivelMin > swivelMeasure){
      return false;
    }
    return true;
  }


  private void debug(String s) {
		if (DEBUG) {
			System.out.println("Arm Subsystem: " + s);
		}
  }
}
