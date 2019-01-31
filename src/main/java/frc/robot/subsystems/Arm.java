package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;

public class Arm extends Subsystem {

  public static final boolean DEBUG = false;
  // TODO: change values from 0
  public static final double VERTICAL_MAX = Double.MAX_VALUE;
  public static final double VERTICAL_MIN = Double.MIN_VALUE;
  public static final double SWIVEL_MAX = Double.MAX_VALUE;
  public static final double SWIVEL_MIN = Double.MIN_VALUE;

  private JetstreamTalon verticalTalon = null;
  private JetstreamTalon swivelTalon = null;

  // TODO: insert safety parameters, how far vertical and swivel talon can turn in current mode
  public Arm(){
    super();
    verticalTalon = new JetstreamTalon(RobotMap.ARM_VERTICAL_TALON, VERTICAL_MIN, VERTICAL_MAX);
    swivelTalon = new JetstreamTalon(RobotMap.ARM_SWIVEL_TALON, SWIVEL_MIN, SWIVEL_MAX);

    // TODO: Let's assume we are using the sensor controls provided on the talon
    // https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#software-select-sensor-type

    debug("constructor");
  }

  // provide functions that can return the max/min/current vertical/swivel encoder values

  public void verticalArm(double speed){
    debug("verticalArm speed:" + speed);
    // check to see if the current is = > < max/min values and set speed to 0 instead if so
    verticalTalon.setSpeed(speed);
  }

  public void swivelArm(double speed){
    debug("swivelArm speed: " + speed);
    swivelTalon.setSpeed(speed); 
  }

  // MAKE SURE TO TEST THIS BECAUSE IT MIGHT BE BACKWARDS
  public void moveTogether(double speed){
    debug("moveTogether speed: " + speed);
    if(verticalTalon.isSpeedValid(speed) && swivelTalon.isSpeedValid(-speed)){
      verticalTalon.setSpeed(speed);
      swivelTalon.setSpeed(-speed);
    } else {
      verticalTalon.setSpeed(0);
      swivelTalon.setSpeed(0);
    }
  }

  @Override
  public void initDefaultCommand() {
  }
  
  public void checkAllMotors(){
    double verticalSpeed = verticalTalon.getSpeed();
    double swivelSpeed = swivelTalon.getSpeed();

    if(!verticalTalon.isSpeedValid(verticalSpeed)){
      verticalTalon.setSpeed(0);
      System.out.println("vertical Talon stopped");
    }
    if(!swivelTalon.isSpeedValid(swivelSpeed)){
      swivelTalon.setSpeed(0);
      System.out.println("swivel Talon stopped");
    }
  }

  public int getVerticalMotorPosition(){
    return verticalTalon.getSensorPosition();
  }

  public int getSwivelMotorPosition(){
    return swivelTalon.getSensorPosition();
  }

  private void debug(String s) {
		if (DEBUG) {
			System.out.println("Arm Subsystem: " + s);
		}
  }
}
