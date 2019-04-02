package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class FollowLineStop extends Command {
    public FollowLineStop() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        Robot.isFollowingLine = false;
        Robot.isAuto = false;
    }

    @Override
    public void execute(){
    }

    @Override
    public boolean isFinished() {
          return true;
	}
	
    @Override
	protected void interrupted() {
		end();
    }
}