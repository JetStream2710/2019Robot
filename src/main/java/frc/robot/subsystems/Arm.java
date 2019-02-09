package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;

public class Arm extends Subsystem {

  public static final int VERTICAL_MAX = Integer.MAX_VALUE;
  public static final int VERTICAL_MIN = Integer.MIN_VALUE;
  public static final int SWIVEL_MAX = Integer.MAX_VALUE;
  public static final int SWIVEL_MIN = Integer.MIN_VALUE;
  public static final double CARGO_DOWN = 0;
  public static final double CARGO_UP = 0;
  public static final double HATCH_DOWN = 0;
  public static final double HATCH_UP = 0;
  public static final double SPEED = 0.5;

  private Logger logger = new Logger(Arm.class.getName());
  private JetstreamTalon verticalTalon;
  private JetstreamTalon swivelTalon;
  private Encoder verticalEncoder;
  private Encoder swivelEncoder;
  private double currentPosition;

  public Arm() {
    super();
    logger.detail("constructor");

    verticalEncoder = new Encoder(RobotMap.ARM_VERTICAL_ENCODER_A, RobotMap.ARM_VERTICAL_ENCODER_B);
    swivelEncoder = new Encoder(RobotMap.ARM_SWIVEL_ENCODER_A, RobotMap.ARM_SWIVEL_ENCODER_B);
    verticalTalon = new JetstreamTalon(RobotMap.ARM_VERTICAL_TALON, verticalEncoder, VERTICAL_MIN, VERTICAL_MAX);
    swivelTalon = new JetstreamTalon(RobotMap.ARM_SWIVEL_TALON, swivelEncoder, SWIVEL_MIN, SWIVEL_MAX);
    currentPosition = verticalEncoder.getDistance();
  }

  // in subsystem
  public void armCargoUp(){
    logger.info("armCargoUp called");
    while(currentPosition < VERTICAL_MAX){
      this.currentPosition = verticalEncoder.getDistance();
      verticalTalon.set(SPEED);
    }
  }

  public void armCargoDown(){
    logger.info("armCargoDown called");
    while(currentPosition < VERTICAL_MIN){
      this.currentPosition = verticalEncoder.getDistance();
      verticalTalon.set(-SPEED);
    }
  }

  public void armHatchUp(){
    logger.info()
  }

  public void armHatchDown(){
    
  }

  public void moveVerticalArm(double speed) {
    logger.info("moveVerticalArm speed: " + speed);
    verticalTalon.set(speed);
  }

  public void moveSwivelArm(double speed) {
    logger.info("moveSwivelArm speed: " + speed);
    swivelTalon.set(speed);
  }

  // MAKE SURE TO TEST THIS BECAUSE IT MIGHT BE BACKWARDS
  public void moveTogether(double speed) {
    if (verticalTalon.isValidSpeed(speed) && swivelTalon.isValidSpeed(-speed)) {
      logger.info("moveTogether speed: " + speed);
      verticalTalon.set(speed);
      swivelTalon.set(-speed);
    } else {
      logger.warning("moveTogether stopped");
      verticalTalon.set(0);
      swivelTalon.set(0);
    }
  }

  @Override
  public void initDefaultCommand() {
  }
}
