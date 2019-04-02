package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ClimbCommand;
import frc.robot.util.Logger;

public class Climb extends Subsystem {

  public static final double MOVE_SPEED = 0.7;
  public static final double CLIMB_SPEED = 0.5;
  public static final double FRONT_CLIMB_UP = 0.63;
//  private Logger logger = new Logger(Climb.class.getName());

  private WPI_VictorSPX frontVictor;
  private WPI_VictorSPX backVictor;
  private WPI_VictorSPX moveVictor;

  public Climb() {
    super();
  //  logger.detail("constructor");

    frontVictor = new WPI_VictorSPX(RobotMap.CLIMB_FRONT_VICTOR);
    backVictor = new WPI_VictorSPX(RobotMap.CLIMB_BACK_VICTOR);
    moveVictor = new WPI_VictorSPX(RobotMap.CLIMB_MOVE_VICTOR);

    frontVictor.setSafetyEnabled(false);
    backVictor.setSafetyEnabled(false);
    moveVictor.setSafetyEnabled(false);

    frontVictor.setNeutralMode(NeutralMode.Brake);
    backVictor.setNeutralMode(NeutralMode.Brake);
    moveVictor.setNeutralMode(NeutralMode.Brake);
  }

  public void climbFrontExtend(double climbSpeed){
  //  logger.info("climbFrontExtend called");
    frontVictor.set(climbSpeed);
  }

  public void climbRearExtend(double climbSpeed){
  //  logger.info("climbRearExtend called");
    backVictor.set(climbSpeed);
  }

  public void moveForward(){
  //  logger.info("moveForward called");
    moveVictor.set(-MOVE_SPEED);
  }

  public void moveBackward(){
  //  logger.info("moveBackward called");
    moveVictor.set(MOVE_SPEED);
  }

  public void moveStop(){
    moveVictor.set(0);
  }

  public void setFrontMotorSpeed(double speed) {
  //  logger.info("setFrontMotorSpeed speed: " + speed);
    frontVictor.set(speed);
  }

  public void setBackMotorSpeed(double speed) {
  //  logger.info("setBackMotorSpeed speed : " + speed);
    backVictor.set(speed);
  }

  public void climbUp(){
    frontVictor.set(FRONT_CLIMB_UP);
    backVictor.set(CLIMB_SPEED);
  }

  public void climbDown(){
    frontVictor.set(-FRONT_CLIMB_UP);
    backVictor.set(-CLIMB_SPEED);
  }

  public void climbStop(){
    frontVictor.set(0);
    backVictor.set(0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ClimbCommand());
  }
}
