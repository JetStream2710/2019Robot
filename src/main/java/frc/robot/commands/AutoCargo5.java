package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCargo5 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoCargo5() {
    addSequential(new DriveForwardSeconds(13300,0.5));
    // Acquire Line
    // Install Hatch
    addSequential(new DriveForwardSeconds(6000,-0.5));
    addSequential(new TurnDegrees(45,0.6));
    addSequential(new DriveForwardSeconds(6000,0.5));
    // Stop
    // Win?
  }
}
