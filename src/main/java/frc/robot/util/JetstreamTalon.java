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
  private double maxSpeed;
  private double minSpeed;
  private boolean inverted;

  public JetstreamTalon(int id, Encoder encoder, int minPosition, int maxPosition) {
    this(id, encoder, minPosition, maxPosition, 1, false);
  }

  public JetstreamTalon(int id, Encoder encoder, int minPosition, int maxPosition, double maxSpeed, boolean inverted) {
    logger.detail("constructor id: " + id);
    talon = new WPI_TalonSRX(id);
    this.encoder = encoder;
    this.minPosition = minPosition;
    this.maxPosition = maxPosition;
    this.maxSpeed = maxSpeed;
    this.minSpeed = -maxSpeed;
    this.inverted = inverted;
    talon.setSafetyEnabled(false);
    talon.setNeutralMode(NeutralMode.Brake);
    talon.configVoltageCompSaturation(MAX_VOLTAGE);
    talon.enableVoltageCompensation(true);
    if (encoder != null) {
      encoder.reset();
    } else {
      talon.setSelectedSensorPosition(0);
      System.out.println("TEMP: id=" + id + " position:" + talon.getSelectedSensorPosition());
    }
  }

  public int getPosition() {
    return encoder == null ? 0 : encoder.get();
  }

  public boolean isValidSpeed(double speed) {
    double position = getPosition();
    if (inverted) {
      if (speed > 0) {
        return position > minPosition;
      }
      if (speed < 0) {
        return position < maxPosition;
      }
    } else {
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
    if (inverted) {
      speed *= -1;
    }
    if (!isValidSpeed(speed)) {
      logger.warning(String.format("[%d] INVALID set speed: %f", talon.getDeviceID(), speed));
      talon.set(0);
      return;
    }
    if (speed > maxSpeed) {
      speed = maxSpeed;
    }
    if (speed < minSpeed) {
      speed = minSpeed;
    }
    logger.info(String.format("[%d] set speed: %f", talon.getDeviceID(), speed));
    talon.set(speed);
  }

  @Override
  public double get() {
    return inverted ? -talon.get() : talon.get();
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
