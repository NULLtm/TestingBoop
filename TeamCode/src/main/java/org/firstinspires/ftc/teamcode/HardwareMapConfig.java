package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

public abstract class HardwareMapConfig {

    private HardwareMap internalMap;
    public boolean isAutonomous;
    public RobotInstance robot;
    /**
     * Add hardware components to this method in any extension of this class
     */
    public abstract void initializeMap();
    public abstract void setMotorDirection();
    public abstract void setMotorMode();
    public abstract void setStartingPositions();

    public HardwareMapConfig(HardwareMap internalMap, boolean isAutonomous, RobotInstance robot){
        this.internalMap = internalMap;
        this.robot = robot;
        this.isAutonomous = isAutonomous;
        initializeMap();
    }

    public HardwareMap getInternalMap(){
        return internalMap;
    }

    public abstract void setMotorBrakeMode();
}
