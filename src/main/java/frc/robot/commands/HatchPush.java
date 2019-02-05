package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class HatchPush extends Command {

  private Logger logger = new Logger(HatchPush.class.getName());
  private long time;
  private long timePassed;

  public HatchPush() {
    logger.detail("constructor");
    requires(Robot.hatch);
  }

  @Override
  protected void initialize() {
    logger.info("initialize");
    time = System.currentTimeMillis();
    logger.detail("time: " + time);
  }

  // SHOULD LOGGER RECORD timePassed
  // NEEDS TO BE CHECKED -- just want to retract motor all the way
  @Override
  protected void execute() {
    logger.info("execute");
    this.timePassed = System.currentTimeMillis() - time;
    if(timePassed < 3){
      Robot.hatch.hatchPush();
    }
    else if(timePassed < 6){
      Robot.hatch.hatchIn();
    }
  }

  // SHOULD LOGGER RECORD timePassed
  @Override
  protected boolean isFinished() {
    logger.info("finished");
    if(timePassed == 6){
      return true;
    } else{
      return false;
    }
  }

  @Override
  protected void end() {
    logger.info("end");
  }

  // WHAT IF I WANT TO CHANGE THIS SO THAT IT JUST GOES TO WHATEVER IT'S INTERRUPTED BY 
  @Override
  protected void interrupted() {
    logger.warning("interrupted");
    end();
  }
}
