package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.util.JetstreamControllerGroup;
import frc.robot.util.JetstreamTalon;
import frc.robot.util.Logger;

public class Elevator extends Subsystem {

  private static final double MIN = Double.MIN_VALUE;
  private static final double MAX = Double.MAX_VALUE;
  private static final double SPEED = 0.5;

  // CHECK
  public static int TOP_HATCH_POSITION = 1000;
  public static int MID_HATCH_POSITION = 700;
  public static int LOW_HATCH_POSITION = 400;
  public static int DISTANCE_BETWEEN_HATCH = 300;

  public static int TOP_CARGO_POSITION = 1050;
  public static int MID_CARGO_POSITION = 750;
  public static int LOW_CARGO_POSITION = 450;
  public static int DISTANCE_BETWEEN_CARGO = 300;

  public int currentDistance;

  private Logger logger = new Logger(Elevator.class.getName());
  private JetstreamTalon leftTalon;
  private JetstreamTalon rightTalon;
  private JetstreamControllerGroup group;

  public Elevator() {
    super();
    logger.detail("constructor");
    leftTalon = new JetstreamTalon(RobotMap.ELEVATOR_LEFT_TALON, MIN, MAX);
    rightTalon = new JetstreamTalon(RobotMap.ELEVATOR_RIGHT_TALON, MIN, MAX);
    group = new JetstreamControllerGroup(leftTalon, rightTalon, MIN, MAX);
  }

  public void elevatorMove(double speed) {
    logger.info("elevatorMove speed: " + speed);
    group.setSpeed(speed);
  }

  // QUESTION: WHEN ELEVATOR IS STOPPED, SHOULD IT MOVE DOWN BY GRAVITY OR STAY AT THAT POSITION?
  public void elevatorVoid(){
    logger.info("elevatorVoid called");
    group.setSpeed(0);
  }

  // DOES THERE NEED TO BE ALLOWANCE?
  // ALSO DOES THAT WORK OR SHOULD THE COMMAND SPECIFY GOING UP OR DOWN AND THEN CALL THE RESPECTIVE
  // FUNCTION FROM HERE
  // JUST KIDDING I WANT TO TALK ABOUT THIS -- DETERMINE BEST EFFICIENCY
  public void goToPosition(int position){
    logger.info("goToPosition called");
    this.currentDistance = group.getSensorPosition();
    while(currentDistance < position){
      group.setSpeed(SPEED);
    }
    while(currentDistance > position){
      group.setSpeed(-SPEED);
    }
  }

  // SAME NOTES AS ABOVE
  public void goToHigherIncrement(){
    logger.info("goToHigherIncrement called  isHatchMode: " + Robot.isHatchMode);
    if(Robot.isHatchMode){}
  }

  @Override
  public void initDefaultCommand() {
  }
}
