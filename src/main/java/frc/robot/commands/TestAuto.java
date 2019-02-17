package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class TestAuto extends CommandGroup {
  public TestAuto() {
    addSequential(new MoveArm(3500));
    addSequential(new WaitCommand(5));
    addSequential(new MoveArm(1000));
    addSequential(new WaitCommand(5));
    addSequential(new MoveArm(3500));
    addSequential(new WaitCommand(5));
    addSequential(new MoveArm(1000));
    addSequential(new WaitCommand(5));
  }
}
