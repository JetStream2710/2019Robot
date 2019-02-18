package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamVictor;
import frc.robot.util.Logger;

public class Cargo extends Subsystem {

  public static final double SPEED_IN = -0.5;
  public static final double SPEED_OUT = 0.5;

  private Logger logger = new Logger(Cargo.class.getName());
//  private JetstreamVictor victor;
  private WPI_VictorSPX victor;

  public Cargo() {
    super();
    logger.detail("constructor");

//    victor = new JetstreamVictor("Cargo Victor", RobotMap.CARGO_VICTOR, SPEED_IN, SPEED_OUT);
    victor = new WPI_VictorSPX(RobotMap.CARGO_VICTOR);
    victor.setSafetyEnabled(false);
    victor.setNeutralMode(NeutralMode.Brake);
    victor.configVoltageCompSaturation(12);
    victor.enableVoltageCompensation(true);
  }

  // CHECK THESE VALUES
  public void cargoIntake() {
    logger.info("cargoIntake speed: " + SPEED_IN);
    victor.set(SPEED_IN);
  }

  public void cargoOuttake() {
    logger.info("cargoOuttake speed: " + SPEED_OUT);
    victor.set(SPEED_OUT);
  }

  public void cargoStop() {
    logger.info("cargoStop");
    victor.set(0);
  }

  @Override
  public void initDefaultCommand() {
  }
}
