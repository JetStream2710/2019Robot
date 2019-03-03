/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Drives forward and places hatch
 */
public class Auto1 extends CommandGroup {
  
  public Auto1() {
    addSequential(new DriveDistance(110, 0.5));
    addSequential(new WaitCommand(0.5));
    addSequential(new HatchPush());
    addSequential(new DriveDistance(-10, 0.5));
  }
}
