package frc.robot.util;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JetstreamTalon implements SpeedController {

  public static final double MAX_VOLTAGE = 12.0;

  private Logger logger = new Logger(JetstreamTalon.class.getName());

  private WPI_TalonSRX talon;
  private String name;
  private boolean hasEncoder;
  private int minPosition;
  private int maxPosition;
  private double maxSpeed;
  private double minSpeed;
  private boolean invertPosition;
  private Faults faults;

  public JetstreamTalon(String name, int id, boolean hasEncoder, int minPosition, int maxPosition, double maxSpeed, boolean invertPosition) {
    logger.info(String.format("constructor: %s [%d]", name, id));
    talon = new WPI_TalonSRX(id);
    this.name = name;
    this.hasEncoder = hasEncoder;
    this.minPosition = minPosition;
    this.maxPosition = maxPosition;
    this.maxSpeed = maxSpeed;
    this.minSpeed = -maxSpeed;
    this.invertPosition = invertPosition;
    talon.setSafetyEnabled(false);
    talon.setNeutralMode(NeutralMode.Brake);
    talon.configVoltageCompSaturation(MAX_VOLTAGE);
    talon.enableVoltageCompensation(true);
    if (hasEncoder) {
      talon.setSelectedSensorPosition(0);
    }
    sendTelemetry();
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
    if (name == null) {
      return;
    }
    SmartDashboard.putNumber(String.format("%s [%d] output:", name, talon.getDeviceID()), talon.getMotorOutputPercent());
    SmartDashboard.putNumber(String.format("%s [%d] bus voltage:", name, talon.getDeviceID()), talon.getBusVoltage());
    SmartDashboard.putNumber(String.format("%s [%d] motor voltage:", name, talon.getDeviceID()), talon.getMotorOutputVoltage());
    SmartDashboard.putNumber(String.format("%s [%d] temperature:", name, talon.getDeviceID()), talon.getTemperature());
    SmartDashboard.putNumber(String.format("%s [%d] firmware version:", name, talon.getDeviceID()), talon.getFirmwareVersion());
//    talon.getFaults(faults);
//    SmartDashboard.putBoolean(String.format("%s [%d] hardware failure:", name, talon.getDeviceID()), faults.HardwareFailure);
//    SmartDashboard.putBoolean(String.format("%s [%d] under voltage:", name, talon.getDeviceID()), faults.UnderVoltage);
    if (hasEncoder) {
      SmartDashboard.putNumber(String.format("%s [%d] position:", name, talon.getDeviceID()), getPosition());
      SmartDashboard.putNumber(String.format("%s [%d] velocity:", name, talon.getDeviceID()), getVelocity());
//      SmartDashboard.putBoolean(String.format("%s [%d] sensor out of phase:", name, talon.getDeviceID()), faults.SensorOutOfPhase);
    }
  }

  // SpeedController functions

  @Override
  public void set(double speed) {
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
    logger.warning(String.format("[%d] disable", talon.getDeviceID()));
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
