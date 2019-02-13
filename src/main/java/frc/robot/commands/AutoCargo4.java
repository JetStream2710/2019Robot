package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCargo4 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoCargo4() {
    addSequential(new DriveDistance(13300,0.5));
    // Acquire Line
    // Install Hatch
    // Win?
  }
}
