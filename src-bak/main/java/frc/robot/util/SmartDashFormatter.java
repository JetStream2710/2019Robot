package frc.robot.util;

import java.io.*;

public class SmartDashFormatter {
    public static final int LINESIZE = 21;
    public static void main(String[] args) throws Exception {
        System.out.println("Running...");
        String output = createDash();
        FileOutputStream fos = new FileOutputStream("c:\\Users\\FRCTeam2710Jetstream\\Desktop\\smartdash.xml");
        fos.write(output.getBytes());
        fos.flush();
        fos.close();
    }

    public static String[] motorNames = {
        "Swivel Talon [12]",
        "Arm Talon [13]",
        "Elevator Talon [2]",
        "Drivetrain Right Talon [15]",
        "Drivetrain Left Talon [0]"
    };

    public static String[] typeNames = {
        "output",
        "bus voltage",
        "motor voltage",
        "temperature",
        "firmware version",
        "position",
        "velocity"
    };

    public static String createDash() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<xml version=\"1.0\">\n");

        //////////////////////////////////
        // dashboard
        //////////////////////////////////
        sb.append("<dashboard>\n");

        for (int i=0; i<motorNames.length; i++) {
            for (int j=0; j<typeNames.length; j++) {
                sb.append(String.format("<widget field=\"%s %s:\" type=\"Number\" class=\"edu.wpi.first.smartdashboard.gui.elements.TextBox\">\n", motorNames[i], typeNames[j]));
                sb.append(String.format("<location y=\"%d\" x=\"%d\"/>\n", j * 20 + (150 * (i / 3)), (i % 3) * 300));
                sb.append(String.format("</widget>\n"));
            }

            sb.append("\n");
        }

        sb.append("</dashboard>\n");

        //////////////////////////////////
        // livewindow
        //////////////////////////////////


        sb.append("</xml>");

        return sb.toString();
    }
}