package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;

public class JetstreamVictor implements SpeedController {

  public static final double MAX_VOLTAGE = 12.0;

  private Logger logger = new Logger(JetstreamVictor.class.getName());

  private WPI_VictorSPX victor;

  public JetstreamVictor(int id) {
    logger.detail("constructor id: " + id);
    victor = new WPI_VictorSPX(id);
    victor.setSafetyEnabled(false);
    victor.setNeutralMode(NeutralMode.Brake);
    victor.configVoltageCompSaturation(MAX_VOLTAGE);
    victor.enableVoltageCompensation(true);
  }

  // SpeedController functions

  @Override
  public void set(double speed) {
    logger.info(String.format("[%d] set speed: %f", victor.getDeviceID(), speed));
    victor.set(speed);
  }

  @Override
  public double get() {
    return victor.get();
  }

  @Override
  public void setInverted(boolean isInverted) {
    logger.info(String.format("[%d] setInverted isInverted: %b", victor.getDeviceID(), isInverted));
    victor.setInverted(isInverted);
  }

  @Override
  public boolean getInverted() {
    return victor.getInverted();
  }

  @Override
  public void disable() {
    logger.info(String.format("[%d] disable", victor.getDeviceID()));
    victor.disable();
  }

  @Override
  public void stopMotor() {
    logger.info(String.format("[%d] stopMotor", victor.getDeviceID()));
    victor.stopMotor();
  }

  @Override
  public void pidWrite(double output) {
    logger.info(String.format("[%d] pidWrite output: ", victor.getDeviceID(), output));
    victor.pidWrite(output);
  }
}
