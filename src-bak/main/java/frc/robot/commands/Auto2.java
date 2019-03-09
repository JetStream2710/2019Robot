/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives forward to cross HAB Line
 */
public class Auto2 extends CommandGroup {

  public Auto2() {
    addSequential(new DriveDistance(110, 0.5));
  }
}
