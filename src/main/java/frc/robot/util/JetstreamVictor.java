package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class JetstreamVictor {

  private Logger logger = new Logger(JetstreamVictor.class.getName());

  WPI_VictorSPX victor;

  public JetstreamVictor(int port) {
    logger.detail("constructor");
    victor = new WPI_VictorSPX(port);
    victor.setSafetyEnabled(false);
    victor.setNeutralMode(NeutralMode.Brake);
  }

  public void setSpeed(double speed) {
    logger.info("setSpeed speed: " + speed);
    victor.set(speed);
  }

  public double getSpeed() {
    return victor.get();
  }
}
