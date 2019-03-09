package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;

public class JetstreamTalon implements SpeedController {

  public static final double MAX_VOLTAGE = 12.0;

//  private Logger logger = new Logger(JetstreamTalon.class.getName());

  private WPI_TalonSRX talon;
  private String name;
  private int minPosition;
  private int maxPosition;
  private double maxSpeed;
  private double minSpeed;
  private boolean invertPosition;

  public JetstreamTalon(String name, int id, int minPosition, int maxPosition, double minSpeed, double maxSpeed, boolean invertPosition) {
  //  logger.info(String.format("constructor: %s [%d]", name, id));
    talon = new WPI_TalonSRX(id);
    this.name = name;
    this.minPosition = minPosition;
    this.maxPosition = maxPosition;
    this.minSpeed = minSpeed;
    this.maxSpeed = maxSpeed;
    this.invertPosition = invertPosition;
    talon.setSafetyEnabled(false);
    talon.setNeutralMode(NeutralMode.Coast);
    talon.configVoltageCompSaturation(MAX_VOLTAGE);
    talon.enableVoltageCompensation(true);
    talon.setSelectedSensorPosition(0);
//    sendTelemetry();
  }

  public void reset() {
    talon.setSelectedSensorPosition(0);
  }

  public int getPosition() {
    return invertPosition ? -talon.getSelectedSensorPosition() : talon.getSelectedSensorPosition();
  }

  public double getVelocity() {
    return invertPosition ? -talon.getSelectedSensorVelocity() : talon.getSelectedSensorVelocity();
  }

  public boolean isValidSpeed(double speed) {
    double position = getPosition();
    if (speed > 0) {
      return position < maxPosition;
    }
    if (speed < 0) {
      return position > minPosition;
    }
    return true;
  }

  public void sendTelemetry() {
    SmartDash.put(String.format("%s [%d] output:", name, talon.getDeviceID()), talon.getMotorOutputPercent());
    SmartDash.put(String.format("%s [%d] bus voltage:", name, talon.getDeviceID()), talon.getBusVoltage());
    SmartDash.put(String.format("%s [%d] motor voltage:", name, talon.getDeviceID()), talon.getMotorOutputVoltage());
    SmartDash.put(String.format("%s [%d] temperature:", name, talon.getDeviceID()), talon.getTemperature());
    SmartDash.put(String.format("%s [%d] firmware version:", name, talon.getDeviceID()), talon.getFirmwareVersion());
    SmartDash.put(String.format("%s [%d] position:", name, talon.getDeviceID()), getPosition());
    SmartDash.put(String.format("%s [%d] velocity:", name, talon.getDeviceID()), getVelocity());
  }

  public double getVoltage() {
    return talon.getMotorOutputVoltage();
  }

  // SpeedController functions

  @Override
  public void set(double speed) {
    if (!isValidSpeed(speed)) {
    //  logger.warning(String.format("%s [%d] INVALID set speed: %f", name, talon.getDeviceID(), speed));
      talon.set(0);
      return;
    }
    if (speed > maxSpeed) {
      speed = maxSpeed;
    }
    if (speed < minSpeed) {
      speed = minSpeed;
    }
  //  logger.info(String.format("%s [%d] set speed: %f", name, talon.getDeviceID(), speed));
    talon.set(speed);
  }

  @Override
  public double get() {
    return talon.get();
  }

  @Override
  public void setInverted(boolean isInverted) {
  //  logger.info(String.format("%s [%d] setInverted isInverted: %b", name, talon.getDeviceID(), isInverted));
    talon.setInverted(isInverted);
  }

  @Override
  public boolean getInverted() {
    return talon.getInverted();
  }

  @Override
  public void disable() {
  //  logger.warning(String.format("%s [%d] disable", name, talon.getDeviceID()));
    talon.disable();
  }

  @Override
  public void stopMotor() {
  //  logger.info(String.format("%s [%d] stopMotor", name, talon.getDeviceID()));
    talon.stopMotor();
  }

  @Override
  public void pidWrite(double output) {
  //  logger.info(String.format("%s [%d] pidWrite output: ", name, talon.getDeviceID(), output));
    talon.pidWrite(output);
  }
}
