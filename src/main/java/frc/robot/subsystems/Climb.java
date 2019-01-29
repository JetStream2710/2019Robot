package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Climb extends Subsystem {

  private static final boolean DEBUG = false;

  private WPI_VictorSPX frontLeftVictor = null;
  private WPI_VictorSPX frontRightVictor = null;
  private WPI_VictorSPX backVictor = null;

  private SpeedControllerGroup group;

  private double speed = 0;

  public Climb(){
    frontLeftVictor = new WPI_VictorSPX(RobotMap.CLIMB_FRONT_LEFT_VICTOR);
    frontRightVictor = new WPI_VictorSPX(RobotMap.CLIMB_FRONT_RIGHT_VICTOR);
    backVictor = new WPI_VictorSPX(RobotMap.CLIMB_BACK_VICTOR);

    frontLeftVictor.setNeutralMode(NeutralMode.Brake);
    frontRightVictor.setNeutralMode(NeutralMode.Brake);
    backVictor.setNeutralMode(NeutralMode.Brake);

    frontLeftVictor.setSafetyEnabled(false);
    frontRightVictor.setSafetyEnabled(false);
    backVictor.setSafetyEnabled(false);

    group = new SpeedControllerGroup(frontLeftVictor, frontRightVictor, backVictor);

    debug("constructor");
  }

  public void climbMech(){
    group.set(speed);
  }

  @Override
  public void initDefaultCommand() {
  }

  private void debug(String s) {
		if (DEBUG) {
			System.out.println("Climb Subsystem: " + s);
		}
	}
}
