package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JetstreamVictor implements SpeedController {

  public static final double MAX_VOLTAGE = 12.0;

  private Logger logger = new Logger(JetstreamVictor.class.getName());

  private WPI_VictorSPX victor;
  private String name;
  private int minSpeed;
  private int maxSpeed;

  public JetstreamVictor(String name, int id, double minSpeed, double maxSpeed) {
    logger.info(String.format("constructor: %s [%d]", name, id));
    victor = new WPI_VictorSPX(id);
    victor.setSafetyEnabled(false);
    victor.setNeutralMode(NeutralMode.Brake);
    victor.configVoltageCompSaturation(MAX_VOLTAGE);
    victor.enableVoltageCompensation(true);
  }

  public void sendTelemetry() {
    SmartDashboard.putNumber(String.format("%s [%d] output:", name, victor.getDeviceID()), victor.getMotorOutputPercent());
    SmartDashboard.putNumber(String.format("%s [%d] bus voltage:", name, victor.getDeviceID()), victor.getBusVoltage());
    SmartDashboard.putNumber(String.format("%s [%d] motor voltage:", name, victor.getDeviceID()), victor.getMotorOutputVoltage());
    SmartDashboard.putNumber(String.format("%s [%d] temperature:", name, victor.getDeviceID()), victor.getTemperature());
    SmartDashboard.putNumber(String.format("%s [%d] firmware version:", name, victor.getDeviceID()), victor.getFirmwareVersion());
  }

  // SpeedController functions

  @Override
  public void set(double speed) {
    if (speed > maxSpeed) {
      speed = maxSpeed;
    }
    if (speed < minSpeed) {
      speed = minSpeed;
    }
    logger.info(String.format("%s [%d] set speed: %f", name, victor.getDeviceID(), speed));
    victor.set(speed);
  }

  @Override
  public double get() {
    return victor.get();
  }

  @Override
  public void setInverted(boolean isInverted) {
    logger.info(String.format("%s [%d] setInverted isInverted: %b", name, victor.getDeviceID(), isInverted));
    victor.setInverted(isInverted);
  }

  @Override
  public boolean getInverted() {
    return victor.getInverted();
  }

  @Override
  public void disable() {
    logger.info(String.format("%s [%d] disable", name, victor.getDeviceID()));
    victor.disable();
  }

  @Override
  public void stopMotor() {
    logger.info(String.format("%s [%d] stopMotor", name, victor.getDeviceID()));
    victor.stopMotor();
  }

  @Override
  public void pidWrite(double output) {
    logger.info(String.format("%s [%d] pidWrite output: ", name, victor.getDeviceID(), output));
    victor.pidWrite(output);
  }
}
