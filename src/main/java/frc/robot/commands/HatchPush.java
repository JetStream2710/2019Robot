package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.Logger;

public class HatchPush extends Command {

  private Logger logger = new Logger(HatchPush.class.getName());
  private long time;

  public HatchPush() {
    logger.detail("constructor");
    requires(Robot.hatch);
  }

  //just making it push in and out right after one another (calling commands wise), won't really do the job right?
  //for now the program waits one second before pushing the pneumatics back in but that can be checked
  @Override
  protected void initialize() {
    logger.info("initialize");
    time = System.currentTimeMillis();
    logger.detail("time: " + time);
    Robot.hatch.hatchPush();
    }

  // SHOULD LOGGER RECORD timePassed
  // NEEDS TO BE CHECKED -- just want to retract motor all the way
  @Override
  protected void execute() {
    logger.info("execute");
  }

  @Override
  protected boolean isFinished() {
    logger.info("finished");
    long timePassed = System.currentTimeMillis() - time;
    if(timePassed > 2000){
      return true;
    } else{
      return false;
    }
  }

  @Override
  protected void end() {
    logger.info("end");
    Robot.hatch.hatchIn();
  }

  @Override
  protected void interrupted() {
    logger.warning("interrupted");
    end();
  }
}
