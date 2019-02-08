package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;

public class Arm extends Subsystem {

  public static final double VERTICAL_MAX = Double.MAX_VALUE;
  public static final double VERTICAL_MIN = Double.MIN_VALUE;
  public static final double SWIVEL_MAX = Double.MAX_VALUE;
  public static final double SWIVEL_MIN = Double.MIN_VALUE;
  public static final double CARGO_DOWN = 0;
  public static final double CARGO_UP = 0;
  public static final double HATCH_DOWN = 0;
  public static final double HATCH_UP = 0;
  public static final double SPEED = 0.5;

  private Logger logger = new Logger(Arm.class.getName());
  private JetstreamTalon verticalTalon;
  private JetstreamTalon swivelTalon;

  public Arm() {
    super();
    logger.detail("constructor");

    verticalTalon = new JetstreamTalon(RobotMap.ARM_VERTICAL_TALON, VERTICAL_MIN, VERTICAL_MAX);
    swivelTalon = new JetstreamTalon(RobotMap.ARM_SWIVEL_TALON, SWIVEL_MIN, SWIVEL_MAX);
  }

  // in subsystem
  public void armCargoUp(){

  }

  public void armCargoDown(){

  }

  public void armHatchUp(){

  }

  public void armHatchDown(){
    
  }

  public void moveVerticalArm(double speed) {
    logger.info("moveVerticalArm speed: " + speed);
    verticalTalon.setSpeed(speed);
  }

  public void moveSwivelArm(double speed) {
    logger.info("moveSwivelArm speed: " + speed);
    swivelTalon.setSpeed(speed);
  }

  // MAKE SURE TO TEST THIS BECAUSE IT MIGHT BE BACKWARDS
  public void moveTogether(double speed) {
    if (verticalTalon.isSpeedValid(speed) && swivelTalon.isSpeedValid(-speed)) {
      logger.info("moveTogether speed: " + speed);
      verticalTalon.setSpeed(speed);
      swivelTalon.setSpeed(-speed);
    } else {
      logger.warning("moveTogether stopped");
      verticalTalon.setSpeed(0);
      swivelTalon.setSpeed(0);
    }
  }

  @Override
  public void initDefaultCommand() {
  }

  public void checkAllMotors() {
    if (!verticalTalon.isCurrentSpeedValid()) {
      verticalTalon.setSpeed(0);
      logger.warning("Vertical Talon stopped");
    }
    double swivelSpeed = swivelTalon.getSpeed();
    if (!swivelTalon.isSpeedValid(swivelSpeed)) {
      swivelTalon.setSpeed(0);
      logger.warning("Swivel Talon stopped");
    }
  }

  public int getVerticalMotorPosition() {
    return verticalTalon.getSensorPosition();
  }

  public int getSwivelMotorPosition() {
    return swivelTalon.getSensorPosition();
  }
}
