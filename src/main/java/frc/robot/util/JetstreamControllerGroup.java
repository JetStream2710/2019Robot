package frc.robot.util;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class JetstreamControllerGroup{

  private Logger logger = new Logger(JetstreamControllerGroup.class.getName());

  private SpeedControllerGroup group;
  private JetstreamTalon talon;
  private double min;
  private double max;
  
  public JetstreamControllerGroup(JetstreamVictor victorA, JetstreamVictor victorB) {
    logger.detail("victor constructor");
    group = new SpeedControllerGroup(victorA.victor, victorB.victor);
  }

  public JetstreamControllerGroup(JetstreamTalon talonA, JetstreamTalon talonB, double min, double max) {
    logger.detail("talon constructor");
    group = new SpeedControllerGroup(talonA.talon, talonB.talon);
    talon = talonA;
    this.min = min;
    this.max = max;
  }

  public boolean isSpeedValid(double speed) {
    if (talon == null) {
        return true;
    }
    double position = talon.getSensorPosition();
    return !(position > max && speed > 0) && !(position < min && speed < 0);
  }
    
  public void setSpeed(double speed) {
    logger.info("setSpeed speed: " + speed);
    if (isSpeedValid(speed)) {
      group.set(speed);
    }
  }
 
  public double getSpeed() {
    return group.get();
  }

  public int getSensorPosition() {
    return talon.getSensorPosition();
  }
}
