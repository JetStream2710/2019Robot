package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamVictor;

public class Climb extends Subsystem {

  private static final boolean DEBUG = false;

  private JetstreamVictor frontLeftVictor = null;
  private JetstreamVictor frontRightVictor = null;
  private JetstreamVictor backVictor = null;

  private SpeedControllerGroup frontGroup;

  public Climb(){
    frontLeftVictor = new JetstreamVictor(RobotMap.CLIMB_FRONT_LEFT_VICTOR);
    frontRightVictor = new JetstreamVictor(RobotMap.CLIMB_FRONT_RIGHT_VICTOR);
    backVictor = new JetstreamVictor(RobotMap.CLIMB_BACK_VICTOR);

    frontGroup = new SpeedControllerGroup(new WPI_VictorSPX(frontLeftVictor.getPort()), 
      new WPI_VictorSPX(frontRightVictor.getPort()));

    debug("constructor");
  }

  // Did we want to be able to control movement of the victors independently?

  public void setFrontMotorSpeed(double speed){
    frontGroup.set(speed);
    debug("setFrontMotorSpeed speed: " + speed);
  }

  public void setBackMotorSpeed(double speed){
    backVictor.setSpeed(speed);
    debug("setBackMotorSpeed speed : " + speed);
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
