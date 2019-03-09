package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoRightRocket extends CommandGroup {
  public AutoRightRocket() {
    addSequential(new DriveDistance(8600, 0.5));
    addSequential(new TurnDegrees(65, 0.6));
    addSequential(new DriveDistance(10200, 0.5));
    addSequential(new TurnDegrees(-35, 0.6));
  }
}
