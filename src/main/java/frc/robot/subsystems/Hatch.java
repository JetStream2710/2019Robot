package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Hatch extends Subsystem {

  private static final boolean DEBUG = false;

  private Solenoid solenoidOn;
  private Solenoid solenoidOff;

  public Hatch(){
    super();
    solenoidOn = new Solenoid(RobotMap.PCM, RobotMap.HATCH_SOLENOID_ON);
    solenoidOff = new Solenoid(RobotMap.PCM, RobotMap.HATCH_SOLENOID_OFF);

    debug("constructor");
  }

  public void hatchIn() {
    solenoidOn.set(true);
    solenoidOff.set(false);
    
    debug("hatchIn called");
  }

  public void hatchOut(){
    solenoidOn.set(false);
    solenoidOff.set(true);
    debug("hatchOut called");
  }

  @Override
  public void initDefaultCommand() {
  }

  private void debug(String s) {
		if (DEBUG) {
			System.out.println("Hatch Subsystem: " + s);
		}
	}

}
