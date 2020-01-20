package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.official.WABOTOmniDrive;

public class RobotParameters {
    public HardwareMap map = null;
    public Gamepad gamepad1 = null;
    public Gamepad gamepad2 = null;
    public Telemetry console = null;
    public boolean isAutonomous = false;
    public boolean useRevIMU = false;

    // Parameters for vuforia
    public boolean useVuforia = false;
    public String vuforiaKey = null;
    public boolean showVuforiaDisplay = false;
    public boolean isWebcam = false;
}
