package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.Logger;

public class Hatch extends Subsystem {

//  private Logger logger = new Logger(Hatch.class.getName());
  private Solenoid solenoidOn;
  private Solenoid solenoidOff;

  public Hatch() {
    super();
  //  logger.detail("constructor");
    solenoidOn = new Solenoid(RobotMap.PCM_NODE, RobotMap.HATCH_SOLENOID_ON);
    solenoidOff = new Solenoid(RobotMap.PCM_NODE, RobotMap.HATCH_SOLENOID_OFF);
  }

  public void hatchOut() {
  //  logger.info("hatchOut called");
    solenoidOn.set(false);
    solenoidOff.set(true);
  }

  public void hatchIn(){
  //  logger.info("hatchIn called");
    solenoidOn.set(true);
    solenoidOff.set(false);
  }

  @Override
  public void initDefaultCommand() {
  }
}
