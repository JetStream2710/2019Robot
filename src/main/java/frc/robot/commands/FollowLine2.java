package frc.robot.commands;

//import org.graalvm.compiler.nodes.calc.LeftShiftNode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.PixyI2CDriver;
import frc.robot.util.PixyLine;

/**
 * Assumes that robot's center is near the ray
 * of the line and that the line is in view
 * 
 * Attempts to turn until the top point
 * 
 * of tape intersects the ideal line
 */ 
public class FollowLine2 extends Command {
    /**
     * References to "ideal" represent a theoretical line extending from
     * the center of the Robot perpendicular to the front of the chassis; 
     * 
     * This is our goal to line up the tape with
     */

    // If driver is moving:
    private boolean driverMoving = false;

    // Viewport Constants
    public static final int MAX_X = 78;
    public static final int MAX_Y = 51;

    private final double minSpeed = 0.45;
    private final double maxSpeed = 0.6;
    private final double speedRange = maxSpeed - minSpeed;

    private PixyLine line;
    private int lastLineId;

    public FollowLine2() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {
        System.out.println("FollowLine2: init");
        lastLineId = 0;
        driverMoving = false;
        Robot.isAuto = true;
    }

    @Override
    public void execute(){
        double driverRotateValue = Robot.oi.drivestick.getRawAxis(OI.DRIVER_MOVE_AXIS);
        if(driverRotateValue > 0.1) {
            driverMoving = true;
            System.out.println("really driver moving...");
            return;
        }

        // Returns if there is no line to avoid NullPointerException(s)
        line = Robot.pixy.getLatestLine();
        if (line == null) {
            return;
        }
        if (lastLineId != 0) {
            if (line.getId() != lastLineId) {
                System.out.println("line id's do not match: " + line.getId() + " " + lastLineId);
                driverMoving = true;
                return;
            }
        }
        lastLineId = line.getId();
        
//        int averageX = (line.getLowerX() + line.getUpperX()) / 2;
        int averageX = line.getLowerX();
        double offset = averageX - 39;
        double turn = 0.05 * offset;
        System.out.println("Line: " + line.toString());
        System.out.println("ave: " + averageX + " offset: " + offset + " turn: " + turn);
        Robot.drivetrain.arcadeDrive(-0.5, turn);
    }

    @Override
    public boolean isFinished() {
        if (driverMoving) {
            System.out.println("exit: driver moving");
            return true;
        }
        if (line == null) {
          return true;
        }
        if (line.getUpperY() >= MAX_Y - 4) {
            System.out.println("exit: line limit reached");
            return true;
        }
        return false;
    }

    @Override
	protected void end() {
        System.out.println("calling end");
        Robot.drivetrain.arcadeDrive(0.0, 0.0);
        lastLineId = 0;
        Robot.isAuto = false;
	}
	
    @Override
	protected void interrupted() {
		end();
    }
}