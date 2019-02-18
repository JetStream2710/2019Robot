package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class TestAuto extends CommandGroup {
  public TestAuto() {
    addSequential(new MoveElevator(-35000));
    addSequential(new WaitCommand(5));
    addSequential(new MoveElevator(-10000));
    addSequential(new WaitCommand(5));
    addSequential(new MoveElevator(-35000));
    addSequential(new WaitCommand(5));
    addSequential(new MoveElevator(-10000));
    addSequential(new WaitCommand(5));
  }
}
