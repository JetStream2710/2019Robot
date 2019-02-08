package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class JetstreamTalon implements SpeedController {

  public static final double MAX_VOLTAGE = 12.0;

  private Logger logger = new Logger(JetstreamTalon.class.getName());

  private WPI_TalonSRX talon;
  private Encoder encoder;
  private int minPosition;
  private int maxPosition;

  public JetstreamTalon(int id, Encoder encoder, int minPosition, int maxPosition) {
    logger.detail("constructor id: " + id);
    talon = new WPI_TalonSRX(id);
    this.encoder = encoder;
    this.minPosition = minPosition;
    this.maxPosition = maxPosition;
    talon.setSafetyEnabled(false);
    talon.setNeutralMode(NeutralMode.Brake);
    talon.configVoltageCompSaturation(MAX_VOLTAGE);
    talon.enableVoltageCompensation(true);
    if (encoder != null) {
      encoder.reset();
    }
  }

  public int getPosition() {
    return encoder == null ? 0 : encoder.get();
  }

  public boolean isValidSpeed(double speed) {
    if (encoder != null) {
      double position = getPosition();
      if (speed > 0) {
        return position < maxPosition;
      }
      if (speed < 0) {
        return position > minPosition;
      }
    }
    return true;
  }

  // SpeedController functions

  @Override
  public void set(double speed) {
    if (!isValidSpeed(speed)) {
      logger.warning(String.format("[%d] INVALID set speed: %f", talon.getDeviceID(), speed));
      talon.set(0);
      return;
    }
    logger.info(String.format("[%d] set speed: %f", talon.getDeviceID(), speed));
    talon.set(speed);
  }

  @Override
  public double get() {
    return talon.get();
  }

  @Override
  public void setInverted(boolean isInverted) {
    logger.info(String.format("[%d] setInverted isInverted: %b", talon.getDeviceID(), isInverted));
    talon.setInverted(isInverted);
  }

  @Override
  public boolean getInverted() {
    return talon.getInverted();
  }

  @Override
  public void disable() {
    logger.info(String.format("[%d] disable", talon.getDeviceID()));
    talon.disable();
  }

  @Override
  public void stopMotor() {
    logger.info(String.format("[%d] stopMotor", talon.getDeviceID()));
    talon.stopMotor();
  }

  @Override
  public void pidWrite(double output) {
    logger.info(String.format("[%d] pidWrite output: ", talon.getDeviceID(), output));
    talon.pidWrite(output);
  }
}
