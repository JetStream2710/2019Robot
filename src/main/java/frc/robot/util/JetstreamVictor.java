package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class JetstreamVictor {

  private static final boolean DEBUG = false;

  private WPI_VictorSPX victor;

  public JetstreamVictor(int port) {
    victor = new WPI_VictorSPX(port);
    victor.setSafetyEnabled(false);
    victor.setNeutralMode(NeutralMode.Brake);
    victor.setSelectedSensorPosition(0);
    debug("constructor");
  }

  public void setSpeed(double speed) {
    victor.set(speed);
    debug("setSpeed speed: " + speed);
  }

  public double getSpeed() {
    double speed = victor.get();
    debug("getSpeed speed: " + speed);
    return victor.get();
  }

  public int getPort() {
    int port = victor.getDeviceID();
    debug("getPort port: " + port);
    return port;
  }

  // ATTEMPT NUMERO DOS
  public SpeedControllerGroup speedControlGroup(JetstreamVictor victor1, JetstreamVictor victor2) {
    SpeedControllerGroup group;
    WPI_VictorSPX talonA;
    WPI_VictorSPX talonB;

    talonA = new WPI_VictorSPX(victor1.getPort());
    talonB = new WPI_VictorSPX(victor2.getPort());
    group = new SpeedControllerGroup(talonA, talonB);
    return group;
  }

  private void debug(String s) {
    if (DEBUG) {
      System.out.println("Jetstream Victor util: " + s);
    }
  }
}
