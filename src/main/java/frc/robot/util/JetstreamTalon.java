package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class JetstreamTalon {

  private Logger logger = new Logger(JetstreamTalon.class.getName());

  WPI_TalonSRX talon;
  private double min;
  private double max;

  public JetstreamTalon(int port, double min, double max) {
    logger.detail("constructor");
    talon = new WPI_TalonSRX(port);
    this.min = min;
    this.max = max;
    talon.setSafetyEnabled(false);
    talon.setNeutralMode(NeutralMode.Brake);
    talon.setSelectedSensorPosition(0);
  }

  //CAN'T I MAKE "isCurrentSpeedValid()" AND "isSpeedValid(double speed)" into one method?
  public boolean isCurrentSpeedValid(){
    return isSpeedValid(getSpeed());
  }

  public boolean isSpeedValid(double speed) {
    double position = talon.getSelectedSensorPosition();
    logger.info("isSpeedValid speed: " + speed + " position: " + position);
    return !(position > max && speed > 0) && !(position < min && speed < 0);
  }

  public void setSpeed(double speed) {
    logger.info("setSpeed speed: " + speed);
    if (isSpeedValid(speed)) {
      talon.set(speed);
    }
  }

  public double getSpeed() {
    double speed = talon.get();
    return speed;
  }

  public int getSensorPosition() {
    return talon.getSelectedSensorPosition();
  }
}
