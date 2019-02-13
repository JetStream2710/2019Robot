package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCargo3 extends CommandGroup {
  public AutoCargo3() {
    addSequential(new DriveDistance(8600,0.5));
    addSequential(new TurnDegrees(-65,0.6));
    addSequential(new DriveDistance(5400,0.5));
    addSequential(new TurnDegrees(65,0.6));
    addSequential(new DriveDistance(8600,0.5));
    addSequential(new TurnDegrees(90,0.6));
    addSequential(new DriveDistance(3000,0.5));
    // Acquire Line
    // Install Hatch
    // Win?
  }
}
